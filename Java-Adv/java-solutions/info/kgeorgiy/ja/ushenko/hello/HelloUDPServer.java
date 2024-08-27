package info.kgeorgiy.ja.ushenko.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.kgeorgiy.java.advanced.hello.NewHelloServer;

public class HelloUDPServer implements NewHelloServer {
    private ExecutorService receivers;
    private ExecutorService senders;

    private final Set<DatagramSocket> sockets = ConcurrentHashMap.newKeySet();

    public HelloUDPServer() {
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println("Usage: HelloUDPServer <port number> <threads number>");
            return;
        }
        HelloUDPServer server = new HelloUDPServer();
        try {
            int port = Integer.parseInt(args[0]);
            int threads = Integer.parseInt(args[1]);
            server.start(port, threads);
        } catch (final NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        }
    }

    @Override
    public void start(int threads, Map<Integer, String> ports) {
        receivers = Executors.newFixedThreadPool((int) Math.ceil( ((double)threads + 1) / 2));
        senders = Executors.newFixedThreadPool((int) Math.ceil( ((double)threads + 1) / 2));
        for (int port : ports.keySet()) {
            try {
                DatagramSocket socket = new DatagramSocket(port);
                receivers.execute(() -> startToListenPort(socket, port, ports.get(port)));
            } catch (final SecurityException | IllegalArgumentException e) {
                throw new RuntimeException("Unable to open a socket %d : %s".formatted(port, e.getMessage()));
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void startToListenPort(DatagramSocket socket, int port, String format) {
        try {
            sockets.add(socket);
            while (!socket.isClosed()) {
                try {
                    DatagramPacket received = new DatagramPacket(
                            new byte[socket.getReceiveBufferSize()],
                            socket.getReceiveBufferSize()
                    );
                    socket.receive(received);
                    senders.execute(() -> sendMessage(socket,received,format));

                } catch (IOException e) {
                    System.err.println("IOException: " + e.getMessage());
                }
            }
        } catch (final SecurityException e) {
            throw new SecurityException("Unable to create socket on port %d".formatted(port));
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Port %d is out of range".formatted(port));
        }
    }
    private static void sendMessage(DatagramSocket socket, DatagramPacket received, String format) {
        DatagramPacket toSend = formDatagramPacket(format, received);
        try {
            socket.send(toSend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static DatagramPacket formDatagramPacket(String format, DatagramPacket received) {
        String messageToSend = Objects.requireNonNullElse(format, "Hello, $")
                .replace(
                        "$",
                        new String(received.getData(), received.getOffset(), received.getLength())
                );

        return new DatagramPacket(
                messageToSend.getBytes(),
                messageToSend.length(),
                received.getAddress(),
                received.getPort()
        );
    }

    @Override
    public void close() {
        for (DatagramSocket socket : sockets) {
            socket.close();
        }
        senders.close();
        receivers.close();
    }
}

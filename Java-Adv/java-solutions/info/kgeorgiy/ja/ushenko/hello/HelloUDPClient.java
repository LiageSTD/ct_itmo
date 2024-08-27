package info.kgeorgiy.ja.ushenko.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.kgeorgiy.java.advanced.hello.HelloClient;

public class HelloUDPClient implements HelloClient {

    public static final int TIMEOUT = 50;

    public HelloUDPClient() {
    }

    public static void main(String[] args) {
        if (args == null || args.length != 5) {
            System.err.println("Usage: HelloUDPClient <host> <port> <prefix> <threads> <requests>");
            return;
        }
        HelloClient client = new HelloUDPClient();
        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String prefix = args[2];
            int threads = Integer.parseInt(args[3]);
            int requests = Integer.parseInt(args[4]);
            client.run(host, port, prefix, threads, requests);
        } catch (final NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        }
    }

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
            for (int i = 1; i < threads + 1; i++) {
                final int pos = i;
                executorService.execute(() -> sendRequest(host, port, prefix, requests, pos));
            }
        }
    }

    private void sendRequest(String host, int port, String prefix, int requests, int threadNum) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress addr = InetAddress.getByName(host);

            datagramSocket.setSoTimeout(TIMEOUT);
            for (int requestNum = 1; requestNum <= requests; requestNum++) {
                while (!datagramSocket.isClosed()) {
                    try {
                        String strToSend = message(prefix, threadNum, requestNum);
                        byte[] to = strToSend.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket toSend = new DatagramPacket(to, to.length, addr, port);

                        datagramSocket.send(toSend);

                        DatagramPacket received = new DatagramPacket(
                                new byte[datagramSocket.getReceiveBufferSize()],
                                datagramSocket.getReceiveBufferSize()
                        );

                        datagramSocket.receive(received);

                        String receivedString = new String(
                                received.getData(),
                                received.getOffset(),
                                received.getLength(),
                                StandardCharsets.UTF_8
                        );
                        if (receivedString.contains(strToSend)) {
                            System.out.println(receivedString);
                            break;
                        }
                    } catch (final IOException ignored) {
                    }
                }
            }
            datagramSocket.close();
        } catch (final SocketException e) {
            throw new RuntimeException("Unable to open a socket on port %d : %s".formatted(port, e.getMessage()));
        } catch (final UnknownHostException e) {
            throw new RuntimeException("Unable to open port on this host : %s".formatted(e.getMessage()));
        }
    }

    private static String message(String prefix, int threadNum, int requestNum) {
        return prefix + threadNum + "_" + requestNum;
    }
}

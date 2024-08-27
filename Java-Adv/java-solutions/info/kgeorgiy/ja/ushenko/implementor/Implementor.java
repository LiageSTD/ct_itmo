package info.kgeorgiy.ja.ushenko.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Homework provided as implementation of {@link info.kgeorgiy.java.advanced.implementor.Impler}.
 *
 * @author Ushenko Yaroslav
 */
public class Implementor implements JarImpler {
    /**
     * Line separator provided by system
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * Hash map for default values of classes
     *
     * @see Map
     * @see HashMap
     */
    static Map<Class<?>, String> defaultValues = new HashMap<Class<?>, String>();

    /**
     * Default constructor for Implementor
     */
    public Implementor() {
    }

    static {
        defaultValues.put(boolean.class, "false;");
        defaultValues.put(byte.class, "(byte)0;");
        defaultValues.put(short.class, "(short)0;");
        defaultValues.put(int.class, "0;");
        defaultValues.put(long.class, "0L;");
        defaultValues.put(char.class, "'\0';");
        defaultValues.put(float.class, "0.0F;");
        defaultValues.put(double.class, "0.0;");
        defaultValues.put(void.class, ";");
    }

    /**
     * The main method of the Implementor class. It is used to run the implementation process.
     * It takes an array of arguments which can either be a single class name or a class name followed by a path.
     * If the arguments are null or the number of arguments is not 1 or 3, it will print an error message.
     * If the class cannot be found or the path is invalid, it will also print an error message.
     *
     * @param args an array of command-line arguments. If the length is 1, it should contain the name of the class to be implemented.
     *             If the length is 3, the second argument should be the name of the class and the third argument should be the path where the implementation should be created.
     */
    public static void main(String[] args) {
        Implementor implementor = new Implementor();

        try {
            if (args == null) {
                System.err.println("Arguments can not be null");
                return;
            }
            if (args.length == 1) {
                Class<?> token = Class.forName(args[0]);
                implementor.implement(token, Paths.get("."));
            } else if (args.length == 3) {
                Class<?> token = Class.forName(args[1]);
                Path path = Path.of(args[2]);

                implementor.implement(token, path);

            } else {
                System.err.printf("Wrong amount of arguments: %d", args.length);
            }
        } catch (final ImplerException e) {
            System.out.printf("Implementation exception: %s", e.getMessage());
        } catch (final ClassNotFoundException e) {
            System.err.printf("Class was not found: %s", e.getMessage());
        } catch (final InvalidPathException e) {
            System.err.printf("Unable to find class: %s", e.getMessage());
        } catch (final LinkageError e) {
            System.err.printf("Linkage error for %s", e.getMessage());
        }
    }

    /**
     * Generates a compilable class that implements interface in token.
     * Generated file will have the name as token + suffix "Impl"
     *
     * @param token token that describes which class should be generated
     * @param root  root directory
     * @throws ImplerException In case it can't generate a class
     * @see Class
     * @see Path
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null) {
            throw new ImplerException("Class token can't be null. Wrong class name or path.");
        }
        if (root == null) {
            throw new ImplerException("Class path can't be null. Wrong class path.");
        }
        if (!token.isInterface()) {
            throw new ImplerException("Incorrect input. There's only interfaces available");
        }
        if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Interface can't be private");
        }

        Path fullPath = getFullPath(token, root);

        try {
            Files.createDirectories(fullPath.getParent());
        } catch (IOException e) {
            throw new ImplerException("Unable to make directories for: " + root);
        }
        try (BufferedWriter bw = Files.newBufferedWriter(fullPath)) {
            StringBuilder sb = new StringBuilder();

            sb.append(makeHeader(token));
            sb.append(makeMethods(token));
            sb.append(LINE_SEPARATOR).append("}");

            bw.write(fixString(sb.toString()));
        } catch (final IOException e) {
            throw new ImplerException("Unable to open file for writing");
        }

    }

    /**
     * Removes overflowed symbols from a string,
     *
     * @param string string to fix
     * @return string fixed string
     */
    private String fixString(String string) {
        StringBuilder sb = new StringBuilder();
        for (char ch : string.toCharArray()) {
            if (ch < 128) {
                sb.append(ch);
            } else {
                sb.append(String.format("\\u%04X", (int) ch));
            }
        }
        return sb.toString();
    }


    /**
     * Generates a header. It's always a public class that implements interface from a token
     *
     * @param token token to generate a header for
     * @return Strings that contains Header of current token.
     * @see Class
     */
    private String makeHeader(Class<?> token) {
        StringBuilder sb = new StringBuilder();
        if (token.getPackage() != null) {
            sb.append("package ").append(token.getPackageName()).append(";").append(LINE_SEPARATOR.repeat(2));
        }
        sb.append("public class ").append(token.getSimpleName()).append("Impl implements ")
                .append(token.getCanonicalName()).append(" {").append(LINE_SEPARATOR);
        return sb.toString();
    }

    /**
     * Generates all methods from a token.
     * It's always public methods that return default values of their return types.
     * If there's no methods in interface, returns white string.
     *
     * @param token token to generate methods for
     * @return string A string that contains methods of token.
     */

    private String makeMethods(Class<?> token) {
        Method[] a = token.getMethods();
        if (a.length != 0) {
            return Arrays.stream(a)
                    .filter(method -> !(method.isDefault() || Modifier.isStatic(method.getModifiers())))
                    .map(method ->
                            "public " + getOtherModifiers(method.getModifiers())
                                    + method.getReturnType().getCanonicalName() + " " + method.getName() + "("
                                    + makeParameters(method.getParameterTypes()) + ")" + getExceptions(method)
                                    + " {" + LINE_SEPARATOR + "return "
                                    + getDefaultValueOfReturnType(method.getReturnType()) + LINE_SEPARATOR + "}"
                    ).collect(Collectors.joining(LINE_SEPARATOR));
        } else {
            return "";
        }
    }

    //-----------------------------------------------

    /**
     * Generates parameters of methods.
     * Variables always have prefix "var".
     *
     * @param methods methods to create parameters for.
     * @return string A string that contains parameters of a method.
     */
    private String makeParameters(Class<?>[] methods) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < methods.length; i++) {
            sb.append(methods[i].getCanonicalName()).append(" var").append(i);
            if (i != methods.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Compiles .java file.
     *
     * @param root  Path to file to compile.
     * @param token Type token of a class.
     * @throws ImplerException In case there's no Java compiler or it can't compile a file.
     */
    public void compile(Path root, Class<?> token) throws ImplerException {
        final String classpath;
        try {
            classpath = Paths.get(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new ImplerException("Can't get classpath");
        }
        final int exitCode = ToolProvider.getSystemJavaCompiler().run(
                null, null, null,
                "-cp", classpath,
                "-encoding", StandardCharsets.UTF_8.name(),
                root.resolve(
                        token.getPackageName().replace(".", File.separator)
                                + File.separator + token.getSimpleName() + "Impl.java"
                ).toString()
        );
        if (exitCode != 0) {
            throw new ImplerException("Compiler exit code");
        }
    }

    /**
     * Generates .jar file with content from {@code path}
     *
     * @param token type token of the class that will be put in .jar.
     * @param path  Path to file to put in .jar.
     * @throws ImplerException In case it can't write a jar file.
     */
    private void makeJar(Class<?> token, Path path) throws ImplerException {
        Manifest manifest = new Manifest();

        try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(path), manifest)) {
            jos.putNextEntry(new ZipEntry(
                            token.getPackageName().replace(".", "/") + "/" + token.getSimpleName()
                                    + "Impl.class"
                    )
            );
            Files.copy(
                    Paths.get(
                            path.getParent() + "/" + token.getPackageName().replace(".", "/") +
                                    "/" + token.getSimpleName() + "Impl.class"
                    ),
                    jos
            );
            jos.closeEntry();
        } catch (IOException e) {
            throw new ImplerException("Error while creating jar file: " + e.getMessage(), e);
        }
    }

    /**
     * Returns String with exceptions separated by commas.
     * If there's no exceptions, returns white string.
     *
     * @param method Method to take exceptions from.
     * @return String A string that contains all exceptions for a method.
     * @see Method
     */
    private String getExceptions(Method method) {
        String res = Arrays.stream(method.getExceptionTypes()).map(Class::getName).collect(Collectors.joining(", "));
        return res.isEmpty() ? "" : "throws " + res;
    }

    /**
     * Returns String with modifiers separated by spaces.
     * If there's no modifiers, returns white sting.
     * Supported modifiers: static, final, synchronized, volatile, native.
     *
     * @param modifiers int value that represents list of modifiers.
     * @return String A string that contains all modifiers. (Without abstract and transient).
     * @see Modifier
     * @see Method#getModifiers()
     */
    private String getOtherModifiers(int modifiers) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isStatic(modifiers)) {
            sb.append("static ");
        }
        if (Modifier.isFinal(modifiers)) {
            sb.append("final ");
        }
        if (Modifier.isSynchronized(modifiers)) {
            sb.append("synchronized ");
        }
        if (Modifier.isVolatile(modifiers)) {
            sb.append("volatile ");
        }
        if (Modifier.isNative(modifiers)) {
            sb.append("native ");
        }
        return sb.toString();
    }

    /**
     * Returns root path with suffix of package name and Impl.java
     *
     * @param token Token to take package name from.
     * @param root  Source root.
     * @return Path Returns resolved full path for root and token.
     * @see Path
     * @see Class
     */
    private Path getFullPath(Class<?> token, Path root) {
        return Path.of(
                root.toString(),
                        (token.getPackage() == null) ?
                                "" :
                                token.getPackage()
                                        .getName()
                                        .replace('.', File.separatorChar)
                )
                .resolve(token.getSimpleName() + "Impl.java");
    }

    /**
     * Returns default value of Class.
     *
     * @param token Type token of the return value to take default value of.
     * @return String A string that contains default value of a method.
     */
    private String getDefaultValueOfReturnType(Class<?> token) {
        return defaultValues.getOrDefault(token, "null;");
    }

    /**
     * A method to create an implementation of type token and put it into jar file.
     *
     * @param token   type token to create implementation for.
     * @param jarFile target <var>.jar</var> file.
     * @throws ImplerException Check inner methods to see possible causes of an exception.
     * @see Implementor#implement(Class, Path)
     * @see Implementor#compile(Path, Class)
     * @see Implementor#implementJar(Class, Path)
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        Implementor implementor = new Implementor();
        implementor.implement(token, jarFile.getParent());
        implementor.compile(jarFile.getParent(), token);
        implementor.makeJar(token, jarFile);
    }
}


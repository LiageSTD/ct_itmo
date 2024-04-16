package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Md2Html {
    public static boolean prevIsText = false;
    public static boolean prevIsPar = false;
    public static boolean prevIsNull = true;
    public static int power;
    public static int posInList = 0;
    public static Stack<String> hungry = new Stack<>();
    public static StringBuilder readyLine = new StringBuilder();
    public static StringBuilder boofText = new StringBuilder();
    public static StringBuilder buffPar = new StringBuilder();

    public static void sendboofText() {
        readyLine.append("<p>");
        readyLine.append(checkForMarkdown(boofText));
        boofText.setLength(0);
    }

    public static void sendbuffPar() {
        readyLine.append("<h" + power + ">");
        readyLine.append(checkForMarkdown(buffPar));
        buffPar.setLength(0);
    }

    public static void main(String[] args) {
        try (BufferedReader readly = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8))) {

            while (true) {
                String line = readly.readLine();
                //System.err.println("New Line " + line);
                if (line == null) {
                    if (prevIsPar) {
                        sendbuffPar();
                        closePar();
                    } else if (prevIsText) {
                        sendboofText();
                        closeText();
                    }
                    break;
                }
                if (line.trim().length() == 0) {
                    prevIsNull = true;
                    if (prevIsPar) {
                        prevIsPar = false;
                        sendbuffPar();
                        closePar();
                    } else if (prevIsText) {
                        sendboofText();
                        closeText();
                        prevIsText = false;
                    }
                } else if (prevIsPar) {
                    contPar(line);
                } else if (prevIsText) {
                    pushExText(line);
                } else {
                    checkForPar(line);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {

                writer.write(readyLine.toString());
                //System.err.print(readyLine);
                writer.close();
                readly.close();
                readyLine.setLength(0);
                prevIsText = false;
                prevIsPar = false;
                prevIsNull = true;
                power = 0;
                posInList = 0;
                if (!hungry.empty()) {
                    hungry.pop();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                readly.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            readyLine.setLength(0);
        }

    }

    public static void checkForPar(String line) {
        if (line.charAt(0) == '#') {
            for (int pos = 1; pos < line.length(); pos++) {
                if (line.charAt(pos - 1) == '#' && line.charAt(pos) == ' ') {
                    power = pos;
                    if (prevIsNull) {
                        prevIsPar = true;
                        prevIsNull = false;
                        pushNewPar(line.substring(pos + 1));
                        break;
                    }
                }
            }
        }
        if (!prevIsPar) {
            prevIsText = true;
            prevIsNull = false;
            pushNewText(line);
        }

    }

    public static void pushNewPar(String line) {
        buffPar.append(line);
    }

    public static void contPar(String line) {
        buffPar.append(System.lineSeparator());
        buffPar.append(line);
    }

    public static void closePar() {
        readyLine.append("</h");
        readyLine.append(power);
        readyLine.append(">");
        readyLine.append(System.lineSeparator());
    }

    public static void pushNewText(String line) {
        boofText.append(line);
    }

    public static void pushExText(String line) {
        boofText.append(System.lineSeparator());
        boofText.append(line);
    }

    public static void closeText() {
        readyLine.append("</p>");
        readyLine.append(System.lineSeparator());
    }

    public static StringBuilder checkForMarkdown(StringBuilder line) {
        List<StringBuilder> list = new ArrayList<>();
        iterLine(line, list);
        posInList = 0;
        while (!hungry.empty()) {
            mergePrev(list);
        }
        return list.get(0);
    }

    public static void iterLine(StringBuilder line, List<StringBuilder> list) {
        StringBuilder subline = new StringBuilder();
        list.add(subline);
        for (int pos = 0; pos < line.length(); pos++) {
            char ch = line.charAt(pos);
            switch (ch) {
                case '\\':
                    pos++;
                    if (pos < line.length()) {
                        list.get(posInList).append(line.charAt(pos));
                    }
                    break;
                case '*':
                    if (pos < line.length() - 1 && line.charAt(pos + 1) == '*') {
                        checkSymbol("**", list);
                        pos++;
                    } else {
                        checkSymbol("*", list);
                    }
                    break;
                case '_':
                    if (pos < line.length() - 1 && line.charAt(pos + 1) == '_') {
                        checkSymbol("__", list);
                        pos++;
                    } else {
                        checkSymbol("_", list);
                    }
                    break;
                case '`':
                    checkSymbol("`", list);
                    break;
                case '-':
                    if (pos < line.length() - 1 && line.charAt(pos + 1) == '-') {
                        checkSymbol("--", list);
                        pos++;
                    } else {
                        list.get(posInList).append(line.charAt(pos));
                    }
                    break;
                case '~':
                    checkSymbol("~", list);
                    break;
                case '&':
                    list.get(posInList).append("&amp;");
                    break;
                case '<':
                    list.get(posInList).append("&lt;");
                    break;
                case '>':
                    list.get(posInList).append("&gt;");
                    break;
                default:
                    list.get(posInList).append(line.charAt(pos));
                    break;
            }
        }
    }

    public static void checkSymbol(String symb, List<StringBuilder> list) {
        if (!hungry.empty() && Objects.equals(hungry.peek(), symb)) {
            mergeWithPrev(list);
        } else if (hungry.search(symb) > 0) {
            mergeTPrev(list, hungry.search(symb));
        } else {
            hungry.push(symb);
            list.add(new StringBuilder());
            posInList++;

        }
    }

    public static void mergeWithPrev(List<StringBuilder> list) {
        int pos = list.size() - 1;
        String delim = hungry.pop();
        delim = switch (delim) {
            case "*", "_" -> "<em>";
            case "**", "__" -> "<strong>";
            case "--" -> "<s>";
            case "`" -> "<code>";
            case "~" -> "<mark>";
            default -> hungry.pop();
        };

        list.get(pos - 1).append(delim);
        list.get(pos - 1).append(list.get(pos));
        list.get(pos - 1).append(delim.charAt(0)).append("/").append(delim.substring(1));
        list.remove(pos);
        posInList--;
        //        int pos = list.size() - 1;
//        StringBuilder first = list.get(pos);
//        StringBuilder second = list.get(pos - 1);
//        String delim = hungry.pop();
//        first.append(delim);
//        first.append(second);
//        first.append(delim.charAt(0)).append("/").append(delim.substring(1));
//        list.remove(pos);
    }

    public static void mergeTPrev(List<StringBuilder> list, int count) {
        for (int x = 0; x < count - 1; x++) {
            int pos = list.size() - 1;
            list.get(pos - 1).append(hungry.pop());
            list.get(pos - 1).append(list.get(pos));
            list.remove(pos);
            posInList = pos - 1;
        }
        mergeWithPrev(list);
    }

    public static void mergePrev(List<StringBuilder> list) {
        int pos = list.size() - 1;
        list.get(pos - 1).append(hungry.pop());
        list.get(pos - 1).append(list.get(pos));
        list.remove(pos);
        posInList = pos - 1;
    }
}


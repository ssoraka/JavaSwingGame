package view;

import controllers.Controller;

import java.util.Scanner;

public class TerminalScanner implements Runnable{
    private CommandReader reader;
    private Controller controller;

    public TerminalScanner(Controller controller) {
        this.controller = controller;
        reader = new CommandReader();
    }

    private char translateCommandToChar(String line) {
        switch (line) {
            case "up" :
            case "^":
            case "w": return 'w';
            case "down" :
            case "s": return 's';
            case "left" :
            case "<":
            case "a": return 'a';
            case "right" :
            case ">":
            case "d": return 'd';
            case "text":
            case "talk": return '\n';
            case "quit":
            case "exit": return 'q';
            default:
                return ' ';
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String text = scanner.nextLine();

            if (!reader.isWriting()) {
                controller.execute(reader.getAction(translateCommandToChar(text)));
            } else {
                for (int i = 0; i < text.length(); i++) {
                    reader.getAction(text.charAt(i));
                }
                controller.execute(reader.getAction(CommandReader.END_LINE));
            }
        }
    }
}

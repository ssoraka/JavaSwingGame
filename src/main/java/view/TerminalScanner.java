package view;

import controllers.Actions;
import controllers.AllController;

import java.util.Scanner;

public class TerminalScanner implements Runnable{
//    private CommandReader1 reader;
    private AllController controller;

    public TerminalScanner(AllController controller) {
        this.controller = controller;
//        reader = new CommandReader1(controller);
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String text = scanner.nextLine();

            controller.executeCommand(Actions.getAction(text));


//            if (!reader.isWriting()) {
//                reader.applyCommand(translateCommandToChar(text));
//            } else {
//                for (int i = 0; i < text.length(); i++) {
//                    reader.applyCommand(text.charAt(i));
//                }
//                reader.applyCommand(CommandReader1.END_LINE);
//            }
        }
    }
}

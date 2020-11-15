package view;

import controllers.ActionBuilder;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;

public class CommandReader {
    public final static ActionBuilder EMPTY = new ActionBuilder();
    ActionBuilder action;
    StringBuilder buffer;
    boolean isReading;

    public CommandReader() {
        buffer = new StringBuilder();
        isReading = false;
    }

    public ActionBuilder getAction(KeyEvent key) {
        return getAction(key.getKeyChar(), key.getKeyCode());
    }

    public ActionBuilder getAction(char keyChar) {
        return getAction(keyChar, KeyEvent.getExtendedKeyCodeForChar(keyChar));
    }

    public ActionBuilder getAction(char keyChar, int keyCode) {

        if (isReading) {
            readMessage(keyChar, keyCode);
        } else {
            readComand(keyChar);
        }
        return action;
    }

    private void readMessage(char keyChar, int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            action = new ActionBuilder()
                    .setAction(ActionBuilder.Action.TEXT)
                    .setMessage(buffer.toString())
                    .build();
            buffer = new StringBuilder();
        } else {
            buffer.append(keyCode);
            action = EMPTY;
        }
    }

    private void readComand(int keyCode) {
        action = new ActionBuilder();

        switch (keyCode) {
            case KeyEvent.VK_W:
                action.setAction(ActionBuilder.Action.MOVE_UP).build();
                break;
            case KeyEvent.VK_S:
                action.setAction(ActionBuilder.Action.MOVE_DOWN).build();
                break;
            case KeyEvent.VK_A:
                action.setAction(ActionBuilder.Action.MOVE_LEFT).build();
                break;
            case KeyEvent.VK_D:
                action.setAction(ActionBuilder.Action.MOVE_RIGHT).build();
                break;
            case KeyEvent.VK_T: {
                isReading = true;
                action = EMPTY;
                break;
            }
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            default:
                action = EMPTY;
                break;
        }

    }
}

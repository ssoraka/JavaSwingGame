package view;

import controllers.ActionBuilder;

import java.awt.event.KeyEvent;

public class CommandReader {
    public final static ActionBuilder EMPTY = new ActionBuilder();
    ActionBuilder action;
    StringBuilder buffer;
    boolean isWriting;

    public CommandReader() {
        buffer = new StringBuilder();
        isWriting = false;
    }

    public ActionBuilder getAction(KeyEvent key) {
        return getAction(key.getKeyChar(), key.getKeyCode());
    }

    public ActionBuilder getAction(char keyChar) {
        return getAction(keyChar, KeyEvent.getExtendedKeyCodeForChar(keyChar));
    }

    public ActionBuilder getAction(char keyChar, int keyCode) {
        action = EMPTY;
        if (isWriting) {
            readMessage(keyChar);
        } else {
            readCommand(keyChar);
        }
        return action;
    }

    private void readCommand(char keyChar) {
        switch (keyChar) {
            case 'w' : action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_UP).build(); break;
            case 's': action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_DOWN).build(); break;
            case 'a': action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_LEFT).build(); break;
            case 'd': action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_RIGHT).build(); break;
            case '\n': isWriting = true; break;
            case '\b': System.exit(0);
            default:
                break;
        }
    }

    private void readMessage(char keyChar) {
        if (keyChar == '\n') {
            action = new ActionBuilder()
                    .setAction(ActionBuilder.Action.TEXT)
                    .setMessage(buffer.toString())
                    .build();
            buffer = new StringBuilder();
            isWriting = false;
        } else {
            if (keyChar == '\b')
                buffer.deleteCharAt(buffer.length() - 1);
            else if (isWritable(keyChar))
                buffer.append(keyChar);
        }
    }

    private void readCommand(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W: action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_UP).build(); break;
            case KeyEvent.VK_S: action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_DOWN).build(); break;
            case KeyEvent.VK_A: action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_LEFT).build(); break;
            case KeyEvent.VK_D: action = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_RIGHT).build(); break;
            case KeyEvent.VK_ENTER: isWriting = true; break;
            case KeyEvent.VK_ESCAPE: System.exit(0);
            default:
                break;
        }
    }

    private void readMessage(char keyChar, int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            action = new ActionBuilder()
                    .setAction(ActionBuilder.Action.TEXT)
                    .setMessage(buffer.toString())
                    .build();
            buffer = new StringBuilder();
            isWriting = false;
        } else {
            if (isWritable(keyChar))
                buffer.append(keyChar);
            else if (keyCode == KeyEvent.VK_BACK_SPACE)
                buffer.deleteCharAt(buffer.length() - 1);
        }
    }

    private boolean isWritable(char keyChar) {
        switch (Character.getType(keyChar)) {
            case Character.CONTROL:
            case Character.FORMAT:
            case Character.PRIVATE_USE:
            case Character.SURROGATE:
            case Character.UNASSIGNED:
                return false;
            default:
                return true;
        }
    }
}

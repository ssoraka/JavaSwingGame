package myTest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Point pos = new Point(50, 50);

        MyPanel panel = new MyPanel();
        panel.setPoint(pos);

        MyFrame frame = new MyFrame("Points",350, 250);
        frame.add(panel);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                if (e.getKeyChar() == 'w') {
                    pos.y -= 2;
                } else if (e.getKeyChar() == 's') {
                    pos.y += 2;
                } else if (e.getKeyChar() == 'a') {
                    pos.x -= 2;
                } else if (e.getKeyChar() == 'd') {
                    pos.x += 2;
                } else if (e.getKeyCode() == 27) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });


        while (true) {
            frame.repaint();
            Thread.sleep(100);
        }
    }
}

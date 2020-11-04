package myTest;

import javax.swing.*;

public class MyFrame extends JFrame {

    public MyFrame(String title, int width, int height) {
        setVisible(true);
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

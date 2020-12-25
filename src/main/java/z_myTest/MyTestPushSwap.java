package z_myTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class PushSwapPanel extends JPanel {
    private List<Integer> stackA;
    private List<Integer> stackB;
    private int max;

    public static int SCALE = 10;
    public static int SHIFT = 20;

    public PushSwapPanel(List<Integer> stackA, List<Integer> stackB, int max) {
        this.stackA = stackA;
        this.stackB = stackB;
        this.max = max;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);

        for (int i = 0; i < stackA.size(); i++) {
            int len = stackA.get(i) * SCALE;
            g2.drawRect(SHIFT, i * SCALE + SHIFT, len, 3);
            g2.fillRect(SHIFT, i * SCALE + SHIFT, len, 3);
            g2.drawString(stackA.get(i).toString(), 0, i * SCALE + SHIFT + 5);
        }

        for (int i = 0; i < stackB.size(); i++) {
            int len = stackB.get(i) * SCALE;
            g2.drawRect(SHIFT * 2 + max * SCALE, i * SCALE + SHIFT, len, 3);
            g2.fillRect(SHIFT * 2 + max * SCALE, i * SCALE + SHIFT, len, 3);
            g2.drawString(stackB.get(i).toString(), max * SCALE + SHIFT, i * SCALE + SHIFT + 5);
        }
    }
}

class PushSwapFrame extends JFrame implements Runnable{

    final private List<Integer> stackA;
    final private List<Integer> stackB;

    public PushSwapFrame(List<Integer> stackA, List<Integer> stackB) {
        this.stackA = stackA;
        this.stackB = stackB;

        int max = Stream.concat(stackA.stream(), stackB.stream()).max(Integer::compareTo).get();

        int width = max * 2 * PushSwapPanel.SCALE + PushSwapPanel.SHIFT * 3;
        int count = stackA.size() + stackB.size();
        int height = count * PushSwapPanel.SCALE + PushSwapPanel.SHIFT * 3 + 3;

        setVisible(true);
        setTitle("PushSwap");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        add(new PushSwapPanel(stackA, stackB, max));

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'w' && stackA.size() > 0) {
                    stackB.add(0, stackA.get(0));
                    stackA.remove(0);
                } else if (e.getKeyChar() == 's' && stackB.size() > 0) {
                    stackA.add(0, stackB.get(0));
                    stackB.remove(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MyTestPushSwap {


    public static void main(String[] args) throws InterruptedException {
        List<Integer> stackA = new ArrayList<>();
//        stackA.add(1);
//        stackA.add(2);
//        stackA.add(3);
//        stackA.add(4);
//        stackA.add(5);
//        stackA.add(6);
//        stackA.add(7);
//        stackA.add(8);
//        stackA.add(9);
        stackA =  new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10, 11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30, 35));
        List<Integer> stackB = new ArrayList<>();



        PushSwapFrame frame = new PushSwapFrame(stackA, stackB);

//        Thread thread = new Thread(frame);
//        thread.setDaemon(true);
//        thread.start();

    }
}

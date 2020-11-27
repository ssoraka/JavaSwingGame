package z_myTest.testDraw;//http://zetcode.com/javaswing/painting/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class DrawPanel extends JPanel{
    Graphics2D graphic;

    private void doDrawing(Graphics g) {

        graphic = (Graphics2D) g;
        graphic.setColor(Color.blue);

        for (int i = 0; i <= 1000; i++) {

            Dimension size = getSize();
            Insets insets = getInsets();

            int w = size.width - insets.left - insets.right;
            int h = size.height - insets.top - insets.bottom;

            Random r = new Random();
            int x = Math.abs(r.nextInt()) % w;
            int y = Math.abs(r.nextInt()) % h;
            graphic.drawLine(x, y, x, y);
        }
    }

    public void markNewPoint(Point point){
        graphic.setColor(Color.blue);
        graphic.drawLine(point.x, point.y, 10, 10);
    }

    @Override
    public void paintComponent(Graphics g) {

//        super.paintComponent(g);
        doDrawing(g);
    }
}

class PointsEx extends JFrame {
    DrawPanel drawPanel;
    PointsEx p;

    public PointsEx() {

        initUI();
    }

    private void initUI() {

        drawPanel = new DrawPanel();
        add(drawPanel);
//        repaint(100, 0, 0, 350, 250);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //drawPanel.markNewPoint(e.getPoint());

            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                drawPanel.markNewPoint(e.getPoint());
                PointsEx.super.repaint();
                System.out.println("moved " + e.getX() + " " + e.getY() + " " + e.getButton());
            }
        });

//        addMouseWheelListener(new MouseWheelListener() {
//            @Override
//            public void mouseWheelMoved(MouseWheelEvent e) {
//                System.out.println("wheel " + e.getX() + " " + e.getY() + " " + e.getUnitsToScroll());
//            }
//        });

//        addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                System.out.println("type " + e.getExtendedKeyCode() + " " + e.getKeyCode() + " " + e.getKeyChar() + " " +
//                        e.getKeyLocation() + " " + e.paramString());
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                System.out.println("pressed " + e.getExtendedKeyCode() + " " + e.getKeyCode() + " " + e.getKeyChar() + " " + e.getKeyLocation());
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//            }
//        });

        setSize(350, 250);
        setTitle("Points");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

//        EventQueue.invokeLater(() -> {

            PointsEx ex = new PointsEx();
            ex.setVisible(true);
//        });
    }
}
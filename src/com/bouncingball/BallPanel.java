package com.bouncingball;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class BallPanel extends JPanel {
    
    private static final int REFRESH_RATE = 60;

    private Ball ball;
    private BallArea box;
    private int areaWidth;
    private int areaHeight;

    public BallPanel(int width, int height) {
        this.areaWidth = width;
        this.areaHeight = height;
        this.setPreferredSize(new Dimension(this.areaWidth, this.areaHeight));
        
        Random rand = new Random();

        int radius = 50;
        int x = rand.nextInt(width - 2*radius - 20) + radius + 10;
        int y = rand.nextInt(height - 2*radius - 20) + radius + 10;

        int speed = 5;
        int angle = rand.nextInt(360);

        this.ball = new Ball(x, y, radius, speed, angle, Color.BLUE);
        this.box = new BallArea(0, 0, width, height, Color.BLACK, Color.WHITE);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                Component c = (Component)e.getComponent();
                Dimension dim = c.getSize();
                areaWidth = dim.width;
                areaHeight = dim.height;
                box.set(0, 0, areaWidth, areaHeight);
            }
        });
        startThread();
    }

    public void startThread() {
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    ball.collide(box);
                    repaint();
                    try {
                        Thread.sleep(1000 / REFRESH_RATE);
                    } catch (InterruptedException e) {}
                }
            }
        };
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        box.draw(g);
        ball.draw(g);
    }

}

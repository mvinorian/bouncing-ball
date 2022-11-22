package com.bouncingball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Ball implements MouseListener, MouseMotionListener {

    public final static int PRESSED = 1;
    public final static int RELEASED = 2;

    float x, y;
    float speed;
    float speedX, speedY;
    float radius;
    private Color color;
    private int status;
    private int mouseX, mouseY;
    private float offsetX, offsetY;
    private float mouseVelX, mouseVelY;

    public Ball(float x, float y, float radius, float speed, float angle, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.speedX = speed * (float)Math.cos(Math.toRadians(angle));
        this.speedY = -speed * (float)Math.sin(Math.toRadians(angle));
        this.color = color;
        this.status = Ball.RELEASED;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius));
    }

    public void collide(BallArea box) {
        float ballMinX = box.minX + this.radius;
        float ballMinY = box.minY + this.radius;
        float ballMaxX = box.maxX - this.radius;
        float ballMaxY = box.maxY - this.radius;

        if (this.status != Ball.PRESSED) {
            this.x += this.speedX;
            this.y += this.speedY;
        }

        if (this.x < ballMinX) {
            this.speedX = -this.speedX;
            this.x = ballMinX;
            this.release();
        } else if (this.x > ballMaxX) {
            this.speedX = -this.speedX;
            this.x = ballMaxX;
            this.release();
        }

        if (this.y < ballMinY) {
            this.speedY = -this.speedY;
            this.y = ballMinX;
            this.release();
        } else if (this.y > ballMaxY) {
            this.speedY = -this.speedY;
            this.y = ballMaxY;
            this.release();
        }
        this.resetMouseVelocity();
    }

    private void updateMousePosition(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    private void updateMouseVelocity(MouseEvent e) {
        int dX = e.getX()-this.mouseX;
        int dY = e.getY()-this.mouseY;
        float r = (float)Math.sqrt(dX*dX + dY*dY);
        
        this.mouseVelX = (float)dX/r;
        this.mouseVelY = (float)dY/r;
    }

    private void resetMouseVelocity() {
        this.mouseVelX = this.speedX / this.speed;
        this.mouseVelY = this.speedY / this.speed;
    }

    private void press(float dX, float dY) {
        this.status = Ball.PRESSED;
        this.offsetX = dX;
        this.offsetY = dY;
        this.color = this.color.darker().darker();
    }

    private void release() {
        if (this.status == Ball.PRESSED) {
            this.status = Ball.RELEASED;
            this.speedX = this.speed * this.mouseVelX;
            this.speedY = this.speed * this.mouseVelY;
            this.color = this.color.brighter().brighter();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        float dX = e.getX()-this.x;
        float dY = e.getY()-this.y;
        if (dX*dX + dY*dY <= this.radius*this.radius) {
            this.press(dX, dY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.release();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.updateMouseVelocity(e);
        this.updateMousePosition(e);

        if (this.status == Ball.PRESSED) {
            this.x = e.getX() - this.offsetX;
            this.y = e.getY() - this.offsetY;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.updateMouseVelocity(e);
        this.updateMousePosition(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}

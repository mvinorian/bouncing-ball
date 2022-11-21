package com.bouncingball;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    float x, y;
    float speedX, speedY;
    float radius;
    private Color color;

    public Ball(float x, float y, float radius, float speed, float angle, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speed * (float)Math.cos(Math.toRadians(angle));
        this.speedY = -speed * (float)Math.sin(Math.toRadians(angle));
        this.color = color;
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

        this.x += this.speedX;
        this.y += this.speedY;

        if (this.x < ballMinX) {
            this.speedX = -this.speedX;
            this.x = ballMinX;
        } else if (this.x > ballMaxX) {
            this.speedX = -this.speedX;
            this.x = ballMaxX;
        }

        if (this.y < ballMinY) {
            this.speedY = -this.speedY;
            this.y = ballMinX;
        } else if (this.y > ballMaxY) {
            this.speedY = -this.speedY;
            this.y = ballMaxY;
        }
    }
    
}

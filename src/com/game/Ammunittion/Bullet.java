package com.game.Ammunittion;

import java.awt.image.BufferedImage;

public class Bullet extends Ammo {

    public Bullet(BufferedImage image, float x, float y, float slope, int direction) {
        super(image, x, y, direction);
        setSpeed(1);
        setDamage(4);
        setWidth(8);
        setHeight(2);
        this.slope = slope;
        startX = x;
        startY = y;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    protected void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    protected void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    protected void setWidth(int width) {
        this.width = width;
    }

    @Override
    protected void setHeight(int height) {
        this.height = height;
    }

    private double calculateSpeed(int speed) {
        double adjustedSpeed = (Math.pow(speed, 2)/distancePerTick(speed));
        return adjustedSpeed;
    }

    private double distancePerTick(int speed) {
        double a = Math.pow(speed, 2);
        double c = a+a;
        return Math.sqrt(c)*speed;
    }

}

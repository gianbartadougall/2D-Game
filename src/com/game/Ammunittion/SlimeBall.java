package com.game.Ammunittion;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SlimeBall extends Ammo {

    public SlimeBall(BufferedImage image, float x, float y, int direction) {
        super(image, x, y, direction);
        setDamage(1);
        setSpeed(1);
        setWidth(3);
        setHeight(4);
    }

    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public BufferedImage getTextures() {
        return textures;
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
}

package com.game.Ammunittion;

import com.game.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.game.utilities.Utils.LEFT;

public abstract class Ammo {

    protected final int DEFAULT_AMMO_SPEED = 3;
    protected final int DEFAULT_AMMO_DAMAGE = 4;
    protected final int DEFAULT_AMMO_WIDTH = 5;
    protected final int DEFAULT_AMMO_HEIGHT = 2;

    protected BufferedImage textures;

    protected float startX;
    protected float startY;

    protected float slope;
    protected float x, y;
    protected int damage, direction;
    protected double speed;
    protected int width, height;
    protected boolean visible;
    protected Rectangle bounds;

    public Ammo(BufferedImage image, float x, float y, int direction) {
        this.textures = image;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.bounds = new Rectangle((int) x, (int) y, width, height);
        visible = true;
        setDamage(DEFAULT_AMMO_DAMAGE);
        setSpeed(DEFAULT_AMMO_SPEED);
        setWidth(DEFAULT_AMMO_WIDTH);
        setHeight(DEFAULT_AMMO_HEIGHT);
    }

    public boolean isVisible() {
        return visible;
    }

    public void tick() {
        if (direction == 1) {
            x -= speed;
            y -= slope*speed;
        } else {
            x += speed;
            y += slope*speed;
        }

        checkIfBulletIsOutOfBounds();
    }

    private void checkIfBulletIsOutOfBounds() {
        if (x<=World.MIN_WORLD_WIDTH || x>= World.MAX_FLOAT_WORLD_WIDTH ||
            y<World.MIN_WORLD_HEIGHT || y>World.MAX_FLOAT_WORLD_HEIGHT) {
            visible = false;
        }
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    protected void setSpeed(double speed) {
        this.speed = speed;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public float startX() {
        return startX;
    }

    public float startY() {
        return startY;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public BufferedImage getTextures() {
        return textures;
    }

    public Rectangle getHitColourBox() {
        //just for drawing the hitbox of the bullet
        return  direction == LEFT ? new Rectangle((int) x, (int) y, width, height) :
                new Rectangle((int) (x), (int) y, width, height);
    }

    public Rectangle getHitBoxBullet() {
        //Minus 9 is so bullet hitbox is at front of bullet not back of bullet
        return  direction == LEFT ? new Rectangle((int) x, (int) y, width, height) :
                new Rectangle((int) (x-9), (int) y, width, height);
    }
}

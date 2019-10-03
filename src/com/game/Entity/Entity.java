package com.game.Entity;

import com.game.Ammunittion.Ammo;
import com.game.tile.Tile;
import com.game.world.World;

import java.awt.*;

public abstract class Entity {

    protected static final int DEFAULT_ENTITY_HEALTH = 10;
    protected static final int DEFAULT_ENTITY_WIDTH = 50;
    protected static final int DEFAULT_ENTITY_HEIGHT = 50;

    protected World world;
    protected EntityManager entityManager;
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected int health;
    protected boolean dead;


    public Entity(World world, EntityManager entityManager, float xPos, float yPos, int width, int height) {
        setEntityHitBox();
        this.x = xPos;
        this.y = yPos;
        this.width = width;
        this.height = height;
        this.world = world;
        this.entityManager = entityManager;
        this.height = DEFAULT_ENTITY_HEALTH;
        dead = false;
    }

    protected abstract void tick();
    protected abstract void render(Graphics graphics);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected boolean checkEntityCollisions() {
        for (Entity e : entityManager.getEntities()) {
            if (e.equals(this)) {
                continue;
            }
            if (e.getHitBox().intersects(this.getHitBox())) {
                this.died();
                return true;
            }
        }
        return false;
    }

    protected boolean ammoHitAnObject(Ammo ammo) {
        for (int i=0; i<entityManager.getEntities().size(); i++) {
            Entity e = entityManager.getEntities().get(i);
            if (e.equals(this)) {
                continue;
            }

            int ammoX = (int) ammo.startX()/Tile.TILE_WIDTH;
            int ammoY = (int) ammo.startY()/Tile.TILE_HEIGHT;

            if (ammoX < World.MIN_WORLD_WIDTH || ammoX > World.MAX_FLOAT_WORLD_WIDTH ||
                ammoY < World.MIN_WORLD_HEIGHT || ammoY > World.MAX_FLOAT_WORLD_HEIGHT) {
                return false;
            }

            if (world.getTile(ammoX, ammoY).isSolid() || world.getTile(ammoX, ammoY).isSlope()) {
                return true;
            }

            Rectangle entity = e.getHitBox();
            Rectangle ammunition = ammo.getHitBox();
            if (entity.intersects(ammunition) || entity.contains(ammunition)) {
                e.health -= ammo.getDamage();
                if (e.health <=0) {
                    e.dead = true;
                    e.died();
                }
                return true;
            }
        }
        return false;
    }

    protected abstract void died();

    protected void setHealth(int newHealth) {
        health = newHealth;
    }

    protected Rectangle getHitBox() {
        return new Rectangle((int) x+bounds.x, (int) y+bounds.y, bounds.width, bounds.y+bounds.height);
    }

    protected void setEntityHitBox() {
        bounds = new Rectangle((int) x, (int) y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

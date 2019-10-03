package com.game.Entity.creature;

import com.game.Entity.Entity;
import com.game.Entity.EntityManager;
import com.game.movement.DefaultMovement;
import com.game.movement.Movement;
import com.game.world.World;

import java.awt.*;

public abstract class Creature extends Entity {

    protected final int DEFAULT_CREATURE_SPEED = 1;
    protected static final int DEFAULT_CREATURE_WIDTH = 50, DEFAULT_CREATURE_HEIGHT = 50;
    protected final int DEFAULT_CREATURE_SLOPE_SPEED = DEFAULT_CREATURE_SPEED;

    protected int speed;
    protected float slopeSpeed;
    protected float xMove, yMove;
    protected boolean initiateJump;

    private float horizontal;
    private float vertical;

    protected Movement movement;

    public Creature(World world, EntityManager entityManager, float xPos, float yPos) {
        super(world, entityManager, xPos, yPos, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
        setCreatureSpeed(DEFAULT_CREATURE_SPEED);
        setCreatureSlopeSpeed(DEFAULT_CREATURE_SLOPE_SPEED);
        setCreatureMovement();
        checkCreatureSpeed();
    }

    protected void move() {

        if (initiateJump && movement.isTouchingGround()) {
            movement.jump(this);
        }

        horizontal = movement.moveX(xMove, x, y);
        vertical = movement.moveY(yMove, x, y);

        if (horizontal == -100) {
            //left up
            y -= slopeSpeed;
            x -= slopeSpeed;
        } else if (horizontal == -200) {
            //right up
            y -= slopeSpeed;
            x += slopeSpeed;
        } else if (horizontal == -300) {
            //right down
            x -= slopeSpeed;
            y += slopeSpeed;
        } else if (horizontal == -400) {
            //left down
            x += slopeSpeed;
            y += slopeSpeed;
        } else {
            x += horizontal;
            y += vertical;
        }
    }

    //abstract methods
    protected abstract void createAnimations();
    protected abstract void updateAnimations();
    protected abstract void updateMovement();

    @Override
    protected void tick() {

    }

    @Override
    protected void render(Graphics graphics) {

    }

    //Creature settings
    protected void setCreatureSpeed(int speed) {
        this.speed = speed;
    }

    protected void setCreatureMovement() {
        movement = new DefaultMovement(world, this);
    }

    protected void setCreatureSlopeSpeed(int slopeSpeed) {
        this.slopeSpeed = slopeSpeed;
    }

    private void checkCreatureSpeed() {
        if (speed == 100 || speed == 200 || speed == 300 || speed == 400) {
            System.err.println("Speed cannot have same value as slope Id's");
        }
    }

    //Creature x, y coordinates
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getXRight() {
        return x+bounds.x+bounds.width;
    }

    public Rectangle getCollisionBox() {
        return new Rectangle((int) (x+bounds.x), (int) (y+bounds.y), bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}

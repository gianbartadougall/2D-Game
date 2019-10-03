package com.game.movement;

import com.game.Entity.creature.Creature;
import com.game.tile.Tile;
import com.game.world.World;

import java.awt.*;

import static com.game.utilities.Utils.LEFT;
import static com.game.utilities.Utils.RIGHT;

public class SlimeMovement extends Movement {

    private int speed;
    private int direction;

    public SlimeMovement(World world, Creature creature, int speed) {
        super(world, creature);
        direction = LEFT;
        this.speed = speed;
    }

    @Override
    public float moveX(float zero, float x, float y) {
        if (outOfBounds((int) x,(int) y, direction, speed)) {
            turn();
            return 0;
        }

        int yBottom = ((int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT);
        if (direction == LEFT) {
            int xLeft = (int) (x + -speed + bounds.x) / Tile.TILE_WIDTH;
            if (testMove(xLeft, yBottom) != 0) {
                return -speed;
            }
        }

        if (direction == RIGHT) {
            int xRight = (int) (x + speed + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            if (testMove(xRight, yBottom) != 0) {
                return speed;
            }
        }
        return 0;
    }

    private int testMove(int xDirection, int y) {
        if (blockCollision(xDirection, y)) {
            turn();
            return 0;
        }
        if (!blockUnderneath(xDirection, y)) {
            turn();
            return 0;
        }
        return 1;
    }

    private boolean blockUnderneath(int x, int y) {
        return world.getTile(x, y+1).isSolid();
    }

    private boolean blockCollision(int x, int y) {
        return world.getTile(x, y).isSolid();
    }

    @Override
    public float moveY(float yMove, float x, float y) {
        return 0;
    }

    @Override
    public boolean isTouchingGround() {
        return false;
    }

    @Override
    protected boolean boundsIsValid(Rectangle bounds) {
        return false;
    }

    private void turn() {
        if (direction == LEFT) {
            direction = RIGHT;
        } else direction = LEFT;
    }
}

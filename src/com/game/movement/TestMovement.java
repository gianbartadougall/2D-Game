package com.game.movement;

import com.game.Entity.creature.Creature;
import com.game.Mechanics;
import com.game.tile.Tile;
import com.game.world.World;

import java.awt.*;

public class TestMovement extends Movement {

    public TestMovement(World world, Creature creature) {
        super(world, creature);
    }

    @Override
    public float moveX(float xMove, float x, float y) {
        if (xMove > 0 && x < (945 + (CREATURE_WIDTH - (bounds.x + bounds.width)) )) {

            //RIGHT
            int tempX = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            int yLeft = (int) (y + bounds.y) / Tile.TILE_WIDTH;
            int yRight = (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
            if (!blockCollision(tempX, yLeft) && !blockCollision(tempX, yRight)) {
//                if (!hasHitSlime(tempX, yLeft) && !hasHitSlime(tempX, yRight)) {
//                    x += xMove;
//                } else System.out.println("You died");
                return xMove;
            }

        }

        if (xMove < 0 && x >=(0 - bounds.x)) {
            //LEFT
            int tempX = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
            int yLeft = (int) (y + bounds.y) / Tile.TILE_WIDTH;
            int yRight = (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
            if (!blockCollision(tempX, yLeft) && !blockCollision(tempX, yRight)) {
//                if (!hasHitSlime(tempX, yLeft) && !hasHitSlime(tempX, yRight)) {
//                    x += xMove;
//                } else System.out.println("You died");
                return xMove;
            }
        }
        return 0;
    }

    @Override
    public float moveY(float yMove, float x, float y) {
        if (yMove > 0) {
            //DOWN
            int tempY = (int) (y+yMove+bounds.y+bounds.height) / Tile.TILE_HEIGHT;
            int xLeft = (int) (x+bounds.x) / Tile.TILE_WIDTH;
            int xRight = (int) (x+bounds.x+bounds.width) / Tile.TILE_WIDTH;
            if (!blockCollision(xLeft, tempY) && !blockCollision(xRight, tempY)) {
//                if (!hasHitSlime(xLeft, tempY) && !hasHitSlime(xRight, tempY)) {
//                    y += yMove;
//                } else System.out.println("You died");
                return yMove;


            }
        }

        if (yMove == 0) {
            int tempY = (int) (y+yMove+2+bounds.y+bounds.height) / Tile.TILE_HEIGHT;
            if (!blockCollision((int) (x+bounds.x) / Tile.TILE_WIDTH, tempY) &&
                    !blockCollision((int) (x+bounds.x+bounds.height) / Tile.TILE_WIDTH, tempY)) {
                //does it one to many times
                return Mechanics.GRAVITY;
            }
        }

        if (yMove < 0 && y >= (0 - (CREATURE_HEIGHT - (bounds.height)))){
            //UP
            int tempY = (int) (y+yMove+bounds.y) / Tile.TILE_HEIGHT;
            int xLeft = (int) (x+bounds.x) / Tile.TILE_WIDTH;
            int xRight = (int) (x+bounds.x+bounds.width) / Tile.TILE_WIDTH;

            if (!blockCollision(xLeft, tempY) && !blockCollision(xRight, tempY)) {
//                if (!hasHitSlime(xLeft, tempY) && !hasHitSlime(xRight, tempY)) {
//                    y += yMove;
//                } else System.out.println("You died");
                return yMove;

            }
        }
        return 0;
    }

    private boolean blockCollision(int x, int y) {
        return world.getTile(x, y).isSolid();
    }

    @Override
    public boolean isTouchingGround() {
        return false;
    }

    @Override
    protected boolean boundsIsValid(Rectangle bounds) {
        return true;
    }
}

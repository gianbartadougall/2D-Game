package com.game.movement;

import com.game.Entity.creature.Creature;
import com.game.tile.Tile;
import com.game.world.World;

import java.awt.*;

import static com.game.utilities.Utils.LEFT;

public abstract class Movement {

    protected World world;
    protected Creature creature;
    protected final int CREATURE_WIDTH;
    protected final int CREATURE_HEIGHT;

    Rectangle bounds;

    public Movement(World world, Creature creature) {
        this.world = world;
        this.creature = creature;
        bounds = creature.getBounds();
        this.CREATURE_WIDTH = Tile.TILE_WIDTH;
        this.CREATURE_HEIGHT = Tile.TILE_HEIGHT;
    }

    public abstract float moveX(float xMove, float x, float y);
    public abstract float moveY(float yMove, float x, float y);

    public void jump(Creature creature) {
        System.out.println("This character does not have jump abilities");
    }

    public abstract boolean isTouchingGround();

    protected abstract boolean boundsIsValid(Rectangle bounds);

    protected boolean outOfBounds(int x, int y, int direction, int speed) {
        if (y/ Tile.TILE_HEIGHT <= World.MIN_WORLD_WIDTH || (y+bounds.y+bounds.height)/Tile.TILE_HEIGHT >= World.MAX_BLOCK_WORLD_HEIGHT) {
            return true;
        }
        if (direction == LEFT) {
            return (x+bounds.x-speed) <= World.MIN_WORLD_WIDTH;
        }
        return (x+bounds.x+bounds.width+speed) >= World.MAX_FLOAT_WORLD_WIDTH;
    }

}

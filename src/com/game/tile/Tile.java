package com.game.tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {



    public static Tile[] tileIds = new Tile[256];
    public static Tile emptyyTile = new EmptyTile(0);
    public static Tile dirtTile = new DirtTile(1);
    public static Tile grassTile = new GrassTile(2);
    public static Tile grassSlop = new GrassSlope(3);
    public static Tile invertedGrassSlope = new InvertedGrassSlope(4);
    public static final int TILE_WIDTH = 50, TILE_HEIGHT = 50;

    public static final int EMPTY_TILE = 0;
    public static final int DIRT_TILE = 1;
    public static final int GRASS_TILE = 2;
    public static final int GRASS_SLOPE = 3;
    public static final int INVERTED_GRASS_SLOPE = 4;




    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        tileIds[id] = this;
    }

    public void tick() {

    }

    public void render(Graphics graphics, int xPos, int yPos) {
        graphics.drawImage(texture, xPos, yPos, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isSolid() {
        return false;
    }

    public int getTileId() {
        return this.id;
    }

    public boolean isSlope() {
        return this.id == 3 || this.id == 4;
    }
}

package com.game.world;

import com.game.Game;
import com.game.graphics.Assets;
import com.game.graphics.GameCamera;
import com.game.tile.Tile;
import com.game.utilities.Utils;

import java.awt.*;

public class World {

    public static int MIN_WORLD_WIDTH = 0;
    public static int MIN_WORLD_HEIGHT = 0;
    public static final int MAX_BLOCK_WORLD_WIDTH = 40;
    public static final int MAX_BLOCK_WORLD_HEIGHT = 22;
    public static final int MAX_FLOAT_WORLD_WIDTH = MAX_BLOCK_WORLD_WIDTH*Tile.TILE_WIDTH;
    public static final int MAX_FLOAT_WORLD_HEIGHT = MAX_BLOCK_WORLD_HEIGHT*Tile.TILE_HEIGHT;

    private GameCamera camera;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tilePositions;
    private int[][][] enemyPositions;
    private int minYRender;
    private int minXRender;
    private int maxYRender;
    private int maxXRender;

    public World(GameCamera camera, String path) {
        this.camera = camera;
        loadWorld(path);
    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] info = file.split("\\s+");

        width = Integer.parseInt(info[0]);
        height = Integer.parseInt(info[1]);
        spawnX = Integer.parseInt(info[2])* Tile.TILE_WIDTH;
        spawnY = Integer.parseInt(info[3])* Tile.TILE_HEIGHT;
        System.out.println("width: " + width);
        int numEnemies = Integer.parseInt(info[4]);
        int characteristics = 3;
        int types = 1;

        System.out.println("spawnX - " + spawnX);
        System.out.println("spawnY - " + spawnY);

        tilePositions = new int[width][height];

        //converting the numbers in the textfile to x and y positions in a int[][]
        for (int y=0; y< height; y++) {
            for (int x = 0; x< width; x++) {
                tilePositions[x][y] = Integer.parseInt(info[(x+y * width)+5]);
            }
        }
    }

    public void render(Graphics graphics) {
        minXRender = (int) Math.max(0, camera.xOffset() / Tile.TILE_WIDTH);
        maxXRender = (int) Math.min(width, (camera.xOffset() + Game.GAME_WIDTH) / Tile.TILE_WIDTH +1);
        minYRender = (int) Math.max(0, camera.yOffset() / Tile.TILE_HEIGHT);
        maxYRender = (int) Math.min(height, (camera.yOffset() + Game.GAME_HEIGHT) / Tile.TILE_HEIGHT +1);
        graphics.drawImage(Assets.background, (int)(0-camera.xOffset()), (int)(0-camera.yOffset()), null);

        for (int y=minYRender; y<maxYRender; y++) {
            for (int x=minXRender; x<maxXRender; x++) {
                getTile(x, y).render(graphics, (int) (x*Tile.TILE_WIDTH - camera.xOffset()), (int) (y*Tile.TILE_HEIGHT - camera.yOffset()));
                graphics.setColor(Color.blue);
                graphics.fillRect((int)((x*Tile.TILE_WIDTH) - camera.xOffset()), (int) ((y*Tile.TILE_HEIGHT) - camera.yOffset()), 2, 2);
            }
        }
    }

    public Tile getTile(int xPos, int yPos) {
        //retrieving id number of the tile at position x, y
        int tileId = tilePositions[xPos][yPos];
        //Setting tile to the tile associated with that id
        Tile tile = Tile.tileIds[tileId];
        if (tile == null) {
            return Tile.dirtTile;
        }
        return tile;
    }

    public Tile getTile1(int xPos, int yPos) {
        System.out.println("checking x: " + xPos + " and y: " + yPos);
        int tileId = tilePositions[xPos][yPos];
        System.out.println("tile id was " + tileId);
        Tile tile = Tile.tileIds[tileId];
        if (tile == null) {
            return Tile.dirtTile;
        }
        System.out.println("tile solid is: " + tile.isSolid());
        return tile;
    }

    public int getTileId(int xPos, int yPos) {
        return getTile(xPos, yPos).getTileId();
    }

    public boolean isSlope(int xPos, int yPos) {
        Tile tile = getTile(xPos, yPos);
        return (tile.getTileId() == Tile.GRASS_SLOPE || tile.getTileId() == Tile.INVERTED_GRASS_SLOPE);
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int getMinYRender() {
        return minYRender * Tile.TILE_HEIGHT;
    }

    public int getMinXRender() {
        return minXRender * Tile.TILE_WIDTH;
    }

    public int getMaxYRender() {
        return maxYRender * Tile.TILE_HEIGHT;
    }

    public int getMaxXRender() {
        return maxXRender * Tile.TILE_WIDTH;
    }
}

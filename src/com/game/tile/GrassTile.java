package com.game.tile;

import com.game.graphics.Assets;

public class GrassTile extends Tile {

    public GrassTile(int id) {
        super(Assets.grassBlock, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}

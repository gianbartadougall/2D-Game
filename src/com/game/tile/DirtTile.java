package com.game.tile;

import com.game.graphics.Assets;

public class DirtTile extends Tile {

    public DirtTile(int id) {
        super(Assets.dirtBlock, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}

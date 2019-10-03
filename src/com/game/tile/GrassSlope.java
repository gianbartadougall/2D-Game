package com.game.tile;

import com.game.graphics.Assets;

public class GrassSlope extends Tile {

    public GrassSlope(int id) {
        super(Assets.grassSlope, id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}

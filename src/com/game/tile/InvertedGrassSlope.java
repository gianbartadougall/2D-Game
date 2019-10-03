package com.game.tile;

import com.game.graphics.Assets;

public class InvertedGrassSlope extends Tile {

    public InvertedGrassSlope(int id) {
        super(Assets.invertedGrassSlope, id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}

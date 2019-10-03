package com.game.graphics;

import com.game.Entity.Entity;
import com.game.Game;

public class GameCamera {

    private float xOffset, yOffset;

    public GameCamera(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    public void centerOnEntity(Entity entity) {
        xOffset = entity.getX() - Game.GAME_WIDTH / 2 + entity.getWidth() / 2;
        yOffset = entity.getY() - Game.GAME_HEIGHT /2 + entity.getHeight() / 2;
    }

    public float xOffset() {
        return xOffset;
    }

    public float yOffset() {
        return yOffset;
    }
}

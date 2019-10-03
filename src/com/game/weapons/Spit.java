package com.game.weapons;

import com.game.Ammunittion.SlimeBall;
import com.game.graphics.GameCamera;

import java.awt.image.BufferedImage;

import static com.game.utilities.Utils.LEFT;

public class Spit extends Weapon {

    private BufferedImage[] texture;
    private int index;

    public Spit(BufferedImage[] texture, float x, float y) {
        super(x, y);
        this.texture = texture;
    }

    @Override
    public SlimeBall shoot(GameCamera camera, int direction, int startX, int startY, int mX, int mY) {
        if (direction == LEFT) {
            index = 0;
        } else index = 1;
        return new SlimeBall(texture[index], startX, startY, direction);
    }
}

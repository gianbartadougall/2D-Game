package com.game.weapons;

import com.game.Ammunittion.Bullet;
import com.game.graphics.GameCamera;

import java.awt.image.BufferedImage;

import static com.game.utilities.Utils.LEFT;

public class Gun extends Weapon{

    private BufferedImage[] bulletTexture;

    public Gun(BufferedImage[] bulletTexture, float x, float y) {
        super(x, y);
        this.bulletTexture = bulletTexture;
    }


    public Bullet shoot(GameCamera camera, int direction, int x1, int y1, int x2, int y2) {
        //These variables calculate the slope of the bullet while keeping the correct start position
        float tempX1 = x1 - camera.xOffset();
        float tempY1 = y1 - camera.yOffset();

        float slope = (((y2 - tempY1) / (x2 - tempX1)));

        int texture = direction == LEFT ? 0 : 1;
        return new Bullet(bulletTexture[texture], x1, y1, slope, direction);
    }
}

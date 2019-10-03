package com.game.weapons;

import com.game.Ammunittion.Ammo;
import com.game.graphics.GameCamera;

public abstract class Weapon {

    protected final int DEFAULT_WEAPON_RANGE = 4;
    protected final int DEFAULT_WEAPON_COOL_DOWN = 500;

    protected GameCamera camera;

    protected float x;
    protected float y;
    protected int range;
    protected int coolDown;

    public Weapon(float x, float y) {
        this.x = x;
        this.y = y;
        setRange(DEFAULT_WEAPON_RANGE);
        setCoolDown(DEFAULT_WEAPON_COOL_DOWN);
    }

    public abstract Ammo shoot(GameCamera camera, int direction, int startX, int startY, int mX, int mY);

    protected void setRange(int range) {
        this.range = range;
    }

    protected void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public int getRange() {
        return range;
    }

}

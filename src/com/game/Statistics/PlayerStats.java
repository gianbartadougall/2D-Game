package com.game.Statistics;

public class PlayerStats {

    private int deathCount;
    private int bulletsShot;

    public void AddDeathCount() {
        this.deathCount += 1;
    }

    public void AddBulletsShot() {
        this.bulletsShot += 1;
    }

    public int getBulletsShot() {
        return bulletsShot;
    }
}

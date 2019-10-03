package com.game.Settings;

public class Settings {

    private int FPS = 1;
    private int level = 0;
    private int renderDistance = 1;

    public Settings() {

    }

    public int FPS() {
        return FPS;
    }

    public int level() {
        return level;
    }

    public int renderDistance() {
        return renderDistance;
    }

    public void updateDistance() {
        renderDistance++;
        if (renderDistance == 3) {
            renderDistance = 0;
        }
    }

    public void updateFPS() {
        FPS++;
        if (FPS == 3) {
            FPS = 0;
        }
    }

    public void updateLevel() {
        level++;
        if (level == 3) {
            level = 0;
        }
    }
}

package com.game.animation;

import java.awt.image.BufferedImage;

public class Animation {

    private int speed, index;
    private BufferedImage[] frames;
    private long lastTime, timer;
    private int cycle;
    private int count;
    private boolean pauseAnimation;
    private int timeWaiting = 0;
    private int pausedTime;
    private boolean pauseFinished;

    public Animation(int speed, BufferedImage[] frames, int cycle) {
        this.speed = speed;
        this.frames = frames;
        this.cycle = cycle;
        count = 0;
        index = 0;
        timer = 0;
        lastTime = 0;
        pauseFinished = true;
    }

    public BufferedImage currentFrame() {
        return frames[index];
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (!pauseAnimation) {
            if (timer > speed) {
                if (count >= (cycle*frames.length-1)) {
                    return;
                }
                timer = 0;
                count++;
                index++;
                if (index >= frames.length) {
                    index = 0;
                }
            }
        } else {
            timeWaiting++;
            if (timeWaiting == pausedTime) {
                pauseAnimation = false;
                pauseFinished = true;
                timeWaiting = 0;
                resetCycle();
            }
        }
    }

    public void pauseAnimation(int time) {
        pauseAnimation = true;
        pauseFinished = false;
        pausedTime = time;
    }

    public void resetCycle() {
        count = 0;
        index = 0;
    }

    public BufferedImage lastFrame() {
        return frames[frames.length-1];
    }

    public void resetAnimation() {
        index = 0;
    }

    public boolean isPauseFinished() {
        return pauseFinished;
    }
}

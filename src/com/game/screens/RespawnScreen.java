package com.game.screens;

import com.game.graphics.Assets;

import java.awt.*;

public class RespawnScreen extends Screen {

    private double width;

    public RespawnScreen() {

    }

    @Override
    public void tick() {
        width += 3;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.respawn, 0, 0, null);
        graphics.setColor(Color.green);
        graphics.drawRect(199, 260, 601,31);
        graphics.fillRect(200, 261, (int)width, 30);
        graphics.setFont(new Font("Courier New", Font.BOLD, 28));
        graphics.setColor(Color.black);
        graphics.drawString("R E S P A W N I N G", 330, 286);
    }

    public double getWidth() {
        return width;
    }

    public void resetWidth() {
        width = 0;
    }
}

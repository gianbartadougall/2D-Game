package com.game.screens;

import com.game.Game;
import com.game.Settings.Settings;
import com.game.graphics.Assets;
import com.game.input.MouseManager;

import java.awt.*;

public class SettingsScreen extends Screen {

    private Settings settings = new Settings();

    private Game game;
    private MouseManager mouse;

    private Rectangle fps = new Rectangle(388, 183, 173, 34 );
    private Rectangle difficulty = new Rectangle(388, 261, 173, 34 );
    private Rectangle chunkRendering = new Rectangle(388, 339, 173, 34 );
    private Rectangle back = new Rectangle(388, 417, 173, 34 );

    public SettingsScreen(Game game, MouseManager mouse) {
        this.game = game;
        this.mouse = mouse;
    }

    @Override
    public void tick() {
        Point point = new Point(mouse.x(), mouse.y());


        if (fps.contains(point) && mouse.leftClick()) {
            updateFPS();
        } else if (difficulty.contains(point) && mouse.leftClick()) {
            updateDifficulty();
        } else if (chunkRendering.contains(point) && mouse.leftClick()) {
            updateChunkRendering();
        } else if (back.contains(point) && mouse.leftClick()) {
            game.setScreen(game.getLastScreen());
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.settingsScreen, 0, 0, null);
        graphics.setColor(new Color(35, 109, 41));

        graphics.setFont(new Font("Courier new", Font.BOLD, 24));
        graphics.drawString("FPS", ((173/2)+370), 170);
        graphics.drawString("Difficulty", ((173/2)+317), 245);
        graphics.drawString("Render Distance", ((173/2)+285), 325);
        graphics.drawString("Back", ((173/2)+360), 440);
        graphics.setColor(Color.black);

        graphics.drawString(getfps(), ((173/2)+370), 210);
        graphics.drawString(getDifficulty(), ((173/2)+306), 285);
        graphics.drawString(getRenderDistance(), ((173/2)+340), 360);
    }

    private String getfps() {
        switch (settings.FPS()) {
            case 1:
                return "30";
            case 2:
                return "60";
            default:
                return "120";
        }
    }

    private String getDifficulty() {
        switch (settings.level()) {
            case 1:
                return "Baby";
            case 2:
                return "Skilled";
            default:
                return "Professional";
        }
    }

    private String getRenderDistance() {
        switch (settings.renderDistance()) {
            case 1:
                return "1 block";
            case 2:
                return "2 blocks";
            default:
                return "4 blocks";
        }
    }


    private void updateDifficulty() {
        settings.updateLevel();
    }

    private void updateFPS() {
        settings.updateFPS();
    }

    private void updateChunkRendering() {
        settings.updateDistance();
    }
}

package com.game.screens;

import com.game.Game;
import com.game.graphics.Assets;
import com.game.input.MouseManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuScreen extends Screen {

    private MouseManager mouse;
    private Game game;
    private Rectangle start = new Rectangle(403, 223, 194, 44);
    private Rectangle settings = new Rectangle(403, 290, 194, 44);

    public MenuScreen(Game game, MouseManager mouse) {
        this.mouse = mouse;
        this.game = game;
    }

    @Override
    public void tick() {
        if (mouse.leftPress()) {
            if (start.contains(mouse.x(), mouse.y())) {
                game.setScreen(game.getGameScreen());
            }
            if (settings.contains(mouse.x(), mouse.y())) {
                game.setScreen(game.getSettingsScreen());
            }

        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.drawImage(getCurrentScreen(), 0, 0, null);
    }

    private BufferedImage getCurrentScreen() {
        if (start.contains(mouse.x(), mouse.y())) {
            return Assets.menuStart;
        } else if (settings.contains(mouse.x(), mouse.y())) {
            return Assets.menuSettings;
        }
        return Assets.menuScreen;
    }

}

package com.game.screens;

import com.game.Entity.EntityManager;
import com.game.Game;
import com.game.Statistics.GameStats;
import com.game.Statistics.PlayerStats;
import com.game.graphics.Assets;
import com.game.graphics.GameCamera;
import com.game.input.MouseManager;
import com.game.world.World;

import java.awt.*;

public class GameScreen extends Screen {

    private Game game;
    private GameCamera camera;
    private World world;
    EntityManager entityManager;
    MouseManager mouse;

    Rectangle resume = new Rectangle(((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)-150), 298, 82);
    Rectangle settings = new Rectangle(((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)-50), 298, 82);
    Rectangle exit = new Rectangle(((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)+44), 298, 82);

    public GameScreen(Game game, GameStats gameStats, PlayerStats playerStats, MouseManager mouse) {
        this.game = game;
        this.mouse = mouse;
        camera = new GameCamera(0, 0);
        world = new World(camera, "res/levels/world1.txt");
        entityManager = new EntityManager(world, game, gameStats, playerStats, camera, mouse);
    }

    //JUST FIXING ALL THE SCREEN CHANGING - ATM IT IS A TAD BUGGY

    @Override
    public void tick() {
        if (!game.getKeyManager().escape()) {
            entityManager.tick();
        } else {
            if (mouse.leftPress() && resume.contains(mouse.x(), mouse.y())) {
                game.getKeyManager().setEsc(false);
            } else if (mouse.leftPress() && settings.contains(mouse.x(), mouse.y())) {
                game.setScreen(game.getSettingsScreen());
                game.getKeyManager().setEsc(false);
            } else if (mouse.leftPress() && exit.contains(mouse.x(), mouse.y())) {
                game.setScreen(game.getMenuScreen());
                game.getKeyManager().setEsc(false);
                game.restartGame();
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        world.render(graphics);
        entityManager.render(graphics);
        if (game.getKeyManager().escape()) {
            if (resume.contains(mouse.x(), mouse.y())) {
                graphics.drawImage(Assets.pauseResume, ((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)-150), null);
            } else if (settings.contains(mouse.x(), mouse.y())) {
                graphics.drawImage(Assets.pauseSettings, ((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)-150), null);
            } else if (exit.contains(mouse.x(), mouse.y())) {
                graphics.drawImage(Assets.pauseExit, ((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)-150), null);
            } else graphics.drawImage(Assets.pauseScreen, ((Game.GAME_WIDTH/2)-150), ((Game.GAME_HEIGHT/2)-150), null);
        }
    }

    public void resetCharacter() {
        entityManager.resetPlayer();
    }
}

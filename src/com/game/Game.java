package com.game;

import com.game.Statistics.GameStats;
import com.game.Statistics.PlayerStats;
import com.game.display.Display;
import com.game.graphics.Assets;
import com.game.input.KeyManager;
import com.game.input.MouseManager;
import com.game.screens.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 600;

    private Display display;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private Thread thread;
    boolean running = false;

    //Screen Manager + screens
    private ScreenManager screenManager;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private SettingsScreen settingsScreen;
    private RespawnScreen respawnScreen;
    private Screen lastScreen;

    //Keyboard input
    private KeyManager keyManager;

    //display characteristics
    private String title;

    //Game loop measurements
    private int fps = 60;

    //Mouse manager
    MouseManager mouseManager;

    //Game Stats
    GameStats gameStats = new GameStats();
    PlayerStats playerStats = new PlayerStats();

    public Game(String title) {
        this.title = title;
    }

    @Override
    public void run() {
        initialize();

        double timePerTick = 1_000_000_000 / fps;
        double delta = 0;
        long currentTime;
        long lastTime = System.nanoTime();

        long timer = 0;
        int ticks = 0;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime) / timePerTick;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >=1) {

                if (screenManager.currentScreen() == respawnScreen) {
                    if (respawnScreen.getWidth() == 600) {
                        respawnScreen.resetWidth();
                        gameScreen.resetCharacter();
                        setScreen(gameScreen);
                    }
                }
                update();
                render();
                delta--;
                ticks++;
            }

            if (timer >= 1_000_000_000) {
//                System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

    private void render() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        //draw Images
        if (screenManager.currentScreen() != null) {
            screenManager.currentScreen().render(graphics);
        }
        //stop drawing images
        bufferStrategy.show();
        graphics.dispose();

    }

    private void update() {
        keyManager.listenForInput();

        if (screenManager.currentScreen() != null) {
            screenManager.currentScreen().tick();
            if (screenManager.currentScreen() != settingsScreen) {
                lastScreen = screenManager.currentScreen();
            }
        }

    }

    private void initialize() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        //creating display and adding keyListener to display
        display = new Display(title, GAME_WIDTH, GAME_HEIGHT);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        //Load Images
        Assets.loadSpriteSheet();

        //assign a screen
        gameScreen = new GameScreen(this, gameStats, playerStats, mouseManager);
        menuScreen = new MenuScreen(this, mouseManager);
        respawnScreen = new RespawnScreen();
        settingsScreen = new SettingsScreen(this, mouseManager);
        screenManager = new ScreenManager(gameScreen);
    }

    public void startGame() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stopGame() {
        if (!running) {
            return;
        }
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Getters and Setters

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public void setScreen(Screen screen) {
        screenManager.setCurrentScreen(screen);
    }

    public void restartGame() {
        this.gameScreen = new GameScreen(this, gameStats, playerStats, mouseManager);
    }

    public Screen getLastScreen() {
        return lastScreen;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public SettingsScreen getSettingsScreen() {
        return settingsScreen;
    }

    public RespawnScreen getRespawnScreen() {
        return respawnScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
}

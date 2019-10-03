package com.game.graphics;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int GRID_WIDTH = 50;
    private static final int GRID_HEIGHT = 50;

    public static BufferedImage grassBlock, dirtBlock, grassSlope, invertedGrassSlope, emptyTile;
    public static BufferedImage[] playerCrouchingRight, playerCrouchingLeft, playerMovingLeft, playerMovingRight,
                                  playerJumping, playerIdleRight, playerIdleLeft, slime, bullet, playerShootLeft,
                                  playerShootRight;
    public static BufferedImage[] slimeLeft, slimeRight, slimeShootLeft, slimeShootRight;
    public static BufferedImage menuScreen, menuStart, menuSettings, settingsScreen;
    public static BufferedImage background, respawn, pauseScreen, pauseResume, pauseSettings, pauseExit;

    public static void loadSpriteSheet() {
        ImageLoader imageLoader = new ImageLoader();
        SpriteSheet sheet = new SpriteSheet(imageLoader.loadImage("/textures/blocktextures.png"));

        //Background
        background = new ImageLoader().loadImage("/textures/background.png");

        //RespawnScreen
        respawn = new ImageLoader().loadImage("/textures/respawnScreen.png");

        //TILES
        grassBlock = sheet.crop(0, 0, GRID_WIDTH, GRID_HEIGHT);
        dirtBlock = sheet.crop(GRID_WIDTH, 0, GRID_WIDTH, GRID_HEIGHT);
        grassSlope = sheet.crop(GRID_WIDTH*2, 0, GRID_WIDTH, GRID_HEIGHT);
        invertedGrassSlope = sheet.crop(GRID_WIDTH*3, 0, GRID_WIDTH, GRID_HEIGHT);
        emptyTile = new ImageLoader().loadImage("/textures/emptyTile.png");

        //HERO
        playerCrouchingRight = new BufferedImage[2];
        playerCrouchingRight[0] = sheet.crop(GRID_WIDTH*5, 0, GRID_WIDTH, GRID_HEIGHT);
        playerCrouchingRight[1] = sheet.crop(GRID_WIDTH*4, 0, GRID_WIDTH, GRID_HEIGHT);

        playerCrouchingLeft = new BufferedImage[2];
        playerCrouchingLeft[0] = sheet.crop(GRID_WIDTH*6, 0, GRID_WIDTH, GRID_HEIGHT);
        playerCrouchingLeft[1] = sheet.crop(GRID_WIDTH*7, 0, GRID_WIDTH, GRID_HEIGHT);

        SpriteSheet leftRight = new SpriteSheet(imageLoader.loadImage("/textures/heromovementleft-right.png"));

        playerMovingRight = new BufferedImage[7];
        playerMovingRight[0] = leftRight.crop(0, 0, GRID_WIDTH, GRID_HEIGHT);
        playerMovingRight[1] = leftRight.crop(GRID_WIDTH,0 , GRID_WIDTH, GRID_HEIGHT);
        playerMovingRight[2] = leftRight.crop(GRID_WIDTH*2, 0, GRID_WIDTH, GRID_HEIGHT);
        playerMovingRight[3] = leftRight.crop(GRID_WIDTH*3, 0, GRID_WIDTH, GRID_HEIGHT);
        playerMovingRight[4] = leftRight.crop(GRID_WIDTH*4, 0, GRID_WIDTH, GRID_HEIGHT);
        playerMovingRight[5] = leftRight.crop(GRID_WIDTH*5, 0, GRID_WIDTH, GRID_HEIGHT);
        playerMovingRight[6] = leftRight.crop(GRID_WIDTH*6, 0, GRID_WIDTH, GRID_HEIGHT);

        playerMovingLeft = new BufferedImage[7];
        playerMovingLeft[0] = leftRight.crop(GRID_WIDTH*6, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        playerMovingLeft[1] = leftRight.crop(GRID_WIDTH*5, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        playerMovingLeft[4] = leftRight.crop(GRID_WIDTH*2, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        playerMovingLeft[2] = leftRight.crop(GRID_WIDTH*4, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        playerMovingLeft[3] = leftRight.crop(GRID_WIDTH*3, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        playerMovingLeft[5] = leftRight.crop(GRID_WIDTH, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        playerMovingLeft[6] = leftRight.crop(0, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);

        playerJumping = new BufferedImage[3];

        playerIdleRight = new BufferedImage[1];
        playerIdleRight[0] = sheet.crop(GRID_WIDTH*4, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);

        playerIdleLeft = new BufferedImage[1];
        playerIdleLeft[0] = sheet.crop(GRID_WIDTH*12, GRID_HEIGHT*2, GRID_WIDTH, GRID_HEIGHT);

        SpriteSheet heroStandShoot = new SpriteSheet(imageLoader.loadImage("/textures/heroStandAndShoot.png"));
        playerShootLeft = new BufferedImage[4];
        playerShootLeft[0] = heroStandShoot.crop(GRID_WIDTH*7, 0, GRID_WIDTH, GRID_HEIGHT);
        playerShootLeft[1] = heroStandShoot.crop(GRID_WIDTH*4, 0, GRID_WIDTH, GRID_HEIGHT);
        playerShootLeft[2] = heroStandShoot.crop(GRID_WIDTH*5, 0, GRID_WIDTH, GRID_HEIGHT);
        playerShootLeft[3] = heroStandShoot.crop(GRID_WIDTH*6, 0, GRID_WIDTH, GRID_HEIGHT);

        playerShootRight = new BufferedImage[4];
        playerShootRight[0] = heroStandShoot.crop(GRID_WIDTH*3, 0, GRID_WIDTH, GRID_HEIGHT);
        playerShootRight[1] = heroStandShoot.crop(0, 0, GRID_WIDTH, GRID_HEIGHT);
        playerShootRight[2] = heroStandShoot.crop(GRID_WIDTH, 0, GRID_WIDTH, GRID_HEIGHT);
        playerShootRight[3] = heroStandShoot.crop(GRID_WIDTH*2, 0, GRID_WIDTH, GRID_HEIGHT);

        //CREATURES

        //Slime
        slimeRight = new BufferedImage[2];
        slimeRight[0] = sheet.crop(0, GRID_HEIGHT*3, GRID_WIDTH, GRID_HEIGHT);
        slimeRight[1] = sheet.crop(GRID_WIDTH, GRID_HEIGHT*3, GRID_WIDTH, GRID_HEIGHT);

        slimeLeft = new BufferedImage[2];
        slimeLeft[0] = sheet.crop(0, GRID_HEIGHT*2, GRID_WIDTH, GRID_HEIGHT);
        slimeLeft[1] = sheet.crop(GRID_WIDTH, GRID_HEIGHT*2, GRID_WIDTH, GRID_HEIGHT);

        SpriteSheet slimeShoot = new SpriteSheet(imageLoader.loadImage("/textures/SlimeShoot1.png"));
        slimeShootLeft = new BufferedImage[5];
        slimeShootLeft[0] = slimeShoot.crop(GRID_WIDTH*4, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        slimeShootLeft[1] = slimeShoot.crop(GRID_WIDTH*3, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        slimeShootLeft[2] = slimeShoot.crop(GRID_WIDTH*2, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        slimeShootLeft[3] = slimeShoot.crop(GRID_WIDTH, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);
        slimeShootLeft[4] = slimeShoot.crop(0, GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT);

        slimeShootRight = new BufferedImage[5];
        slimeShootRight[0] = slimeShoot.crop(0, 0, GRID_WIDTH, GRID_HEIGHT);
        slimeShootRight[1] = slimeShoot.crop(GRID_WIDTH, 0, GRID_WIDTH, GRID_HEIGHT);
        slimeShootRight[2] = slimeShoot.crop(GRID_WIDTH*2, 0, GRID_WIDTH, GRID_HEIGHT);
        slimeShootRight[3] = slimeShoot.crop(GRID_WIDTH*3, 0, GRID_WIDTH, GRID_HEIGHT);
        slimeShootRight[4] = slimeShoot.crop(GRID_WIDTH*4, 0, GRID_WIDTH, GRID_HEIGHT);



        //AMMUNITION

        //Bullets
        bullet = new BufferedImage[2];
        SpriteSheet bullets = new SpriteSheet(imageLoader.loadImage("/textures/bullets.png"));
        bullet[0] = bullets.crop(0, 0, GRID_WIDTH, GRID_HEIGHT);
        bullet[1] = bullets.crop(GRID_WIDTH, 0, GRID_WIDTH, GRID_HEIGHT);

        //Slime balls

        //MenuScreen
        menuScreen = new ImageLoader().loadImage("/textures/backgroundMenu.png");
        menuStart = new ImageLoader().loadImage("/textures/backgroundMenuStart.png");
        menuSettings = new ImageLoader().loadImage("/textures/backgroundMenuSetting.png");
        settingsScreen = new ImageLoader().loadImage("/textures/settingsScreen.png");
        pauseScreen = new ImageLoader().loadImage("/textures/pauseScreen.png");
        pauseResume = new ImageLoader().loadImage("/textures/pauseResume.png");
        pauseSettings = new ImageLoader().loadImage("/textures/pauseSettings.png");
        pauseExit = new ImageLoader().loadImage("/textures/pauseExit.png");
    }
}

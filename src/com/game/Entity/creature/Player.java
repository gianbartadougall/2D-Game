package com.game.Entity.creature;

import com.game.Ammunittion.Ammo;
import com.game.Entity.EntityManager;
import com.game.Game;
import com.game.Statistics.PlayerStats;
import com.game.animation.Animation;
import com.game.graphics.Assets;
import com.game.graphics.GameCamera;
import com.game.input.MouseManager;
import com.game.movement.DefaultMovement;
import com.game.weapons.Gun;
import com.game.world.World;
import npcAttack.PlayerAttack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.game.utilities.Utils.LEFT;

public class Player extends Creature {

    private MouseManager mouse;
    private GameCamera camera;
    private PlayerStats stats;

    private Game game;
    private Animation movingLeft, movingRight, crouchingRight, crouchingLeft,
                      idleRight, idleLeft, standShootLeft, standShootRight;

    private Gun gun;
//    private ArrayList<Bullet> bullets;

    private boolean playerRunning;
    private boolean shootRequested;
    private boolean crouching;
    private boolean crouchHitBox;
    private int playerDirection;

    private PlayerAttack attack;

    //Time variables
    private long currentTime;
    private double delta = 0;
    private long lastTime = System.currentTimeMillis();

    public Player(Game game, World world, EntityManager entityManager, float xPos, float yPos, PlayerStats stats, GameCamera camera, MouseManager mouse) {
        super(world, entityManager, xPos, yPos);
        this.camera = camera;
        this.stats = stats;
        this.game = game;
        this.mouse = mouse;
        this.entityManager = entityManager;
        gun = new Gun(Assets.bullet, 5, 0);
        attack = new PlayerAttack(stats, gun, 6);
        initialize();
    }

    private void initialize() {
        setCreatureSpeed(3);
        setCreatureSlopeSpeed(speed);
        createAnimations();
        setHealth(10);
    }

    @Override
    public void tick() {
        updateAnimations();
        updateMovement();
        updateBullets();
        checkEntityCollisions();
        move();
        camera.centerOnEntity(this);
        updateJump();
    }

    private void updateHitBox() {
        if (crouching && !crouchHitBox) {
            setCrouchHitBox();
            crouchHitBox = true;
            return;
        }

        if (!crouching && crouchHitBox) {
            setEntityHitBox();
            crouchHitBox = false;
        }
    }

    @Override
    protected void updateMovement() {
        currentTime = System.currentTimeMillis();
        delta += (currentTime - lastTime);
        lastTime = currentTime;

        xMove = 0; yMove = 0;

        playerDirection = checkMouseDirection();
        crouching = game.getKeyManager().down;
        updateHitBox();

        if (game.getMouseManager().leftPress() && delta >= gun.getCoolDown()) {
            shootRequested = true;
            attack.shoot(camera, playerDirection, (int)bulletStartX(playerDirection),
                                                  (int)bulletStartY(playerDirection),
                                                   mouse.x(), mouse.y());
            delta = 0;
        }

        if (game.getKeyManager().up && !initiateJump) {
            initiateJump = true;
        }

        if (!crouching || (!movement.isTouchingGround())) {

            boolean left = game.getKeyManager().left;
            boolean right = game.getKeyManager().right;

            if (left) {
                xMove = -speed;
            }

            if (right) {
                xMove = speed;
            }

            playerRunning = left || right;
        }
    }

    private int checkMouseDirection() {
        return (((x-500) + mouse.x()) < x) ? 1 : 2;
    }

    @Override
    public void render(Graphics graphics) {
        float xDiff = camera.xOffset();
        float yDiff = camera.yOffset();
        //Render player
        graphics.drawImage(getCurrentAnimationFrame(), (int) (x-camera.xOffset()), (int) (y-camera.yOffset()), null);

        //render bullet
        ArrayList<Ammo> bullets = attack.getAmmo();
        for (Ammo b : bullets) {
            graphics.drawImage(b.getTextures(), (int)((b.getHitBox().x)-xDiff), (int)((b.getHitBox().y)-yDiff), null);
        }
    }

    private BufferedImage getCrouchingPosition() {
        return playerDirection == LEFT ? crouchingLeft.currentFrame() : crouchingRight.currentFrame();
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (crouching) {
            return getCrouchingPosition();
        } else if (playerRunning) {
            return getRunningPosition();
        }

        if (shootRequested) {
            return getShootPosition();
        }
        resetCrouch();
        return getIdlePosition();
    }

    private BufferedImage getShootPosition() {
        return playerDirection == LEFT ? standShootLeft.currentFrame() : standShootRight.currentFrame();
    }

    private BufferedImage getIdlePosition() {
        return playerDirection == LEFT ? idleLeft.currentFrame() : idleRight.currentFrame();
    }

    private BufferedImage getRunningPosition() {
        return playerDirection == LEFT ? movingLeft.currentFrame() : movingRight.currentFrame();
    }

    private void resetCrouch() {
        crouchingLeft.resetCycle();
        crouchingRight.resetCycle();
    }

    private void updateBullets() {
        ArrayList<Ammo> bullets = attack.getAmmo();
        for (int i=0; i<bullets.size(); i++) {
            Ammo b = bullets.get(i);
            if (b.isVisible()) {
                b.tick();
                if (ammoHitAnObject(b)) {
                    bullets.remove(i);
                }
            } else bullets.remove(b);
        }
    }

    private void updateJump() {
        if (initiateJump) {
            initiateJump = false;
        }
    }

    private float bulletStartX(int direction) {
        return direction == LEFT ? x-10 : x+bounds.x+bounds.width+20;
    }

    private float bulletStartY(int position) {
        return position == 1 ? y+13 : y+17;
    }

    @Override
    protected void createAnimations() {
        crouchingRight = new Animation(100, Assets.playerCrouchingRight, 1);
        crouchingLeft = new Animation(100, Assets.playerCrouchingLeft, 1);
        movingRight = new Animation(100, Assets.playerMovingRight, 100000);
        movingLeft = new Animation(100, Assets.playerMovingLeft, 100000);
        idleRight = new Animation(500, Assets.playerIdleRight, 100000);
        idleLeft = new Animation(500, Assets.playerIdleLeft, 100000);
        standShootRight = new Animation(500, Assets.playerShootRight, 1);
        standShootLeft = new Animation(500, Assets.playerShootLeft, 1);
    }

    @Override
    protected void updateAnimations() {
        movingLeft.tick(); movingRight.tick();
        crouchingLeft.tick(); crouchingRight.tick();
        idleLeft.tick(); idleRight.tick();
        standShootLeft.tick(); standShootRight.tick();
    }

    @Override
    protected void setCreatureMovement() {
        movement = new DefaultMovement(world, this);
    }

    @Override
    protected void setEntityHitBox() {
        bounds = playerDirection == LEFT ? new Rectangle(16, 0, 20, 50) :
                new Rectangle(12, 0, 20, 50);
    }

    private void setCrouchHitBox() {
        bounds = playerDirection == LEFT ? new Rectangle(16, 5, 20, 45) :
                                           new Rectangle(12, 5, 20, 45);
    }

    @Override
    protected void setCreatureSlopeSpeed(int slopeSpeed) {
        this.slopeSpeed = slopeSpeed;
    }

    @Override
    protected void setCreatureSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    protected void died() {
        game.setScreen(game.getRespawnScreen());
        System.err.println("YOU DIED");
        stats.AddDeathCount();
    }
}

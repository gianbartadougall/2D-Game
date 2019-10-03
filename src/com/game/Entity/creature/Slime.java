package com.game.Entity.creature;

import com.game.Ammunittion.SlimeBall;
import com.game.Entity.EntityManager;
import com.game.animation.Animation;
import com.game.graphics.Assets;
import com.game.graphics.GameCamera;
import com.game.movement.SlimeMovement;
import com.game.weapons.Spit;
import com.game.world.World;
import npcAttack.DefaultEnemyDetection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.game.utilities.Utils.LEFT;
import static com.game.utilities.Utils.RIGHT;

public class Slime extends Creature {

    private GameCamera camera;
    private Animation slimeRight, slimeLeft, slimeShootLeft, slimeShootRight;
    private DefaultEnemyDetection detectEnemy;
    private Spit slimeAttack;
    private int direction;
    private float move;
    private boolean enemyFound;
    private boolean lastAnimation;
    //Time variables
    private long currentTime;
    private double delta = 0;
    private long lastTime = System.currentTimeMillis();

    private ArrayList<SlimeBall> slimeBalls;

    public Slime(World world, EntityManager entityManager, float xPos, float yPos, ArrayList<Creature> enemies, GameCamera camera) {
        super(world, entityManager, xPos, yPos);
        this.camera = camera;
        createAnimations();
        slimeBalls = new ArrayList<>();
        detectEnemy = new DefaultEnemyDetection(2, 4, this, enemies, world);
        slimeAttack = new Spit(Assets.bullet, 25, 0);
        setHealth(5);
    }

    @Override
    public void tick() {
        calcTime();
        updateMovement();
        updateAnimations();
        searchForEnemies();
        updateSlimeBalls(slimeBalls);
        checkSlimeBallHits();
    }

    private void calcTime() {
        currentTime = System.currentTimeMillis();
        delta += (currentTime - lastTime);
        lastTime = currentTime;
    }

    protected void updateMovement() {
        move = movement.moveX(xMove, x, y);
        if (enemyFound) {return;}
        x += move;
    }

    @Override
    protected void createAnimations() {
        slimeRight = new Animation(1500, Assets.slimeRight, 100000);
        slimeLeft = new Animation(1500, Assets.slimeLeft, 100000);
        slimeShootLeft = new Animation(50, Assets.slimeShootLeft, 100000);
        slimeShootRight = new Animation(50, Assets.slimeShootRight, 100000);
    }

    @Override
    protected void updateAnimations() {
        slimeRight.tick();
        slimeLeft.tick();
        slimeShootLeft.tick();
        slimeShootRight.tick();
    }

    private void searchForEnemies() {
        direction = move < 0 ? LEFT : RIGHT;
        search(direction);
    }

    private void search(int direction) {
        if (detectEnemy.tick(direction)) {
            enemyFound = true;
            if (delta >= slimeAttack.getCoolDown()) {
                slimeBalls.add(slimeAttack.shoot(camera, direction, bulletStartX(direction), bulletStartY(), 4, 4));
                delta = 0;
            }
        } else enemyFound = false;
    }

    private void checkSlimeBallHits() {
        for (int i=0; i<slimeBalls.size(); i++) {
            SlimeBall ammo = slimeBalls.get(i);
            if (ammoHitAnObject(ammo)) {
                slimeBalls.remove(i);
            }
        }
    }

    private int bulletStartX(int direction) {
        if (direction == LEFT) {
            return (int) x;
        } else {
            return(int) x+bounds.x+bounds.width;
        }
    }

    private int bulletStartY() {
        return (int) y+bounds.y;
    }

    private void updateSlimeBalls(ArrayList<SlimeBall> slimeBalls) {
        for (int i = 0; i < slimeBalls.size(); i++) {
            SlimeBall b = slimeBalls.get(i);
            if (!b.isVisible()) {
                slimeBalls.remove(b);
            } else b.tick();
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(getCurrentAnimationFrame(), (int) (x-camera.xOffset()), (int) (y-camera.yOffset()), null);
//        graphics.drawImage(getCurrentAnimationFrame(), (int) (x), (int) (y), null);
        graphics.setColor(Color.RED);
        graphics.drawRect((int) (this.getHitBox().x - camera.xOffset()), (int) ((y+bounds.y) - camera.yOffset()), this.getHitBox().width, bounds.height);
        graphics.setColor(Color.blue);
        graphics.drawRect((int) (x - camera.xOffset()), (int) (y -camera.yOffset()), 0, 100);

        int d;
        d = move >0 ? RIGHT : LEFT;
        Rectangle r = detectEnemy.findRange(d, (int) x, (int) y, bounds);


        graphics.setColor(Color.red);
        graphics.drawRect((int) (r.x - camera.xOffset()), (int) (r.y - camera.yOffset()), r.width, r.height);
        graphics.setColor(Color.blue);

        for (SlimeBall b : slimeBalls) {
            graphics.drawImage(b.getTextures(), (int) (b.getHitBox().getX() - camera.xOffset()), (int) (b.getHitBox().getY() - camera.yOffset()), null);
            graphics.setColor(Color.green);
            graphics.drawRect((int) (b.getHitBox().getX() - camera.xOffset()), (int) (b.getHitBox().getY() - camera.yOffset()), 3, 100 );
        }

    }

    private BufferedImage getCurrentAnimationFrame() {
        if (direction == LEFT) {
            return enemyFound ? getAnimationShoot(slimeShootLeft) : getAnimationMove(slimeLeft);
        }
        return enemyFound ? getAnimationShoot(slimeShootRight) : getAnimationMove(slimeRight);
    }

    private BufferedImage getAnimationMove(Animation animation) {
        lastAnimation = false;
        animation.resetAnimation();
        return animation.currentFrame();
    }

    private BufferedImage getAnimationShoot(Animation animation) {
        checkIfLastFrameShown(animation);
        return lastAnimation ? animation.lastFrame() : animation.currentFrame();
    }

    private void checkIfLastFrameShown(Animation animation) {
        if (animation.currentFrame() == animation.lastFrame()) {
            lastAnimation = true;
        }
    }

    @Override
    protected void setCreatureMovement() {
        movement = new SlimeMovement(world, this, speed);
    }

    @Override
    protected void setHealth(int newHealth) {
        health = newHealth;
    }

    @Override
    protected void setEntityHitBox() {
        bounds = new Rectangle(4, 17, 41, 32);
    }

    @Override
    protected void died() {
        System.out.println("You killed a slime");
    }
}

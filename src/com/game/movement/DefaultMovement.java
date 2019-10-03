package com.game.movement;

import com.game.Entity.creature.Creature;
import com.game.tile.Tile;
import com.game.world.World;

import java.awt.*;

import static com.game.utilities.Utils.*;

public class DefaultMovement extends Movement implements Runnable {

    private Creature creature;
    private final float MAX_HEIGHT = 0f;
    private boolean touchingGround;
    private boolean jumping;
    private float distanceUpwards;
    private boolean rightXCollision;
    private boolean leftXCollision;
    private boolean yCollision;
    private float velocity;
    private boolean fallStart;

    public DefaultMovement(World world, Creature creature) {
        super(world, creature);
        this.creature = creature;
        if (!boundsIsValid(creature.getBounds())) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("You have created a creature with invalid Hit box bounds");
                System.exit(1);
            }
        }
    }

    private boolean outOfBounds(float x, float y) {
        return (x <=World.MIN_WORLD_WIDTH || x >= World.MAX_FLOAT_WORLD_WIDTH ||
            y <= World.MIN_WORLD_HEIGHT || y >= World.MAX_FLOAT_WORLD_HEIGHT);
    }

    private void updateCollisionBox() {
        bounds = creature.getBounds();
    }

    @Override
    public float moveX(float xMove, float x, float y) {
        updateCollisionBox();

//        System.out.println("the bottom y of the character is " + (y+bounds.y+bounds.height));
//        System.out.println("the height of the charcater is " + bounds.height);

        if (outOfBounds(x, y)) {
            return 0;
        }

        if (xMove > 0) {
            if (rightXCollision) {
                xMove = 1;
            }
            //RIGHT
            int tempX = findX(x, xMove, RIGHT);
            int yTop = findY(y, 0, TOP);
            int yBottom = findY(y, 0, BOTTOM);
            if (collision1(tempX, yTop) || collision1(tempX, yBottom)) {
                rightXCollision = true;
                return 0;
            }

            rightXCollision = false;
            if (!isSlope(tempX, yBottom)) {
                return xMove;
            }

            if (getTileId(tempX, yBottom) == 3) {
                return -400;
            }
            return -200;
        }

        if (xMove < 0) {
            if (leftXCollision) {
                xMove = -1;
            }
            //LEFT
            int tempX = findX(x, xMove, LEFT);
            int yTop = findY(y, 0, TOP);
            int yBottom = findY(y, 0, BOTTOM);
            if (collision(tempX, yTop) || collision(tempX, yBottom)) {
                leftXCollision = true;
                return 0;
            }

            leftXCollision = false;
            if (!isSlope(tempX, yBottom)) {
                return xMove;
            }

            if (getTileId(tempX, yBottom) == 3) {
                //up
                return -100;
            } else {
                return -300;
            }
        }

        return 0;
    }

    private int findY(float y, float yMove, int height) {
        return height == TOP ? (int) (y + yMove + bounds.y) / Tile.TILE_WIDTH :
                               (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
    }

    private int findX(float x, float xMove, int direction) {
        return direction == LEFT ? (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH :
                                   (int)(x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
    }

    @Override
    public float moveY(float yMove, float x, float y) {
        //Jumping
        if (jumping) {
            float temp = distanceUpwards;
            distanceUpwards = 0;
            return temp;
        }

        //Gravity
        if (yMove == 0.0f) {
            velocity += addVelocity();
            velocity = checkVelocity();

            int tempYInt = findY(y, velocity, BOTTOM);
            int xLeft =  findX(x, 0, LEFT);
            int xRight = findX(x, 0, RIGHT);

            if (collision(xLeft, tempYInt) || collision(xRight, tempYInt)) {
                touchingGround = true;
                yCollision = true;
                fallStart = true;
                return 0;
            }

            fallStart = false;
            yCollision = false;
            touchingGround = false;

            int leftSide = getTileId(xLeft, tempYInt);
            int rightSide = getTileId(xRight, tempYInt);

            if (leftSide == Tile.EMPTY_TILE && rightSide == Tile.EMPTY_TILE) {
                return velocity;
            }

            float tempYFloat = getTileYPosition(tempYInt);
            float hitBoxYPosition = (y + velocity + bounds.y + bounds.height);

            float maxYPositionLeft = findLowestPointLeft(x, bounds.x, leftSide, tempYFloat, hitBoxYPosition) + tempYFloat;
            float maxYPositionRight = findLowestPointRight(x, bounds.x, bounds.width, rightSide, tempYFloat, hitBoxYPosition) + tempYFloat;
            float maxYPosition;

            if (leftSide == rightSide) {
                //both x's are on the same tile
                if (leftSide == 4) {
                    maxYPosition = maxYPositionRight;
                    if (y+velocity < maxYPosition) {
                        return velocity;
                    } else touchingGround = true;
                } else {
                    maxYPosition = maxYPositionLeft;
                    if (y+velocity < maxYPosition) {
                        return velocity;
                    } else touchingGround = true;
                }
            }

            if (leftSide == 0) {
                maxYPosition = maxYPositionRight;
                if (y+velocity < maxYPosition) {
                    return velocity;
                } else touchingGround = true;
            }

            if (rightSide == 0) {
                maxYPosition = maxYPositionLeft;
                if (y+velocity < maxYPosition) {
                    return velocity;
                } else touchingGround = true;
            }

            //if they are both slopes but one is a 3 and one is a 4
            if (maxYPositionLeft > maxYPositionRight) {
                maxYPosition = maxYPositionRight;
                if (y+velocity < maxYPosition) {
                    return velocity;
                } else touchingGround = true;
            } else {
                maxYPosition = maxYPositionLeft;
                if (y+velocity < maxYPosition) {
                    return velocity;
                } else touchingGround = true;
            }
        }
        return 0;
    }

    @Override
    public void jump(Creature creature) {
        this.creature = creature;
        jumping = true;
        Thread animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() {
        jumping = true;
        boolean jumpFinished = false;
        int pixelsJumped = 0;
        float jumpForce = 6.5f;

        distanceUpwards = -1f;
        touchingGround = false;

        long currentTime;
        double delta = 0;
        long lastTime = System.currentTimeMillis();

        while (!jumpFinished) {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                sleepThread();

                if (pixelsJumped <= 130) {

                    float distUp = -(0.9f+jumpForce);
                    int tempY = findY(creature.getY(), distUp, TOP);
                    int xLeft = findX(creature.getX(), 0, LEFT);
                    int xRight = findX(creature.getX(), 0, RIGHT);

                    if (!collision(xLeft, tempY) && !collision(xRight, tempY) && distUp < 0) {
                        if (creature.getY() + distUp >= MAX_HEIGHT) {
                            distanceUpwards = distUp;
                        } else jumpFinished = true;
                    } else jumpFinished = true;

                    pixelsJumped++;
                    delta = 0;
                    jumpForce -= 0.08;

                } else jumpFinished = true;
            }
        }
        jumping = false;
    }

    private int boundaryCheck(int tileId, float x, float boundsX, int width, float tempFloatY, float hitBoxYPos) {
        float xPosition = x+boundsX+width;
        if (xPosition % 50 == 0) {
            if (tileId == 3) {
                return 0;
            } else if (tileId == 4){
                return (int)(hitBoxYPos - tempFloatY) + 50;
            }
        }
        return -1;
    }

    private int findLowestPointLeft(float x, int boundsX, int tileId, float tempFloatY, float hitBoxYPos) {
        int positionAlongTile = boundaryCheck(tileId, x, bounds.x, 0, tempFloatY, hitBoxYPos);
        if (positionAlongTile != -1) {
            return positionAlongTile;
        }

        int difference = 0;
        float a = x+boundsX;
        while (a % 50 != 0) {
            difference++;
            a--;
        }
        if (tileId == 0) {
            return 100;
        }
        return difference;
    }

    private int findLowestPointRight(float x, int boundsX, int boundsWidth, int tileId, float tempFloatY, float hitBoxYPos) {
        int positionAlongTile = boundaryCheck(tileId, x, bounds.x, boundsWidth, tempFloatY, hitBoxYPos);
        if (positionAlongTile != -1) {
            return positionAlongTile;
        }

        int difference = 0;
        float a = x+boundsX+boundsWidth;
        while (a % 50 != 0) {
            difference++;
            a++;
        }
        if (tileId == 0) {
            return 100;
        }
        return difference;
    }

    private float getTileYPosition(int yPos) {
        return (yPos * Tile.TILE_HEIGHT) - 50;
    }

    private void sleepThread() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean collision(int x, int y) {
        return world.getTile(x, y).isSolid();
    }

    private boolean collision1(int x, int y) {
        return world.getTile(x, y).isSolid();
    }

    private boolean isSlope(int x, int y) {
        return world.isSlope(x, y);
    }

    private int getTileId(int x, int y) {
        return world.getTileId(x, y);
    }

    @Override
    public boolean isTouchingGround() {
        return touchingGround;
    }

    @Override
    protected boolean boundsIsValid(Rectangle bounds) {
        if (bounds.x > CREATURE_WIDTH || bounds.x <0 ||
                bounds.y > CREATURE_HEIGHT || bounds.y <0 ||
                bounds.width > CREATURE_WIDTH || bounds.width <0 ||
                bounds.width > CREATURE_HEIGHT || bounds.height <0 ||
                bounds.x + bounds.width > CREATURE_WIDTH ||
                bounds.y+bounds.height > CREATURE_HEIGHT) {
            return false;
        }
        this.bounds = bounds;
        return true;
    }

    private float addVelocity() {
        if (velocity < 6) {
            return 0.25f;
        }
        return 0;
    }

    private float checkVelocity() {
        if (fallStart) {
            return 1.5f;
        }
        if (yCollision) {
            return 1;
        }
        return velocity;
    }
}

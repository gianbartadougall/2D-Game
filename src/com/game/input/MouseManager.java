package com.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    private int mouseX, mouseY;
    private boolean leftPress, rightPress, leftClick;
    private boolean released = true;

    @Override
    public void mouseClicked(MouseEvent e) {
        leftClick = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPress = true;
        } else rightPress = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPress = false;
            released = true;
        } else rightPress = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int x() {
        return mouseX;
    }

    public int y() {
        return mouseY;
    }



    public boolean leftPress() {
        return leftPress;
    }

    public boolean rightPress() {
        return rightPress;
    }

    public boolean leftClick() {
        if (released && leftPress) {
            released = false;
            return true;
        }
        return false;
    }
}

package com.game.screens;

public class ScreenManager{

    Screen currentScreen;

    public ScreenManager(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public Screen currentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }
}

package com.tankstars.game.utils;

public interface Controller {
    public abstract void pause();
    public abstract void play();
    public abstract void playTill(float frame);
    public abstract void reverse(float frame);
}

package com.tankstars.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonActor extends Image {
    Texture texture;
    Sound sound;
    long id;
    boolean isPressed = false;
    public ButtonActor(String path, int width, int height, int x, int y){
        super(new Texture(Gdx.files.internal(path)));
        this.setX(x);this.setY(y);this.setWidth(width);
        setBounds(x,y,width,height);
        setTouchable(Touchable.enabled);
        sound = Gdx.audio.newSound(Gdx.files.internal("tempsound1.mp3"));


    }
    public void Hit(Action action){
        this.addAction(action);
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(this.getColor());
        ((TextureRegionDrawable) getDrawable()).draw(batch, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}

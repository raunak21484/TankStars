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
    Action action;
    Texture texture;
    public ButtonActor(String path, int width, int height, int x, int y){
        //super(new Texture(Gdx.files.internal(path)));
        texture = new Texture(Gdx.files.internal(path));
        this.setDrawable(new TextureRegionDrawable(texture));
        this.setX(x);this.setY(y);this.setWidth(width);
        setBounds(x,y,width,height);
        setTouchable(Touchable.enabled);
    }
    public ButtonActor(Texture texture, int width, int height, int x, int y){
        this.texture = texture;
        this.setDrawable(new TextureRegionDrawable(texture));
        this.setX(x);this.setY(y);this.setWidth(width);
        setBounds(x,y,width,height);
        setTouchable(Touchable.enabled);//
    }
    public void setAction(Action action){
        this.action = action;
    }
    public Action getButtonAction(){
        return this.action;
    }
    public void performAction(){
        this.addAction(action);
       action.restart();

    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(this.getColor());
        ((TextureRegionDrawable) getDrawable()).draw(batch, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}

package com.tankstars.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Logo extends Image {
    public Logo(int width, int height, int x, int y){
        super(new Texture(Gdx.files.internal("MainMenu/logo.png")));
        setBounds(x,y,width,height);

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(this.getColor());
        ((TextureRegionDrawable) getDrawable()).draw(batch, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}

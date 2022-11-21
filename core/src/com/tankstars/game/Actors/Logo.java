package com.tankstars.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Logo extends Image {
    Sound sound;
    long id;
    boolean isPressed = false;
    public Logo(int width, int height, int x, int y){
        super(new Texture(Gdx.files.internal("MainMenu/logo.png")));
        setBounds(x,y,width,height);
        sound = Gdx.audio.newSound(Gdx.files.internal("tempsound1.mp3"));

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(button == Input.Buttons.LEFT) {
                    if (!isPressed) {
                        isPressed = true;
                        id = sound.play();

                    }
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(button == Input.Buttons.LEFT) {
                    isPressed = false;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(this.getColor());
        ((TextureRegionDrawable) getDrawable()).draw(batch, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}

package com.tankstars.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tankstars.game.Actions.SoundAction;

public class Button extends Image {
    Texture texture;
    Sound sound;
    long id;
    boolean isPressed = false;
    public Button(int width, int height, int x, int y){
        super(new Texture(Gdx.files.internal("MainMenu/logo.png")));
        this.setX(x);this.setY(y);this.setWidth(width);
        setBounds(x,y,width,height);
        setTouchable(Touchable.enabled);
        sound = Gdx.audio.newSound(Gdx.files.internal("tempsound1.mp3"));

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(button == Input.Buttons.LEFT && !isPressed) {
                   isPressed= true;
                    SoundAction sa = new SoundAction(sound);
                    Button.this.addAction(sa);
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

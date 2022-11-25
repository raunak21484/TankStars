package com.tankstars.game.Actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class SwitchImageAction extends RunnableAction {
    private Image image;
    private String path;
    public SwitchImageAction(Image image, String path){
        this.image = image;
        this.path = path;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                //SwitchImageAction.this.image.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(SwitchImageAction.this.path)))));
                System.out.println("hii pressed!");
            }
        });

    }
}

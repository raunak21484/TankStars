package com.tankstars.game.Actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tankstars.game.utils.ImageNavCollection;

public class SwitchImageAction extends RunnableAction {
    private Image image;
    private ImageNavCollection imcollection;
    private Integer delta;
    public SwitchImageAction(Image image, ImageNavCollection imcollection, Integer delta){
        this.image = image;
        this.imcollection = imcollection;
        this.delta = delta;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                SwitchImageAction.this.imcollection.setIndex(SwitchImageAction.this.imcollection.getIndex().val+SwitchImageAction.this.delta);
                SwitchImageAction.this.image.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(SwitchImageAction.this.imcollection.getPath()))));
                //System.out.println("hii pressed!");
            }
        });

    }
}

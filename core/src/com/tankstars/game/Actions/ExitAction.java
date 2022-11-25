package com.tankstars.game.Actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tankstars.game.utils.ImageNavCollection;

public class ExitAction extends RunnableAction {

    public ExitAction(){
        setRunnable(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        });

    }
}

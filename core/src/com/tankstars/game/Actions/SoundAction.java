package com.tankstars.game.Actions;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

public class SoundAction extends RunnableAction {
    Sound sound;
    Long id;
    public SoundAction(Sound music){
        this.sound= music;
        music.play();

        this.setRunnable(new Runnable() {
            @Override
            public void run() {
               SoundAction.this.id = SoundAction.this.sound.play();
            }
        });
    }
}

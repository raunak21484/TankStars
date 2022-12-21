package com.tankstars.game.Actions;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.tankstars.game.GameObjects.GameScreen;
import com.tankstars.game.TankStars;

public class FireProjectileAction extends RunnableAction {
    private GameScreen gameScreen;
    private TankStars tankStars;
    private Integer id;
    public FireProjectileAction(TankStars tankStars, GameScreen GameScreen, Integer id){
        this.tankStars = tankStars;
        this.gameScreen = GameScreen;
        this.id = id;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                GameScreen gameScreen = FireProjectileAction.this.gameScreen;
                TankStars tankStars = FireProjectileAction.this.tankStars;
                gameScreen.fire(FireProjectileAction.this.id);

            }
        });
    }
}

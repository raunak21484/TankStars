package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tankstars.game.Actions.AnimationAction;
import com.tankstars.game.Actions.SoundAction;
import com.tankstars.game.Actions.StageSwitchAction;
import com.tankstars.game.Actors.ButtonActor;
import com.tankstars.game.Actors.ImageAnimation;
import com.tankstars.game.utils.Controller;
import com.tankstars.game.utils.MutableInt;

import java.util.ArrayList;

public class StageCreator {
    MutableInt currStage;
    TankStars tankStars;
    public StageCreator(MutableInt currStage, TankStars tankStars){
        this.currStage = currStage;
        this.tankStars = tankStars;
    }
    public Stage IndexedInit(int index, InputMultiplexer mux){
        switch(index){
            case TankStars.LOADING_SCREEN:
                return this.initLoadingScreen(mux);
            case TankStars.MAIN_MENU:
                return this.initMainMenu(mux);
            case TankStars.SELECTION_SCREEN:
                return this.initSelectionScreen(mux);
            default:
                return null;
        }
    }
    public void LoadScreen(int index, InputMultiplexer mux){
        switch(index){
//            case TankStars.LOADING_SCREEN:
//                return this.initLoadingScreen(mux);
//            case TankStars.MAIN_MENU:
//                return this.initMainMenu(mux);
            case TankStars.SELECTION_SCREEN:
                this.loadSelectionScreen(mux);
                break;
        }
    }
    public Stage initLoadingScreen(InputMultiplexer mux){
        Stage stage = new Stage(new ScreenViewport());
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stage);
        Image img = new Image(new Sprite(new Texture(Gdx.files.internal("MainMenu/loadingscreen.png"))));
        img.setSize(1920,887);
        stage.addActor(img);
        return stage;
    }
    public Stage initMainMenu(InputMultiplexer mux){
        mux.removeProcessor(mux.size()-1);
        Stage stage = new Stage(new ScreenViewport());
        mux.addProcessor(stage);
        Image background = new Image(new Texture(Gdx.files.internal("MainMenu/LeftBackground.jpg")));
        background.setBounds(0,0,1183,887);
        Image buttonbackground = new Image(new Texture(Gdx.files.internal("MainMenu/ButtonBackground.png")));
        buttonbackground.setBounds(1183,0,737,887);
        ButtonActor playbutton = new ButtonActor("MainMenu/Play.png",(int)(584/1.5),(int)(204/1.5),1350,600);
        playbutton.setAction(new StageSwitchAction(this,mux,TankStars.SELECTION_SCREEN,currStage,this.tankStars,true,false));


        ButtonActor loadbutton = new ButtonActor("MainMenu/Load.png",(int)(584/1.5),(int)(204/1.5),1350,350);
        ButtonActor exitbutton = new ButtonActor("MainMenu/Exit.png",(int)(584/1.5),(int)(204/1.5),1350,100);
        ButtonActor logo = new ButtonActor("MainMenu/logo.png",307,152,456,625);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("tempsound1.mp3"));
        logo.setAction(new SoundAction(sound));
        stage.addActor(background);
        stage.addActor(buttonbackground);
        stage.addActor(playbutton);
        stage.addActor(loadbutton);
        stage.addActor(exitbutton);
        stage.addActor(logo);

        return stage;
    }

    public Stage initSelectionScreen(InputMultiplexer mux){
        mux.removeProcessor(mux.size()-1);
        Stage stage = new Stage(new ScreenViewport());
        mux.addProcessor(stage);
        Image buttonbackground = new Image(new Texture(Gdx.files.internal("MainMenu/ButtonBackground.png")));
        buttonbackground.setBounds(1183,0,737,887);
        ButtonActor startbutton = new ButtonActor("SelectionMenu/Start.png",486,180,1300,150);
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("SelectionMenu/SpriteSheets/TankSelection/tanksel.atlas"));
        Animation<TextureRegion> ani =  new Animation<TextureRegion>(0.033f, textureAtlas.findRegions("imageout"), Animation.PlayMode.LOOP);
        ImageAnimation selectorAnimation = new ImageAnimation();
        selectorAnimation.getBreakPoints().add(1.54f);
        selectorAnimation.getBreakPoints().add(4.5f);
        selectorAnimation.getBreakPoints().add(7.37f);
        selectorAnimation.getBreakPoints().add(11f);

        selectorAnimation.setPose(new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg")),1183,887));
        selectorAnimation.setAnimation(ani);
        selectorAnimation.setBounds(0,0,1183,887);
        ButtonActor right = new ButtonActor("SelectionMenu/Right.png",100,100,1780,500);
        right.setAction(new AnimationAction(selectorAnimation,1));
        ButtonActor left = new ButtonActor("SelectionMenu/Left.png",100,100,1223,500);
        left.setAction(new AnimationAction(selectorAnimation,-1));
        Image first = new Image(new Texture(Gdx.files.internal("SelectionMenu/1st.png")));
        first.setBounds((1780-1322)/2 - 225 + 1322,350,450,450);
        stage.addActor(buttonbackground);
        stage.addActor(startbutton);
        stage.addActor(selectorAnimation);
        stage.addActor(first);
        stage.addActor(right);
        stage.addActor(left);
        return stage;
    }
    public void loadSelectionScreen(InputMultiplexer mux){
        System.out.println("here");
        ArrayList<Stage> stages = this.tankStars.getStages();
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stages.get(currStage.val));
        ImageAnimation ac = (ImageAnimation) stages.get(currStage.val).getActors().get(2);
        ac.playTill(ac.getBreakPoints().get(0));

    }
}

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
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tankstars.game.Actions.AnimationAction;
import com.tankstars.game.Actions.SoundAction;
import com.tankstars.game.Actions.StageSwitchAction;
import com.tankstars.game.Actions.SwitchImageAction;
import com.tankstars.game.Actors.ButtonActor;
import com.tankstars.game.Actors.ImageAnimation;
import com.tankstars.game.utils.Controller;
import com.tankstars.game.utils.ImageNavCollection;
import com.tankstars.game.utils.MutableInt;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

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
            case TankStars.GAME_SCREEN:
                return this.initGameScreen(mux);
            case TankStars.SETTINGS:
                return this.initSettings(mux);
            case TankStars.LOAD_SCREEN:
                return this.initLoadScreen(mux);
            case TankStars.END_SCREEN:
                return this.initEndingScreen(mux);
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
        Image tank = new Image(new Texture(Gdx.files.internal("MainMenu/Tank.png")));
        tank.setBounds(320,220,603,350);
        Image buttonbackground = new Image(new Texture(Gdx.files.internal("MainMenu/ButtonBackground.png")));
        buttonbackground.setBounds(1183,0,737,887);
        ButtonActor playbutton = new ButtonActor("MainMenu/Play.png",(int)(584/1.5),(int)(204/1.5),1350,600);
        playbutton.setAction(new StageSwitchAction(this,mux,TankStars.SELECTION_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor loadbutton = new ButtonActor("MainMenu/Load.png",(int)(584/1.5),(int)(204/1.5),1350,350);
        loadbutton.setAction(new StageSwitchAction(this,mux,TankStars.LOAD_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor exitbutton = new ButtonActor("MainMenu/Exit.png",(int)(584/1.5),(int)(204/1.5),1350,100);
        ButtonActor logo = new ButtonActor("MainMenu/logo.png",307,152,456,625);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("tempsound1.mp3"));
        logo.setAction(new SoundAction(sound));
        stage.addActor(background);
        stage.addActor(tank);
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
        startbutton.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
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

        Image first = new Image(new Texture(Gdx.files.internal("SelectionMenu/1st.png")));
        first.setBounds((1780-1322)/2 - 225 + 1322,350,450,450);
        ArrayList<String> imagecollection = new ArrayList<>();
        imagecollection.add("SelectionMenu/1st.png");
        imagecollection.add("SelectionMenu/2nd.png");
        imagecollection.add("SelectionMenu/3rd.png");
        imagecollection.add("SelectionMenu/4th.png");
        ImageNavCollection images = new ImageNavCollection(imagecollection,new MutableInt(0));
        ButtonActor right = new ButtonActor("SelectionMenu/Right.png",100,100,1780,500);
        right.setAction(new ParallelAction(new AnimationAction(selectorAnimation,1), new SwitchImageAction(first,images,1)));


        ButtonActor left = new ButtonActor("SelectionMenu/Left.png",100,100,1223,500);
        left.setAction(new ParallelAction(new AnimationAction(selectorAnimation,-1), new SwitchImageAction(first,images,-1)));


        Image choose = new Image(new Texture(Gdx.files.internal("SelectionMenu/Choose.png")));
        choose.setBounds(1385,800,330,46);
        ButtonActor back = new ButtonActor("SelectionMenu/back.png",65,47,0,787);
        back.setAction(new StageSwitchAction(this,mux,TankStars.MAIN_MENU,currStage,this.tankStars,true,false));
        stage.addActor(buttonbackground);
        stage.addActor(startbutton);
        stage.addActor(selectorAnimation);
        stage.addActor(first);
        stage.addActor(choose);
        stage.addActor(right);
        stage.addActor(left);
        stage.addActor(back);
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

    public Stage initGameScreen(InputMultiplexer mux){
        Stage stage = new Stage(new ScreenViewport());
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stage);
        Image background = new Image(new Texture(Gdx.files.internal("GameScreen/theme1.png")));
        background.setBounds(0,0,1920,887);
        Image terrain = new Image(new Texture(Gdx.files.internal("GameScreen/terrain.png")));
        terrain.setBounds(0,0,1920,600);
        ButtonActor tank1 = new ButtonActor("GameScreen/tank1.png",167,86,1150,887-687);
        ButtonActor tank2 = new ButtonActor("GameScreen/tank2.png",188,106,298,887-581);
        Image bar = new Image(new Texture(Gdx.files.internal("GameScreen/bar.png")));
        bar.setBounds((1920/2)-480,800,960,93);
        ButtonActor setting = new ButtonActor("GameScreen/setting.png",65,47,0,787);
        setting.setAction(new StageSwitchAction(this,mux,TankStars.SETTINGS,currStage,this.tankStars,true,false));
        ButtonActor forward = new ButtonActor("GameScreen/forward.png",65,47,1920-67,787);
        forward.setAction(new StageSwitchAction(this,mux,TankStars.END_SCREEN,currStage,this.tankStars,true,false));
        stage.addActor(background);
        stage.addActor(terrain);
        stage.addActor(tank1);
        stage.addActor(tank2);
        stage.addActor(bar);
        stage.addActor(setting);
        stage.addActor(forward);
        return stage;
    }

    public Stage initSettings(InputMultiplexer mux){
        Stage stage = new Stage(new ScreenViewport());
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stage);
        ButtonActor background = new ButtonActor("Settings/background.png",1920,887,0,0);
        background.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
        Image back = new Image(new Texture(Gdx.files.internal("GameScreen/theme1.png")));
        background.setBounds(0,0,1920,887);
        Image terrain = new Image(new Texture(Gdx.files.internal("GameScreen/terrain.png")));
        terrain.setBounds(0,0,1920,600);
        ButtonActor tank1 = new ButtonActor("GameScreen/tank1.png",167,86,1150,887-687);
        ButtonActor tank2 = new ButtonActor("GameScreen/tank2.png",188,106,298,887-581);
        Image bar = new Image(new Texture(Gdx.files.internal("GameScreen/bar.png")));
        bar.setBounds((1920/2)-480,800,960,93);
        ButtonActor setting = new ButtonActor("GameScreen/setting.png",65,47,0,787);
        ButtonActor forward = new ButtonActor("GameScreen/forward.png",65,47,1920-67,787);
        ButtonActor resume = new ButtonActor("Settings/resume.png",324,120,960-162-30,550);
        resume.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor sound = new ButtonActor("Settings/sound.png",324,120,960-162-30,400);
        ButtonActor vibrations = new ButtonActor("Settings/vibration.png",324,120,960-162-30,250);
        ButtonActor menu = new ButtonActor("Settings/mainmenu.png",324,120,960-162-30,100);
        menu.setAction(new StageSwitchAction(this,mux,TankStars.MAIN_MENU,currStage,this.tankStars,true,false));
        stage.addActor(back);
        stage.addActor(terrain);
        stage.addActor(tank1);
        stage.addActor(tank2);
        stage.addActor(bar);
        stage.addActor(setting);
        stage.addActor(forward);
        stage.addActor(background);
        stage.addActor(resume);
        stage.addActor(sound);
        stage.addActor(vibrations);
        stage.addActor(menu);
        return stage;
    }

    public Stage initLoadScreen(InputMultiplexer mux){
        Stage stage = new Stage(new ScreenViewport());
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stage);
        Image background = new Image(new Texture(Gdx.files.internal("LoadScreen/background.png")));
        background.setBounds(0,0,1920,887);
        ButtonActor g1 = new ButtonActor("LoadScreen/game1.png",391,234,200,250);
        g1.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor g2 = new ButtonActor("LoadScreen/game2.png",391,234,750,250);
        g2.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor g3 = new ButtonActor("LoadScreen/game3.png",391,234,1300,250);
        g3.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor back = new ButtonActor("SelectionMenu/back.png",65,47,0,787);
        back.setAction(new StageSwitchAction(this,mux,TankStars.MAIN_MENU,currStage,this.tankStars,true,false));
        stage.addActor(background);
        stage.addActor(g1);
        stage.addActor(g2);
        stage.addActor(g3);
        stage.addActor(back);
        return stage;
    }

    public Stage initEndingScreen(InputMultiplexer mux){
        Stage stage = new Stage(new ScreenViewport());
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stage);
        Image background = new Image(new Texture(Gdx.files.internal("MainMenu/LeftBackground.jpg")));
        background.setBounds(0,0,1183,887);
        Image tank = new Image(new Texture(Gdx.files.internal("MainMenu/Tank.png")));
        tank.setBounds(320,220,603,350);
        Image buttonbackground = new Image(new Texture(Gdx.files.internal("MainMenu/ButtonBackground.png")));
        buttonbackground.setBounds(1183,0,737,887);
        ButtonActor logo = new ButtonActor("EndScreen/victory.png",413,118,456-40,645);
        ButtonActor restart = new ButtonActor("EndScreen/restart.png",584,204,1350-100,500);
        restart.setAction(new StageSwitchAction(this,mux,TankStars.GAME_SCREEN,currStage,this.tankStars,true,false));
        ButtonActor menu = new ButtonActor("EndScreen/menu.png",584,204,1350-100,200);
        menu.setAction(new StageSwitchAction(this,mux,TankStars.MAIN_MENU,currStage,this.tankStars,true,false));
        stage.addActor(background);
        stage.addActor(tank);
        stage.addActor(buttonbackground);
        stage.addActor(logo);
        stage.addActor(restart);
        stage.addActor(menu);
        return stage;
    }
}

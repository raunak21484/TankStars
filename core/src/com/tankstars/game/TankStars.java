package com.tankstars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tankstars.game.Actors.ButtonActor;
import com.tankstars.game.utils.MutableInt;

import java.util.ArrayList;

public class TankStars extends ApplicationAdapter implements InputProcessor {

	public static final int LOADING_SCREEN = 0;
	public static final int MAIN_MENU = 1;
	public static final int SELECTION_SCREEN = 2;

	public static final int GAME_SCREEN = 3;
	private MutableInt currStage;
	StageCreator stageCreator;
	private ArrayList<Stage> stages;



	private Boolean bool;
	private Actor tempActor;
	private Vector2 coord;
	InputMultiplexer mux;
	@Override
	public void create () {
		mux	= new InputMultiplexer();
		mux.addProcessor(this);
		mux.addProcessor(this);
		currStage= new MutableInt(LOADING_SCREEN);
		stageCreator = new StageCreator(currStage,this);
		stages = new ArrayList<>();
		stages.add(stageCreator.initLoadingScreen(mux));
		stages.add(stageCreator.initMainMenu(mux));
		stages.add(stageCreator.initSelectionScreen(mux));
		stages.add(stageCreator.initGameScreen(mux));
		bool = false;
		Gdx.input.setInputProcessor(mux);
		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				bool = true;
			}
		},1);

	}

	@Override
	public void render () {
		stages.get(currStage.val).act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stages.get(currStage.val).draw();


		switch(currStage.val){
			case LOADING_SCREEN:
				if(bool){
					tempActor =stages.get(currStage.val).getActors().first();
					tempActor.setColor(tempActor.getColor().r,tempActor.getColor().g,tempActor.getColor().b,tempActor.getColor().a-0.05f);
					if(tempActor.getColor().a<=0){
						currStage.val = MAIN_MENU;
					}
				}
				break;
			case MAIN_MENU:

				break;
			case SELECTION_SCREEN:
				//stages.get(SELECTION_SCREEN).getActors().get(2).
				break;
		}
	}
	
	@Override
	public void dispose () {
		for(int i=0;i<stages.size();i++){
			stages.get(i).dispose();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;//
	}//

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println(screenX);
		System.out.println(screenY);
		coord = this.stages.get(currStage.val).screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
		Actor hitActor = this.stages.get(currStage.val).hit(coord.x, coord.y, false);
		if(hitActor instanceof ButtonActor){
			ButtonActor button1 = (ButtonActor) hitActor;
			if(button1.getButtonAction()!=null){
			button1.performAction();}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
	public ArrayList<Stage> getStages() {
		return stages;
	}
}

package com.tankstars.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tankstars.game.utils.Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageAnimation extends Image implements Controller
{
    private Animation<TextureRegion> animation;
    private float time;
    public ArrayList<Float> BreakPoints;
    private boolean isRewinding,isFrameLimited;

    private float limitFrame;

    private float animationLength;

    protected float speed = 1f;
    protected boolean isPlayed =false;
    private int PlayIndex= 0;


    protected TextureRegionDrawable drawable = new TextureRegionDrawable();

    public ImageAnimation() {
        super();
        setDrawable(drawable);
        this.BreakPoints = new ArrayList<>();
        //System.out.println("Constructor called!");
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
        animationLength = animation.getKeyFrames().length/animation.getFrameDuration();
        //System.out.println("AnimationLength: "+animationLength);
    }

    public void setPose(TextureRegion textureRegion) {
        drawable.setRegion(textureRegion);
        setDrawable(drawable);
        invalidateHierarchy();
        setSize(getPrefWidth(), getPrefHeight());
        invalidate();
        this.animation = null;
    }

    @Override
    public void act(float delta)
    {
        if(animation != null && animation.getAnimationDuration() > 0&& isPlayed && checkFrame(time)){
            if(isRewinding){time -= delta * speed;}
            else{time += delta * speed;}
            //System.out.println("Time = "+time%getAnimationLength() + "\t PlayIndex = "+getPlayIndex());
            TextureRegion frame = animation.getKeyFrame(time%getAnimationLength(), true);
            drawable.setRegion(frame);
            setDrawable(drawable);
            invalidateHierarchy();
            invalidate();
        }else{
            this.isPlayed = false;
            // setDrawable(null);
        }
        super.act(delta);
    }
    public boolean checkFrame(float frame){
    //System.out.println("Animation Length: "+ animationLength);
        if(this.isFrameLimited){
            if(this.isRewinding){
                if((frame%animationLength) < (this.limitFrame%animationLength)){
                    return false;
                }else{return true;}
            }
            else{
                if((frame%animationLength) > (this.limitFrame%animationLength)){
                    return false;
                }else{return true;}
            }
        }
        return true;
    }
    @Override
    public void pause() {
        this.isPlayed = false;
    }

    @Override
    public void play() {
        this.isPlayed = true;
        this.isFrameLimited = false;
    }

    @Override
    public void playTill(float frame) {
        this.isPlayed = true;
        limitFrame = frame;
        this.isFrameLimited =true;
    }

    @Override
    public void reverse(float frame) {
        limitFrame =frame;
        this.isRewinding = !this.isRewinding;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }
    public ArrayList<Float> getBreakPoints(){
        return this.BreakPoints;
    }
    public boolean isRewinding() {
        return isRewinding;
    }

    public boolean isFrameLimited() {
        return isFrameLimited;
    }

    public float getLimitFrame() {
        return limitFrame;
    }

    public float getAnimationLength() {
        return animationLength;
    }

    public float getSpeed() {
        return speed;
    }
    public void setRewinding(boolean rewinding) {
        isRewinding = rewinding;
    }

    public void setFrameLimited(boolean frameLimited) {
        isFrameLimited = frameLimited;
    }

    public void setLimitFrame(float limitFrame) {
        this.limitFrame = limitFrame;
    }
    public int getPlayIndex() {
        return PlayIndex;
    }

    public void setPlayIndex(int playIndex) {
        PlayIndex = playIndex;
    }
}
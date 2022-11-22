package com.tankstars.game.Actions;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.tankstars.game.Actors.ImageAnimation;

public class AnimationAction extends RunnableAction {
    private ImageAnimation imageAnimation;
    private Integer delta;
    public AnimationAction(ImageAnimation imageAnimation,  int delta){
        this.imageAnimation = imageAnimation;
        this.delta = delta;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                ImageAnimation lia = AnimationAction.this.imageAnimation;
                Integer lframeIndex = AnimationAction.this.delta + lia.getPlayIndex();
                System.out.println("Called!, to_move_frameIndex:" + lframeIndex +", currFrameIndex: "+lia.getPlayIndex());
                if(lia.getBreakPoints().size() <= lframeIndex || lframeIndex<0){
                    return;
                }
                if((lia.getTime()%lia.getAnimationLength())>(lia.getBreakPoints().get(lframeIndex)%lia.getAnimationLength())){
                    lia.setRewinding(true);
                }else{
                    lia.setRewinding(false);
                }

                lia.setPlayIndex(lframeIndex);
                System.out.println("Updated lia.frameindex to "+lframeIndex);
                lia.playTill(lia.getBreakPoints().get(lframeIndex));

            }
        });
    }
}

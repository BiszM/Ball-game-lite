package com.weem.ballgame;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.weem.ballgame.Helpers.PixelHelper;

import static com.weem.ballgame.MainActivity.TAG;
import static com.weem.ballgame.MainActivity.isGameOver;

/**
 * Created by shailendra on 11/12/17.
 */

public class Ball extends ImageView implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    ValueAnimator animator;
    int screenheight;


 BallListener ballListener;

boolean isPopped;

    public Ball(Context context) {
        super(context);
    }


    public void createBall( Context context, int rawheight) {


        int dpheight = PixelHelper.PixelToDp(rawheight, context);
        int dpwidth = PixelHelper.PixelToDp(rawheight, context);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpwidth, dpheight);
        setLayoutParams(params);
        
       ballListener= (BallListener) context;

    }

    public void animateBall(int screenHeight){
        this.screenheight=screenHeight;

        animator= new ValueAnimator();
        animator.setInterpolator(new LinearInterpolator());
        animator.setFloatValues(screenHeight,0f);
        animator.setDuration(3000);      //change the duration according to duration later
        animator.setTarget(this);
        animator.start();
        animator.addListener(this);
        animator.addUpdateListener(this);





    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {



    }

    @Override
    public void onAnimationCancel(Animator animation) {



    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
         setY((Float) animation.getAnimatedValue());

         if((Float)animation.getAnimatedValue()==0) {

             ballListener.popBall(this, false,getContext());
             isPopped = true;
             if(isGameOver)
                 setVisibility(View.GONE);



         }
    }

public interface BallListener{
        public void popBall(Ball ball,boolean userTouch,Context context);

        
        
}

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "onTouchEvent: balltouched");

if (!isPopped && event.getAction()==MotionEvent.ACTION_DOWN)


        ballListener.popBall(this,true,getContext());
         isPopped=true;
    animator.cancel();
        return super.onTouchEvent(event);
    }




}

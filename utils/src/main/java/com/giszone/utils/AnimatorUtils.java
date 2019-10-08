package com.giszone.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimatorUtils {

    public static final int SHOW_FROM_LEFT_TO_RIGHT = 1;
    public static final int SHOW_FROM_RIGHT_TO_LEFT = 2;
    public static final int SHOW_FROM_TOP_TO_BOTTOM = 3;
    public static final int SHOW_FROM_BOTTOM_TO_TOP = 4;

    public static final int HIDE_FROM_LEFT_TO_RIGHT = 5;
    public static final int HIDE_FROM_RIGHT_TO_LEFT = 6;
    public static final int HIDE_FROM_TOP_TO_BOTTOM = 7;
    public static final int HIDE_FROM_BOTTOM_TO_TOP = 8;


    public static TranslateAnimation myTranslateAnimation(float x, float tox, float y, float toy, int time) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, x,
                Animation.RELATIVE_TO_SELF, tox,
                Animation.RELATIVE_TO_SELF, y,
                Animation.RELATIVE_TO_SELF, toy);
        ta.setDuration(time);
        return ta;
    }

    public static void toggleDisplayStatus(View v, int active, int time) {
        toggleDisplayStatus(v, active, time, null);
    }

    public static void toggleDisplayStatus(View v, int active, int time, final onAnimationEndListener listener) {
        TranslateAnimation anim;
        switch (active) {
            case SHOW_FROM_LEFT_TO_RIGHT:
                v.setVisibility(View.VISIBLE);
                anim = myTranslateAnimation(-1, 0, 0, 0, time);
                break;
            case SHOW_FROM_RIGHT_TO_LEFT:
                v.setVisibility(View.VISIBLE);
                anim = myTranslateAnimation(1, 0, 0, 0, time);
                break;
            case SHOW_FROM_TOP_TO_BOTTOM:
                v.setVisibility(View.VISIBLE);
                anim = myTranslateAnimation(0, 0, -1, 0, time);
                break;
            case SHOW_FROM_BOTTOM_TO_TOP:
                v.setVisibility(View.VISIBLE);
                anim = myTranslateAnimation(0, 0, 1, 0, time);
                break;
            case HIDE_FROM_LEFT_TO_RIGHT:
                v.setVisibility(View.GONE);
                anim = myTranslateAnimation(0, 1, 0, 0, time);
                break;
            case HIDE_FROM_RIGHT_TO_LEFT:
                v.setVisibility(View.GONE);
                anim = myTranslateAnimation(0, -1, 0, 0, time);
                break;
            case HIDE_FROM_TOP_TO_BOTTOM:
                v.setVisibility(View.GONE);
                anim = myTranslateAnimation(0, 0, 0, 1, time);
                break;
            case HIDE_FROM_BOTTOM_TO_TOP:
            default:
                v.setVisibility(View.GONE);
                anim = myTranslateAnimation(0, 0, 0, -1, time);
                break;
        }
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);

    }

    public interface onAnimationEndListener {
        void onAnimationEnd(Animation animation);
    }

}


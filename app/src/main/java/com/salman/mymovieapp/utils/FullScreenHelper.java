package com.salman.mymovieapp.utils;

import android.app.Activity;
import android.view.View;

public class FullScreenHelper {
    private Activity activity;
    private View[] views;

    public FullScreenHelper(Activity activity, View... views) {
        this.activity = activity;
        this.views = views;
    }

    public void showSystemUi(View decorView) {
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    public void hideSystemUi(View decorView) {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    public void enterFullScreen(){
        View view = activity.getWindow().getDecorView();
        hideSystemUi(view);
        for (View v : views){
            v.setSystemUiVisibility(View.GONE);
            v.invalidate();
        }
    }

    public void exitFullScreen(){
        View view = activity.getWindow().getDecorView();
        showSystemUi(view);
        for (View v : views){
            v.setSystemUiVisibility(View.VISIBLE);
            v.invalidate();
        }
    }
}

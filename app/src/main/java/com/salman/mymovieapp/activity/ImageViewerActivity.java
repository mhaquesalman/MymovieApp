package com.salman.mymovieapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.jsibbold.zoomage.ZoomageView;
import com.salman.mymovieapp.R;
import com.squareup.picasso.Picasso;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        // set full screen for the activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        ZoomageView zoomageView = findViewById(R.id.zoom_image);
        if (intent != null && intent.getExtras() != null){
            if (intent.getExtras().getString("imageUrl") != null){
                String url = intent.getExtras().getString("imageUrl");
                Picasso.with(this).load(url).into(zoomageView);
            }
        }
    }
}

package com.naver.myapplication;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;


public class imageAct extends Activity {
String iid;
ImageView ImageView;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview);

        ImageView = (ImageView)findViewById(R.id.imageView);

        Intent intent = getIntent();
        iid = intent.getStringExtra("image");
        MediaController mc = new MediaController(this);

        ImageView.setImageURI(Uri.parse(iid));






    }


}

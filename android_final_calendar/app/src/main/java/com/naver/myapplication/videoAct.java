package com.naver.myapplication;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;


public class videoAct extends Activity {
String vid;
    VideoView videoView;
    public final static String VIDEO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview);

        Intent intent = getIntent();
        vid = intent.getStringExtra("video");


        videoView = (VideoView)findViewById(R.id.videoView);
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(vid));


        try{
            videoView.seekTo(0);
            videoView.start();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}

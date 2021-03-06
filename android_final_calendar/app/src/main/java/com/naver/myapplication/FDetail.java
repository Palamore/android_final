package com.naver.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FDetail extends Activity implements OnClickListener {
    private MediaPlayer mMediaPlayer;
    MemoDBHelper meDBhelper;
    MyDBHelper mDBHelper;
    final Context context = this;
    int mId;
    String today, timee;
    EditText editDate, editTitle, editTime, editMemo, editUri;
    Button editMedia, playau, playvi, stopau, getim;
    String defTitle, defD, defTime, defU, defM, twice_mp4_URL, gitan_mp3_URL, apple_jpg_URL;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detali);

        editDate = (EditText) findViewById(R.id.editdate);
        editTitle = (EditText) findViewById(R.id.edittitle);
        editTime = (EditText) findViewById(R.id.edittime);
        editMemo = (EditText) findViewById(R.id.editmemo);
        editUri = (EditText) findViewById(R.id.edituri);
        editMedia = (Button) findViewById(R.id.editmedia);
        editMedia.setOnClickListener(this);
        playau = (Button) findViewById(R.id.playaudio);
        playau.setOnClickListener(this);
        playvi = (Button) findViewById(R.id.playvideo);
        playvi.setOnClickListener(this);
        stopau = (Button)findViewById(R.id.stopaudio);
        stopau.setOnClickListener(this);
        getim = (Button)findViewById(R.id.getimage);
        getim.setOnClickListener(this);

        Intent intent = getIntent();
        mId = intent.getIntExtra("ParamID", -1);
        today = intent.getStringExtra("ParamDate");
        timee = intent.getStringExtra("ParamTime");

        mDBHelper = new MyDBHelper(this, "Schedule.db", null, 1);

        if (mId == -1) {
            editDate.setText(today);
            editTime.setText(timee);
        } else {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM schedule WHERE _id='" + mId
                    + "'", null);

            if (cursor.moveToNext()) {
                editTitle.setText(cursor.getString(1));
                editDate.setText(cursor.getString(2));
                editTime.setText(cursor.getString(3));
                editUri.setText(cursor.getString(4));
                editMemo.setText(cursor.getString(5));
            }
            mDBHelper.close();
        }

        defTitle = "Android / B107";
        defD = "2016/12/7";
        defTime = "10:00";
        defU = "https://github.com/kwanu70/AndroidExamples/blob/master/musics/twice.mp4?raw=true";
        defM = "twice.mp4";

        apple_jpg_URL = "http://cfile3.uf.tistory.com/image/25134B365845673E191907";
        twice_mp4_URL = "https://github.com/kwanu70/AndroidExamples/blob/master/musics/twice.mp4?raw=true";
        gitan_mp3_URL = "http://cfile10.uf.tistory.com/media/25720C365845672D373574";

        Button btn1 = (Button) findViewById(R.id.btnsave);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btndel);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.btncancel);
        btn3.setOnClickListener(this);

        if (mId == -1) {
            btn2.setVisibility(View.INVISIBLE);

        }
    }

    public void playAudio(Uri uri) throws Exception {
        killMediaPlayer();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(getApplicationContext(), uri);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }
    private void killMediaPlayer() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void defaultinto(){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (mId != -1) {
            db.execSQL("UPDATE schedule SET title='"
                    + editTitle.getText().toString()
                    + editDate.getText().toString()
                    + editTime.getText().toString()
                    + editUri.getText().toString()
                    + editMemo.getText().toString() + mId
                    + "';");
        } else {
            db.execSQL("INSERT INTO schedule VALUES(null, '"
                    + editTitle.getText().toString() + "', '"
                    + editDate.getText().toString() + "', '"
                    + editTime.getText().toString() + "', '"
                    + editUri.getText().toString() + "', '"
                    + editMemo.getText().toString() + "');");
        }
        mDBHelper.close();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnsave:
                if (mId != -1) {
                    db.execSQL("UPDATE schedule SET title='"
                            + editTitle.getText().toString()
                            + editDate.getText().toString()
                            + editTime.getText().toString()
                            + editUri.getText().toString()
                            + editMemo.getText().toString() + mId
                            + "';");
                } else {
                    db.execSQL("INSERT INTO schedule VALUES(null, '"
                            + editTitle.getText().toString() + "', '"
                            + editDate.getText().toString() + "', '"
                            + editTime.getText().toString() + "', '"
                            + editUri.getText().toString() + "', '"
                            + editMemo.getText().toString() + "');");
                }
                mDBHelper.close();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btndel:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder
                        .setTitle("delete alert!")
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        if (mId != -1) {
                                            SQLiteDatabase db = mDBHelper.getWritableDatabase();
                                            db.execSQL("DELETE FROM schedule WHERE _id='" + mId + "';");
                                            mDBHelper.close();
                                        }

                                        setResult(RESULT_OK);
                                        finish();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                break;


            case R.id.btncancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.editmedia:
                AlertDialog.Builder selectDialogBuilder = new AlertDialog.Builder(
                        context);
                selectDialogBuilder
                        .setTitle("get URL")
                        .setMessage("URL 텍스트 불러오기")
                        .setPositiveButton("twice.mp4",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        editUri.setText(twice_mp4_URL);

                                    }
                                })
                        .setNegativeButton("gitan.mp3",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        editUri.setText(gitan_mp3_URL);
                                    }
                                })
                        .setNeutralButton("apple.jpg",  new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                editUri.setText(apple_jpg_URL);
                            }
                        })

                ;

                AlertDialog selectDialog = selectDialogBuilder.create();
                selectDialog.show();


                break;
            case R.id.playaudio:
                String uriString = editUri.getText().toString();
                Uri uri=Uri.parse(uriString);
                try {
                    playAudio(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //  Toast.makeText(Detail.this, uriString,Toast.LENGTH_SHORT).show();
                break;
            case R.id.stopaudio:
                mMediaPlayer.pause();

                break;
            case R.id.playvideo:
                String vuriString = editUri.getText().toString();
                Intent vintent = new Intent(this, videoAct.class);
                vintent.putExtra("video", vuriString);
                startActivity(vintent);

                break;
            case R.id.getimage:
                String iuriString = editUri.getText().toString();
                Intent iintent = new Intent(this, imageAct.class);
                iintent.putExtra("image", iuriString);
                startActivity(iintent);
                break;
        }

    }


}


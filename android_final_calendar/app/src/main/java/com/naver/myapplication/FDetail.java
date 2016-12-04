package com.naver.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
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
    int mId;
    String today, timee;
    EditText editDate, editTitle, editTime, editMemo, editUri;
    Button editMedia, playau, playvi, stopau;

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
                new AlertDialog.Builder(this)
                        .setTitle("delete alert!")
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No", null)
                        .show();
                if (mId != -1) {
                    db.execSQL("DELETE FROM schedule WHERE _id='" + mId + "';");
                    mDBHelper.close();
                }

                    setResult(RESULT_OK);
                finish();
                    break;

            case R.id.btncancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.editmedia:
                String iuriString = editUri.getText().toString();
                Intent iintent = new Intent(this, imageAct.class);
                iintent.putExtra("image", iuriString);
                startActivity(iintent);

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

        }

    }


}


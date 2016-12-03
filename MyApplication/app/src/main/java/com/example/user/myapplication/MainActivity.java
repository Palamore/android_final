package com.example.user.myapplication;

import blog.naver.com.since201109.imagepuzzle.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imgView;
//	private View view = null;
	ImageProcess imgPrcs = new ImageProcess();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgView = (ImageView) findViewById(R.id.selectImg);
	}
	
	public void mOnClick(View v) {
		switch(v.getId()){
		case R.id.loadImg:
			Intent intent = imgPrcs.setIntent();	// ������ ���. �̹��� �����ϸ� OnActivityResult �޼��� ȣ��ȴ�.
			startActivityForResult(intent, 0);
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	// �̹����� ���õǸ� ȣ��
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
		case 0 :
			ContentResolver cr = getContentResolver();
			Bitmap bitMap = imgPrcs.getBitMap(cr, intent);
//			Display defaultDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
//			int width = defaultDisplay.getWidth();		// ȭ�� ���� ũ��
//			bitMap = imgPrcs.reSizeBitMap(bitMap);
			imgView.setImageBitmap(bitMap);
			break;
		default:
			break;
		}
	}
}
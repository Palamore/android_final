package com.naver.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class WeekActivity extends Activity implements OnClickListener,
        OnItemClickListener {
    ArrayList<String> mItems;
    ArrayAdapter<String> adapter;




    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekfrag);



        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems);

        ListView gird = (ListView) this.findViewById(R.id.wgrid1);
        gird.setAdapter(adapter);
        gird.setOnItemClickListener(this);

        Date date = new Date();// 오늘의 날짜를 세팅
        int year = date.getYear() + 1900;
        int mon = date.getMonth() + 1;



        fillDate(year, mon);




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, Menu.NONE, "Month");
        menu.add(0, 1, Menu.NONE, "Week");
        menu.add(0, 2, Menu.NONE, "DAY");
        menu.add(0, 3, Menu.NONE, "Add");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(WeekActivity.this,"MonthView",Toast.LENGTH_SHORT).show();
                Intent mintent = new Intent(this, MainActivity.class);
                startActivity(mintent);
                break;
            case 1:
                Date date = new Date();
                Toast.makeText(WeekActivity.this,"WeekView",Toast.LENGTH_SHORT).show();

                break;
            case 2:
                Toast.makeText(WeekActivity.this,"DayView",Toast.LENGTH_SHORT).show();
                Intent dintent = new Intent(this, DayActivity.class);
                startActivity(dintent);
                break;
            case 3:
                Toast.makeText(WeekActivity.this,"Add Schedule",Toast.LENGTH_SHORT).show();
                Intent aintent = new Intent(this, Detail.class);
                startActivity(aintent);
                break;
        }
        return  super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick (View arg0){
        // TODO Auto-generated method stub

        if (arg0.getId() == R.id.bt2){


        }

    }


    private void fillDate(int year, int mon) {
        mItems.clear();


        mItems.add("월");
        mItems.add("화");
        mItems.add("수");
        mItems.add("목");
        mItems.add("금");
        mItems.add("토");
        mItems.add("일");

        Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // 요일도 int로 저장.

     //   for (int i = 0; i < day; i++) {
     //       mItems.add("");
     //   }

        current.setDate(8);// 32일까지 입력하면 1일로 바꿔준다.
        int last = 7 - current.getDate();

        for (int i = 1; i <= last; i++) {
            mItems.add(i + "");
        }
        adapter.notifyDataSetChanged();

    }





    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if (mItems.get(arg2).equals("")) {
            ;
        } else {
            Date date = new Date();
            Intent intent = new Intent(this,WexToday.class);
            intent.putExtra("Param1",  date.getYear() + 1900 + "/"
                    + (date.getMonth() + 1) + "/" +( ((date.getDay()+13)/7) + "주차 " + mItems.get(arg2)+ "요일" ));
            intent.putExtra("Param2",  date.getYear() + 1900 + "/"
                    + (date.getMonth() + 1) + "/" +( ((date.getDay()+13)/7) + "주차 " + mItems.get(arg2)+ "요일" ));
            startActivity(intent);
        }
    }
}


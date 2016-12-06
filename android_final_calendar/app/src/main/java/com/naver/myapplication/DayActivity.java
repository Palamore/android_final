package com.naver.myapplication;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DayActivity extends Activity implements OnClickListener,
        OnItemClickListener {
    MyDBHelper mDBHelper;
    Cursor cursor;
    ArrayList<String> mItems;
    ArrayAdapter<String> adapter;
    TextView textYear;
    TextView textMon, textDay;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayfrag);


        textYear = (TextView) this.findViewById(R.id.edit1);
        textMon = (TextView) this.findViewById(R.id.edit2);
        textDay = (TextView) this.findViewById(R.id.edit3);




        Date date = new Date();// 오늘의 날짜를 세팅
        int year = date.getYear() + 1900;
        int mon = date.getMonth() + 1;
        int da = date.getMonth() - 4;
        textYear.setText(year + "");
        textMon.setText(mon + "");
        textDay.setText(da + "");


        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems);

        ListView gird = (ListView) this.findViewById(R.id.list2);
        gird.setAdapter(adapter);
        gird.setOnItemClickListener(this);



        fillday();


        Button btnmove2 = (Button) this.findViewById(R.id.bt2);
        btnmove2.setOnClickListener(this);




    }

private void fillday(){
    mItems.clear();

    for (int i = 1; i <= 24; i++) {
        mItems.add(i + "");

    }
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
                Toast.makeText(DayActivity.this,"MonthView",Toast.LENGTH_SHORT).show();
                Intent mintent = new Intent(this, MainActivity.class);
                startActivity(mintent);
                break;
            case 1:
                Toast.makeText(DayActivity.this,"WeekView",Toast.LENGTH_SHORT).show();
                Intent wintent = new Intent(this, WeekActivity.class);
                startActivity(wintent);
                break;
            case 2:
                Toast.makeText(DayActivity.this,"DayView",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(DayActivity.this,"Add Schedule",Toast.LENGTH_SHORT).show();
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
            Dialog_DatePicker();

        }


    }



    public void Dialog_DatePicker() {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int Syear, int monthOfYear, int dayOfMonth) {
                String _dateStr = Syear+"년 "+(monthOfYear + 1) + "월 " + (dayOfMonth) + "일 ";
                Toast.makeText(DayActivity.this, "SET"+ _dateStr, Toast.LENGTH_SHORT).show();
                textYear.setText(Syear + "");
                textMon.setText((monthOfYear+1) + "");
                textDay.setText(dayOfMonth + "");


            }
        };
        DatePickerDialog alert = new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
        alert.show();
    }





    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if (mItems.get(arg2).equals("")) {

        } else {
            Date date = new Date();
            Intent intent = new Intent(this, DexToday.class);
            intent.putExtra("Param1",  textYear.getText() + "/"
                    + textMon.getText() + "/" + textDay.getText());
            intent.putExtra("Param2", mItems.get(arg2));
            startActivity(intent);
        }
    }
}


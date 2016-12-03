package com.naver.myapplication;


        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;

        import android.Manifest;
        import android.Manifest.permission;
        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.FragmentTransaction;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.GridView;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
        OnItemClickListener {
    ArrayList<String> mItems;
    ArrayAdapter<String> adapter;
    TextView textYear;
    TextView textMon;







    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // animact();

        checkDangerousPermissions();


        textYear = (TextView) this.findViewById(R.id.edit1);
        textMon = (TextView) this.findViewById(R.id.edit2);


        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems);

        GridView gird = (GridView) this.findViewById(R.id.grid1);
        gird.setAdapter(adapter);
        gird.setOnItemClickListener(this);

        Date date = new Date();// 오늘의 날짜를 세팅
        int year = date.getYear() + 1900;
        int mon = date.getMonth() + 1;
        textYear.setText(year + "");
        textMon.setText(mon + "");

        fillDate(year, mon);



        Button btnmove2 = (Button) this.findViewById(R.id.bt2);
        btnmove2.setOnClickListener(this);


    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,

        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    public void animact (){
        Intent intents = new Intent(this, animationActivity.class);
        startActivity(intents);

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
                Toast.makeText(MainActivity.this,"MonthView",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(MainActivity.this,"WeekView",Toast.LENGTH_SHORT).show();
                Intent wintent = new Intent(this, WeekActivity.class);
                startActivity(wintent);
                break;
            case 2:
                Toast.makeText(MainActivity.this,"DayView",Toast.LENGTH_SHORT).show();
                Intent dintent = new Intent(this, DayActivity.class);
                startActivity(dintent);
                break;
            case 3:
                Toast.makeText(MainActivity.this,"Add Schedule",Toast.LENGTH_SHORT).show();
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


    private void fillDate(int year, int mon) {
        mItems.clear();

        mItems.add("일");
        mItems.add("월");
        mItems.add("화");
        mItems.add("수");
        mItems.add("목");
        mItems.add("금");
        mItems.add("토");


        Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // 요일도 int로 저장.

        for (int i = 0; i < day; i++) {
            mItems.add("");
        }

        current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.
        int last = 32 - current.getDate();

        for (int i = 1; i <= last; i++) {
            mItems.add(i + "");
        }
        adapter.notifyDataSetChanged();

    }

    public void Dialog_DatePicker() {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int Syear, int monthOfYear, int dayOfMonth) {
                String _dateStr = Syear+"년 "+(monthOfYear + 1) + "월 ";
                Toast.makeText(MainActivity.this, "SET"+ _dateStr, Toast.LENGTH_SHORT).show();
                textYear.setText(Syear + "");
                textMon.setText((monthOfYear+1) + "");
                fillDate(Syear,(monthOfYear+1));
            }
        };
        DatePickerDialog alert = new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
        alert.show();
    }






    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if (mItems.get(arg2).equals("")) {
            ;
        } else {
            Intent intent = new Intent(this, ExToday.class);
            intent.putExtra("Param1", textYear.getText().toString() + "/"
                    + textMon.getText().toString() + "/" + mItems.get(arg2));
            startActivity(intent);
        }
    }
}


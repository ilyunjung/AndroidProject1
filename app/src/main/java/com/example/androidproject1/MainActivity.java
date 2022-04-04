package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //날짜를 채우기 위한 dates 배열
    ArrayList dates = new ArrayList<String>();
    //전역변수 선언을 통해 년과 월을 전달받아 Calendar 설정함.
    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView date = (TextView) findViewById(R.id.date);

        Calendar cal1 = Calendar.getInstance();

        Intent dataIntent = getIntent();
        //인텐트로 전달된 데이터인 년, 월이 있으면 month, year에 저장되고 없으면 0으로 초기화
        year = dataIntent.getIntExtra("년",0);
        month = dataIntent.getIntExtra("월",0);
        //액티비티로 전달된 년도와 월 정보가 없는 경우
        if(year ==0 && month==0)
        {
            Calendar cal = Calendar.getInstance();

            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH); //0월부터 시작
            cal1.set(year,month,1);
            int firstDay = cal1.get(Calendar.DAY_OF_WEEK); // 첫번째 요일
            int lastDay = cal1.getActualMaximum(Calendar.DATE); //달의 마지막 날짜
            for (int i = 1; i < firstDay; i++) {
                dates.add(""); 
            }
            for (int j = 1; j <= lastDay; j++) {

                dates.add(String.valueOf(j));
            }

            date.setText(year + "년" + (month+1) + "월");

        }
        //액티비티로 전달된 년도와 월 정보가 있는 경우
        else
        {
            cal1.set(year,month,1);
            int firstDay = cal1.get(Calendar.DAY_OF_WEEK); // 첫번째 요일
            int lastDay = cal1.getActualMaximum(Calendar.DATE); //달의 마지막 날짜
            for (int i = 1; i < firstDay; i++) {
                dates.add("");
            }
            for (int j = 1; j <= lastDay; j++) {

                dates.add(String.valueOf(j));
            }

            date.setText(year + "년" + (month+1) + "월");
        }

        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dates);

        // 어댑터를 설정
        GridView gridView = (GridView) findViewById(R.id.gridview);
        // 어댑터 연결
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView print = (TextView) view.findViewById(android.R.id.text1);
                Toast.makeText(MainActivity.this,
                        //토스트 메시지를 통해서 그리드뷰 각 항목을 선택했을 경우 년,월,일을 띄움
                        year + "." + (month+1) + "." + print.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        Button prev = findViewById(R.id.monthprev);
        prev.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent p_intent = new Intent(getApplicationContext(), MainActivity.class);
                //month변수가 0이면 1월이므로 이전 버튼을 눌렀을때 이전년도 12월이 되게 설정함
                if(month ==0) {
                    p_intent.putExtra("월", 11);
                    p_intent.putExtra("년", year-1);
                    finish();
                    startActivity(p_intent);
                }
                else {
                    p_intent.putExtra("년", year);
                    p_intent.putExtra("월", month - 1);
                    finish();
                    startActivity(p_intent);
                }
            }
        });
        Button next = findViewById(R.id.monthnext);
        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent n_intent = new Intent(getApplicationContext(), MainActivity.class);
                //month변수가 11이면 12월이므로 다음 버튼을 눌렀을 때 다음년도 1월이 되게 설정함
                if(month ==11) {
                    n_intent.putExtra("월", 0);
                    n_intent.putExtra("년", year + 1);
                    finish();
                    startActivity(n_intent);
                }
                else {
                    n_intent.putExtra("년", year);
                    n_intent.putExtra("월", month + 1);
                    finish();
                    startActivity(n_intent);
                }
            }
        });

    }
}


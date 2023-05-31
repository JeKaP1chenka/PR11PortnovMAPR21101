package com.example.pr11portnovmapr21101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public boolean Started = false;
    public boolean Finished = false;
    private Handler handler;
    private Runnable car2Runnable;
    Random random = new Random();
    Button start, drive1;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        start=findViewById(R.id.btnStartStop);
        start.setOnClickListener(this);
        drive1=findViewById(R.id.btnDriveUpCar);
        drive1.setOnClickListener(this);
        result = findViewById(R.id.tvResult);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        handler = new Handler();
        car2Runnable = new Runnable() {
            @Override
            public void run() {
                carDrive(R.id.Car2, "Победил 2 игрок",30);
                if (Started && !Finished) {
                    handler.postDelayed(this, 100);
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartStop:
                if (!Finished) {
                    if (!Started) {
                        start.setBackgroundColor(Color.RED);
                        start.setText("Пауза");
                        Started = true;
                        handler.post(car2Runnable);
                    } else {
                        start.setBackgroundColor(Color.GREEN);
                        start.setText("Старт");
                        Started = false;
                        handler.removeCallbacks(car2Runnable);
                    }
                }else {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btnDriveUpCar:
                carDrive(R.id.Car1, "Победил 1 игрок", 70);
                break;
        }

    }
    public void carDrive(int id, String t, int speed){
        View Car = findViewById(id);
        if(Started && !Finished){
            ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams)Car.getLayoutParams();
            margin.leftMargin += speed;
            Car.requestLayout();
            if(margin.leftMargin > this.getResources().getDisplayMetrics().heightPixels + this.getResources().getDisplayMetrics().heightPixels/4){
                result.setText(t);
                start.setText("Заново");
                result.setTextColor(0xfff00000);
                Finished = true;
                Started = false;
            }
        }
    }
}

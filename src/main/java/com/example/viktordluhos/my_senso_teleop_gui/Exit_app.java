package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Exit_app extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_app);


        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }
}

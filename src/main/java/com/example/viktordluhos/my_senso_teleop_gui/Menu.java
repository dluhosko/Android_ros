package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick_connect_to_ROS(View view) {
        Intent intent = new Intent (this, Connect_to_ROS.class);
        startActivity(intent);
    }

    public void onClick_joint_control(View view) {
        Intent intent = new Intent (this, Joint_control.class);
        startActivity(intent);
    }

    public void onClick_linear_control(View view) {
        Intent intent = new Intent (this, Linear_control.class);
        startActivity(intent);
    }

    public void onClick_ModeAndTest(View view) {
        Intent intent = new Intent (this, Mode_and_test.class);
        startActivity(intent);
    }


    public void onClick_sensodrive(View view){
        Intent intent = new Intent (this, Sensodrive_info.class);
        startActivity(intent);
    }

    public void onClick_exit(View view) {
        Intent intent = new Intent (this, Exit_app.class);
        startActivity(intent);
    }








}

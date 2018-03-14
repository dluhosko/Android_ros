package com.example.viktordluhos.my_senso_teleop_gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Connect_to_ROS extends AppCompatActivity {

    ImageView status_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to__ros);

        status_iv=(ImageView) findViewById(R.id.imageView);


    }
}

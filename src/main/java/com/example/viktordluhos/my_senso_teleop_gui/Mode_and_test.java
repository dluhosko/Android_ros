package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Mode_and_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_and_test);
    }

    public void onClick_teach_mode(View view) {
        Intent intent = new Intent (this, Teach_mode.class);
        startActivity(intent);
    }

    public void onClick_virtual_cube(View view) {
        Intent intent = new Intent (this, Virtual_cube.class);
        startActivity(intent);
    }

    public void onClick_VibrotacTest(View view) {
        Intent intent = new Intent (this, Vibrotac_test.class);
        startActivity(intent);

    }
}

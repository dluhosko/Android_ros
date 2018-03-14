package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


//import org.ros.namespace.GraphName;
//import org.ros.node.topic.Publisher;
//import org.ros.node.topic.Subscriber;

public class Vibrotac_test extends AppCompatActivity {

    Button btn_testAll, btn_upLeft, btn_upCenter, btn_upRight, btn_left, btn_right, btn_downLeft, btn_downCenter, btn_downRight;
    boolean toggle=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrotac_test);

        btn_testAll=(Button) findViewById(R.id.button_testAll);
        btn_upLeft=(Button) findViewById(R.id.button17);
        btn_upCenter=(Button) findViewById(R.id.button16);
        btn_upRight=(Button) findViewById(R.id.button18);
        btn_left=(Button) findViewById(R.id.button19);
        btn_right=(Button) findViewById(R.id.button20);
        btn_downLeft=(Button) findViewById(R.id.button22);
        btn_downCenter=(Button) findViewById(R.id.button21);
        btn_downRight=(Button) findViewById(R.id.button23);

        btn_upLeft.setBackgroundColor(Color.RED);
        btn_upCenter.setBackgroundColor(Color.RED);
        btn_upRight.setBackgroundColor(Color.RED);
        btn_left.setBackgroundColor(Color.RED);
        btn_right.setBackgroundColor(Color.RED);
        btn_downLeft.setBackgroundColor(Color.RED);
        btn_downCenter.setBackgroundColor(Color.RED);
        btn_downRight.setBackgroundColor(Color.RED);
        btn_testAll.setBackgroundColor(Color.RED);
    }

    public void onClick_upCenter(View view) {
        Toast.makeText(getApplicationContext(),"Up center clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_upLeft(View view) {
        Toast.makeText(getApplicationContext(),"Up left clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_upRight(View view) {
        Toast.makeText(getApplicationContext(),"Up right clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_left(View view) {
        Toast.makeText(getApplicationContext(),"Left clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_right(View view) {
        Toast.makeText(getApplicationContext(),"Right clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_downCenter(View view) {
        Toast.makeText(getApplicationContext(),"Down center clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_downLeft(View view) {
        Toast.makeText(getApplicationContext(),"Down left clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_downRight(View view) {
        Toast.makeText(getApplicationContext(),"Down right clicked",Toast.LENGTH_SHORT).show();
    }

    public void onClick_testAll(View view) {
        if (toggle){
            toggle=false;
            btn_testAll.setBackgroundColor(Color.RED);
        }else {
            toggle=true;
            btn_testAll.setBackgroundColor(Color.GREEN);
        }
        Toast.makeText(getApplicationContext(),"Test all clicked",Toast.LENGTH_SHORT).show();
    }

}

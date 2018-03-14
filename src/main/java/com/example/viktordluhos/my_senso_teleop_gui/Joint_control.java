package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Joint_control extends AppCompatActivity {

    ImageButton imageButtonJ1Left,imageButtonJ2Left,imageButtonJ1Right,imageButtonJ2Right,imageButtonJ3Down, imageButtonJ3Up;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Switch switchButton;
    TextView joint1_tv, joint2_tv, joint3_tv, gripper_state_tv, x_pos_tv, y_pos_tv, z_pos_tv;

    private int step_size=1;
    private double pos_x=0, pos_y=0, pos_z=0, joint1_val=0, joint2_val=0, joint3_val=0;
    private boolean gripper_state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joint_control);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        joint1_tv = (TextView) findViewById(R.id.tv_joint1_value);
        joint2_tv = (TextView) findViewById(R.id.tv_joint2_value);
        joint3_tv = (TextView) findViewById(R.id.tv_joint3_value);
        gripper_state_tv = (TextView) findViewById(R.id.tv_gripper_state);
        x_pos_tv = (TextView) findViewById(R.id.tv_x_pos);
        y_pos_tv = (TextView) findViewById(R.id.tv_y_pos);
        z_pos_tv = (TextView) findViewById(R.id.tv_z_pos);

        imageButtonJ1Left=(ImageButton) findViewById(R.id.ib_j1_left);
        imageButtonJ1Right=(ImageButton) findViewById(R.id.ib_j1_right);
        imageButtonJ2Left=(ImageButton) findViewById(R.id.ib_j2_left);
        imageButtonJ2Right=(ImageButton) findViewById(R.id.ib_j2_right);
        imageButtonJ3Down=(ImageButton) findViewById(R.id.ib_j3_down);
        imageButtonJ3Up=(ImageButton) findViewById(R.id.ib_j3_up);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        switchButton = (Switch) findViewById(R.id.switchButton);

        imageButtonJ1Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joint1_val=joint1_val-step_size;
                joint1_tv.setText(joint1_val+" deg");
                pos_x-=step_size;
                x_pos_tv.setText(pos_x + " mm");
                Toast.makeText(getApplicationContext(),"J1 left button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonJ1Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joint1_val=joint1_val+step_size;
                joint1_tv.setText(joint1_val+" deg");
                pos_x+=step_size;
                x_pos_tv.setText(pos_x + " mm");
                Toast.makeText(getApplicationContext(),"J1 right button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonJ2Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joint2_val=joint2_val-step_size;
                joint2_tv.setText(joint2_val+" deg");
                pos_y-=step_size;
                y_pos_tv.setText(pos_y + " mm");
                Toast.makeText(getApplicationContext(),"J2 left button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonJ2Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joint2_val=joint2_val+step_size;
                joint2_tv.setText(joint2_val+" deg");
                pos_y+=step_size;
                y_pos_tv.setText(pos_y + " mm");
                Toast.makeText(getApplicationContext(),"J2 right button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonJ3Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joint3_val=joint3_val-step_size;
                joint3_tv.setText(joint3_val + " mm");
                pos_z-=step_size;
                z_pos_tv.setText(pos_z + " mm");
                Toast.makeText(getApplicationContext(),"J3 down button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonJ3Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joint3_val=joint3_val+step_size;
                joint3_tv.setText(joint3_val + " mm");
                pos_z+=step_size;
                z_pos_tv.setText(pos_z + " mm");
                Toast.makeText(getApplicationContext(),"J3 up button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    gripper_state_tv.setText("On");
                }else{
                    gripper_state_tv.setText("Off");
                }

                Toast.makeText(getApplicationContext(),"State of the gripper is "+isChecked,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onRadioButtonClicked (View view){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        int index;
        radioButton = (RadioButton)findViewById(radioButtonId);
        index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
        switch (index){
            case 0:
                step_size=1;
                break;
            case 1:
                step_size=2;
                break;
            case 2:
                step_size=5;
                break;
            case 3:
                step_size=10;
                break;
            default:
                break;
        }
        Toast.makeText(Joint_control.this,"You have choosed "+ radioButton.getText() , Toast.LENGTH_SHORT).show();
    }


}

package com.example.viktordluhos.my_senso_teleop_gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Linear_control extends AppCompatActivity {

    ImageButton ib_up,ib_down,ib_left,ib_right, ib_elev_down, ib_elev_up;
    TextView x_pos_tv, y_pos_tv, z_pos_tv, joint1_tv, joint2_tv, joint3_tv, gripper_state_tv;
    Switch switchButton;
    EditText editText_step_size;

    private int step_size=1;
    private double pos_x=0, pos_y=0, pos_z=0, joint1_val=0, joint2_val=0, joint3_val=0;
    private boolean gripper_state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_control);

        x_pos_tv=(TextView) findViewById(R.id.tv_x_pos);
        y_pos_tv=(TextView) findViewById(R.id.tv_y_pos);
        z_pos_tv=(TextView) findViewById(R.id.tv_z_pos);
        joint1_tv=(TextView) findViewById(R.id.tv_joint1_value);
        joint2_tv=(TextView) findViewById(R.id.tv_joint2_value);
        joint3_tv=(TextView) findViewById(R.id.tv_joint3_value);
        gripper_state_tv=(TextView) findViewById(R.id.tv_gripper_state);
        editText_step_size = (EditText) findViewById(R.id.et_step_size);

        ib_up=(ImageButton) findViewById(R.id.ib_button_up);
        ib_down=(ImageButton) findViewById(R.id.ib_button_down);
        ib_right=(ImageButton) findViewById(R.id.ib_button_right);
        ib_left=(ImageButton) findViewById(R.id.ib_button_left);
        ib_elev_up= (ImageButton) findViewById(R.id.ib_button_elevation_up);
        ib_elev_down= (ImageButton) findViewById(R.id.ib_button_elevation_down);
        switchButton = (Switch) findViewById(R.id.switchButton);


        ib_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_x-=step_size;
                x_pos_tv.setText(pos_x + " mm");
                joint1_val-=step_size;
                joint1_tv.setText(joint1_val+" deg");

                Toast.makeText(getApplicationContext(),"Button up clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ib_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_x+=step_size;
                x_pos_tv.setText(pos_x + " mm");
                joint1_val+=step_size;
                joint1_tv.setText(joint1_val+" deg");

                Toast.makeText(getApplicationContext(),"Button down clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ib_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_y-=step_size;
                y_pos_tv.setText(pos_y + " mm");
                joint2_val-=step_size;
                joint2_tv.setText(joint2_val+" deg");

                Toast.makeText(getApplicationContext(),"Button left clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ib_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_y+=step_size;
                y_pos_tv.setText(pos_y + " mm");
                joint2_val+=step_size;
                joint2_tv.setText(joint2_val+" deg");
                Toast.makeText(getApplicationContext(),"Button right clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ib_elev_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_z+=step_size;
                z_pos_tv.setText(pos_z + " mm");
                joint3_val+=step_size;
                joint3_tv.setText(joint3_val+" mm");
                Toast.makeText(getApplicationContext(),"Button right clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ib_elev_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_z-=step_size;
                z_pos_tv.setText(pos_z + " mm");
                joint3_val-=step_size;
                joint3_tv.setText(joint3_val+" mm");
                Toast.makeText(getApplicationContext(),"Button right clicked",Toast.LENGTH_SHORT).show();
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

    public void onEnterClicked(View view) {

        String value=editText_step_size.getText().toString();
        step_size=Integer.parseInt(value);
//        step_size = ((int) editText_step_size.getTextAlignment());
        Toast.makeText(getApplicationContext(),"Step size set to "+step_size,Toast.LENGTH_SHORT).show();
    }
}

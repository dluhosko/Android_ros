package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sensodrive.vibrotac.api.IVibroTacDevice;
import com.sensodrive.vibrotac.api.exceptions.VibroTacException;
import com.sensodrive.vibrotac.api.impl.VibroTacDevice;


//import org.ros.namespace.GraphName;
//import org.ros.node.topic.Publisher;
//import org.ros.node.topic.Subscriber;


public class Vibrotac_test extends AppCompatActivity {

    Button btn_testAll, btn_left, btn_right, btn_downLeft, btn_downCenter1, btn_downCenter2, btn_downRight;
    boolean toggle=false;
    String bluetooth_address;
    public static final int btnTestAllID = 0, btnLeftID = 1, btnRightID =2, btnDownLeftID=3, btnDownCenter1ID =4, btnDownCenter2ID =5, btnDownRightID =6;
    Context ctx;

    IVibroTacDevice vibroTac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrotac_test);

        btn_testAll=(Button) findViewById(R.id.button_testAll);
        btn_left=(Button) findViewById(R.id.button_left);
        btn_right=(Button) findViewById(R.id.button_right);
        btn_downLeft=(Button) findViewById(R.id.button_down_1);
        btn_downCenter1=(Button) findViewById(R.id.button_down_2);
        btn_downCenter2=(Button) findViewById(R.id.button_down_3);
        btn_downRight=(Button) findViewById(R.id.button_down_4);

        btn_testAll.setBackgroundColor(Color.RED);
        btn_left.setBackgroundColor(Color.RED);
        btn_right.setBackgroundColor(Color.RED);
        btn_downLeft.setBackgroundColor(Color.RED);
        btn_downCenter1.setBackgroundColor(Color.RED);
        btn_downCenter2.setBackgroundColor(Color.RED);
        btn_downRight.setBackgroundColor(Color.RED);

        ctx = getApplicationContext();
    }

    public void onClick_ConnectBluetooth(View view) {
        EditText editText = (EditText) findViewById(R.id.et_bluetoothAdress);
        bluetooth_address = editText.getText().toString();
        Toast.makeText(getApplicationContext(),"Connect to bluetooth adress: "+bluetooth_address,Toast.LENGTH_SHORT).show();
        try {
            IVibroTacDevice vibroTac = new VibroTacDevice();
            vibroTac.connectBluetooth(bluetooth_address);
            vibroTac.disconnect();
        }
        catch(VibroTacException e){
            Toast.makeText(getApplicationContext(),"ERROR: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick_left(final View view) {
        Toast.makeText(getApplicationContext(),"Left clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnLeftID);
        workerBtn.start();
    }

    public void onClick_right(View view) {
        Toast.makeText(getApplicationContext(),"Right clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnRightID);
        workerBtn.start();
    }

    public void onClick_downCenter1(View view) {
        Toast.makeText(getApplicationContext(),"Down center 1 clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnDownCenter1ID);
        workerBtn.start();
    }

    public void onClick_downCenter2(View view) {
        Toast.makeText(getApplicationContext(),"Down center 2 clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnDownCenter2ID);
        workerBtn.start();
    }

    public void onClick_downLeft(View view) {
        Toast.makeText(getApplicationContext(),"Down left clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnDownLeftID);
        workerBtn.start();
    }

    public void onClick_downRight(View view) {
        Toast.makeText(getApplicationContext(),"Down right clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnDownRightID);
        workerBtn.start();
    }

    public void onClick_testAll(View view) {
        Toast.makeText(getApplicationContext(),"Test all clicked",Toast.LENGTH_SHORT).show();
        WorkerBtn workerBtn = new WorkerBtn(btnTestAllID);
        workerBtn.start();
    }

    private void setColorBtnLeft(final int color){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn_left.setBackgroundColor(color);
            }
        });
    }

    private void setColor(final int btnID, final int color){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (btnID) {
                    case 0:
                        btn_testAll.setBackgroundColor(color);
                        btn_left.setBackgroundColor(color);
                        btn_right.setBackgroundColor(color);
                        btn_downLeft.setBackgroundColor(color);
                        btn_downCenter1.setBackgroundColor(color);
                        btn_downCenter2.setBackgroundColor(color);
                        btn_downRight.setBackgroundColor(color);
                        break;
                    case 1:
                        btn_left.setBackgroundColor(color);
                        break;
                    case 2:
                        btn_right.setBackgroundColor(color);
                        break;
                    case 3:
                        btn_downLeft.setBackgroundColor(color);
                        break;
                    case 4:
                        btn_downCenter1.setBackgroundColor(color);
                        break;
                    case 5:
                        btn_downCenter2.setBackgroundColor(color);
                        break;
                    case 6:
                        btn_downRight.setBackgroundColor(color);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private class WorkerBtn extends  Thread {
        int btnID=-1;

        private  WorkerBtn(int buttonID){
            this.btnID = buttonID;
        }
        @Override
        public void run(){
            setColor(btnID, Color.GREEN);
            try {
           IVibroTacDevice vibroTac = new VibroTacDevice();
            vibroTac.connectBluetooth(bluetooth_address);
                switch (btnID){
                    case btnTestAllID:
                        vibroTac.sendBasicModuleControl(1,100);
                        vibroTac.sendBasicModuleControl(2,100);
                        vibroTac.sendBasicModuleControl(3,100);
                        vibroTac.sendBasicModuleControl(4,100);
                        vibroTac.sendBasicModuleControl(5,100);
                        vibroTac.sendBasicModuleControl(6,100);
                        break;
                    case btnLeftID:
                        vibroTac.sendBasicModuleControl(1,100);
                        break;
                    case btnRightID:
                        vibroTac.sendBasicModuleControl(6,100);
                        break;
                    case btnDownLeftID:
                        vibroTac.sendBasicModuleControl(2,100);
                        break;
                    case btnDownCenter1ID:
                        vibroTac.sendBasicModuleControl(3,100);
                        break;
                    case btnDownCenter2ID:
                        vibroTac.sendBasicModuleControl(4,100);
                        break;
                    case btnDownRightID:
                        vibroTac.sendBasicModuleControl(5,100);
                        break;
                    default:
                        break;
                }
                Thread.sleep(1000);
            vibroTac.stopAll();
            vibroTac.disconnect();
            }
        catch(final VibroTacException e){
            //Toast.makeText(getApplicationContext(),"ERROR motor 1: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                public void run(){
                    Toast.makeText(ctx,"ERROR: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }});
        }
            catch(InterruptedException e){
                // log
                e.printStackTrace();
            }
            setColor(btnID, Color.RED);
        }
    }
}

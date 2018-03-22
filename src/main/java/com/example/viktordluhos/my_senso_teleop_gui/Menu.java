package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.ros.internal.node.topic.DefaultPublisher;
//import org.ros.node.Node;
//import org.ros.node.ConnectedNode;
//import org.ros.exception.RosException;
//import org.ros.namespace.NameResolver;
//import org.ros.node.ConnectedNode;
//import org.ros.node.topic.Subscriber;
////import org.ros.android.activity.RosAppActivity;
////import ros.android.views.JoystickView;
////import ros.android.views.SensorImageView;
//
//import org.ros.concurrent.CancellableLoop;
//import org.ros.namespace.GraphName;
//import org.ros.node.AbstractNodeMain;
//import org.ros.node.ConnectedNode;
//import org.ros.node.NodeMain;
//import org.ros.node.topic.Publisher;

import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;

public class Menu extends AppCompatActivity implements NodeMain{

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


    @Override
    public GraphName getDefaultNodeName() {
        return null; //new GraphName("senso_teleop_node");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {

    }

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }
}

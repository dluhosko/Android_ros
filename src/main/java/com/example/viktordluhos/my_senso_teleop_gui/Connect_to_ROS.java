package com.example.viktordluhos.my_senso_teleop_gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.ros.internal.node.topic.DefaultPublisher;
import org.ros.node.Node;
import org.ros.node.ConnectedNode;
import org.ros.exception.RosException;
import org.ros.namespace.NameResolver;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;
import org.ros.internal.node.topic.TopicParticipant;
import org.ros.RosCore;
import java.net.URI;

public class Connect_to_ROS extends AppCompatActivity {

    ImageView status_iv;
    String masterURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super("Pubsub Tutorial", "Pubsub Tutorial", URI.create("http://localhost:11311"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to__ros);
        status_iv=(ImageView) findViewById(R.id.imageView);

        //publisher.publish();

    }

    public void onClick_Connect(View view) {
        EditText editText = (EditText) findViewById(R.id.et_masterURI);
        masterURI = editText.getText().toString();
        Toast.makeText(getApplicationContext(),"Master URI: "+masterURI,Toast.LENGTH_SHORT).show();

    }
}

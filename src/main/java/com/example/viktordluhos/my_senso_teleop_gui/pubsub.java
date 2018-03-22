package com.example.viktordluhos.my_senso_teleop_gui;

import android.widget.Toast;

import org.apache.commons.logging.Log;
import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import std_msgs.String;

public class pubsub extends AbstractNodeMain{
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros_android_node");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        //Toast.makeText(getApplicationContext(),"onStart!!!!",Toast.LENGTH_SHORT).show();
        final Publisher<String> publisher = connectedNode.newPublisher("test_pub_topic", std_msgs.String._TYPE);
        final Log log = connectedNode.getLog();
        Subscriber<String> subscriber = connectedNode.newSubscriber("test_sub_topic", std_msgs.String._TYPE);


        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;

            @Override
            protected void setup(){
                sequenceNumber=0;
            }

            @Override
            protected void loop() throws InterruptedException {
                std_msgs.String str = publisher.newMessage();
                str.setData("Hello world" + sequenceNumber);
                sequenceNumber++;
                Thread.sleep(1000);
            }
        });


        subscriber.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(std_msgs.String message) {
                log.info("I heard" + message.getData() + "\"");
            }
        });
    }

}

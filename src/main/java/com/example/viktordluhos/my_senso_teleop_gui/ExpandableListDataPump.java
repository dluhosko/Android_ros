package com.example.viktordluhos.my_senso_teleop_gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> home = new ArrayList<String>();
        home.add("-SENSODRIVE is a spin-off from the German Aerospace Center (DLR). We are specialists in implementing the latest developments in drive technology in either our own or custom-built products. In order to do this we use the most modern methods from concurrent engineering and mechatronics. \n\n" +
                "-Innovation, quality, creativity and flexibility have the highest priority for us. As do professional and personal customer support and a reliable service – that is why we can count well-known manufacturers from the areas of aerospace, robotics, medical technology, simulator technology and automobile technology as our customers. \n\n" +
                "-We see ourselves as a system supplier for optimal drive solutions. Since 2010 our Quality Management System is certified.");

        List<String> products = new ArrayList<String>();
        products.add("-Our comprehensive portfolio leaves nothing to be desired and offers the perfect solution for every customer: force-feedback systems, vibrotactile man-machine interfaces, motor controller, sensor actuators, valve drives, linear actuators, laboratory robots and test stands, torque sensors, electrical rotary rables.\n\n "+
                "-The sensor drives are based on the DLR light-weight robot joints. By optimizing each individual component the sensor drives cannot be surpassed as regards compactness and performance. It can be assumed that the demand for such intelligent, (torque) controlled, highly integrated light-weight drives will increase enormously in the future for the most varied areas of application – in particular, automotive technology, aircraft construction, robotics and machine tools.\n\n");
        products.add("SENSO cockpit simulator");
        products.add("Force-Feedback Wheels");
        products.add("Force-Feedback Pedals");
        products.add("Force-Feedback Joystick");
        products.add("Force-Feedback Shifter");
        products.add("Force-Feedback Button");
        products.add("Force-Feedback Knob");
        products.add("Master-Slave System");
        products.add("Vibrotac");
        products.add("Motor controllers");
        products.add("Torque sensors");
        products.add("Torque controlled drives");
        products.add("Laboratory robot");
        products.add("Electrical rotary table");

        List<String> aboutSensodrive = new ArrayList<String>();
        aboutSensodrive.add("We have made it our goal to transfer the latest developments in drive technology into services and products.\n\n" +
                "A top quality all-round service awaits our customers: consultation and conception, prototype construction and the product development to the series device – everything from one source. Force-Feedback-Systems, motor controllers, sensor actuators, valve drives, linear actuators, laboratory robots and test stands, torque sensors, electrical rotary\n" +
                "tables – our comprehensive portfolio leaves nothing to be desired. We have the appropriate solution for every customer – or implement completely individual ideas.\n\n"+
                "Years of experience and highly-qualified, dedicated employees are not only the basis of the company but also the guarantee of a successful, trustful collaboration with you. With a large range of services and products from our three core areas, development, custom-built products and series products, we are your partner for innovative solutions regarding drive technology.");

        List<String> contact = new ArrayList<String>();
        contact.add("Address: SENSODRIVE GmbHm Argelsrieder Feld 20 TE04, D-82234 Weßling, Germany");
        contact.add("Phone number: +49 (0)8153 90 90 1 - 0");
        contact.add("Fax: +49 (0)8153 90 90 1 - 10");

        expandableListDetail.put("Contact", contact);
        expandableListDetail.put("Products", products);
        expandableListDetail.put("About Sensodrive", aboutSensodrive);
        expandableListDetail.put("Home", home);
        return expandableListDetail;
    }
}
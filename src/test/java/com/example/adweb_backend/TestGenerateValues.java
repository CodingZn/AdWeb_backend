package com.example.adweb_backend;

import com.example.adweb_backend.controller.DistributionController;
import org.junit.jupiter.api.Test;

public class TestGenerateValues {
    @Test
    void testGenerateXYNormal(){
        short[][]r = DistributionController.generateXYNormal(20, 5, 10000);
        for (int i = 0; i < r.length; i++) {
            StringBuilder a = new StringBuilder();
            for (int j = 0; j < r[0].length; j++) {
                a.append(r[i][j]);
                a.append("\t");
            }
            System.out.println(a.toString());
        }
    }


    @Test
    void testUniform(){
        short[][]r = DistributionController.generateXYUniform(10000);
        for (int i = 0; i < r.length; i++) {
            StringBuilder a = new StringBuilder();
            for (int j = 0; j < r[0].length; j++) {
                a.append(r[i][j]);
                a.append("\t");
            }
            System.out.println(a.toString());
        }
    }

    @Test
    void testConcurrent(){
        DistributionController d = new DistributionController();
        MyThread t1 = new MyThread("1", d);
        MyThread t2 = new MyThread("2", d);
        MyThread t3 = new MyThread("3", d);

        t1.start();
        t2.start();
        t3.start();

    }
}

class MyThread extends Thread {
    private Thread t;
    private String threadName;
    private DistributionController distributionController;

    MyThread(String name, DistributionController distributionController) {
        threadName = name;
        this.distributionController = distributionController;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );
        distributionController.concurrentDo();
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
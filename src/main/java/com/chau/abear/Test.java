package com.chau.abear;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Test {

    public static void main(String[] args){
        final String path = "/file/new.txt";
        File file = new File(path);

    }


    public static String get(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1000 * 10);
            connection.setUseCaches(true);
            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                StringBuffer stringBuffer = new StringBuffer();
                int length = 0;
                while((length=inputStream.read(buffer)) > 0){
                    stringBuffer.append(new String(buffer, 0, length));
                }
                return stringBuffer.toString();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void test1(){
        CyclicBarrier cb = new CyclicBarrier(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("current waiting thread number:" + cb.getNumberWaiting());
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}


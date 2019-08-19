package com.example.app.Printer.printer;

import android.content.Context;

import com.example.app.Printer.adapter.USBAdapter;


public class PrintOrder {

    public PrintOrder() {

    }

    public void Print(Context context, String msg) {
        USBAdapter usba = new USBAdapter();
        usba.createConn(context);
        try {
            usba.printMessage(context, msg);
            usba.closeConnection(context);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

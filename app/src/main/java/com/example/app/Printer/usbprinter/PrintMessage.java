package com.example.app.Printer.usbprinter;

import android.app.Activity;
import android.os.Bundle;

import com.example.app.R;
import com.example.app.Printer.printer.PrintOrder;


public class PrintMessage extends Activity {

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        String msg = "This is a test message";
        PrintOrder printer = new PrintOrder();
        printer.Print(this, msg);
    }
}

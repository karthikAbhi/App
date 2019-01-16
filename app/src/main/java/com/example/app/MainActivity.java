package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

public class MainActivity extends AppCompatActivity {

    public static String CHAT_PREFS = "";
    public static String NAME = "";
    public static int code[] = new int[10];
    //public static String list[][] = new String[10][10];
    public static String AGE = "";
    public static List<Book> lstBook;
    public static SharedPreferences prefs;
    Button Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstBook = new ArrayList<>();
        /*lstBook.add(new Book("The Vegetarian", "Category Book", "Description book",R.drawable.thevigitarian));
        lstBook.add(new Book("The Wild Robot", "Category Book", "Description book",R.drawable.thewildrobot));
        lstBook.add(new Book("Maria Semples", "Category Book", "Description book",R.drawable.mariasemples));
        lstBook.add(new Book("The Martian", "Category Book", "Description book",R.drawable.themartian));
        lstBook.add(new Book("He Died with...", "Category Book", "Description book",R.drawable.hediedwith));

        lstBook.add(new Book("The Vegetarian", "Category Book", "Description book",R.drawable.thevigitarian));
        lstBook.add(new Book("The Wild Robot", "Category Book", "Description book",R.drawable.thewildrobot));
        lstBook.add(new Book("Maria Semples", "Category Book", "Description book",R.drawable.mariasemples));
        lstBook.add(new Book("The Martian", "Category Book", "Description book",R.drawable.themartian));
        lstBook.add(new Book("He Died with...", "Category Book", "Description book",R.drawable.hediedwith));

        lstBook.add(new Book("The Vegetarian", "Category Book", "Description book",R.drawable.thevigitarian));
        lstBook.add(new Book("The Wild Robot", "Category Book", "Description book",R.drawable.thewildrobot));
        lstBook.add(new Book("Maria Semples", "Category Book", "Description book",R.drawable.mariasemples));
        lstBook.add(new Book("The Martian", "Category Book", "Description book",R.drawable.themartian));
        lstBook.add(new Book("He Died with...", "Category Book", "Description book",R.drawable.hediedwith));
*/
        lstBook.add(new Book("Veg. Farmhouse", "Category Food", "Description Food", R.drawable.img10, 100.00));
        lstBook.add(new Book("Mint & Tomato", "Category Food", "Description Food", R.drawable.img2, 100.00));
        lstBook.add(new Book("Mushroomfarm", "Category Food", "Description Food", R.drawable.img3, 100.00));
        lstBook.add(new Book("Onion & Cheese", "Category Food", "Description Food", R.drawable.img4, 100.00));
        lstBook.add(new Book("Corn & Cheese", "Category Food", "Description Food", R.drawable.img5, 100.00));
        lstBook.add(new Book("Mushroom ", "Category Food", "Description Food", R.drawable.img6, 100.00));
        lstBook.add(new Book("Margherrita", "Category Food", "Description Food", R.drawable.img7, 100.00));
        lstBook.add(new Book("Chicken Sausage", "Category Food", "Description Food", R.drawable.img8, 100.00));
        lstBook.add(new Book("Tomato & Leaves", "Category Food", "Description Food", R.drawable.img9, 100.00));
        lstBook.add(new Book("Pepperoni", "Category Food", "Description Food", R.drawable.img10, 100.00));

        RecyclerView myrv = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);



        /*Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        long usedMemory = rt.totalMemory() - rt.freeMemory();
        Log.d("Memory", "Max Memory: " + Long.toString(maxMemory));
        Log.d("Memory", "Available Memory :" + usedMemory);

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        Log.d("Memory", "Memory Class :" + Integer.toString(memoryClass));*/

        prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(NAME, "KARTHIK");
        prefs.edit().putString(AGE, "12");
        prefs.edit().commit();

    }

    public void print_the_content(View view) {

        Intent intent = new Intent(getApplicationContext(),Checkout.class);
        startActivity(intent);

    }


}

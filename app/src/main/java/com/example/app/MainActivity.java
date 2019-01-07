package com.example.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String CHAT_PREFS = "";
    public static String NAME = "";
    public static String AGE = "";
    List<Book> lstBook;

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
        lstBook.add(new Book("Mushroom  & Cheese", "Category Food", "Description Food", R.drawable.img6, 100.00));
        lstBook.add(new Book("Margherrita", "Category Food", "Description Food", R.drawable.img7, 100.00));
        lstBook.add(new Book("Chicken Sausage", "Category Food", "Description Food", R.drawable.img8, 100.00));
        lstBook.add(new Book("Tomato & Leaves", "Category Food", "Description Food", R.drawable.img9, 100.00));
        lstBook.add(new Book("Pepperoni", "Category Food", "Description Food", R.drawable.img10, 100.00));

        RecyclerView myrv = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        long usedMemory = rt.totalMemory() - rt.freeMemory();
        Log.d("Memory", "Max Memory: " + Long.toString(maxMemory));
        Log.d("Memory", "Available Memory :" + usedMemory);

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        Log.d("Memory", "Memory Class :" + Integer.toString(memoryClass));

        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(NAME, "KARTHIK");
        prefs.edit().putString(AGE, "12");
        prefs.edit().commit();

    }
}

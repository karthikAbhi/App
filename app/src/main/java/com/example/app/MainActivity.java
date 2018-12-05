package com.example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        lstBook.add(new Book(" Veg. Farmhouse", "Category Food", "Description Food", R.drawable.pizza1));
        lstBook.add(new Book("Mint & Tomato", "Category Food", "Description Food", R.drawable.img2));
        lstBook.add(new Book("Mushroomfarm", "Category Food", "Description Food", R.drawable.img3));
        lstBook.add(new Book("Onion & Cheese", "Category Food", "Description Food", R.drawable.img4));
        lstBook.add(new Book("Corn & Cheese", "Category Food", "Description Food", R.drawable.img5));
        lstBook.add(new Book("Mushroom  & Cheese", "Category Food", "Description Food", R.drawable.img6));
        lstBook.add(new Book("Margherrita", "Category Food", "Description Food", R.drawable.img7));
        lstBook.add(new Book("Chicken Sausage", "Category Food", "Description Food", R.drawable.img8));
        lstBook.add(new Book("Tomato & Leaves", "Category Food", "Description Food", R.drawable.img9));
        lstBook.add(new Book("Pepperoni", "Category Food", "Description Food", R.drawable.img10));

        RecyclerView myrv = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }
}

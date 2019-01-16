package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;

    public RecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_layout2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tv_book_title.setText(mData.get(position).getTitle());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(mContext, Book_Activity.class);
                intent.putExtra("BookTitle", mData.get(position).getTitle());
                intent.putExtra("Description", mData.get(position).getDescription());
                intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                intent.putExtra("Price", mData.get(position).getPrice());
                mContext.startActivity(intent);*/
            }
        });

        holder.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cur_quantity = Integer.parseInt(holder.mQuantity.getText().toString());
                holder.cur_quantity +=1;
                holder.mQuantity.setText(String.valueOf(holder.cur_quantity));
                holder.mTotalAmount.setText(String.valueOf(holder.cur_quantity*mData.get(position).getPrice()));
                MainActivity.code[position] = holder.cur_quantity;
                Toast.makeText(mContext,""+MainActivity.code[position],Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext,"Add Button pressed!",Toast.LENGTH_SHORT).show();
            }
        });
        holder.mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cur_quantity = Integer.parseInt(holder.mQuantity.getText().toString());
                holder.cur_quantity -= 1;
                if(holder.cur_quantity < 0){
                    Toast.makeText(mContext,"Oops..",Toast.LENGTH_SHORT).show();
                }
                else{
                    holder.mQuantity.setText(String.valueOf(holder.cur_quantity));
                    holder.mTotalAmount.setText(String.valueOf(holder.cur_quantity*mData.get(position).getPrice()));
                    //Toast.makeText(mContext,"Sub Button pressed!",Toast.LENGTH_SHORT).show();
                    MainActivity.code[position] = holder.cur_quantity;
                    Toast.makeText(mContext,""+MainActivity.code[position],Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_book_title, mQuantity, mTotalAmount;
        ImageView img_book_thumbnail;
        CardView cardView;
        public Button mAddButton,mSubButton;
        public int cur_quantity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            cardView = itemView.findViewById(R.id.cardview_id_1);
            mAddButton = itemView.findViewById(R.id.add);
            mSubButton = itemView.findViewById(R.id.sub);
            mQuantity = itemView.findViewById(R.id.quantity);
            cur_quantity = Integer.parseInt(mQuantity.getText().toString());
            mTotalAmount = itemView.findViewById(R.id.total_amount);
        }
    }
}

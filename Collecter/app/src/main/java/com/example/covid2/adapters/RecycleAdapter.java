package com.example.covid2.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid2.R;
import com.example.covid2.models.Deas;

import java.util.ArrayList;
import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewItem> {

    public static List<String> index;

    ArrayList<Deas> items;
    Context context;

    public RecycleAdapter(Context c, ArrayList<Deas> item) {
        items = item;
        context = c;
        index = new ArrayList<>();

    }

    //The View Item part responsible for connecting the row.xml with
    // each item in the RecyclerView
    //make declare and initalize
    class ViewItem extends RecyclerView.ViewHolder {

        //Declare
        TextView name;
        ImageView image;
        Button btn;

        //initialize
        public ViewItem(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.img);
            btn = itemView.findViewById(R.id.btnSelect);

        }
    }


    //onCreateViewHolder used to HAndle on Clicks
    @Override
    public ViewItem onCreateViewHolder(final ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);


        // this on click is for the row
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(parent.getContext(), "ROW Clicked", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        //this on click is for the button
        itemView.findViewById(R.id.btnSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "Sa7a wa Hana", Toast.LENGTH_SHORT).show();

            }
        });


        return new ViewItem(itemView);
    }


    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(ViewItem holder, final int position) {
        holder.name.setText(items.get(position).getDea());
        holder.image.setImageResource(items.get(position).getImPath());


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setImPath(R.drawable.checked);
                Toast.makeText(context, items.get(position).getDea(), Toast.LENGTH_SHORT).show();
                index.add(items.get(position).getDea());
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    // private final View.OnClickListener mOnClickListener = new MyOnClickListener();


//    @Override
//    public void onClick(final View view) {
//        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
//        String item = mList.get(itemPosition);
//        Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
//    }


}

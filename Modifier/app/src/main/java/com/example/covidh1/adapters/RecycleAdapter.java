package com.example.covidh1.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.covidh1.R;

import java.util.ArrayList;
import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewItem> {

    public static List<String> index;

    ArrayList<String> items;
    Context context;

    public RecycleAdapter(Context c, ArrayList<String> item) {
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
//        Button btnRecovered;


        //initialize
        public ViewItem(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_nid);
//            btnRecovered = itemView.findViewById(R.id.btnRecovered);
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
//        itemView.findViewById(R.id.btnRecovered).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(parent.getContext(), "Sa7a wa Hana", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        return new ViewItem(itemView);
    }


    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(ViewItem holder, final int position) {
        holder.name.setText(items.get(position));


//        holder.btnRecovered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, items.get(position), Toast.LENGTH_SHORT).show();
//                index.add(items.get(position));
//            }
//        });
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

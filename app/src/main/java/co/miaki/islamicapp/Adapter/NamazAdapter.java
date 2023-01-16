package co.miaki.islamicapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.miaki.islamicapp.Activities.DuaDetailActivity;
import co.miaki.islamicapp.Activities.NamazLessonDetailActivity;
import co.miaki.islamicapp.Models.DuaModel.DuaDataModel;
import co.miaki.islamicapp.Models.NamazLessonModel.NamazDataModel;
import co.miaki.islamicapp.R;

public class NamazAdapter extends RecyclerView.Adapter<DuaAdapter.ViewHolder> {


    private Context context;
    private ArrayList<NamazDataModel> dataList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public NamazAdapter(Context context, ArrayList<NamazDataModel> dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public DuaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.namaz_row_layout, parent, false);
        return new DuaAdapter.ViewHolder(view);
    }


    // binds the data to the view and textview in each row
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(DuaAdapter.ViewHolder holder, int position) {

        final String title = dataList.get(position).getTopic();
        final int itemId = dataList.get(position).getId();


        if(title!= null){
            holder.titleTxt.setText(title);
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NamazLessonDetailActivity.class);

                intent.putExtra("itemId",itemId);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener {

        TextView titleTxt;

        ViewHolder(View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.duaTxt);
        }


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        }
    }
}

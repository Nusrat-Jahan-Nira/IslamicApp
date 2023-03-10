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
import co.miaki.islamicapp.Activities.IslamicTopicActivity;
import co.miaki.islamicapp.Activities.IslamicTopicDetailActivity;
import co.miaki.islamicapp.Models.DuaModel.DuaDataModel;
import co.miaki.islamicapp.Models.IslamicTopicModel.IslamicTopicDataModel;
import co.miaki.islamicapp.R;


public class IslamicTopicAdapter extends RecyclerView.Adapter<IslamicTopicAdapter.ViewHolder> {


    private Context context;
    private ArrayList<IslamicTopicDataModel> dataList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public IslamicTopicAdapter(Context context, ArrayList<IslamicTopicDataModel> dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public IslamicTopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dua_row_layout, parent, false);
        return new IslamicTopicAdapter.ViewHolder(view);
    }


    // binds the data to the view and textview in each row
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(IslamicTopicAdapter.ViewHolder holder, int position) {

        final String title = dataList.get(position).getTitle();
        final String audioItem = dataList.get(position).getAudio_path();


        holder.titleTxt.setText(title);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, IslamicTopicDetailActivity.class);

                intent.putExtra("audioItem",audioItem);

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

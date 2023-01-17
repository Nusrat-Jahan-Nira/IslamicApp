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

import co.miaki.islamicapp.Activities.QuranDetailActivity;
import co.miaki.islamicapp.Models.QuranModel.QuranDataModel;
import co.miaki.islamicapp.R;


public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.ViewHolder> {


    private Context context;
    private ArrayList<QuranDataModel> dataList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public QuranAdapter(Context context, ArrayList<QuranDataModel> dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public QuranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.quran_row_layout, parent, false);
        return new QuranAdapter.ViewHolder(view);
    }


    // binds the data to the view and textview in each row
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final QuranAdapter.ViewHolder holder, int position) {

        final String name = dataList.get(position).getSuraName();
        final int itemId = dataList.get(position).getId();

        holder.surahName.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, QuranDetailActivity.class);
                i.putExtra("suraName",name);
                i.putExtra("itemId",itemId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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


        TextView surahName;

        ViewHolder(View itemView) {
            super(itemView);

            surahName = itemView.findViewById(R.id.quranTxt);

        }


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        }
    }
}

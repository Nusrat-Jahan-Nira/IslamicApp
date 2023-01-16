package co.miaki.islamicapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.miaki.islamicapp.Models.QuranDetailModel.QuranDetailDataModel;
import co.miaki.islamicapp.R;

public class QuranDetailAdapter extends RecyclerView.Adapter<QuranDetailAdapter.ViewHolder> {


    private Context context;
    private ArrayList<QuranDetailDataModel> dataList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public QuranDetailAdapter(Context context, ArrayList<QuranDetailDataModel> dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public QuranDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.quran_row_detail_layout, parent, false);
        return new QuranDetailAdapter.ViewHolder(view);
    }




    // binds the data to the view and textview in each row
    @SuppressLint("CheckResult")
    public void onBindViewHolder(final QuranDetailAdapter.ViewHolder holder, int position) {

        final String ayat = dataList.get(position).getAyat();
        final String meaning = dataList.get(position).getMeaning();

        holder.ayat.setText(ayat);
        holder.meaning.setText(meaning);



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView ayat,meaning;

        ViewHolder(View itemView) {
            super(itemView);

            ayat = itemView.findViewById(R.id.ayatTxt);
            meaning = itemView.findViewById(R.id.meaningAyat);

        }

    }
}


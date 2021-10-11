package com.example.assignment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Item> listItem;

    public ItemAdapter(Activity activity, List<Item> listItem) {
        this.activity = activity;
        this.listItem = listItem;
    }

    public void reloadData(List<Item> list){
        this.listItem = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.hour_list, parent, false);
        ItemHolder holder = new ItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        Item item = listItem.get(position);
        itemHolder.tvHour.setText(convertTime(item.getDateTime()));
        itemHolder.tvTemp.setText(item.getTemperature().getValue()+"");
        String url = "";
        if(item.getWeatherIcon() <10){
            url = "https://developer.accuweather.com/sites/default/files/0"+item.getWeatherIcon()+"-s.png";
        } else {
            url = "https://developer.accuweather.com/sites/default/files/"+item.getWeatherIcon()+"-s.png";
        }
        Glide.with(activity).load(url).into(itemHolder.ivCover);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView tvHour,tvTemp;
        ImageView ivCover;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            ivCover = itemView.findViewById(R.id.ivCover);
        }
    }

    public String convertTime(String inputTime) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("ha");
        String goal = outFormat.format(date);
        return goal;
    }

}

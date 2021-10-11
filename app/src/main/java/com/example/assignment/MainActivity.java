package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListItem;
    TextView tvStatus,tvCurrentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        tvStatus = findViewById(R.id.tvStatus);
        tvCurrentTemp = findViewById(R.id.tvCurrentTemp);

        getListData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);

        rvListItem = findViewById(R.id.rvListHour);
        rvListItem.setLayoutManager(layoutManager);

    }

    private void getListData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiManager service = retrofit.create(apiManager.class);
        service.getList().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.body() != null){
                    Log.d("dataList", response.body().toString());
                    List<Item> list = response.body();
                    ItemAdapter adapter = new ItemAdapter(MainActivity.this, list);
                    rvListItem.setAdapter(adapter);

                    Item item = list.get(0);
                    tvStatus.setText(item.getIconPhrase());
//                    tvCurrentTemp.setText(Html.fromHtml(item.getTemperature().getValue()+"<sup style='font-size: 10%'>o</sup>"));
                    String temp = String.valueOf(item.getTemperature().getValue());
                    String fullTemp = temp.concat("\u2070");
                    tvCurrentTemp.setText(fullTemp);

                    adapter.reloadData(list);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_LONG).show();
                Log.d("apiError", t.toString());
            }
        });
    }
}
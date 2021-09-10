package com.example.consultingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.Controller.ConsigneAdapter;
import com.example.consultingapp.Model.Consigne;
import com.example.consultingapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.consultingapp.SERVER.URLs.ROOT_URL;

public class ConsigneActivity extends AppCompatActivity {
    private Bundle extras;
    private ApiHold apiHold;
    private List<Consigne> consigneList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consigne);
        recyclerView = findViewById(R.id.consigneRecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ConsigneActivity.this));
        consigneList =  new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHold = retrofit.create(ApiHold.class);
        extras = getIntent().getExtras();
        String cat = extras.getString("catgeorieConsigne");


        Call<List<Consigne>> call = apiHold.consigneByCategorie(cat);
        call.enqueue(new Callback<List<Consigne>>() {
            @Override
            public void onResponse(Call<List<Consigne>> call, Response<List<Consigne>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ConsigneActivity.this,
                            "Code: " + response.code(), LENGTH_LONG).show();
                    return;
                }
                List<Consigne> consignes = response.body();


                for (Consigne consigne : consignes)
                {
                    consigneList.add(consigne);
                }

                adapter = new ConsigneAdapter( ConsigneActivity.this,consigneList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Consigne>> call, Throwable t) {
                Toast.makeText(ConsigneActivity.this,
                        t.getMessage(), LENGTH_LONG).show();
            }
        });

    }
}

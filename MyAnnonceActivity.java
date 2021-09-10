package com.example.consultingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.consultingapp.Controller.AnnonceAdapter;
import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.Controller.MyAnnonceAdapter;
import com.example.consultingapp.Controller.SessionManager;
import com.example.consultingapp.Model.Annonce;
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

public class MyAnnonceActivity extends AppCompatActivity {
    private Bundle extras;
    private ApiHold apiHold;
    private List<Annonce> annonceList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_annonce);

            // Inflate the layout for this fragment

            recyclerView = findViewById(R.id.AnnonceReyclerHistoryViewID);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            annonceList =  new ArrayList<>();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiHold = retrofit.create(ApiHold.class);

            Annonce ann = new Annonce(SessionManager.getInstance(MyAnnonceActivity.this).getUser().getId());
            Call<List<Annonce>> call = apiHold.userAnnonces(ann.getUserID());
            call.enqueue(new Callback<List<Annonce>>() {
                @Override
                public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Code: " + response.code(), LENGTH_LONG).show();
                        return;
                    }
                    List<Annonce> annonces = response.body();
                    for (Annonce annonce : annonces)
                    {
                        annonceList.add(annonce);
                    }

                    adapter = new MyAnnonceAdapter( MyAnnonceActivity.this,annonceList);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Annonce>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(), LENGTH_LONG).show();
                }
            });



        }
    }


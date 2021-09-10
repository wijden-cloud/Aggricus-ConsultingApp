package com.example.consultingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.Model.Annonce;
import com.example.consultingapp.Model.User;
import com.example.consultingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.consultingapp.SERVER.URLs.ROOT_URL;

public class AnnonceActivity extends AppCompatActivity {
    private TextView annonceTitle ;
    private TextView annonceDesc;
    private TextView userTel,userAddress,userEmail;

    private TextView userInfo;
    private Bundle extras;
    private ApiHold apiHold,apiHold2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);
        annonceDesc=findViewById(R.id.desc);
        annonceTitle=findViewById(R.id.titre);
        userInfo=findViewById(R.id.utilisateurAnn);
        userTel=findViewById(R.id.telAnn);
        userEmail =findViewById(R.id.emailAnn);
        userAddress = findViewById(R.id.addAnn);

        extras = getIntent().getExtras();
        String id = extras.getString("id");
        String userId = extras.getString("userID");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHold = retrofit.create(ApiHold.class);
        Call<Annonce> call = apiHold.annonceByID(id);
        call.enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(Call<Annonce> call, Response<Annonce> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),
                            "Error " , LENGTH_LONG).show();
                    return;
                }
              annonceTitle.setText(response.body().getTitle());
                annonceDesc.setText(response.body().getDescription());


            }


            @Override
            public void onFailure(Call<Annonce> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();
            }

        });
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHold2 = retrofit2.create(ApiHold.class);
        Call<User> callUser = apiHold.getUserInfo(userId);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),
                            "Error " , LENGTH_LONG).show();
                    return;
                }
                String textinfo = new String("Nom et prénom: "+ response.body().getName()+
                        "\n Adresse: " + response.body().getAddress()+"\n Numéro de téléphone: "+ response.body().getTel()+ "\n E-mail ");
                userInfo.setText(response.body().getName());
                userTel.setText( response.body().getTel());
                userEmail.setText(response.body().getEmail());
                userAddress.setText(response.body().getAddress());




            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();
            }

        });


    }
}

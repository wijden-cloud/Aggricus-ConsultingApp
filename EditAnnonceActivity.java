package com.example.consultingapp.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.Controller.SessionManager;
import com.example.consultingapp.MainActivity;
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

public class EditAnnonceActivity extends AppCompatActivity {
    private Button supprimerBtn,modifBtn,saveChangeBtn,annulerBtn;
    private EditText titre,description;
    private ApiHold apiHold;
    private String id;
    private Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_annonce);
        supprimerBtn = findViewById(R.id.supp);
        modifBtn = findViewById(R.id.modifierann);
        saveChangeBtn = findViewById(R.id.enregistrerbtn);
        annulerBtn = findViewById(R.id.annulerbtn);
        titre = findViewById(R.id.titreedit);
        description = findViewById(R.id.descedit);
        extras = getIntent().getExtras();
        id = extras.getString("myid");
        saveChangeBtn.setVisibility(View.INVISIBLE);
        titre.setVisibility(View.INVISIBLE);
        annulerBtn.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        annulerBtn.setClickable(false);
        saveChangeBtn.setClickable(false);


        supprimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprmierAnnonce();
            }
        });

        modifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnnonce();
                titre.setVisibility(View.VISIBLE);
                saveChangeBtn.setVisibility(View.VISIBLE);
                annulerBtn.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
               annulerBtn.setClickable(true);
                saveChangeBtn.setClickable(true);

            }
        });

        annulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangeBtn.setVisibility(View.INVISIBLE);
                titre.setVisibility(View.INVISIBLE);
                annulerBtn.setVisibility(View.INVISIBLE);
                description.setVisibility(View.INVISIBLE);
                annulerBtn.setClickable(false);
                saveChangeBtn.setClickable(false);
                description.setText("");
                titre.setText("");
            }
        });
        saveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Annonce annonce = new Annonce(titre.getText().toString(),description.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ROOT_URL )
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiHold = retrofit.create(ApiHold.class);



                Call<Annonce> call = apiHold.updateAnnonce(id,annonce);

                call.enqueue(new Callback<Annonce>() {
                    @Override
                    public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                        if (!response.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),
                                    "erreur" , LENGTH_LONG).show();
                            return;
                        }
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Annonce> call, Throwable t) {

                    }
                });

            }
        });


    }


    private void supprmierAnnonce(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAnnonceActivity.this);
        builder.setTitle("Supprimer l'annonce");
        builder.setMessage("je confirme la suppression de cette annonce  ");

        builder.setPositiveButton("CONFIRMER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ROOT_URL )
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiHold = retrofit.create(ApiHold.class);



                Call<Annonce> call = apiHold.deleteAnnonce(id);

                call.enqueue(new Callback<Annonce>() {
                    @Override
                    public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                        if (!response.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),
                                    "erreur" , LENGTH_LONG).show();
                            return;
                        }

                        finish();
                        startActivity(new Intent(EditAnnonceActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onFailure(Call<Annonce> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.show();


    }
    private void getAnnonce()
    {

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
                            "erreur" , LENGTH_LONG).show();
                    return;
                }

                titre.setText(response.body().getTitle());
                description.setText(response.body().getDescription());

            }

            @Override
            public void onFailure(Call<Annonce> call, Throwable t) {

            }
        });
    }
}

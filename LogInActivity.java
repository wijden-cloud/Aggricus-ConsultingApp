package com.example.consultingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.Controller.SessionManager;
import com.example.consultingapp.MainActivity;
import com.example.consultingapp.Model.User;
import com.example.consultingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.consultingapp.SERVER.URLs.ROOT_URL;

public class LogInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button buttonLogin,boutonSinscrire;
    private ProgressBar progressBar;
    private ApiHold apiHold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.logineditText);
        password = findViewById(R.id.logineditText2);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar2);
        boutonSinscrire= findViewById(R.id.buttonSignIn2);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiHold = retrofit.create(ApiHold.class);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        boutonSinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            }
        });
    }



    private void userLogin()
    {   final String myEmail = email.getText().toString().trim().toLowerCase();//trim() enleve les espaces
        final String myPassword = password.getText().toString().trim();
        //tester si le champ emal est vide
        if(TextUtils.isEmpty(myEmail)){
            email.setError("Enter you email please");
            email.requestFocus();
            return;
        }

        //tester si le champ ùot de passe est vide
        if(TextUtils.isEmpty(myPassword)){
            password.setError("Enter you password please");
            password.requestFocus();
            return;
        }
        User user= new User(myEmail,myPassword);
        Call<User> call = apiHold.login(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressBar.setVisibility(View.VISIBLE);



                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),
                            "Code: " +"wrong password or email " , LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            response.body().getId()  , LENGTH_LONG).show();
                    User userResopnse = new User(response.body().getName(), response.body().getEmail(), response.body().getPassword(), response.body().getRegion(), response.body().getProfession(), response.body().getTel(), response.body().getAddress(), response.body().getId(), response.body().isSubscriber());

                    SessionManager.getInstance(getApplicationContext()).userLogin(userResopnse);


                    checkInfo();
                    SessionManager.getInstance(getApplicationContext()).saveRealMdp(password.getText().toString());

                    //finir cette activity et start main activity
                    finish();
                    startActivity(new Intent(getApplicationContext(),
                            MainActivity.class));


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();
            }
        });
    }
    private void checkInfo()
    { Retrofit retrofit1 = new Retrofit.Builder()
            .baseUrl(ROOT_URL )
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        apiHold = retrofit1.create(ApiHold.class);
        User utilisateur1 = new User();



        Call<User> call1 = apiHold.getUserInfo(SessionManager.getInstance(getApplicationContext()).getUser(utilisateur1).getId());
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),
                            "Error " , LENGTH_LONG).show();
                    return;
                }

                SessionManager.getInstance(getApplicationContext()).updateInfo(response.body());



            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}

package com.example.consultingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.Controller.SessionManager;
import com.example.consultingapp.MainActivity;
import com.example.consultingapp.Model.User;
import com.example.consultingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.consultingapp.SERVER.URLs.ROOT_URL;

public class ProfilActivity extends AppCompatActivity {
    private Button logOutBtn,modifPwdBtn,saveChangeBtn,annulerBtn,mesAnnoncesBtn,connect;
    private TextView name,email;
    private EditText currentPass,newPass,newPassConfirm;
    private ApiHold apiHold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        mesAnnoncesBtn = findViewById(R.id.mes_annonces);
        name = findViewById(R.id.userName);
        email = findViewById(R.id.UserEmail);
        logOutBtn = findViewById(R.id.logOut);
        modifPwdBtn = findViewById(R.id.modifier);
        saveChangeBtn = findViewById(R.id.enregistrerbtn);
        annulerBtn = findViewById(R.id.annulerbtn);
        currentPass = findViewById(R.id.editTextp);
        newPass = findViewById(R.id.editTextp2);
        newPassConfirm = findViewById(R.id.editTextp3);
        connect = findViewById(R.id.proConnect);
        if(!SessionManager.getInstance(ProfilActivity.this).isLoggedIn())
        {
            connect.setVisibility(View.VISIBLE);
            mesAnnoncesBtn.setVisibility(View.INVISIBLE);
            modifPwdBtn.setVisibility(View.INVISIBLE);
            logOutBtn.setVisibility(View.INVISIBLE);
            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(ProfilActivity.this,LogInActivity.class));
                }
            });

        }

        else{
        final User currentUser = new User();
        name.setText(SessionManager.getInstance(ProfilActivity.this).getUser(currentUser).getName());
        email.setText(SessionManager.getInstance(ProfilActivity.this).getUser(currentUser).getEmail());
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(ProfilActivity.this).userLogout();
                finish();
                startActivity(new Intent(ProfilActivity.this, MainActivity.class));
            }
        });
        mesAnnoncesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), MyAnnonceActivity.class));
            }
        });
        modifPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesAnnoncesBtn.setVisibility(View.INVISIBLE);
                modifPwdBtn.setVisibility(View.INVISIBLE);
                currentPass.setVisibility(View.VISIBLE);
                newPass.setVisibility(View.VISIBLE);
                newPassConfirm.setVisibility(View.VISIBLE);
                saveChangeBtn.setVisibility(View.VISIBLE);
                annulerBtn.setVisibility(View.VISIBLE);
                modifPwdBtn.setClickable(false);
                currentPass.setClickable(true);
                newPass.setClickable(true);
                newPassConfirm.setClickable(true);
                saveChangeBtn.setClickable(true);
                annulerBtn.setClickable(true);

            }
        });
        annulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifPwdBtn.setVisibility(View.VISIBLE);
                currentPass.setVisibility(View.INVISIBLE);
                newPass.setVisibility(View.INVISIBLE);
                mesAnnoncesBtn.setVisibility(View.VISIBLE);
                newPassConfirm.setVisibility(View.INVISIBLE);
                saveChangeBtn.setVisibility(View.INVISIBLE);
                annulerBtn.setVisibility(View.INVISIBLE);
                modifPwdBtn.setClickable(true);
                currentPass.setClickable(false);
                newPass.setClickable(false);
                newPassConfirm.setClickable(false);
                saveChangeBtn.setClickable(false);
                annulerBtn.setClickable(false);
                currentPass.setText("");
                newPassConfirm.setText("");
                newPass.setText("");

            }
        });
        saveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mycurrentPass = currentPass.getText().toString().trim().toLowerCase();
                final String mynewPassConfirm = newPassConfirm.getText().toString().trim().toLowerCase();
                final String mynewPass = newPass.getText().toString().trim().toLowerCase();
                if (TextUtils.isEmpty(mycurrentPass)) {
                    currentPass.setError("Enter password please");
                    currentPass.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mynewPassConfirm)) {
                    newPassConfirm.setError("Enter password please");
                    newPassConfirm.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mynewPass)) {
                    newPass.setError("Enter password please");
                    newPass.requestFocus();
                    return;
                }
                if (!mycurrentPass.equals(SessionManager.getInstance(ProfilActivity.this).getPassword())) {
                    currentPass.setError("wrong password");
                    currentPass.requestFocus();
                    return;
                } else if (!mynewPass.equals(mynewPassConfirm)) {
                    newPass.setError("saisir le meme password");
                    newPass.requestFocus();
                    newPassConfirm.setError("saisir le meme password");
                    newPassConfirm.requestFocus();
                    return;
                } else {
                    sendNewPass(mynewPass);
                    SessionManager.getInstance(getApplicationContext()).saveRealMdp(mynewPass);
                    modifPwdBtn.setVisibility(View.VISIBLE);
                    currentPass.setVisibility(View.INVISIBLE);
                    newPass.setVisibility(View.INVISIBLE);
                    newPassConfirm.setVisibility(View.INVISIBLE);
                    saveChangeBtn.setVisibility(View.INVISIBLE);
                    annulerBtn.setVisibility(View.INVISIBLE);
                    modifPwdBtn.setClickable(true);
                    currentPass.setClickable(false);
                    newPass.setClickable(false);
                    newPassConfirm.setClickable(false);
                    saveChangeBtn.setClickable(false);
                    annulerBtn.setClickable(false);
                    currentPass.setText("");
                    newPassConfirm.setText("");
                    newPass.setText("");
                }


            }
        });

    }
    }
    private void sendNewPass(String password)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiHold = retrofit.create(ApiHold.class);
        User utilisateur = new User();
        final User userToSend = new User();
        userToSend.setPassword(password);
        Call<User> call = apiHold.updatePassword(SessionManager.getInstance(ProfilActivity.this)
                .getUser(utilisateur).getId(),userToSend);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),
                            "Code: " +"wrong password or email " , LENGTH_LONG).show();
                    return;
                }
                SessionManager.getInstance(ProfilActivity.this).userLogin(userToSend);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}

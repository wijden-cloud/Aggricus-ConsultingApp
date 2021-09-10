package com.example.consultingapp.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ApiHold apiHold;
    private String token;

    private EditText titreChamp;
    private EditText descriptionChamp;
    private Spinner categorieSpinner;
    private Button creer;
    private String categorie;


    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);




        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post, container, false);


        Button connect = view.findViewById(R.id.button3);
        Button sinscrire = view.findViewById(R.id.button);
        Button abonne = view.findViewById(R.id.button4);
         creer = view.findViewById(R.id.button5);
        titreChamp = view.findViewById(R.id.editText8);
        descriptionChamp = view.findViewById(R.id.editText7);
        categorieSpinner = view.findViewById(R.id.spinner2);
        categorieSpinner.setOnItemSelectedListener(this);


        abonne.setVisibility(view.INVISIBLE);

        checkInfo();

        sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),SignInActivity.class);
                startActivity(i);
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),LogInActivity.class);
                startActivity(i);
            }
        });
        abonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSubscription();
            }
        });





        //si l'utilisateur est déjà connecté
        if((SessionManager.getInstance(getActivity()).isLoggedIn())){


            connect.setVisibility(view.INVISIBLE);
            sinscrire.setVisibility(view.INVISIBLE);
            if((SessionManager.getInstance(getActivity()).isSubscribed()))
            { abonne.setVisibility(view.INVISIBLE);
                descriptionChamp.setVisibility(view.VISIBLE);
                categorieSpinner.setVisibility(view.VISIBLE);
                titreChamp.setVisibility(view.VISIBLE);
                creer.setVisibility(view.VISIBLE);

                creerAnnonce(view); }
            else {
                abonne.setVisibility(view.VISIBLE);
                descriptionChamp.setVisibility(view.INVISIBLE);
                categorieSpinner.setVisibility(view.INVISIBLE);
                titreChamp.setVisibility(view.INVISIBLE);
                creer.setVisibility(view.INVISIBLE);

            }

        }
        else {
            descriptionChamp.setVisibility(view.INVISIBLE);
            categorieSpinner.setVisibility(view.INVISIBLE);
            titreChamp.setVisibility(view.INVISIBLE);
            creer.setVisibility(view.INVISIBLE);

            if(SessionManager.getInstance(getActivity()).getToken() != null){
                //mettre à jour token
                token = SessionManager.getInstance(getActivity()).getToken().getToken();
            }
        }



        return view;



    }
    private void creerAnnonce(View view){
        //link attributs with view


        //categorie

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.catégorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorieSpinner.setAdapter(adapter);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHold = retrofit.create(ApiHold.class);


        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final  String myTitle = titreChamp.getText().toString();
                final  String myDescription = descriptionChamp.getText().toString();

                Annonce annonce= new Annonce(myTitle,myDescription,categorie,SessionManager.getInstance(getActivity())
                        .getUser().getId());


                Call<Annonce> call = apiHold.createAnnonce(annonce);
                call.enqueue(new Callback<Annonce>() {
                    @Override
                    public void onResponse(Call<Annonce> call, Response<Annonce> response) {

                        if (!response.isSuccessful()) {

                            Toast.makeText(getActivity(),
                                    "Error " , LENGTH_LONG).show();
                            return;
                        }
                  view.getContext().startActivity(new Intent(view.getContext(),MyAnnonceActivity.class));
                    }


                    @Override
                    public void onFailure(Call<Annonce> call, Throwable t) {
                        Toast.makeText(getActivity(),
                                t.getMessage(), LENGTH_LONG).show();
                    }

                });

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categorie = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void verifierChamps()
    {
    }


    private void sendSubscription()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiHold = retrofit.create(ApiHold.class);
        User utilisateur = new User();
        final User userToSend = new User();
        userToSend.setSubscriber(true);
        Call<User> call = apiHold.updateSubscription(SessionManager.getInstance(getActivity())
                .getUser(utilisateur).getId(),userToSend);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(),
                            "Code: " +"wrong password or email " , LENGTH_LONG).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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
        Call<User> call1 = apiHold.getUserInfo(SessionManager.getInstance(getActivity()).getUser(utilisateur1).getId());
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(),
                            "Error " , LENGTH_LONG).show();
                    return;
                }
                SessionManager.getInstance(getActivity()).userLogin(response.body());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }


}

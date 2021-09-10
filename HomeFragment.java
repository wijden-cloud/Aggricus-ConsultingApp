package com.example.consultingapp.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.consultingapp.Controller.AnnonceAdapter;
import com.example.consultingapp.Controller.ApiHold;
import com.example.consultingapp.MainActivity;
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
import static android.widget.Toast.makeText;
import static com.example.consultingapp.SERVER.URLs.ROOT_URL;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Bundle extras;
    private ApiHold apiHold;
    private List<Annonce> annonceList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView ;
    private Spinner spi ;
    private String statut;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        spi = (Spinner)view.findViewById(R.id.spinner3);
        spi.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapterSpi = ArrayAdapter.createFromResource(getActivity(),
                R.array.allCat, android.R.layout.simple_spinner_item);
        adapterSpi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(adapterSpi);

        recyclerView = view.findViewById(R.id.reyclerHistoryViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        annonceList =  new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHold = retrofit.create(ApiHold.class);


        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         statut = parent.getItemAtPosition(position).toString();

         if(statut.equals("Produit végétal"))
             statut="veg";
         if(statut.equals("Produit animal"))
             statut="ani";

        Call<List<Annonce>> call;

        if (statut.equals("Tout"))
            call = apiHold.allAnnonces();
        else call = apiHold.annoncesByCath(statut);
        call.enqueue(new Callback<List<Annonce>>() {
            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(),
                            "Code: " + response.code(), LENGTH_LONG).show();
                    return;
                }
                annonceList.clear();
                List<Annonce> annonces = response.body();
                for (Annonce annonce : annonces)
                {
                    annonceList.add(annonce);
                }

                adapter = new AnnonceAdapter( getActivity(),annonceList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        t.getMessage(), LENGTH_LONG).show();
            }
        });



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

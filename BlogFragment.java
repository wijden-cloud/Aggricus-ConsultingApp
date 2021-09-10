package com.example.consultingapp.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.consultingapp.Model.Consigne;
import com.example.consultingapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView irrImage,cpImage,plaImage,surImage,cheImage,deshImage,prepImage,feroImage,fercImage,recImage,proImage,mesImage;
    public BlogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
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
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        cpImage = view.findViewById(R.id.imageView8);
        plaImage = view.findViewById(R.id.imageView82);
        surImage = view.findViewById(R.id.imageView83);
        cheImage = view.findViewById(R.id.imageView84);
        deshImage = view.findViewById(R.id.imageView85);
        prepImage = view.findViewById(R.id.imageView86);
        irrImage = view.findViewById(R.id.imageView87);
        feroImage = view.findViewById(R.id.imageView88);
        fercImage = view.findViewById(R.id.imageView89);
        mesImage = view.findViewById(R.id.imageView810);
        recImage = view.findViewById(R.id.imageView811);
        proImage = view.findViewById(R.id.imageView812);

        cpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("choix_des_plantes");
            }
        });
        plaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("plantation");
            }
        });
        surImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("surveillance");
            }
        });
        cheImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("choix _d_emplacement");
            }
        });
        deshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("desherbage");
            }
        });
        prepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("preparation_du_champs");
            }
        });
        irrImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("irrigation");
            }
        });
        feroImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("fertilisation_organique");
            }
        });
        fercImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("fertilisation_chimique");
            }
        });
        mesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("mesures_preventives");
            }
        });
        recImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("recolte");
            }
        });
        proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerListe("protection_des_plantes");
            }
        });



        return view;

    }

    private void lancerListe(String bnd){
        Intent intent = new Intent( getActivity(), ConsigneActivity.class);
        intent.putExtra("catgeorieConsigne",bnd);
        startActivity(intent);
    }
}

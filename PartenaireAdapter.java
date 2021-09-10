package com.example.consultingapp.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultingapp.Model.Consigne;
import com.example.consultingapp.Model.Partenaire;
import com.example.consultingapp.R;

import java.util.List;

public class PartenaireAdapter extends RecyclerView.Adapter<PartenaireAdapter.PartenaireViewHolder> {
    private Context context;
    private List<Partenaire> partenaireList;

    public  PartenaireAdapter(Context context, List<Partenaire>partenaireList) {
        this.context = context;
        this.partenaireList = partenaireList;
    }

    @NonNull
    @Override
    public PartenaireAdapter.PartenaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partenaire_list,parent,false);


        return new PartenaireAdapter.PartenaireViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull PartenaireAdapter.PartenaireViewHolder holder, int position) {
        Partenaire partenaire = partenaireList.get(position);


        holder.titre.setText(partenaire.getName());
        holder.description.setText(partenaire.getDescription());
        holder.address.setText(partenaire.getAddress());
        holder.tel.setText(partenaire.getTel());
        holder.email.setText(partenaire.getEmail());


    }

    @Override
    public int getItemCount() {
        return partenaireList.size();
    }

    public class PartenaireViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titre,description,email,tel,address;
        public PartenaireViewHolder(@NonNull View itemView) {
            super(itemView);

            titre=itemView.findViewById(R.id.titreprt);
            description=itemView.findViewById(R.id.descprt);
            address= itemView.findViewById(R.id.adrprt);
            email = itemView.findViewById(R.id.emailptr);
            tel = itemView.findViewById(R.id.telptr);
        }

    }

}

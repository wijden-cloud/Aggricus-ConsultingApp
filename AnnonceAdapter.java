package com.example.consultingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultingapp.Model.Annonce;
import com.example.consultingapp.R;
import com.example.consultingapp.UI.AnnonceActivity;

import java.util.List;

public class AnnonceAdapter  extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {

    private Context context;
    private List<Annonce> annonceList;

    public  AnnonceAdapter(Context context, List<Annonce> annonceList) {
        this.context = context;
        this.annonceList = annonceList;
    }

    @NonNull
    @Override
    public AnnonceAdapter.AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annonce_list,parent,false);

        return new AnnonceAdapter.AnnonceViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull AnnonceAdapter.AnnonceViewHolder holder, int position) {
        Annonce annonce = annonceList.get(position);

        String newDate = formatDate(annonce.getCreationDate());
        holder.date.setText(newDate);
        holder.titre.setText(annonce.getTitle());

    }

    @Override
    public int getItemCount() {
        return annonceList.size();
    }

    public class AnnonceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView date,titre;
        public AnnonceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titre=itemView.findViewById(R.id.titreconsigne);
            date=itemView.findViewById(R.id.consignedate);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Annonce annonce = annonceList.get(position);
            Intent intent = new Intent( context, AnnonceActivity.class);
            intent.putExtra("id",annonce.getId());
            intent.putExtra("userID",annonce.getUserID());
            context.startActivity(intent);

        }
    }

    private String formatDate(String dateStr)
    {
        String MM = dateStr.substring(5,7);
        String dd = dateStr.substring(8,10);
        String HHmm = dateStr.substring(11,16);
        return dd+"/"+MM+" "+HHmm;
    }



}

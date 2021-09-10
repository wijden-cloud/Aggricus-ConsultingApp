package com.example.consultingapp.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultingapp.Model.Consigne;
import com.example.consultingapp.R;

import java.util.List;

public class ConsigneAdapter extends RecyclerView.Adapter<ConsigneAdapter.ConsigneViewHolder> {
    private Context context;
    private List<Consigne> consigneList;

    public  ConsigneAdapter(Context context, List<Consigne> consigneList) {
        this.context = context;
        this.consigneList = consigneList;
    }

    @NonNull
    @Override
    public ConsigneAdapter.ConsigneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consigne_list,parent,false);


        return new ConsigneAdapter.ConsigneViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull ConsigneAdapter.ConsigneViewHolder holder, int position) {
        Consigne consigne = consigneList.get(position);

        String newDate = formatDate(consigne.getCreationDate());
        holder.date.setText(newDate);
        holder.titre.setText(consigne.getTitle());
        holder.description.setText(consigne.getDescription());

    }

    @Override
    public int getItemCount() {
        return consigneList.size();
    }

    public class ConsigneViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titre,date,description;
        public ConsigneViewHolder(@NonNull View itemView) {
            super(itemView);

            titre=itemView.findViewById(R.id.titreconsigne);
            date=itemView.findViewById(R.id.consignedate);
            description=itemView.findViewById(R.id.consginedesc);
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

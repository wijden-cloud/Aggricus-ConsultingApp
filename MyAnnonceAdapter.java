package com.example.consultingapp.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultingapp.Model.Annonce;
import com.example.consultingapp.Model.User;
import com.example.consultingapp.R;
import com.example.consultingapp.UI.AnnonceActivity;
import com.example.consultingapp.UI.EditAnnonceActivity;
import com.example.consultingapp.UI.LogInActivity;
import com.example.consultingapp.UI.ProfilActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class MyAnnonceAdapter extends RecyclerView.Adapter<MyAnnonceAdapter.MyAnnonceViewHolder> {
    private Context context;
    private List<Annonce> annonceList;

    public  MyAnnonceAdapter(Context context, List<Annonce> annonceList) {
        this.context = context;
        this.annonceList = annonceList;
    }

    @NonNull
    @Override
    public MyAnnonceAdapter.MyAnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annonce_edit,parent,false);


        return new MyAnnonceAdapter.MyAnnonceViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull MyAnnonceAdapter.MyAnnonceViewHolder holder, int position) {
        Annonce annonce = annonceList.get(position);

        holder.titre.setText(annonce.getTitle());

    }

    @Override
    public int getItemCount() {
        return annonceList.size();
    }

    public class MyAnnonceViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {
        private TextView titre;
        public MyAnnonceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            titre=itemView.findViewById(R.id.titreAnnonce);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Annonce annonce = annonceList.get(position);
            Intent intent = new Intent( context, EditAnnonceActivity.class);
            intent.putExtra("myid",annonce.getId());

            context.startActivity(intent);
        }
    }

}

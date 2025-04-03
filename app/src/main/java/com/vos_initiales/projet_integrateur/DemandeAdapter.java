package com.vos_initiales.projet_integrateur;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DemandeAdapter extends RecyclerView.Adapter<DemandeAdapter.DemandeViewHolder> {

    private List<Demande> demandes;

    public DemandeAdapter(List<Demande> demandes) {
        this.demandes = demandes;
    }

    @NonNull
    @Override
    public DemandeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demande, parent, false);
        return new DemandeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemandeViewHolder holder, int position) {
        Demande demande = demandes.get(position);
        holder.Poste.setText(demande.titreStage);
        holder.textEtat.setText(demande.etat);

        switch (demande.etat.toLowerCase()) {
            case "refusé":
                holder.textEtat.setTextColor(Color.RED);
                break;
            case "en attente":
                holder.textEtat.setTextColor(Color.parseColor("#FFA500")); // orange
                break;
            case "accepté": //bref, si c'est ecrit accepté ca devient vert
                holder.textEtat.setTextColor(Color.parseColor("#4CAF50")); // vert doux
                break;
        }
    }

    @Override
    public int getItemCount() {
        return demandes.size();
    }

    static class DemandeViewHolder extends RecyclerView.ViewHolder {
        TextView Poste, textEtat;

        public DemandeViewHolder(@NonNull View itemView) {
            super(itemView);
            Poste = itemView.findViewById(R.id.textTitreStage);
            textEtat = itemView.findViewById(R.id.textEtat);
        }
    }
}

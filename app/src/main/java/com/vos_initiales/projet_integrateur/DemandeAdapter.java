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
        View vue = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demande, parent, false);
        return new DemandeViewHolder(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull DemandeViewHolder holder, int position) {
        Demande demande = demandes.get(position);
        holder.titreStage.setText(demande.getNomPoste());


        String statut = demande.getStatut();
        holder.statut.setText(statut);

        // Appliquer une couleur selon le statut
        switch (statut.toLowerCase()) {
            case "accepté":
                holder.statut.setTextColor(Color.parseColor("#2E7D32")); // vert foncé
                break;
            case "refusé":
                holder.statut.setTextColor(Color.parseColor("#C62828")); // rouge foncé
                break;
            case "en attente":
                holder.statut.setTextColor(Color.parseColor("#EF6C00")); // orange foncé
                break;
            default:
                holder.statut.setTextColor(Color.BLACK); // fallback
                break;
        }
    }

    @Override
    public int getItemCount() {
        return demandes.size();
    }

    static class DemandeViewHolder extends RecyclerView.ViewHolder {
        TextView titreStage, statut;

        public DemandeViewHolder(@NonNull View itemView) {
            super(itemView);
            titreStage = itemView.findViewById(R.id.textNomPoste);
            statut = itemView.findViewById(R.id.textStatut);
        }
    }
}

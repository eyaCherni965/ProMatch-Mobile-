package com.vos_initiales.projet_integrateur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {

    private List<Stage> stages;

    public StageAdapter(List<Stage> stages) {
        this.stages = stages;
    }

    @NonNull
    @Override
    public StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stage_card, parent, false);
        return new StageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StageViewHolder holder, int position) {
        Stage stage = stages.get(position);
        holder.titre.setText(stage.getNom_poste());
        holder.entreprise.setText("Entreprise : " + stage.getCompagnie());
        holder.lieu.setText("Lieu : " + stage.getAdresse());
        holder.description.setText(stage.getDesc_poste());
    }


    @Override
    public int getItemCount() {
        return stages.size();
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {
        TextView titre, entreprise, lieu, description;

        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre_stage);
            entreprise = itemView.findViewById(R.id.entreprise_stage);
            lieu = itemView.findViewById(R.id.lieu_stage);
            description = itemView.findViewById(R.id.desc_stage);
        }
    }
}


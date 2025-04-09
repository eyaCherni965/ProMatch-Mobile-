package com.vos_initiales.projet_integrateur;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Gestionnaire des données de stages
 */
public class StageDataManager {
    private final SwipingActivity activity;
    private List<Stage> stageList;
    private StageAdapter stageAdapter;
    private int currentPosition = 0;
    private boolean isFirstLaunch = true;

    public StageDataManager(SwipingActivity activity) {
        this.activity = activity;
        this.stageList = new ArrayList<>();
    }

    /**
     * Chargement des données de stages
     */
    public void loadStageData() {
        // Données temporaires
        stageList.add(new Stage(1, "Développeur Android",
                "Stage de 4 mois en développement mobile. Vous travaillerez sur des fonctionnalités innovantes pour nos applications Android les plus populaires.",
                "Google", "Montréal"));

        stageList.add(new Stage(2, "Designer UX/UI",
                "Participez à la conception de nos prochains jeux en créant des interfaces à la fois esthétiques et fonctionnelles. Expérience en design mobile requise.",
                "Ubisoft", "Québec"));

        stageList.add(new Stage(3, "Analyste de données",
                "Analysez les tendances financières et contribuez à la stratégie data-driven de notre organisation. Maîtrise de Python et SQL souhaitée.",
                "Desjardins", "Lévis"));

        stageList.add(new Stage(4, "Développeur Full-Stack",
                "Participez au développement de notre plateforme web et mobile en utilisant les dernières technologies. Stage de 6 mois avec possibilité d'embauche.",
                "Shopify", "Montréal"));

        stageList.add(new Stage(5, "Spécialiste Marketing Digital",
                "Aidez-nous à optimiser nos campagnes sur les réseaux sociaux et à analyser leur performance. Vous travaillerez directement avec notre équipe créative.",
                "LG Canada", "Toronto"));

        // Créer l'adaptateur
        stageAdapter = new StageAdapter(stageList);

        // Optionnel: récupérer des données depuis une API
        fetchStagesFromAPI();
    }

    /**
     * Récupération des stages depuis l'API
     */
    private void fetchStagesFromAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3008/") // à adapter
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StageAPI stageAPI = retrofit.create(StageAPI.class);
        stageAPI.getStages().enqueue(new Callback<List<Stage>>() {
            @Override
            public void onResponse(Call<List<Stage>> call, Response<List<Stage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stageList = response.body();
                    stageAdapter = new StageAdapter(stageList);

                    // Notifier le gestionnaire d'actions que les données ont été mises à jour
                    notifyDataUpdated();
                } else {
                    Log.e("API", "Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Stage>> call, Throwable t) {
                Log.e("API", "Échec : " + t.getMessage());
                // On garde les données temporaires si l'API échoue
            }
        });
    }

    /**
     * Notifie les autres composants que les données ont été mises à jour
     */
    private void notifyDataUpdated() {
        // Cette méthode sera étendue si besoin pour notifier d'autres composants
    }

    // Getters et setters
    public List<Stage> getStageList() { return stageList; }
    public StageAdapter getStageAdapter() { return stageAdapter; }
    public int getCurrentPosition() { return currentPosition; }
    public void setCurrentPosition(int position) { this.currentPosition = position; }
    public boolean isFirstLaunch() { return isFirstLaunch; }
    public void setFirstLaunch(boolean isFirstLaunch) { this.isFirstLaunch = isFirstLaunch; }
    public Stage getCurrentStage() {
        if (stageList.isEmpty() || currentPosition >= stageList.size()) {
            return null;
        }
        return stageList.get(currentPosition);
    }
    public boolean isLastStage() {
        return currentPosition >= stageList.size() - 1;
    }
}


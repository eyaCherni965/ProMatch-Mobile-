package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    public void loadStageData(String domaine, String salaire, String duree) {
        stageAdapter = new StageAdapter(stageList); // vide au départ
        fetchStagesFromAPI(domaine, salaire, duree); // charger depuis l'API avec filtres
    }


    /**
     * Récupère les stages depuis l'API Express
     */
    private void fetchStagesFromAPI(String domaine, String salaire, String duree) {
        Retrofit retrofit = RetrofitClient.getClient();
        StageAPI stageAPI = retrofit.create(StageAPI.class);

        boolean aucunFiltre =
                (domaine == null || domaine.equals("Tous les domaines")) &&
                        (salaire == null || salaire.equals("Tous les salaires")) &&
                        (duree == null || duree.equals("Toutes durées"));

        Call<List<Stage>> call;
        if (aucunFiltre) {
            call = stageAPI.getStages();
        } else {
            if (domaine != null && domaine.equals("Tous les domaines")) domaine = null;
            if (salaire != null && salaire.equals("Tous les salaires")) salaire = null;
            if (duree != null && duree.equals("Toutes durées")) duree = null;

            call = stageAPI.getFilteredStages(domaine, salaire, duree);
        }

        call.enqueue(new Callback<List<Stage>>() {
            @Override
            public void onResponse(Call<List<Stage>> call, Response<List<Stage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stageList = response.body();
                    stageAdapter = new StageAdapter(stageList);
                    Log.d("API", "Nombre de stages reçus : " + stageList.size());
                    notifyDataUpdated();
                } else {
                    Log.e("API", "Erreur HTTP : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Stage>> call, Throwable t) {
                Log.e("API", "Échec de la connexion : " + t.getMessage());
            }
        });
    }




    private void notifyDataUpdated() {
        activity.runOnUiThread(() -> {
            activity.getViewPager().setAdapter(stageAdapter);
        });
    }

    public List<Stage> getStageList() { return stageList; }
    public StageAdapter getStageAdapter() { return stageAdapter; }
    public int getCurrentPosition() { return currentPosition; }
    public void setCurrentPosition(int position) { this.currentPosition = position; }
    public boolean isFirstLaunch() { return isFirstLaunch; }
    public void setFirstLaunch(boolean isFirstLaunch) { this.isFirstLaunch = isFirstLaunch; }

    public Stage getCurrentStage() {
        if (stageList.isEmpty() || currentPosition >= stageList.size()) return null;
        return stageList.get(currentPosition);
    }

    public boolean isLastStage() {
        return currentPosition >= stageList.size() - 1;
    }
}

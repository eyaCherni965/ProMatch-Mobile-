package com.vos_initiales.projet_integrateur;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
     * Chargement initial des données
     */
    public void loadStageData() {
        // Données locales temporaires (utilisées si l'API échoue)
        stageList.add(new Stage(1, "Développeur Android", "Stage de 4 mois...", "Google", "Montréal"));
        stageList.add(new Stage(2, "Designer UX/UI", "Participez à la conception...", "Ubisoft", "Québec"));

        stageAdapter = new StageAdapter(stageList);
        fetchStagesFromAPI(); // ← important !
    }

    /**
     * Récupération des stages depuis l'API
     */
    private void fetchStagesFromAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = RetrofitClient.getClient();


        StageAPI stageAPI = retrofit.create(StageAPI.class);

        stageAPI.getStages().enqueue(new Callback<List<Stage>>() {
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

    /**
     * Appelée quand les données API sont prêtes
     */
    private void notifyDataUpdated() {
        activity.runOnUiThread(() -> {
            activity.showToast("Stages mis à jour depuis l'API !");
            activity.getViewPager().setAdapter(stageAdapter); // mise à jour dynamique
        });
    }

    // Getters / Setters
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

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

    // Chargement des données filtrées
    public void loadStageData(String domaine, String salaire, String duree) {
        fetchStagesFromAPI(domaine, salaire, duree);
    }

    private void fetchStagesFromAPI(String domaine, String salaire, String duree) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3005/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StageAPI stageAPI = retrofit.create(StageAPI.class);

        Call<List<Stage>> call = stageAPI.getFilteredStages(domaine, salaire, duree);
        call.enqueue(new Callback<List<Stage>>() {
            @Override
            public void onResponse(Call<List<Stage>> call, Response<List<Stage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stageList = response.body();
                    stageAdapter = new StageAdapter(stageList);
                    notifyDataUpdated();
                } else {
                    Log.e("API", "Erreur HTTP : " + response.code());
                    activity.showToast("Erreur lors du chargement des stages");
                }
            }

            @Override
            public void onFailure(Call<List<Stage>> call, Throwable t) {
                Log.e("API", "Échec API : " + t.getMessage());
                activity.showToast("Erreur de connexion à l'API");
            }
        });
    }

    private void notifyDataUpdated() {
        activity.runOnUiThread(() -> {
            activity.showToast("Stages filtrés chargés !");
            activity.getViewPager().setAdapter(stageAdapter);
        });
    }

    // Getters
    public List<Stage> getStageList() {
        return stageList;
    }

    public StageAdapter getStageAdapter() {
        return stageAdapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isFirstLaunch() {
        return isFirstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        isFirstLaunch = firstLaunch;
    }

    public Stage getCurrentStage() {
        if (stageList.isEmpty() || currentPosition >= stageList.size()) return null;
        return stageList.get(currentPosition);
    }

    public boolean isLastStage() {
        return currentPosition >= stageList.size() - 1;
    }
}

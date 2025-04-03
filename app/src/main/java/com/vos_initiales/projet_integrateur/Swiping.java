package com.vos_initiales.projet_integrateur;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;
import com.vos_initiales.projet_integrateur.StageAdapter;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Swiping extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private StageAdapter stageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping); // ton layout principal avec ViewPager2

        viewPager2 = findViewById(R.id.viewPagerStages);

        //  temporaire : on simule des stages (plus tard tu peux les récupérer via API)
        List<Stage> stageList = new ArrayList<>();
        stageList.add(new Stage(1, "Développeur Android", "Stage mobile Android", "Google", "Montréal"));
        stageList.add(new Stage(2, "Designer UX", "Stage UI/UX pour app mobile", "Ubisoft", "Québec"));
        stageList.add(new Stage(3, "Analyste données", "Stage en data", "Desjardins", "Lévis"));

        stageAdapter = new StageAdapter(stageList);
        viewPager2.setAdapter(stageAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3008/") // ← adapte selon ton backend
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StageAPI stageAPI = retrofit.create(StageAPI.class);

        stageAPI.getStages().enqueue(new Callback<List<Stage>>() {
            @Override
            public void onResponse(Call<List<Stage>> call, Response<List<Stage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Stage> stageList = response.body();
                    stageAdapter = new StageAdapter(stageList);
                    viewPager2.setAdapter(stageAdapter);
                } else {
                    Log.e("API", "Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Stage>> call, Throwable t) {
                Log.e("API", "Échec : " + t.getMessage());
            }
        });

    }
}

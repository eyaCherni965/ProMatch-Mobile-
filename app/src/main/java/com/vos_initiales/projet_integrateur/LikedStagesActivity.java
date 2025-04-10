package com.vos_initiales.projet_integrateur;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LikedStagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_stages);

        recyclerView = findViewById(R.id.recycler_view_liked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StageAdapter(LikedStagesManager.getLikedStages());
        recyclerView.setAdapter(adapter);
    }
}

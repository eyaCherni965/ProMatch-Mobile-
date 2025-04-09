package com.vos_initiales.projet_integrateur;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class SwipingActivity extends AppCompatActivity {

    private StageDataManager stageDataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping2); // Assurez-vous que ce fichier XML existe

        // Initialisation des vues nécessaires
        ImageView arrowLeft = findViewById(R.id.arrowLeft);
        ImageView arrowRight = findViewById(R.id.arrowRight);
        TextView swipeHintText = findViewById(R.id.swipeHintText);
        FrameLayout swipeHintContainer = findViewById(R.id.swipeHintContainer);
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerStages);
        ImageView overlayLike = findViewById(R.id.overlayLike);
        ImageView overlayDislike = findViewById(R.id.overlayDislike);
        Button btnStartSwiping = findViewById(R.id.btnStartSwiping);

        // Gestionnaires
        stageDataManager = new StageDataManager(this);
        AnimationManager animationManager = new AnimationManager(
                this,
                viewPager2,
                swipeHintContainer,
                arrowLeft,
                arrowRight,
                swipeHintText,
                overlayLike,
                overlayDislike
        );
        UIManager uiManager = new UIManager(this);
        SwipeActionManager swipeActionManager = new SwipeActionManager(
                this,
                viewPager2,
                animationManager,
                uiManager,
                stageDataManager
        );

        // Setup UI et interactions
        uiManager.setupUI(stageDataManager.isFirstLaunch());
        uiManager.setupListeners(swipeActionManager);

        // Chargement des données
        stageDataManager.loadStageData();
        viewPager2.setAdapter(stageDataManager.getStageAdapter());

        // Mise à jour des points de progression
        uiManager.updateProgressDots(0, stageDataManager.getStageList().size());
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

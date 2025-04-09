package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class SwipeActionManager {
    private final SwipingActivity activity;
    private final ViewPager2 viewPager;
    private final AnimationManager animationManager;
    private final UIManager uiManager;
    private final StageDataManager dataManager;

    private boolean isManualSwipe = false;
    private boolean isActionInProgress = false;

    public SwipeActionManager(SwipingActivity activity,
                              ViewPager2 viewPager,
                              AnimationManager animationManager,
                              UIManager uiManager,
                              StageDataManager dataManager) {
        this.activity = activity;
        this.viewPager = viewPager;
        this.animationManager = animationManager;
        this.uiManager = uiManager;
        this.dataManager = dataManager;
    }

    public void dismissTutorial() {
        animationManager.animateTutorialDismissal(() -> dataManager.setFirstLaunch(false));
    }

    public void likeCurrentStage() {
        if (isActionInProgress) return;

        if (!dataManager.isLastStage()) {
            isManualSwipe = true;
            isActionInProgress = true;

            animationManager.animateButton(uiManager.getBtnLike());

            Stage stage = dataManager.getCurrentStage();
            if (stage != null) {
                Log.d("SwipeAction", "LIKE -> " + stage.getTitre());
            } else {
                Log.w("SwipeAction", "LIKE -> Aucune donnée pour la position " + dataManager.getCurrentPosition());
            }

            new Handler().postDelayed(() -> {
                int nextPosition = dataManager.getCurrentPosition() + 1;
                viewPager.setCurrentItem(nextPosition, true);
            }, 200);
        } else {
            activity.showToast("Vous avez parcouru tous les stages disponibles");
        }
    }

    public void rejectCurrentStage() {
        if (isActionInProgress) return;

        if (!dataManager.isLastStage()) {
            isManualSwipe = true;
            isActionInProgress = true;

            animationManager.animateButton(uiManager.getBtnReject());

            Stage stage = dataManager.getCurrentStage();
            if (stage != null) {
                Log.d("SwipeAction", "REJECT -> " + stage.getTitre());
            } else {
                Log.w("SwipeAction", "REJECT -> Aucune donnée pour la position " + dataManager.getCurrentPosition());
            }

            new Handler().postDelayed(() -> {
                int nextPosition = dataManager.getCurrentPosition() + 1;
                simulateLeftSwipe(nextPosition);
            }, 200);
        } else {
            activity.showToast("Vous avez parcouru tous les stages disponibles");
        }
    }

    private void simulateLeftSwipe(int nextPosition) {
        viewPager.setCurrentItem(nextPosition, false); // saut sans animation
        // Peut être remplacé par une animation personnalisée si besoin
    }

    public void superLikeCurrentStage() {
        if (isActionInProgress) return;

        isManualSwipe = true;
        isActionInProgress = true;

        animationManager.animateButton(uiManager.getBtnSuperLike());

        Stage stage = dataManager.getCurrentStage();
        if (stage != null) {
            Log.d("SwipeAction", "SUPER LIKE -> " + stage.getTitre());
        }

        activity.showToast("Super Like! Redirection vers l'état des demandes...");

        // Redirection vers l'état des demandes
        Intent intent = new Intent(activity, EtatDeMesDemandes.class); // Assure-toi que c’est le bon nom
        activity.startActivity(intent);
    }

    public void rewindToPreviousStage() {
        int currentPosition = dataManager.getCurrentPosition();
        if (currentPosition > 0) {
            viewPager.setCurrentItem(currentPosition - 1, true);
            Stage stage = dataManager.getStageList().get(currentPosition - 1);
            Log.d("SwipeAction", "REWIND -> Retour sur : " + stage.getTitre());
        } else {
            activity.showToast("Vous êtes au début de la liste");
        }
    }

    public void onPageSelected(int newPosition) {
        int previous = dataManager.getCurrentPosition();
        Stage stage = dataManager.getStageList().get(newPosition);

        if (!isManualSwipe) {
            if (newPosition > previous) {
                Log.d("Swipe", "Intéressé par : " + stage.getTitre());
                animationManager.animateLikeSuccess();
            } else if (newPosition < previous) {
                Log.d("Swipe", "Pas intéressé par : " + stage.getTitre());
                animationManager.animateRejectSuccess();
            }
        }

        isManualSwipe = false;
        isActionInProgress = false;

        dataManager.setCurrentPosition(newPosition);
        uiManager.updateProgressDots(newPosition, dataManager.getStageList().size());
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Plus d'overlays
    }

    public void onPageScrollStateChanged(int state) {
        isActionInProgress = (state != ViewPager2.SCROLL_STATE_IDLE);
    }

    public boolean isManualSwipe() {
        return isManualSwipe;
    }
}
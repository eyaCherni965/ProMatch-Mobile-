package com.vos_initiales.projet_integrateur;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Gestionnaire des animations de l'application
 */
public class AnimationManager {
    private final SwipingActivity activity;
    private final ViewPager2 viewPager;
    private final FrameLayout swipeHintContainer;
    private final ImageView arrowLeft, arrowRight;
    private final TextView swipeHintText;
    private final ImageView overlayLike, overlayDislike;

    public AnimationManager(SwipingActivity activity,
                            ViewPager2 viewPager,
                            FrameLayout swipeHintContainer,
                            ImageView arrowLeft,
                            ImageView arrowRight,
                            TextView swipeHintText,
                            ImageView overlayLike,
                            ImageView overlayDislike) {
        this.activity = activity;
        this.viewPager = viewPager;
        this.swipeHintContainer = swipeHintContainer;
        this.arrowLeft = arrowLeft;
        this.arrowRight = arrowRight;
        this.swipeHintText = swipeHintText;
        this.overlayLike = overlayLike;
        this.overlayDislike = overlayDislike;
    }


    /**
     * Animation pour faire disparaître le tutoriel
     */
    public void animateTutorialDismissal(Runnable onAnimationEnd) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(swipeHintContainer, "alpha", 1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                swipeHintContainer.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);

                // Animer l'apparition des cartes
                viewPager.setAlpha(0f);
                viewPager.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();

                // Exécuter le callback une fois l'animation terminée
                if (onAnimationEnd != null) {
                    onAnimationEnd.run();
                }
            }
        });
        fadeOut.start();

        // Animation des flèches
        arrowLeft.animate().translationX(-200).alpha(0).setDuration(500).start();
        arrowRight.animate().translationX(200).alpha(0).setDuration(500).start();
        swipeHintText.animate().translationY(-100).alpha(0).setDuration(500).start();
    }

    /**
     * Animation pour l'apparition initiale des cartes
     */
    public void setupInitialAnimations(boolean isFirstLaunch) {
        if (!isFirstLaunch && viewPager != null) {
            viewPager.setScaleX(0.8f);
            viewPager.setScaleY(0.8f);
            viewPager.setAlpha(0f);

            viewPager.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(400)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }

    /**
     * Animation pour l'appui sur un bouton
     */
    public void animateButton(View button) {
        button.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(100)
                .withEndAction(() -> {
                    button.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start();
                }).start();
    }

    /**
     * Animation de super like
     */
    public void animateSuperLike(Runnable onAnimationEnd) {
        // Créer un effet d'étoile qui monte
        ImageView star = new ImageView(activity);
        star.setImageResource(R.drawable.perso);
        star.setLayoutParams(new FrameLayout.LayoutParams(150, 150));
        star.setX(viewPager.getWidth() / 2f - 75);
        star.setY(viewPager.getHeight() / 2f - 75);
        star.setAlpha(0.9f);

        ((FrameLayout)viewPager.getParent()).addView(star);

        star.animate()
                .translationY(-500)
                .alpha(0)
                .rotation(720)
                .setDuration(1000)
                .withEndAction(() -> {
                    ((FrameLayout)viewPager.getParent()).removeView(star);

                    // Exécuter le callback une fois l'animation terminée
                    if (onAnimationEnd != null) {
                        onAnimationEnd.run();
                    }
                })
                .start();
    }

    /**
     * Animation de succès après un like/superlike
     */
    public void animateLikeSuccess() {
        // Cette méthode peut être améliorée avec des animations plus complexes
    }

    /**
     * Animation après un rejet
     */
    public void animateRejectSuccess() {
        // Cette méthode peut être améliorée avec des animations plus complexes
    }
}

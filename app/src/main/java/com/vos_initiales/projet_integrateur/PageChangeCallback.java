package com.vos_initiales.projet_integrateur;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Gestion des événements de changement de page du ViewPager2
 */
public class PageChangeCallback extends ViewPager2.OnPageChangeCallback {
    private final SwipeActionManager actionManager;

    public PageChangeCallback(SwipeActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        actionManager.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
        actionManager.onPageScrollStateChanged(state);
    }

    // La méthode onPageScrolled a été retirée car elle n'est plus nécessaire
}

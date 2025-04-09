package com.vos_initiales.projet_integrateur;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;

public class UIManager {
    private final SwipingActivity activity;

    private ViewPager2 viewPager;
    private FrameLayout swipeHintContainer;
    private TextView swipeHintText;
    private ImageView arrowLeft, arrowRight;
    private Button btnStartSwiping;

    private ImageView btnReject, btnLike, btnSuperLike, btnRewind, btnProfile;
    private View dot1, dot2, dot3;

    public UIManager(SwipingActivity activity) {
        this.activity = activity;
        initViews();
    }

    private void initViews() {
        swipeHintContainer = activity.findViewById(R.id.swipeHintContainer);
        swipeHintText = activity.findViewById(R.id.swipeHintText);
        arrowLeft = activity.findViewById(R.id.arrowLeft);
        arrowRight = activity.findViewById(R.id.arrowRight);
        btnStartSwiping = activity.findViewById(R.id.btnStartSwiping);

        viewPager = activity.findViewById(R.id.viewPagerStages);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        btnReject = activity.findViewById(R.id.btnReject);
        btnLike = activity.findViewById(R.id.btnLike);
        btnSuperLike = activity.findViewById(R.id.btnSuperLike);
        btnRewind = activity.findViewById(R.id.btnRewind);
        btnProfile = activity.findViewById(R.id.btnProfile);

        dot1 = activity.findViewById(R.id.dot1);
        dot2 = activity.findViewById(R.id.dot2);
        dot3 = activity.findViewById(R.id.dot3);
    }

    public void setupUI(boolean isFirstLaunch) {
        if (isFirstLaunch) {
            swipeHintContainer.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.INVISIBLE);
        } else {
            swipeHintContainer.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }
    }

    public void setupListeners(SwipeActionManager actionManager) {
        btnStartSwiping.setOnClickListener(v -> actionManager.dismissTutorial());

        btnLike.setOnClickListener(v -> actionManager.likeCurrentStage());
        btnReject.setOnClickListener(v -> actionManager.rejectCurrentStage());
        btnSuperLike.setOnClickListener(v -> actionManager.superLikeCurrentStage());
        btnRewind.setOnClickListener(v -> actionManager.rewindToPreviousStage());
        btnProfile.setOnClickListener(v -> activity.showToast("Profil et préférences"));

        viewPager.registerOnPageChangeCallback(new PageChangeCallback(actionManager));
    }

    public void updateProgressDots(int position, int totalStages) {
        if (totalStages <= 0) return;

        boolean isFirst = position == 0;
        boolean isLast = position == totalStages - 1;
        boolean isMiddle = !isFirst && !isLast;

        dot1.setBackgroundResource(isFirst ? R.drawable.dot_active : R.drawable.dot_inactive);
        dot2.setBackgroundResource(isMiddle ? R.drawable.dot_active : R.drawable.dot_inactive);
        dot3.setBackgroundResource(isLast ? R.drawable.dot_active : R.drawable.dot_inactive);
    }

    // Getters
    public ViewPager2 getViewPager() { return viewPager; }
    public FrameLayout getSwipeHintContainer() { return swipeHintContainer; }
    public TextView getSwipeHintText() { return swipeHintText; }
    public ImageView getArrowLeft() { return arrowLeft; }
    public ImageView getArrowRight() { return arrowRight; }
    public ImageView getBtnLike() { return btnLike; }
    public ImageView getBtnReject() { return btnReject; }
    public ImageView getBtnSuperLike() { return btnSuperLike; }
}

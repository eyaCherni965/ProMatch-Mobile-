package com.vos_initiales.projet_integrateur;

import java.util.ArrayList;
import java.util.List;

public class LikedStagesManager {
    private static final List<Stage> likedStages = new ArrayList<>();

    public static void addStage(Stage stage) {
        if (!likedStages.contains(stage)) {
            likedStages.add(stage);
        }
    }

    public static List<Stage> getLikedStages() {
        return new ArrayList<>(likedStages); // pour éviter les modifications externes
    }

    public static void clear() {
        likedStages.clear();
    }
}

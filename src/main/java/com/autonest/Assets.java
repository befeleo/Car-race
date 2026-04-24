package com.autonest;

import javafx.scene.image.Image;

public class Assets {

    public static Image img(String name) {
        return new Image(Assets.class.getResource("/images/" + name).toExternalForm());
    }
}

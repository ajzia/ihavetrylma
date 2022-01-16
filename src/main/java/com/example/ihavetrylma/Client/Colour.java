package com.example.ihavetrylma.Client;

import javafx.scene.paint.Color;

/**
 * Class responsible for storing colours for tiles
 */
public class Colour {

    /**
     * Method returning a color depending on an owner number
     * @param owner player's id
     * @return color
     */
    public static Color getTileColor(int owner) {
        return switch (owner) {
            case 0 -> Color.web("#FCC6F6");
            case 1 -> Color.web("#C55FFC");
            case 2 -> Color.web("#68BBE3");
            case 3 -> Color.web("#94C973");
            case 4 -> Color.web("#E7625F");
            case 5 -> Color.web("#FDB750");
            default -> Color.web("#F3F3F3");
        };
    }

}

package com.example.ihavetrylma;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tile extends Circle {

    Color color;

    public Tile(Color color, int radius){
        this.color = color;
        setFill(color);
        setRadius(radius);
    }

    public void setColor(Color color){
        this.color = color;
        setFill(color);
    }

    public Color getColor(){
        return color;
    }
}
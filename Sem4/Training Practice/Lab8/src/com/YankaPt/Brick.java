package com.YankaPt;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Set;

public class Brick extends Label implements Observer {
    private int level;
    private ImageView imageView = new ImageView("flag_ireland.png");
    private double width = 40;
    private double height = 20;
    private Observable observable;

    public Brick(Observable o, int level) {
        super();
        o.addObserver(this);
        observable = o;
        setGraphic(imageView);
    }

    @Override
    public void update(Set setOfHitBlocks) {
        if(setOfHitBlocks.contains(hashCode()));
            getHit();
    }

    public void getHit() {
        level--;
        if(level==0)
            observable.removeObserver(this);

    }

}

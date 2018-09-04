package com.YankaPt;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Set;

public class Brick extends Label implements Observer {
    private int level;
    private ImageView level1Image = new ImageView("flag_ireland.png");
    private ImageView level2Image = new ImageView("flag_great_britain.png");
    public double width=0;
    public double height=0;
    //TODO private fields
    private boolean isDestroy = false;
    private Observable observable;

    public Brick(Observable o, int level) {
        super();
        o.addObserver(this);
        observable = o;
        this.level = level;
        switch (level) {
            case 1: {
                setGraphic(level1Image);
                width = level1Image.getImage().getWidth();
                height = level1Image.getImage().getHeight();
                break;
            }
            case 2: {
                setGraphic(level2Image);
                width = level2Image.getImage().getWidth();
                height = level2Image.getImage().getHeight();
                break;
            }
            default: break;
        }
    }

    @Override
    public void update(Set setOfHitBlocks) {
        if(setOfHitBlocks.contains(this))
            getHit();
    }

    public void getHit() {
        level--;
        //TODO change skin
        switch (level) {
            case 0: {
                isDestroy = true;
                this.setGraphic(null);
                observable.removeObserver(this);
                break;
            }
            case 1: {
                setGraphic(level1Image);
                width = level1Image.getImage().getWidth();
                height = level1Image.getImage().getHeight();
                break;
            }
            case 2: {
                setGraphic(level2Image);
                width = level2Image.getImage().getWidth();
                height = level2Image.getImage().getHeight();
                break;
            }
            default: break;
        }
    }
    public boolean isDestroy() {return isDestroy;}
}

package main.java.com.ubo.tp.twitub.ihm;

import main.java.com.ubo.tp.twitub.observers.IAccountObserver;

import java.awt.*;

public interface IPage {

    interface IView {

        Component show();
        void initUIComponents();
    }

    interface IController {

        void init();
        Component show();
        void addObserver(IAccountObserver observer);
    }

}

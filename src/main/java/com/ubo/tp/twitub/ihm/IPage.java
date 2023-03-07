package com.ubo.tp.twitub.ihm;

import com.ubo.tp.twitub.newObserver.IAccountObserver;

import java.awt.*;

public interface IPage {

    interface IView {

        Component show();

        void initUIComponents();
    }

    interface IController {

        void init();

        Component show();

        default void addObserver(IAccountObserver observer) {
        }
    }

}

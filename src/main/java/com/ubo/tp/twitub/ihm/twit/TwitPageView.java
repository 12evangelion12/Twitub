package com.ubo.tp.twitub.ihm.twit;

import com.ubo.tp.twitub.component.JTwitList;
import com.ubo.tp.twitub.component.JTwitSend;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.TwitListModel;
import com.ubo.tp.twitub.newObserver.ITwitObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TwitPageView implements IPage.IView {

    private final TwitListModel twits;
    private final User session;
    private final List<ITwitObserver> twitObservers;
    private final List<ITwitObserver> twitControllerObservers;
    private JPanel jPanel;
    private JTwitList jTwitList;
    private JTwitSend jTwitSend;

    public TwitPageView(User session, TwitListModel twits) {
        twitControllerObservers = new ArrayList<>();
        twitObservers = new ArrayList<>();
        this.session = session;
        this.twits = twits;
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        jTwitSend = new JTwitSend(session);
        jTwitSend.initGUI();
        twitObservers.add(jTwitSend);
        GridBagConstraints jTwitSendContraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 0, 0, 20), 0, 0);
        initSendTwitButton();
        initSearchTwitButton();

        jTwitList = new JTwitList(twits);
        jTwitList.initGUI();
        GridBagConstraints jTwitListContraint = new GridBagConstraints(0, 1, 1, 1, 1, 50, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 20, 20), 0, 0);

        jPanel.add(jTwitSend.getComponent(), jTwitSendContraint);
        jPanel.add(jTwitList.getComponent(), jTwitListContraint);
    }

    @Override
    public JPanel show() {
        return jPanel;
    }

    private void initSendTwitButton() {

        jTwitSend.initTwitButtonEvent(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String twitMessage = jTwitSend.getTwitMessage();

                if (twitMessage.length() == 0) {
                    twitObservers.forEach(ITwitObserver::notifyTwitIsEmpty);
                    return;
                }

                if (twitMessage.length() > 250) {
                    twitObservers.forEach(ITwitObserver::notifyTwitIsTooLong);
                    return;
                }

                twitObservers.forEach(ITwitObserver::notifyTwitAccepted);
                twitControllerObservers.forEach(iTwitObserver -> iTwitObserver.sendTwit(session, twitMessage));
            }
        });
    }

    private void initSearchTwitButton() {

        jTwitSend.initTwitSearchButtonEvent(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String twitMessage = jTwitSend.getTwitMessage();
                twitControllerObservers.forEach(iTwitObserver -> iTwitObserver.searchTwit(twitMessage));
            }
        });
    }

    public void addController(ITwitObserver observer) {
        twitControllerObservers.add(observer);
    }
}

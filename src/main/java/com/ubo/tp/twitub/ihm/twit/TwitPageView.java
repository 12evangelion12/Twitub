package main.java.com.ubo.tp.twitub.ihm.twit;

import main.java.com.ubo.tp.twitub.component.JTwitList;
import main.java.com.ubo.tp.twitub.component.JTwitSend;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.model.TwitListModel;
import main.java.com.ubo.tp.twitub.newObserver.ITwitControllerObserver;
import main.java.com.ubo.tp.twitub.newObserver.ITwitSendComponentObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TwitPageView implements IPage.IView {

    private JPanel jPanel;
    private JTwitList jTwitList;
    private JTwitSend jTwitSend;
    private final TwitListModel twits;
    private final User session;
    private final List<ITwitSendComponentObserver> twitSendComponentObserverList;
    private final List<ITwitControllerObserver> twitControllerObservers;

    public TwitPageView(User session, TwitListModel twits) {
        twitControllerObservers = new ArrayList<>();
        twitSendComponentObserverList = new ArrayList<>();
        this.session = session;
        this.twits = twits;
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        jTwitSend = new JTwitSend(session);
        jTwitSend.initGUI();
        twitSendComponentObserverList.add(jTwitSend);
        GridBagConstraints jTwitSendContraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 0, 0, 20), 0, 0);
        initSendTwitButton();

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
                    twitSendComponentObserverList.forEach(ITwitSendComponentObserver::notifyTwitIsEmpty);
                    return;
                }

                if (twitMessage.length() > 250) {
                    twitSendComponentObserverList.forEach(ITwitSendComponentObserver::notifyTwitIsTooLong);
                    return;
                }

                twitControllerObservers.forEach(iTwitControllerObserver -> iTwitControllerObserver.sendTwit(session, twitMessage));
            }
        });
    }

    public void addController(ITwitControllerObserver observer) {
        twitControllerObservers.add(observer);
    }

    /*
    private void initTwitButtonEvent() {
        twitSendButton.addActionListener(action -> twitSendObservers.forEach(observer -> observer.sendTwit(twitTextField.getText())));
    }

    @Override
    public void twitTooLong() {
        twitStateLabel.setText("Le twit est trop long ! (" + twitTextField.getText().length() + ">250)  <(｀^´)>");
    }

    @Override
    public void twitAccepted() {
        twitStateLabel.setText("Le twit à été envoyé ! :)");
        twitTextField.setText("");
    }*/
}

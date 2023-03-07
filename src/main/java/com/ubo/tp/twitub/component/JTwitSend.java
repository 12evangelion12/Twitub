package com.ubo.tp.twitub.component;

import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.newObserver.ITwitObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTwitSend implements JComponent, ITwitObserver {

    private final User session;
    private JPanel jPanel;
    private JButton twitSendButton;
    private JTextArea twitTextArea;
    private String twitMessage;
    private JLabel twitStateLabel;


    public JTwitSend(User session) {
        this.session = session;
    }

    @Override
    public void initGUI() {

        jPanel = new JPanel(new GridBagLayout());

        JLabel twitSenderLabel = new JLabel();
        GridBagConstraints twitSenderLabelConstraints = new GridBagConstraints(0, 0, 3, 1, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
        twitSenderLabel.setText("Pseudonyme : " + session.getUserTag());

        twitTextArea = new JTextArea(3, 10);
        JScrollPane scrollPane = new JScrollPane(twitTextArea);
        GridBagConstraints scrollPaneAreaConstraints = new GridBagConstraints(0, 1, 3, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        twitSendButton = new JButton("Envoyer le twit");
        GridBagConstraints twitSendConstraints = new GridBagConstraints(2, 4, 1, 1, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

        twitStateLabel = new JLabel();
        GridBagConstraints twitStateLabelConstraints = new GridBagConstraints(0, 3, 3, 1, 1, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        jPanel.add(twitSendButton, twitSendConstraints);
        jPanel.add(scrollPane, scrollPaneAreaConstraints);
        jPanel.add(twitSenderLabel, twitSenderLabelConstraints);
        jPanel.add(twitStateLabel, twitStateLabelConstraints);

    }

    public void initTwitButtonEvent(MouseAdapter mouseAdapter) {
        twitSendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                twitMessage = twitTextArea.getText();
                mouseAdapter.mouseClicked(e);
            }
        });
    }

    public String getTwitMessage() {
        return twitMessage;
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }

    @Override
    public void notifyTwitIsTooLong() {
        twitStateLabel.setText("Le twit est trop long ! (" + twitTextArea.getText().length() + ">250)  <(｀^´)>");
    }

    @Override
    public void notifyTwitIsEmpty() {
        twitStateLabel.setText("Un twit ne peux pas être vide ! <(｀^´)>");
    }

    @Override
    public void notifyTwitAccepted() {
        twitStateLabel.setText("Le twit à été envoyé ! :)");
    }
}

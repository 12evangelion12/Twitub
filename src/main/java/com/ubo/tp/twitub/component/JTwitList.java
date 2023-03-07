package main.java.com.ubo.tp.twitub.component;

import main.java.com.ubo.tp.twitub.datamodel.Twit;
import main.java.com.ubo.tp.twitub.model.TwitListModel;
import main.java.com.ubo.tp.twitub.newObserver.ITwitListModelObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JTwitList implements JComponent, ITwitListModelObserver {

    private final TwitListModel twits;
    private JPanel jPanel;
    private JScrollPane jScrollPane;

    public JTwitList(TwitListModel twits) {
        this.twits = twits;
        this.twits.addObserver(this);
    }

    @Override
    public void initGUI() {

        jPanel = new JPanel(new GridBagLayout());

        initList(twits.getTwits());

        jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    @Override
    public Component getComponent() {
        return jScrollPane;
    }

    private void initList(List<Twit> twits) {

        AtomicInteger yPos = new AtomicInteger(0);

        twits.forEach(twit -> {

            JTwit jTwit = new JTwit(twit);
            jTwit.initGUI();

            GridBagConstraints jTwitContraint = new GridBagConstraints(0, yPos.get(), 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 0, 0);
            yPos.getAndIncrement();
            jPanel.add(jTwit.getComponent(), jTwitContraint);
        });
    }

    @Override
    public void notifyTwitListChanged(List<Twit> twits) {

        System.out.println(twits);

        if (jScrollPane != null) {
            jPanel.removeAll();
            initList(twits);
            jScrollPane.revalidate();
            jScrollPane.repaint();
        }
    }
}

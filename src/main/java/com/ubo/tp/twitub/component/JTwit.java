package com.ubo.tp.twitub.component;

import com.ubo.tp.twitub.datamodel.Twit;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class JTwit implements JComponent {

    private final Twit twit;
    private JPanel jPanel;

    public JTwit(Twit twit) {
        this.twit = twit;
    }

    @Override
    public void initGUI() {

        jPanel = new JPanel(new GridBagLayout());

        JLabel userInfo = new JLabel("Twiter: " + twit.getTwiter().getUserTag());
        GridBagConstraints userInfoContraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 0), 0, 0);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(twit.getEmissionDate()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        JLabel twitDate = new JLabel(localDateTime.format(formatter));
        GridBagConstraints twitDateContraint = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 20), 0, 0);

        JLabel twit = new JLabel(this.twit.getText());
        GridBagConstraints twitContraint = new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 0, 0);
        twit.setBorder(new LineBorder(Color.gray));

        jPanel.add(userInfo, userInfoContraint);
        jPanel.add(twitDate, twitDateContraint);
        jPanel.add(twit, twitContraint);
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }
}

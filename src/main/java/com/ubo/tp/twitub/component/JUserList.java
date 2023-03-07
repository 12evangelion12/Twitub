package com.ubo.tp.twitub.component;

import com.ubo.tp.twitub.datamodel.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JUserList implements JComponent {

    private final User session;
    private final List<User> users;
    private JScrollPane jScrollPane;
    private User selectedUser;
    private boolean followingButtonState;
    private MouseAdapter mouseAdapter;

    public JUserList(User session, List<User> users) {
        this.session = session;
        this.users = users;
    }

    @Override
    public void initGUI() {


        JPanel jPanel = new JPanel(new GridBagLayout());
        AtomicInteger yPos = new AtomicInteger(0);

        users.forEach(user -> {

            JUser jUser = new JUser(user, session.getFollows().contains(user.getUserTag()));
            jUser.initGUI();

            jUser.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedUser = user;
                    followingButtonState = jUser.followButtonState();
                    mouseAdapter.mouseClicked(e);
                }
            });

            GridBagConstraints jUserContraint = new GridBagConstraints(0, yPos.get(), 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 0, 0);
            yPos.getAndIncrement();
            jPanel.add(jUser.getComponent(), jUserContraint);
        });
        jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public boolean getFollowingButtonState() {
        return followingButtonState;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    @Override
    public Component getComponent() {
        return jScrollPane;
    }


}

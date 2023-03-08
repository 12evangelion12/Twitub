package com.ubo.tp.twitub.ihm.profil;

import com.ubo.tp.twitub.component.JFollowerList;
import com.ubo.tp.twitub.component.JUserProfil;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.UserProfilModel;
import com.ubo.tp.twitub.observer.IUserObserver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ProfilPageView implements IPage.IView, IUserObserver {

    private final UserProfilModel userProfilModel;
    private final List<IUserObserver> userFollowObserverList;
    private JPanel jPanel;
    private JUserProfil jUserProfil;
    private JFollowerList jFollowerList;

    public ProfilPageView(UserProfilModel userProfilModel) {
        userFollowObserverList = new ArrayList<>();
        this.userProfilModel = userProfilModel;
        this.userProfilModel.addObserver(this);
    }

    @Override
    public Component show() {
        return jPanel;
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        jUserProfil = new JUserProfil(userProfilModel);
        jUserProfil.initGUI();
        GridBagConstraints jUserProfilContraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(30, 0, 0, 20), 0, 0);

        JPanel jUserProfilPanel = (JPanel) jUserProfil.getComponent();
        jUserProfilPanel.setBorder(new LineBorder(Color.gray));

        jPanel.add(jUserProfilPanel, jUserProfilContraint);
        initFollowersList();
    }

    public void updateUserTwitCount(int count) {
        jUserProfil.setCount(count);
    }

    private void initFollowersList() {

        jFollowerList = new JFollowerList(userProfilModel.getSession(), userProfilModel.getFollowers());
        jFollowerList.initGUI();
        GridBagConstraints jUserListContraint = new GridBagConstraints(0, 1, 1, 1, 1, 50, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 0, 0, 20), 0, 0);
        initMouseAdapter();

        jPanel.add(jFollowerList.getComponent(), jUserListContraint);
        jPanel.revalidate();
        jPanel.repaint();
    }

    private void initMouseAdapter() {
        jFollowerList.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jFollowerList.getFollowingButtonState()) {
                    userFollowObserverList.forEach(observer -> observer.notifyUserFollow(jFollowerList.getSelectedUser()));
                } else {
                    userFollowObserverList.forEach(observer -> observer.notifyUserUnfollow(jFollowerList.getSelectedUser()));
                }
            }
        });
    }

    public void addFollowUserObserver(IUserObserver observer) {
        userFollowObserverList.add(observer);
    }

    @Override
    public void updateFollowerList(User session) {

        jPanel.remove(1);
        initFollowersList();
    }
}

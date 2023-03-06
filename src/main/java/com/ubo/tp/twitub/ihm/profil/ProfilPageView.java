package main.java.com.ubo.tp.twitub.ihm.profil;

import main.java.com.ubo.tp.twitub.components.JUserList;
import main.java.com.ubo.tp.twitub.components.JUserProfil;
import main.java.com.ubo.tp.twitub.datamodel.User;
import main.java.com.ubo.tp.twitub.ihm.IPage;
import main.java.com.ubo.tp.twitub.model.UserProfil;
import main.java.com.ubo.tp.twitub.observer.IUserFollowObserver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ProfilPageView implements IPage.IView {

    private JPanel jPanel;
    private final UserProfil userProfil;
    private JUserProfil jUserProfil;
    private JUserList jUserList;
    private final List<User> followers;
    private final List<IUserFollowObserver> userFollowObserverList;

    public ProfilPageView(UserProfil userProfil, List<User> followers) {
        userFollowObserverList = new ArrayList<>();
        this.followers = followers;
        this.userProfil = userProfil;
    }

    @Override
    public Component show() {
        return jPanel;
    }

    @Override
    public void initUIComponents() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        jUserProfil = new JUserProfil(userProfil);
        jUserProfil.initGUI();
        GridBagConstraints jUserProfilContraint = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(30, 0, 0, 20), 0, 0);
        JPanel jUserProfilPanel = (JPanel) jUserProfil.getComponent();
        jUserProfilPanel.setBorder(new LineBorder(Color.gray));

        jPanel.add(jUserProfilPanel, jUserProfilContraint);
        initFollowersList(followers);
    }

    public void updateUserTwitCount(int count) {
        jUserProfil.setCount(count);
    }

    public void updateListFollowers(List<User> followers) {
        jPanel.remove(1);
        initFollowersList(followers);
    }

    private void initFollowersList(List<User> followers) {

        jUserList = new JUserList(followers, userProfil.getUser());
        jUserList.initGUI();
        GridBagConstraints jUserListContraint = new GridBagConstraints(0, 1, 1, 1, 1, 50, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 0, 0, 20), 0, 0);
        initMouseAdapter(jUserList);

        jPanel.add(jUserList.getComponent(), jUserListContraint);
        jPanel.revalidate();
        jPanel.repaint(

        );
    }

    private void initMouseAdapter(JUserList followersList) {
        followersList.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (followersList.getFollowingButtonState()) {
                    userFollowObserverList.forEach(observer -> observer.notifyUserFollow(followersList.getSelectedUser()));
                } else {
                    userFollowObserverList.forEach(observer -> observer.notifyUserUnfollow(followersList.getSelectedUser()));
                }
            }
        });
    }

    public void addFollowUserObserver(IUserFollowObserver observer) {
        userFollowObserverList.add(observer);
    }
}

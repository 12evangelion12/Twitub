package com.ubo.tp.twitub.ihm.user;


import com.ubo.tp.twitub.component.JUserList;
import com.ubo.tp.twitub.component.JUserSearch;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.observer.ISearchItemObserver;
import com.ubo.tp.twitub.observer.IUserFollowObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersPageView implements IPage.IView, ISearchItemObserver {

    private JPanel jPanel;
    private final List<User> users;
    private JUserSearch jUserSearch;
    private final User session;
    private List<IUserFollowObserver> userFollowObserverList;

    public UsersPageView(List<User> users, User session) {
        this.session = session;
        userFollowObserverList = new ArrayList<>();
        this.users = users;
    }

    @Override
    public Component show() {
        return jPanel;
    }

    @Override
    public void initUIComponents() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        jUserSearch = new JUserSearch();
        jUserSearch.initGUI();
        jUserSearch.addObserver(this);
        GridBagConstraints jUserSearchContraint = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 0, 20), 0, 0);

        jPanel.add(jUserSearch.getComponent(), jUserSearchContraint);
        showUsers(users);
    }

    @Override
    public void notifySearchButtonClicked() {

        jPanel.remove(1);
        showUsers(parseUsers(jUserSearch.getSearchingText()));
    }

    private List<User> parseUsers(String texte) {

        List<User> tempUsers = new ArrayList<>(users);
        return tempUsers.stream().filter(user -> user.getUserTag().contains(texte)).collect(Collectors.toList());
    }

    private void showUsers(List<User> users) {

        JUserList userList = new JUserList(users, session);
        initMouseAdapter(userList);
        userList.initGUI();
        GridBagConstraints userListContraint = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 0, 20), 0, 0);
        jPanel.add(userList.getComponent(), userListContraint);

        jPanel.revalidate();
        jPanel.repaint();
    }

    private void initMouseAdapter(JUserList userList) {
        userList.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userList.getFollowingButtonState()) {
                    userFollowObserverList.forEach(observer -> observer.notifyUserFollow(userList.getSelectedUser()));
                } else {
                    userFollowObserverList.forEach(observer -> observer.notifyUserUnfollow(userList.getSelectedUser()));
                }
            }
        });
    }

    public void addFollowUserObserver(IUserFollowObserver observer) {
        userFollowObserverList.add(observer);
    }
}

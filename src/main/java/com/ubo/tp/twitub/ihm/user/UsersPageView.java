package com.ubo.tp.twitub.ihm.user;

import com.ubo.tp.twitub.component.JUserList;
import com.ubo.tp.twitub.component.JUserSearch;
import com.ubo.tp.twitub.datamodel.User;
import com.ubo.tp.twitub.ihm.IPage;
import com.ubo.tp.twitub.model.UserListModel;
import com.ubo.tp.twitub.observer.IUserObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersPageView implements IPage.IView, IUserObserver {

    private final UserListModel userListModel;
    private final List<IUserObserver> userObservers;
    private JPanel jPanel;
    private JUserSearch jUserSearch;
    private JUserList jUserList;

    public UsersPageView(UserListModel userListModel) {
        this.userListModel = userListModel;
        this.userListModel.addObserver(this);
        userObservers = new ArrayList<>();
        userObservers.add(this);
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
        initUserSearchMouseAdapter();
        GridBagConstraints jUserSearchContraint = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 0, 20), 0, 0);

        jPanel.add(jUserSearch.getComponent(), jUserSearchContraint);
        showUsers();
    }

    @Override
    public void notifySearchUserButtonClicked() {

        jPanel.remove(1);
        showUsers();
    }

    private List<User> parseUsers(List<User> users, String texte) {
        return users.stream().filter(user -> user.getUserTag().contains(texte)).collect(Collectors.toList());
    }

    private void showUsers() {

        List<User> tempList = new ArrayList<>();
        if (jUserSearch.getSearchingText() != null && jUserSearch.getSearchingText().isEmpty()) {
            tempList.addAll(userListModel.getUsers());
        } else {
            tempList.addAll(parseUsers(userListModel.getUsers(), jUserSearch.getSearchingText()));
        }

        jUserList = new JUserList(userListModel.getSession(), tempList);
        initUserListMouseAdapter();
        jUserList.initGUI();
        GridBagConstraints userListContraint = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 0, 20), 0, 0);
        jPanel.add(jUserList.getComponent(), userListContraint);

        jPanel.revalidate();
        jPanel.repaint();
    }

    private void initUserSearchMouseAdapter() {
        jUserSearch.initSearchButtonListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userObservers.forEach(IUserObserver::notifySearchUserButtonClicked);
            }
        });
    }

    private void initUserListMouseAdapter() {
        jUserList.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jUserList.getFollowingButtonState()) {
                    userObservers.forEach(observer -> observer.notifyUserFollow(jUserList.getSelectedUser()));
                } else {
                    userObservers.forEach(observer -> observer.notifyUserUnfollow(jUserList.getSelectedUser()));
                }
            }
        });
    }

    public void addObserver(IUserObserver observer) {
        userObservers.add(observer);
    }

    @Override
    public void updateUserList(User session) {
        jPanel.remove(1);
        showUsers();
    }
}

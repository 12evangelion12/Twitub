package main.java.com.ubo.tp.twitub.component;

import main.java.com.ubo.tp.twitub.datamodel.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JUserList implements JComponent {

    private JScrollPane jScrollPane;
    private final List<User> userList;
    private User selectedUser;
    private boolean followingButtonState;
    private MouseAdapter mouseAdapter;
    private final User session;

    public JUserList(List<User> userList, User session) {
        this.userList = userList;
        this.session = session;
    }

    @Override
    public void initGUI() {


        JPanel jPanel = new JPanel(new GridBagLayout());
        AtomicInteger yPos = new AtomicInteger(0);

        userList.forEach(user -> {

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

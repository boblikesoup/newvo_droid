package com.newvo.android.remote;

import com.facebook.model.GraphUser;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by David on 6/25/2014.
 */
public class EditGroupRequest {

    private final List<GraphUser> users;
    private Group group;

    private SaveCallback saveCallback;

    public EditGroupRequest(Group group, String title, String description, List<GraphUser> users) throws MissingTitleError {
        if(title == null || title.isEmpty()){
            throw new MissingTitleError();
        }
        this.users = users;

        if(group == null) {
            group = new Group();
        }
        this.group = group;
        group.setTitle(title);
        group.setDescription(description);
        group.setMemberIds(users);
        group.setUserId(User.getCurrentUser());
        group.setStatus(Group.UNDELETED);

    }

    public void request(final SaveCallback saveCallback) {

        new ParseUsersFromFacebookUsersRequest(users).request(new FindCallback<User>() {
            @Override
            public void done(List<User> pushUsers, ParseException e) {
                if (e == null) {
                    group.setPushIds(pushUsers);
                    group.saveInBackground(saveCallback);
                } else {
                    saveCallback.done(e);
                }
            }
        });
    }

    public class MissingTitleError extends Throwable {
    }
}

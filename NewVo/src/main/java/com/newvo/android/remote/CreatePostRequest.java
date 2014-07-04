package com.newvo.android.remote;

import android.content.Context;
import com.facebook.model.GraphUser;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.*;
import com.personagraph.api.PGAgent;

import java.util.*;

import static com.parse.ParseUser.getCurrentUser;

/**
 * Created by David on 4/27/2014.
 */
public class CreatePostRequest {

    private boolean friendsOnly;
    private Post post;
    private List<GraphUser> users;
    private List<Group> groups;

    public CreatePostRequest(Context context, String caption, ParseFile image1, ParseFile image2, List<Group> groups, List<GraphUser> users, boolean friendsOnly) throws MissingCaptionError, MissingImageError {
        this(context, caption, image1, image2);
        this.users = users;
        this.groups = groups;
        this.friendsOnly = friendsOnly;
        post.setViewableBy(this.friendsOnly ? Post.FRIENDS : Post.PUBLIC);

    }

    public CreatePostRequest(Context context, String caption, ParseFile image1, ParseFile image2) throws MissingCaptionError, MissingImageError {
        if (caption == null || caption.isEmpty()) {
            throw new MissingCaptionError();
        } else if (image1 == null) {
            throw new MissingImageError();
        }
        post = new Post();
        post.setCaption(caption);
        post.setPhoto1(image1);
        if (image2 != null) {
            post.setPhoto2(image2);
        }
        User currentUser = (User) getCurrentUser();
        post.setUser(currentUser);
        post.setStatus(Post.ACTIVE);
        post.setViewableBy(Post.PUBLIC);
        post.setVotes1(0);
        post.setVotes2(0);
        post.setCounter1(0);
        post.setCounter2(0);
        post.setNumberOfSuggestions(0);
    }


    public void request() {
        request(null);
    }

    public void request(final SaveCallback saveCallback) {
        ParseFile photo1 = post.getPhoto1();
        ParseFile photo2 = post.getPhoto2();

        if (photo1 != null && photo2 != null) {
            PhotoPairSaver photo1PairSaver = new PhotoPairSaver(photo1, saveCallback);
            PhotoPairSaver photo2PairSaver = new PhotoPairSaver(photo2, saveCallback);
            photo1PairSaver.setOtherSaver(photo2PairSaver);
            photo2PairSaver.setOtherSaver(photo1PairSaver);
            photo1PairSaver.save();
            photo2PairSaver.save();
        } else {
            new PhotoPairSaver(photo1, saveCallback).save();
        }


    }

    class PhotoPairSaver {

        private final SaveCallback saveCallback;
        private boolean photoSaved = false;
        private PhotoPairSaver otherSaver;
        private ParseFile image;

        public PhotoPairSaver(ParseFile image, final SaveCallback saveCallback) {
            this.image = image;
            this.saveCallback = new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        if (saveCallback != null) {
                            saveCallback.done(e);
                        }
                        return;
                    }
                    photoSaved = true;
                    if (otherSaver == null || otherSaver.photoSaved && saveCallback != null) {
                        savePost(saveCallback);
                    }
                }
            };
        }

        public void setOtherSaver(PhotoPairSaver otherSaver) {
            this.otherSaver = otherSaver;
        }

        public void save() {
            image.saveInBackground(saveCallback);
        }
    }

    private void savePost(final SaveCallback saveCallback) {

        final Set<String> ids = new HashSet<String>();

        //Notify users after tagging is successful.
        final User self = User.getCurrentUser();
        final SaveCallback nestedCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    if (!ids.isEmpty()) {
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        List<String> userTags = post.getUserTags();
                        params.put("userIDS", userTags);
                        params.put("msg", self.getPublicName() + " has tagged you in a post. Take a look!");
                        params.put("searchObjectPost", post.getObjectId());
                        ParseCloud.callFunctionInBackground("push_tagged_notifications", params, null);

                        if (post.getStatus().equals(Post.getStatusValue(Post.ACTIVE))) {
                            if (userTags == null | userTags.isEmpty()) {
                                PGAgent.logEvent("user shared with everyone");
                            } else {
                                PGAgent.logEvent("user shared with friends and community");
                            }
                        } else {
                            PGAgent.logEvent("user shared with friends only");
                        }
                    }
                }
                if (saveCallback != null) {
                    saveCallback.done(e);
                }
            }
        };


        //Tagging groups
        if (groups != null && !groups.isEmpty()) {
            post.setGroupIds(groups);
            for (Group group : groups) {
                for (String pushId : group.getPushIds()) {
                    ids.add(pushId);
                }
            }
            if (ids.contains(self.getObjectId())) {
                ids.remove(self.getObjectId());
            }
            if (users == null || users.isEmpty()) {
                post.setUserTags(new ArrayList<String>(ids));
            }
        }

        //Tagging users
        if (users != null && !users.isEmpty()) {
            new ParseUsersFromFacebookUsersRequest(users).request(new FindCallback<User>() {
                @Override
                public void done(List<User> pushUsers, ParseException e) {
                    if (e == null) {
                        for (User pushUser : pushUsers) {
                            ids.add(pushUser.getUserId());
                        }
                        post.setUserTags(new ArrayList<String>(ids));
                        post.saveInBackground(nestedCallback);
                    } else {
                        nestedCallback.done(e);
                    }
                }
            });
        } else {
            post.saveInBackground(nestedCallback);
        }
    }

    public class MissingCaptionError extends Error {
    }

    public class MissingImageError extends Error {
    }

    public Post getPost() {
        return post;
    }
}

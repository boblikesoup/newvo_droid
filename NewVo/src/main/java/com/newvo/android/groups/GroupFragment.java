package com.newvo.android.groups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.SummaryAdapter;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.newvo.android.remote.GroupProfileRequest;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.ToastUtils;
import com.parse.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by David on 6/26/2014.
 */
public class GroupFragment extends Fragment implements ChildFragment {

    @InjectView(R.id.group_name)
    TextView groupName;

    @InjectView(R.id.settings)
    ImageButton settings;

    @InjectView(R.id.posts)
    ListView posts;

    private Group group;
    private List<Post> postList;

    public GroupFragment(Group group) {
        this.group = group;
        requestPosts();
    }


    private void requestPosts() {
        new GroupProfileRequest(group).request(new FindCallback<Post>() {

            @Override
            public void done(List<Post> postList, ParseException e) {
                if (e == null) {
                    GroupFragment.this.postList = postList;
                    if (posts != null) {
                        ((ArrayAdapter<Post>) posts.getAdapter()).addAll(postList);
                    }
                } else {
                    requestPosts();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.group, container, false);
        ButterKnife.inject(this, rootView);

        groupName.setText(group.getTitle());

        posts.setAdapter(new SummaryAdapter(getActivity(), R.layout.summary));

        final PopupMenu popupMenu = new PopupMenu(getActivity(), settings);
        boolean writeAccess = group.getACL().getWriteAccess(User.getCurrentUser());


        //Populate popup with available choices
        popupMenu.getMenu().add(0, 0, 0, "Create Post");
        popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //TODO: When tagging has been implemented, add this.
                //       ((NewVoActivity) getActivity()).displayChildFragment(new CreatePostFragment(group), getActivity().getString(R.string.title_create_post), "CreateGroupPost");
                return true;
            }
        });
        popupMenu.getMenu().add(0, 0, 1, "Manage Notification Settings");
        popupMenu.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                createNotificationDialog();
                return true;
            }
        });


        if (writeAccess) {
            popupMenu.getMenu().add(0, 0, 1, "Edit Group");
            popupMenu.getMenu().add(0, 0, 3, "Disband Group");
            popupMenu.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    createDisbandGroupDialog();
                    return true;
                }
            });
        } else {
            popupMenu.getMenu().add(0, 0, 1, "View Group");
            popupMenu.getMenu().add(0, 0, 3, "Leave Group");
            popupMenu.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    createLeaveGroupDialog();
                    return true;
                }
            });
        }
        //Edit/View Group
        popupMenu.getMenu().getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((NewVoActivity) getActivity()).displayChildFragment(new EditGroupFragment(group), getActivity().getString(R.string.title_group), "EditGroup");
                return true;
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        if (postList != null) {
            ((ArrayAdapter<Post>) posts.getAdapter()).addAll(postList);
        }


        return rootView;
    }

    private void createNotificationDialog() {
        CharSequence[] values = new CharSequence[]{
                "Enable Notifications",
                "Disable Notifications"
        };
        String title = "Manage Notification Settings";
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Context context = getActivity();
                final String status;
                List<String> pushIds = group.getPushIds();
                String userId = User.getCurrentUser().getUserId();
                if (which > 0) {
                    pushIds.removeAll(Arrays.asList(userId));
                    status = "Disable";
                } else {
                    if(!pushIds.contains(userId)) {
                        pushIds.add(userId);
                    }
                    status = "Enable";
                }

                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("groupId", group.getObjectId());
                params.put("push_ids", pushIds);
                ParseCloud.callFunctionInBackground("updateGroupNotifications", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        if (e != null) {
                            ToastUtils.makeText(context, "Could Not " + status + " Notifications", Toast.LENGTH_SHORT, -1).show();
                        } else {
                            ToastUtils.makeText(context, "Notifications " + status + "d", Toast.LENGTH_SHORT, -1).show();
                        }
                    }
                });
                dialog.dismiss();
            }
        };
        createDialog(title, values, onClickListener);
    }

    private void createDisbandGroupDialog() {
        CharSequence[] values = new CharSequence[]{
                "Disband Group",
        };
        String title = "Are You Sure You Want To Disband This Group?";
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Activity activity = getActivity();
                group.setStatus(Group.DELETED);
                group.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            ToastUtils.makeText(activity, "Could Not Disband Group", Toast.LENGTH_SHORT, -1).show();
                        } else {
                            ToastUtils.makeText(activity, "Group Disbanded", Toast.LENGTH_SHORT, -1).show();
                            if(GroupFragment.this.equals( ((NewVoActivity) activity).getActiveFragment())) {
                                activity.onBackPressed();
                            }
                        }
                    }
                });
                dialog.dismiss();
            }
        };
        createDialog(title, values, onClickListener);
    }

    private void createLeaveGroupDialog() {
        CharSequence[] values = new CharSequence[]{
                "Leave Group",
        };
        String title = "Are You Sure You Want To Leave This Group?";
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Activity activity = getActivity();
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("groupId", group.getObjectId());
                params.put("push_ids", Arrays.asList(User.getCurrentUser().getUserId()));
                params.put("member_ids", Arrays.asList(User.getCurrentUser().getFacebookId()));
                ParseCloud.callFunctionInBackground("leaveGroup", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        if (e != null) {
                            ToastUtils.makeText(activity, "Could Not Leave Group", Toast.LENGTH_SHORT, -1).show();
                        } else {
                            ToastUtils.makeText(activity, "Left Group", Toast.LENGTH_SHORT, -1).show();
                            if(GroupFragment.this.equals( ((NewVoActivity) activity).getActiveFragment())) {
                                activity.onBackPressed();
                            }
                        }
                    }
                });
            }
        };
        createDialog(title, values, onClickListener);
    }

    private void createDialog(String title, CharSequence[] values,  DialogInterface.OnClickListener onClickListener){
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LinearLayout inflate = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.flag_dialog, null);
        TextView textView = (TextView) inflate.findViewById(R.id.textView);
        textView.setText(title);
        builder.setCustomTitle(inflate)
                .setItems(values, onClickListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

}

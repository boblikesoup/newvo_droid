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
import com.newvo.android.DrawerActivity;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.SummaryAdapter;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.newvo.android.remote.GroupProfileRequest;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.LoadingFragment;
import com.newvo.android.util.ToastUtils;
import com.parse.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by David on 6/26/2014.
 */
public class GroupFragment extends Fragment implements ChildFragment, LoadingFragment {

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
        Activity activity = getActivity();
        if(activity != null) {
            ((DrawerActivity) activity).setActionBarLoading(true);
        }
        new GroupProfileRequest(group).request(new FindCallback<Post>() {

            @Override
            public void done(List<Post> postList, ParseException e) {
                Activity activity = getActivity();
                if (e == null) {
                    GroupFragment.this.postList = postList;
                    if (posts != null) {
                        ((ArrayAdapter<Post>) posts.getAdapter()).addAll(postList);
                    }
                    if(activity != null) {
                        ((DrawerActivity) activity).setActionBarLoading(false);
                    }
                } else if(activity != null) {
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

        String edit = writeAccess ? "Edit Group" : "View Group";
        String disband = writeAccess ? "Disband Group" : "Leave Group";
        MenuItem.OnMenuItemClickListener disbandListener = writeAccess ?
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        createDisbandGroupDialog();
                        return true;
                    }
                } :
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        createLeaveGroupDialog();
                        return true;
                    }
                };

        //Populate popup with available choices
        popupMenu.getMenu().add(0, 0, 0, edit);
        popupMenu.getMenu().add(0, 0, 0, "Manage Notification Settings");
        popupMenu.getMenu().add(0, 0, 2, disband);
        //Popup Actions
        popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((NewVoActivity) getActivity()).displayChildFragment(new EditGroupFragment(group), getActivity().getString(R.string.title_group), "EditGroup");
                return true;
            }
        });
        popupMenu.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                createNotificationDialog();
                return true;
            }
        });
        popupMenu.getMenu().getItem(2).setOnMenuItemClickListener(disbandListener);

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
                    if (!pushIds.contains(userId)) {
                        pushIds.add(userId);
                    }
                    status = "Enable";
                }

                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("groupId", group.getObjectId());
                params.put("push_ids", pushIds);
                ((DrawerActivity) getActivity()).setActionBarLoading(true);
                ParseCloud.callFunctionInBackground("updateGroupNotifications", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        if (GroupFragment.this.equals(((NewVoActivity) context).getActiveFragment())) {
                            ((DrawerActivity) context).setActionBarLoading(false);
                        }
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
                ((DrawerActivity) getActivity()).setActionBarLoading(true);
                group.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (GroupFragment.this.equals(((NewVoActivity) activity).getActiveFragment())) {
                            ((DrawerActivity) activity).setActionBarLoading(false);
                        }
                        if (e != null) {
                            ToastUtils.makeText(activity, "Could Not Disband Group", Toast.LENGTH_SHORT, -1).show();
                        } else {
                            ToastUtils.makeText(activity, "Group Disbanded", Toast.LENGTH_SHORT, -1).show();
                            if (GroupFragment.this.equals(((NewVoActivity) activity).getActiveFragment())) {
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
                List<String> pushIds = group.getPushIds();
                if (pushIds.contains(User.getCurrentUser().getUserId())) {
                    pushIds.remove(User.getCurrentUser().getUserId());
                }
                List<String> memberIds = group.getMemberIds();
                if (memberIds.contains(User.getCurrentUser().getFacebookId())) {
                    memberIds.remove(User.getCurrentUser().getFacebookId());
                }
                params.put("push_ids", pushIds);
                params.put("member_ids", memberIds);
                ((DrawerActivity) getActivity()).setActionBarLoading(true);
                ParseCloud.callFunctionInBackground("leaveGroup", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        if (GroupFragment.this.equals(((NewVoActivity) activity).getActiveFragment())) {
                            ((DrawerActivity) activity).setActionBarLoading(false);
                        }
                        if (e != null) {
                            ToastUtils.makeText(activity, "Could Not Leave Group", Toast.LENGTH_SHORT, -1).show();
                        } else {
                            ToastUtils.makeText(activity, "Left Group", Toast.LENGTH_SHORT, -1).show();
                            if (GroupFragment.this.equals(((NewVoActivity) activity).getActiveFragment())) {
                                activity.onBackPressed();
                                ((NewVoActivity) activity).attachDetachFragment();
                            }
                        }
                    }
                });
            }
        };
        createDialog(title, values, onClickListener);
    }

    private void createDialog(String title, CharSequence[] values, DialogInterface.OnClickListener onClickListener) {
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

    @Override
    public boolean hasLoaded() {
        return postList != null;
    }
}

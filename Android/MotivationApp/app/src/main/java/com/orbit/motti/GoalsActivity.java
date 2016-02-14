package com.orbit.motti;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orbit.motti.Records.ExtendedCursor;
import com.orbit.motti.Records.Goal;
import com.orbit.motti.Records.Profile;
import com.orbit.motti.Records.SubGoal;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GoalsActivity extends AppCompatActivity {
    private int lastDeletedPosition = -1;
    private Goal lastDeletedGoal;
    private RecyclerView mRecyclerView;
    private List<Goal> goals;
    private TextView firstTimeTextView;
    private GoalAdapter goalSwipeAdapter;
    public static Profile p;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        firstTimeTextView = (TextView) findViewById(R.id.first_time_text_view);
        goals = new ArrayList<>();
        fillGoals();
        updateUI();
        mRecyclerView = (RecyclerView) findViewById(R.id.goals_recycler_view);
        goalSwipeAdapter = new GoalAdapter(goals, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(goalSwipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    lastDeletedPosition = position;
                                    lastDeletedGoal = goalSwipeAdapter.getGoal(position);
                                    goalSwipeAdapter.removeItem(position);
                                    goalSwipeAdapter.notifyItemRemoved(position);
                                }
                                goalSwipeAdapter.notifyDataSetChanged();
                                Database database = new Database(getApplicationContext());
                                database.connectToDatabase();
                                try {
                                    p.loadFromDatabase();
                                } catch (InvalidClassException e) {
                                    e.printStackTrace();
                                }
                                int currentCoints = p.getCredits();

                                database.executeSQL("Update profile set credits=" + (currentCoints + 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                try {
                                    p.loadFromDatabase();
                                } catch (InvalidClassException e) {
                                    e.printStackTrace();
                                }
                                Snackbar.make(recyclerView, "Goal deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (lastDeletedGoal != null && lastDeletedPosition != -1) {
                                                    goalSwipeAdapter.addItem(lastDeletedGoal, lastDeletedPosition);
                                                    lastDeletedGoal = null;
                                                    lastDeletedPosition = -1;
                                                    Database database = new Database(getApplicationContext());
                                                    database.connectToDatabase();
                                                    try {
                                                        p.loadFromDatabase();
                                                    } catch (InvalidClassException e) {
                                                        e.printStackTrace();
                                                    }
                                                    int currentCoints = p.getCredits();

                                                    database.executeSQL("Update profile set credits=" + (currentCoints - 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                                    try {
                                                        p.loadFromDatabase();
                                                    } catch (InvalidClassException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }).show();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    lastDeletedPosition = position;
                                    lastDeletedGoal = goalSwipeAdapter.getGoal(position);
                                    goalSwipeAdapter.removeItem(position);
                                    goalSwipeAdapter.notifyItemRemoved(position);
                                }
                                Database database = new Database(getApplicationContext());
                                database.connectToDatabase();
                                try {
                                    p.loadFromDatabase();
                                } catch (InvalidClassException e) {
                                    e.printStackTrace();
                                }
                                int currentCoints = p.getCredits();

                                database.executeSQL("Update profile set credits=" + (currentCoints + 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                try {
                                    p.loadFromDatabase();
                                } catch (InvalidClassException e) {
                                    e.printStackTrace();
                                }
                                goalSwipeAdapter.notifyDataSetChanged();
                                Snackbar.make(recyclerView, "Goal deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (lastDeletedGoal != null && lastDeletedPosition != -1) {
                                                    goalSwipeAdapter.addItem(lastDeletedGoal, lastDeletedPosition);
                                                    lastDeletedGoal = null;
                                                    lastDeletedPosition = -1;
                                                    Database database = new Database(getApplicationContext());
                                                    database.connectToDatabase();
                                                    try {
                                                        p.loadFromDatabase();
                                                    } catch (InvalidClassException e) {
                                                        e.printStackTrace();
                                                    }
                                                    int currentCoints = p.getCredits();

                                                    database.executeSQL("Update profile set credits=" + (currentCoints - 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                                    try {
                                                        p.loadFromDatabase();
                                                    } catch (InvalidClassException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }).show();
                            }
                        });

        mRecyclerView.addOnItemTouchListener(swipeTouchListener);


        FloatingActionButton addGoalFAB = (FloatingActionButton) findViewById(R.id.fab);
        addGoalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        //  Database database = new Database();
        //  database.connectToDatabase(this);
        //Initialize the database, setting Database.Instance to the first created;

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void updateUI() {

        if (goals.size() == 0) {
            firstTimeTextView.setVisibility(View.VISIBLE);
        } else {
            firstTimeTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_goals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                Intent i = new Intent(this, Spinner.class);
                startActivity(i);
                return true;
            case R.id.status:
                i = new Intent(this, History.class);
                startActivity(i);
                return true;
            case R.id.help:
                final Context context = this;
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Emergency help!")
                        .setMessage("With this action you are confirming that you wish to receive assistance from a professional. You need to specify a way of contact and a trained expert will contact you as soon as possiblle.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogs, int which) {
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.custom_popup);
                                dialog.setTitle("Contact form");

                                // set the custom dialog components - text, image and button
                                TextView text = (TextView) dialog.findViewById(R.id.text);
                                text.setText("Please specify by what method you would like to be contacted. \n(Optional)Specify details for our expert.");
                                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                                image.setImageResource(R.mipmap.fruit_power);

                                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                                // if button is clicked, close the custom dialog
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, "Form submitted succsfully. Our expert will contact you as soon as possible.", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });
                                Button dialogClose = (Button) dialog.findViewById(R.id.dialogButtonDismiss);
                                // if button is clicked, close the custom dialog
                                dialogClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();

                                dialogs.dismiss();


                            }
                        })

                        .setIcon(R.drawable.ic_priority_high_black_24dp)
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {
        createDialog().show();
    }

    private AlertDialog createDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.goal_dialog, null);
        final EditText goalTitleText = (EditText) dialogView.findViewById(R.id.goal_title_edit_text);
        final EditText goalDescriptionText = (EditText) dialogView.findViewById(R.id.goal_description_edit_text);
        final EditText goalRemindText = (EditText) dialogView.findViewById(R.id.goal_remind_edit_text);
        final EditText goalPeriodText = (EditText) dialogView.findViewById(R.id.goal_period_edit_text);
        final String addictionType = "";
        final String[] addictionTypes = new String[]{"Smoking", "Drinking", "Debt", "Drugs"};
        final int[] result = new int[1];
        final AlertDialog goalDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add new goal")
                .setSingleChoiceItems(addictionTypes, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result[0] = which;
                    }
                })
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        goalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = goalDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = goalDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goalDialog.dismiss();
                        updateUI();
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (goalTitleText.getText().length() == 0) {
                            Toast.makeText(
                                    dialogView.getContext(),
                                    "Title can't be empty!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Goal goal = new Goal(
                                    goalTitleText.getText().toString(),
                                    goalDescriptionText.getText().toString(),
                                    Integer.valueOf(goalRemindText.getText().toString()),
                                    Integer.valueOf(goalPeriodText.getText().toString()),
                                    addictionTypes[result[0]]);
                            goals.add(goal);
                            final Goal g = goal;
                            goalDialog.dismiss();
                            updateUI();
                            goalSwipeAdapter.notifyDataSetChanged();
                            ScheduledExecutorService worker =
                                    Executors.newSingleThreadScheduledExecutor();

                            Runnable task = new Runnable() {
                                public void run() {
                                    NotificationManager manager;

                                    Intent intent = new Intent(getApplicationContext(), GoalsActivity.class);
                                    manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(GoalsActivity.this, 1, intent, 0);

                                    Notification.Builder builder = new Notification.Builder(GoalsActivity.this);

                                    builder.setAutoCancel(false);
                                    builder.setTicker(g.getGoalPeriod() + " days left: " + g.getGoalTitle());
                                    builder.setContentTitle(getString(R.string.app_name));
                                    builder.setContentText("Reminder for " + g.getGoalTitle());
                                    builder.setSmallIcon(R.mipmap.ic_launcher);
                                    builder.setContentIntent(pendingIntent);
                                    builder.setOngoing(true);
                                    builder.setSubText("Progress: " + g.progress() + "/" + g.getSubGoals().size() + "\n" + g.getGoalDescription());   //API level 16
                                    builder.setNumber(g.getID());
                                    builder.build();
                                    Notification myNotication;
                                    myNotication = builder.getNotification();
                                    manager.notify(11, myNotication);
                                }
                            };
                            worker.schedule(task, 5, TimeUnit.SECONDS);




            /*
            //API level 8
            Notification myNotification8 = new Notification(R.drawable.ic_launcher, "this is ticker text 8", System.currentTimeMillis());

            Intent intent2 = new Intent(MainActivity.this, SecActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 2, intent2, 0);
            myNotification8.setLatestEventInfo(getApplicationContext(), "API level 8", "this is api 8 msg", pendingIntent2);
            manager.notify(11, myNotification8);
            */




                 /*   btnClear.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            manager.cancel(11);
                        }
                    });*/
                        }
                    }
                });
            }
        });

        return goalDialog;
    }

    private String getFillerText() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae " +
                "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
                "lectus, feugiat eget ullamcorper vitae, ornare et sem. Fusce dapibus ipsum" +
                " sed laoreet suscipit.";
    }

    private void fillGoals() {
        this.goals = GoalsActivity.p.goals;
    }

    @Override
    protected void onResume() {
        try {
            p.loadFromDatabase();
        } catch (Exception e) {
        }
        this.goals = GoalsActivity.p.goals;
        goalSwipeAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Goals Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.orbit.motti/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Goals Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.orbit.motti/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
package com.orbit.motti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orbit.motti.Records.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import spinner.FortuneItem;
import spinner.FortuneView;
import spinner.TypeOfFortune;

public class Spinner extends AppCompatActivity {
    public static int selected = -1;
    private static boolean firstRun = true;
    static FortuneView fortuneView;
    private static Context context;


    static Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                selected = fortuneView.getSelectedIndex();
                Database database = new Database(context);
                database.connectToDatabase();
                GoalsActivity.p.loadFromDatabase();
                TypeOfFortune f = fortuneView.getItem(selected).getFortune();
                Random r = new Random();
                String name = "";
                if (!firstRun) {

                    List<String> ads = GoalsActivity.p.addictions;

                    if (ads.size() > 0) {
                        int number = r.nextInt(ads.size());
                        String add = GoalsActivity.p.addictions.get(number);
                        name = add;
                    }

                    int currentCoints = GoalsActivity.p.getCredits();
                    if (currentCoints > 0) {
                        database.executeSQL("Update profile set credits=" + (currentCoints - 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");

                        int coinsWon = 0;
                        switch (f) {
                            case Action:
                                name = "action";
                                break;
                            case Coin2:
                                //update with 2 coins

                                database.executeSQL("Update profile set credits=" + (currentCoints + 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                coinsWon = 2;
                                name = "";
                                break;
                            case Coin3:

                                database.connectToDatabase();

                                database.executeSQL("Update profile set credits=" + (currentCoints + 2) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                coinsWon = 3;
                                name = "";
                                break;
                            case Did_you_know:
                                name = "good-to-know";
                                break;
                            case Donation:
                                name = "Donation";
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.donation_popup);
                                dialog.setTitle(name);

                                // set the custom dialog components - text, image and button
                                TextView text = (TextView) dialog.findViewById(R.id.sponsor_details);
                                text.setText("Congratulations! You can pick a charity which you wish to receive 1€ as a donation from our sponsors. Select one from the following:");
                                ImageView image = (ImageView) dialog.findViewById(R.id.sponsor1);

                                ImageView image2 = (ImageView) dialog.findViewById(R.id.sponsor2);

                                ImageView image3 = (ImageView) dialog.findViewById(R.id.sponsor3);


                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, "We thank you for the support! Keep up the good work and good luck!", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });

                                image2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, "We thank you for the support! Keep up the good work and good luck!", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });

                                image3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, "We thank you for the support! Keep up the good work and good luck!", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });
                                Button dialogClose = (Button) dialog.findViewById(R.id.sponsor_dismiss);
                                // if button is clicked, close the custom dialog
                                dialogClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();


                                break;
                            case Motivation:
                                name = "none";
                                break;
                            case Personal_Question:
                                name = "p-question";
                                break;
                            case Question:
                                name = "question";
                                break;

                        }
                        if (name.length() == 0) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Coins!")
                                    .setMessage("Congratulations!  You won " + coinsWon + " coins!")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })

                                    .setIcon(R.mipmap.achiv_stars)
                                    .show();
                            GoalsActivity.p.loadFromDatabase();


                        } else {

                            AlertDialog.Builder b = null;
                            if (name.equals("p-question")) {

                                Random ran = new Random();
                                List<Goal> p = GoalsActivity.p.goals;
                                if(p.size() > 0) {
                                    int num = ran.nextInt(p.size());
                                    Goal g = GoalsActivity.p.goals.get(num);
                                    final Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.personal_goal_progress_popup);
                                    dialog.setTitle(g.getGoalTitle());

                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialog.findViewById(R.id.person_view);
                                    text.setText("How do you think is this goal is going?");
                                    SeekBar bar = (SeekBar) dialog.findViewById(R.id.seekBar);

                                    final int value = bar.getProgress();
                                    Button dialogClose = (Button) dialog.findViewById(R.id.person_dismiss);
                                    // if button is clicked, close the custom dialog
                                    dialogClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            String msg = "";
                                            if (value < 20) {
                                                msg = "It may seem alot, but you can do it!";
                                            } else if (value < 50) {
                                                msg = "Improving, improving. Keep going! ";
                                            } else if (value < 80) {
                                                msg = "You are getting closer. You can do it!";
                                            } else {
                                                msg = "You are almost there, what is left now? ";
                                            }
                                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    dialog.show();
                                }
                                else{
                                    Toast.makeText(context, "You do not have goals yet..", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Cursor a = database.executeWithResult("Select * from motivation where motivation_type='" + name + "'");


                                if (a.moveToFirst()) {
                                    r = new Random();
                                    int offset = r.nextInt(a.getCount());

                                    a.move(offset % a.getCount());
                                    String msg = a.getString(a.getColumnIndex("description"));
                                    b = new AlertDialog.Builder(context);

                                    b.setTitle(f.name()).setMessage(msg)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                }
                                            })
                                            .setIcon(R.mipmap.achiv_stars);
                                }
                                if (b != null) {
                                    int bonusCoinChanse = r.nextInt(5);

                                    if (bonusCoinChanse > 2) {
                                        GoalsActivity.p.loadFromDatabase();
                                        currentCoints = GoalsActivity.p.getCredits();
                                        database.executeSQL("Update profile set credits=" + (currentCoints + 1) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                                        GoalsActivity.p.loadFromDatabase();

                                        Toast.makeText(context, "Extra coin found!", Toast.LENGTH_LONG).show();
                                    }
                                    b.show();
                                }


                            }
                        }

                        Handler mainHandler = new Handler(context.getMainLooper());
                        GoalsActivity.p.loadFromDatabase();
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {

                                View rootView = ((Activity) context).getWindow().getDecorView();
                                View z = (View) rootView.findViewById(R.id.textView_spinner);
                                TextView vl = (TextView) z;
                                vl.setText(GoalsActivity.p.getCredits() + "");
                            } // This is your code
                        };
                        mainHandler.post(myRunnable);

                    } else {
                        Toast.makeText(context, "You need more coins to spin the wheel!", Toast.LENGTH_LONG).show();
                    }

                }

                firstRun = false;
            } catch (Exception ex) {
                Log.i("problem", ex.toString());
                ex.printStackTrace();
            } finally {

            }
        }

    });

    public static void startThread() {
        if (selected == -1)
            t.run();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.goals:
                Intent i = new Intent(this, GoalsActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_spinner, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random ran = new Random();
                int randomInt = ran.nextInt(fortuneView.getTotalItems());
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == fortuneView.getTotalItems() - 1 ? 0 : fortuneView.getSelectedIndex() + 1));
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == 0 ? fortuneView.getTotalItems() - 1 : fortuneView.getSelectedIndex() - 1));
                fortuneView.setSelectedItemMultiple(randomInt);
                t.run();
            }
        });

        fortuneView = (FortuneView) findViewById(R.id.dialView);
        int a = GoalsActivity.p.getCredits();
        TextView tv = ((TextView) findViewById(R.id.textView_spinner));
        tv.setText(a + "");

        ArrayList<FortuneItem> dis = new ArrayList<>();

        // Create a wheel with the various graphics
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_always_landscape_portrait)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_add)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_agenda)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_camera)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_help)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_mapmode)));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_save)));

        // Create a wheel with the various graphics
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_always_landscape_portrait), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_add), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_agenda), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_camera), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_help), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_mapmode), FortuneItem.HingeType.Fixed));
//        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_save), FortuneItem.HingeType.Fixed));

        // Create a numbered wheel with the values 0 to 9
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.personal_question), TypeOfFortune.Personal_Question));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.question), TypeOfFortune.Question));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.achiv_cup), TypeOfFortune.Motivation));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.did_you_know), TypeOfFortune.Did_you_know));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.action), TypeOfFortune.Action));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.donation), TypeOfFortune.Donation));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.coin_two), TypeOfFortune.Coin2));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.coin_three), TypeOfFortune.Coin3));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.achiv_cup), TypeOfFortune.Motivation));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.did_you_know), TypeOfFortune.Did_you_know));

        // Create a wheel with colored text values
//        dis.add(new FortuneItem(Color.BLACK, 1));
//        dis.add(new FortuneItem(Color.BLUE, 1));
//        dis.add(new FortuneItem(Color.RED, 1));
//        dis.add(new FortuneItem(Color.GREEN, 1));
//        dis.add(new FortuneItem(Color.MAGENTA, 1));

        fortuneView.addFortuneItems(dis);


    }

}

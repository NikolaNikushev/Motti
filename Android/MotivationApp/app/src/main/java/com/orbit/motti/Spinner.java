package com.orbit.motti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.orbit.motti.Records.Addiction;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
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


    static Thread  t = new Thread(new Runnable() {
        @Override
        public void run() {
        try {
            selected = fortuneView.getSelectedIndex();
            if (!firstRun) {
                TypeOfFortune f = fortuneView.getItem(selected).getFortune();
                Random r = new Random();
                List<Addiction> ads = GoalsActivity.p.addictions;
                String name = "";
                if(ads.size() > 0) {
                    int number = r.nextInt(ads.size());
                    Addiction add = GoalsActivity.p.addictions.get(number);
                    name = add.getName();
                }
                Database database = new Database(context);
                int currentCoints = GoalsActivity.p.getCredits();
                int coinsWon = 0;
                switch (f) {
                    case Action:
                        name = "action";
                        break;
                    case Coin2:
                        //update with 2 coins

                        database.connectToDatabase();

                        database.executeSQL("Update profile set credits=" + (currentCoints + 2) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                        coinsWon = 2;

                        break;
                    case Coin3:

                        database.connectToDatabase();

                        database.executeSQL("Update profile set credits=" + (currentCoints + 3) + " where username = '" + GoalsActivity.p.getUsername() + "'");
                        coinsWon = 3;
                        break;
                    case Did_you_know:
                        name += "-know";
                        break;
                    case Donation:
                        name = "question";
                        break;
                    case Motivation:
                        name = "none";
                        break;
                    case Personal_Question:
                        name = "question";
                        break;

                }
                    if(name.length() == 0){
                        new AlertDialog.Builder(context)
                                .setTitle("Coins!")
                                .setMessage("Congratulations!  You won "+coinsWon+" coins!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })

                                .setIcon(R.mipmap.achiv_stars)
                                .show();

                    }
                else {

                        database.connectToDatabase();

                        Log.i("test", name);
                        Cursor a = database.executeWithResult("Select * from motivation where addiction='"+name+"'");
                        Cursor b = database.executeWithResult("Select * from motivation ");
                        Log.i("b", b.getCount()+"");
                        if (a.getCount() > 0) {
                            String msg = a.getString(0);

                            new AlertDialog.Builder(context)
                                    .setTitle(name)
                                    .setMessage(msg)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })

                                    .setIcon(R.mipmap.achiv_stars)
                                    .show();
                        }
                    }



            }
            firstRun = false;
        }catch(Exception ex){
            Log.i("problem", ex.toString());
            ex.printStackTrace();
        }
        }

    });

    public static void startThread(){
        if(selected == -1)
        t.run();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        t.start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random ran = new Random();
                int randomInt = ran.nextInt(fortuneView.getTotalItems());
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == fortuneView.getTotalItems() - 1 ? 0 : fortuneView.getSelectedIndex() + 1));
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == 0 ? fortuneView.getTotalItems() - 1 : fortuneView.getSelectedIndex() - 1));
                fortuneView.setSelectedItemMultiple(randomInt);


            }
        });

        fortuneView = (FortuneView) findViewById(R.id.dialView);
        int a = GoalsActivity.p.getCredits();
       TextView tv =  ((TextView)findViewById(R.id.textView_spinner));
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
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.personal_question), TypeOfFortune.Action));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.mipmap.question), TypeOfFortune.Personal_Question));
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

    public static void Print(String message){
        //Toast.makeText(context, "message", Toast.LENGTH_LONG).show();
        Log.i("msg", message);
    }
}

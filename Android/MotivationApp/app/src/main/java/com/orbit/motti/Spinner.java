package com.orbit.motti;

import android.app.Instrumentation;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import spinner.FortuneItem;
import spinner.FortuneView;

public class Spinner extends AppCompatActivity {
    FortuneView fortuneView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random ran = new Random();
                int randomInt = ran.nextInt(50);
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == fortuneView.getTotalItems() - 1 ? 0 : fortuneView.getSelectedIndex() + 1));
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == 0 ? fortuneView.getTotalItems() - 1 : fortuneView.getSelectedIndex() - 1));
                fortuneView.setSelectedItemMultiple(randomInt);




            }
        });

        fortuneView = (FortuneView) findViewById(R.id.dialView);

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
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_0)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_1)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_2)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_3)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_4)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_5)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_6)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_7)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_8)));
        dis.add(new FortuneItem(BitmapFactory.decodeResource(getResources(), R.drawable.image_9)));

        // Create a wheel with colored text values
//        dis.add(new FortuneItem(Color.BLACK, 1));
//        dis.add(new FortuneItem(Color.BLUE, 1));
//        dis.add(new FortuneItem(Color.RED, 1));
//        dis.add(new FortuneItem(Color.GREEN, 1));
//        dis.add(new FortuneItem(Color.MAGENTA, 1));

        fortuneView.addFortuneItems(dis);

        findViewById(R.id.btRandom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random ran = new Random();
                int randomInt = ran.nextInt(fortuneView.getTotalItems());
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == fortuneView.getTotalItems() - 1 ? 0 : fortuneView.getSelectedIndex() + 1));
                //fortuneView.setSelectedItem((fortuneView.getSelectedIndex() == 0 ? fortuneView.getTotalItems() - 1 : fortuneView.getSelectedIndex() - 1));
                fortuneView.setSelectedItem(randomInt);
            }
        });





    }
}

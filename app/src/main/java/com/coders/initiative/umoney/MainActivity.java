package com.coders.initiative.umoney;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.coders.initiative.umoney.adapter.DrawerAdapter;
import com.coders.initiative.umoney.commons.CustomFragmentManager;
import com.coders.initiative.umoney.dummies.DummyContent;
import com.coders.initiative.umoney.fragments.HomeFragment;
import com.coders.initiative.umoney.fragments.LoansFragment;
import com.coders.initiative.umoney.fragments.MerchantFragment;
import com.coders.initiative.umoney.fragments.TransactionsFragment;
import com.coders.initiative.umoney.helpers.CircleTransform;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerAdapter drawerAdapter;

    private CharSequence drawerTitle;
    private CharSequence actionBarTitle;


    public static CustomFragmentManager _fragmentManager;
    private static MainActivity _instance;

    private float _lastTranslate = 0.0f; //Variable to hold translation value when page is animating when drawer is opened

    private final int DRAWER_TITLE_DASHBOARD = 0;
    private final int DRAWER_TITLE_TRANSACTIONS = 1;
    private final int DRAWER_TITLE_UTILITIES = 2;
    private final int DRAWER_TITLE_LOANS = 3;
    private final int DRAWER_TITLE_SETTINGS= 4;


    public MainActivity() {
        super();
        MainActivity._instance = this;
    }

    public static MainActivity getInstance() {
        return MainActivity._instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    private void setup(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarTitle = drawerTitle = getTitle();
        drawerListView = (ListView) findViewById(R.id.lv_drawer_items);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        View navHeader = getLayoutInflater().inflate(R.layout.nav_header, drawerListView, false);
        ImageView imageProfile = (ImageView) navHeader.findViewById(R.id.navImage);
        Picasso.with(MainActivity.this)
                .load(DummyContent.getRandomProfileDrawable())
                .placeholder(R.drawable.bg_circle)
                .transform(new CircleTransform())
                .into(imageProfile);

        /**
         * For Pre Kitkat, Add header before adapter
         */
        drawerListView.addHeaderView(navHeader);
        drawerAdapter = new DrawerAdapter(this);
        drawerListView.setAdapter(drawerAdapter);

        drawerListView.setOnItemClickListener(new NavigationDrawerItemClickListener());
        drawerListView.setBackgroundResource(R.drawable.background_media);
        drawerListView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.drawer_width_media);

        final LinearLayout page = (LinearLayout) findViewById(R.id.contentpage);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(actionBarTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (drawerListView.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    page.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(_lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    page.startAnimation(anim);

                    _lastTranslate = moveFactor;
                }
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(R.drawable.icon_hamburger);
        drawerToggle.setToolbarNavigationClickListener(_defaultToggleToolbarNavigationClickListener);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        //Strict Mode Block Guard
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        _fragmentManager = new CustomFragmentManager(this, R.id.container);
        _fragmentManager.switchTo(HomeFragment.class, null);



    }


    /**
     * Navigation Drawer Listener
     */
    private class NavigationDrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            //RESET BACKGROUND COLORS
            for(int x=0;x<drawerListView.getCount();x++){
                drawerListView.getChildAt(x).setBackgroundColor(Color.TRANSPARENT);
            }

            switch (position){
                case 1:
                    _fragmentManager.switchTo(HomeFragment.class, null);
                    setSelected(position,DRAWER_TITLE_DASHBOARD);
                    break;
                case 2:
                    _fragmentManager.switchTo(TransactionsFragment.class, null);
                    setSelected(position,DRAWER_TITLE_TRANSACTIONS);
                    break;
                case 3:
                    _fragmentManager.switchTo(MerchantFragment.class, null);
                    setSelected(position,DRAWER_TITLE_UTILITIES);
                    break;
                case 4:
                    _fragmentManager.switchTo(LoansFragment.class, null);
                    setSelected(position,DRAWER_TITLE_LOANS);
                    break;
                case 5:
                    //_fragmentManager.switchTo(LoansFragment.class, null);
                    //setSelected(position,DRAWER_TITLE_LOANS);
                    Toast.makeText(MainActivity.getInstance(), "Settings", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    toggleNavigation();
                    break;
            }



        }
    }

    /**
     * Avoid Lag on Closing the drawer
     * @param position
     */
    private void setSelected(final int position, final int drawerTitle){

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setTitle(drawerAdapter.getTitle(drawerTitle));
                drawerListView.setItemChecked(position,true);
                drawerLayout.closeDrawer(drawerListView);
            }
        });

    }

    /**
     * Opens the navigation drawer.
     */
    public void openNavigation() {
        drawerLayout.openDrawer(drawerListView);
    }

    /**
     * Closes the navigation drawer.
     */
    public void closeNavigation() {
        drawerLayout.closeDrawer(drawerListView);
    }

    /**
     * Toggles the navigation drawer.
     */
    public void toggleNavigation() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeNavigation();
        } else {
            openNavigation();
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        actionBarTitle = title;
        getSupportActionBar().setTitle(actionBarTitle);
    }

    /**
     * Toggle Listener / Icon Hamburger action
     */
    private View.OnClickListener _defaultToggleToolbarNavigationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (drawerLayout.isDrawerVisible(drawerListView)) {
                drawerLayout.closeDrawer(drawerListView);
            } else {
                drawerLayout.openDrawer(drawerListView);
            }
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}

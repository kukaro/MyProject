package hack.the.wap.musicinstrumentlessoner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

import hack.the.wap.musicinstrumentlessoner.debug.DebugMode;
import hack.the.wap.musicinstrumentlessoner.model.dto.NotificationDto;
import hack.the.wap.musicinstrumentlessoner.model.dto.TemplateDto;
import hack.the.wap.musicinstrumentlessoner.model.dto.UserGroupDto;
import hack.the.wap.musicinstrumentlessoner.mytoggle.MyActionBarDrawerToggle;
import hack.the.wap.musicinstrumentlessoner.myview.MyNavigationView;
import hack.the.wap.musicinstrumentlessoner.session.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GroupFragment.OnFragmentInteractionListener,
        NotificationFragment.OnFragmentInteractionListener, TemplateFragment.OnFragmentInteractionListener {
    private static ImageView ivUserMain;
    private static NotificationFragment notificationFragment;
    private static TemplateFragment templateFragment;
    private static GroupFragment groupFragment;
    private static Session session;
    private static Menu menu;
    private String userName;
    private String userEmail;

    // Initial Block
    {
        notificationFragment = new NotificationFragment();
        templateFragment = new TemplateFragment();
        groupFragment = new GroupFragment();
        session = Session.getInstance();
        DEBUG_SESSION_DATA();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUserNameAndEmail();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        MyActionBarDrawerToggle toggle = new MyActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        MyNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        new Thread(() -> {
//            while (ivUserMain == null) {
//                ivUserMain = findViewById(R.id.mainUser);
//                Log.e("TAG", "onCreate in Thread >>> " + ivUserMain);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }).start();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.flFragment, notificationFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MainActivity.menu = menu;
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fr = getFragmentManager().findFragmentById(R.id.flFragment);
        Log.e("onOptionsItemSelected: ", fr + "");

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notification) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, notificationFragment);
            fragmentTransaction.commit();
            menu.clear();
            getMenuInflater().inflate(R.menu.notification_menu, menu);
        } else if (id == R.id.nav_template) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, templateFragment);
            fragmentTransaction.commit();
            menu.clear();
            getMenuInflater().inflate(R.menu.template_menu, menu);

        } else if (id == R.id.nav_group) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, groupFragment);
            fragmentTransaction.commit();
            menu.clear();
        } else if (id == R.id.nav_store) {

        } else if (id == R.id.nav_information) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUserNameAndEmail() {
        Intent intent = getIntent();
        userName = getIntent().getStringExtra("actLoginName");
        userEmail = getIntent().getStringExtra("actLoginEmail");
        DEBUG_ON_SET_USER_NAME_AND_EMAIL(userName, userEmail);

    }

    private void DEBUG_ON_SET_USER_NAME_AND_EMAIL(String s1, String s2) {
        if (DebugMode.DEBUG_MOD) {
            Log.e("DEBUG", "DEBUG_ON_SET_USER_NAME_AND_EMAIL >>> " + s1 + ":" + s2);
        }
    }

    private void DEBUG_ON_NAVIGATION_ITEM_SELECTED(View v) {
        if (DebugMode.DEBUG_MOD) {
            Log.e("DEBUG", "DEBUG_ON_NAVIGATION_ITEM_SELECTED >>> " + v);
        }
    }

    private void DEBUG_ON_CREATE(View v) {
        if (DebugMode.DEBUG_MOD) {
            Log.e("DEBUG", "DEBUG_ON_CREATE >>> " + v);
        }
    }

    private void DEBUG_ON_BACK_PRESSED(View v) {
        if (DebugMode.DEBUG_MOD) {
            Log.e("DEBUG", "DEBUG_ON_BACK_PRESSED >>> " + v);
        }
    }

    private void DEBUG_ON_START(View v) {
        if (DebugMode.DEBUG_MOD) {
            Log.e("DEBUG", "DEBUG_ON_START >>>" + v);
        }
    }

    private void DEBUG_SESSION_DATA() {
        if (DebugMode.DEBUG_MOD) {
            Log.e("DEBUG", "DEBUG_SESSION_DATA list ▼ ");
            Log.e("DEBUG", "DEBUG_SESSION_DATA >>> user : " + session.getMainUser());
            ArrayList<TemplateDto> templates = session.getTemplates();
            ArrayList<NotificationDto> notifications = session.getNotifications();
            ArrayList<UserGroupDto> userGroups = session.getUserGroups();
            for (TemplateDto dto : templates) {
                Log.e("DEBUG", "DEBUG_SESSION_DATA >>> template : " + dto);
            }
            for (NotificationDto dto : notifications) {
                Log.e("DEBUG", "DEBUG_SESSION_DATA >>> notification : " + dto);
            }
            for (UserGroupDto dto : userGroups) {
                Log.e("DEBUG", "DEBUG_SESSION_DATA >>> userGroups : " + dto);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

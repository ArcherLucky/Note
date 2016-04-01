package com.archer.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.archer.note.constant.Constant;
import com.archer.note.db.Note;
import com.archer.note.fragment.NoteFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoteFragment.OnListFragmentInteractionListener {

    /**
     * 添加按钮
     */
    FloatingActionButton fab;
    /**
     * 侧滑菜单
     */
    NavigationView navigationView;
    /**
     * 侧滑面板
     */
    DrawerLayout drawer;

    /**
     * 笔记Fragment
     */
    NoteFragment noteFragment;
    /**
     * 添加按钮的动画
     */
//    ObjectAnimator anim;
    /**
     * 是否弹出添加菜单
     */
//    boolean isFabOpen;
    /**
     * 增加Note,Bill的Layout
     */
//    RelativeLayout actionAdd;
    /**
     * 添加Note的Fab
     */
//    FloatingActionButton addNoteFab;
    /**
     * 添加Bill的Fab
     */
//    FloatingActionButton addBillFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
//        anim = ObjectAnimator.ofFloat(fab, "rotation", 0f, 235f);
//        anim.setDuration(400);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getNoteIntent(CreateNoteActivity.class, Constant.ACTION_CHANGE_NOTE);
                startActivityForResult(intent, 0);
//                isFabOpen = !isFabOpen;
//                if(!isFabOpen) {
//                    anim = ObjectAnimator.ofFloat(fab, "rotation", 0f, 180f);
//                } else {
//                    anim = ObjectAnimator.ofFloat(fab, "rotation", 0f, -225f);
//                }
//                anim.start();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(null != navigationView) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        noteFragment = NoteFragment.newInstance(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(android.R.id.home, noteFragment);
        fragmentTransaction.commit();

//        actionAdd = (RelativeLayout) findViewById(R.id.ll_add);
//        addNoteFab = (FloatingActionButton) findViewById(R.id.fab_add_note);
//        addBillFab = (FloatingActionButton) findViewById(R.id.fab_add_bill);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        noteFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        if (id == R.id.nav_note) {
            // Handle the camera action
        } else if (id == R.id.nav_bill) {

        } else if (id == R.id.nav_draft) {

        } else if (id == R.id.nav_night) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if(id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Note item, int position) {
        Intent intent = getNoteIntent(CreateNoteActivity.class, Constant.ACTION_CHANGE_NOTE);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACTION_CHANGE_NOTE, item);
        intent.putExtra(Constant.ACTION_CHANGE_NOTE, bundle);
        startActivityForResult(intent, 0);
    }
}

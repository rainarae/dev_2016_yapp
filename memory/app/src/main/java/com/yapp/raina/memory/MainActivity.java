package com.yapp.raina.memory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.raina.db.DBManager;
import com.yapp.raina.dto.AnniversaryDto;
import com.yapp.raina.list.MainAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBManager dbManager;

    //drawer menu
    private LinearLayout menuHome, menuCategory, menuFavorites, menuSetting;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private Toolbar toolbar;
    private LinearLayout toolbarMenuButton;

    //date
    private Calendar cal;
    private TextView txt_today;

    //list
    private ListView list;
    private MainAdapter adapter;
    private ArrayList<AnniversaryDto> myList;
    private int pos;

    //dialog
    private TextView txt_dialong_date;
    private TextView txt_dialog_title;
    private TextView txt_dialog_contents;
    private ImageButton btn_dialog_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        init();
    }

    public void init() {
        dbManager = DBManager.getInstance(this);
        list = (ListView) findViewById(R.id.listview_main);

        cal = Calendar.getInstance();
        myList = dbManager.anniversaryDao.selectByMonth(cal.get(Calendar.MONTH) + 1);
        adapter = new MainAdapter(this, R.layout.list_main, myList);

        toolbarInit();
        drawerMenuInit();
        list.setOnItemClickListener(listener);
        list.setAdapter(adapter);
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.start_tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        dtToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                toolbarMenuButton.setEnabled(true);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toolbarMenuButton.setEnabled(false);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerLayout.setDrawerListener(dtToggle);
        dtToggle.syncState();

        setSupportActionBar(toolbar);

        toolbar.setTitle("");
        toolbar.getMenu().clear();

        toolbarMenuButton = (LinearLayout) findViewById(R.id.layout_menu_btn);
        toolbarMenuButton.setOnClickListener(this);

    }

    //drawer menu
    private void drawerMenuInit() {
        menuHome = (LinearLayout) findViewById(R.id.menu_home_layout);
        menuHome.setOnClickListener(this);
        menuCategory = (LinearLayout) findViewById(R.id.menu_category_layout);
        menuCategory.setOnClickListener(this);
        menuFavorites = (LinearLayout) findViewById(R.id.menu_favorite_layout);
        menuFavorites.setOnClickListener(this);
        menuSetting = (LinearLayout) findViewById(R.id.menu_setting_layout);
        menuSetting.setOnClickListener(this);

        //others matching
        txt_today = (TextView) findViewById(R.id.txt_today_date);

    }

    @Override
    public void onClick(View view) {
        if (view == menuHome) {
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuCategory) {
            Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SettingActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuFavorites) {
            Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, FavoritesActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        } else if (view == menuSetting) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SettingActivity.class);
            drawerLayout.closeDrawers();
            startActivity(i);
            finish();
        }
        if (view == toolbarMenuButton) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawers();
            else
                drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            pos = position;
            final AnniversaryDto data = myList.get(position);
            final LinearLayout linear = (LinearLayout) View.inflate(MainActivity.this, R.layout.dialog_abstract, null
            );
            txt_dialong_date = (TextView) linear.findViewById(R.id.txt_abstract_date);
            txt_dialog_title = (TextView) linear.findViewById(R.id.txt_abstract_title);
            txt_dialog_contents = (TextView) linear.findViewById(R.id.txt_abstract_contents);
            btn_dialog_favorite = (ImageButton) linear.findViewById(R.id.btn_dialog_favorites);
            btn_dialog_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getBookmark_st()) {
                        dbManager.anniversaryDao.updateBookmark(data);
                        btn_dialog_favorite.setImageResource(R.mipmap.ic_launcher);
                        data.setBookmark_st(false);
                    } else {
                        dbManager.anniversaryDao.updateBookmark(data);
                        data.setBookmark_st(true);
                        btn_dialog_favorite.setImageResource(R.drawable.menu_icon);
                        Toast.makeText(MainActivity.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            String[] date = data.getDate_ymd().split("\\.");
            txt_dialong_date.setText(date[1] + "월 " + date[2] + "일");
            txt_dialog_title.setText(data.getTitle());
            txt_dialog_contents.setText(data.getAbstract_());

            if (data.getBookmark_st())
                btn_dialog_favorite.setImageResource(R.drawable.menu_icon);
            else
                btn_dialog_favorite.setImageResource(R.mipmap.ic_launcher);


            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setView(linear);
            dialog.setPositiveButton("자세히", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(MainActivity.this, DetailContentsActivity.class);
                    i.putExtra("Datadto", myList.get(pos));
                    startActivity(i);
                }
            });
            dialog.show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //today's date setting
        String today = (cal.get(Calendar.MONTH) + 1) + "월 ";
        today += cal.get(Calendar.DATE) + "일";

        txt_today.setText(today);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuHome = null;
        menuCategory = null;
        menuFavorites = null;
        menuSetting = null;
        drawerLayout = null;
        dtToggle = null;

        toolbarMenuButton = null;
        toolbar = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

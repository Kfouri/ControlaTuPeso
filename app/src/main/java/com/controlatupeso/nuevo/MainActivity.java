package com.controlatupeso.nuevo;

import android.app.LocalActivityManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.controlatupeso.MenuPrincipal;
import com.controlatupeso.Perfil;
import com.controlatupeso.PerfilDAO;
import com.controlatupeso.R;

public class MainActivity extends AppCompatActivity implements ScrollListener {

    private PerfilDAO datasource;

    PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        float density = getResources().getDisplayMetrics().density;


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        datasource = new PerfilDAO(this);
        datasource.open();

        Perfil per = new Perfil();


        if (prefs.getString("perfil","").equals("1"))
        {
            per = datasource.getPerfil(prefs.getInt("id1",0));
        }
        else
        {
            if (prefs.getString("perfil","").equals("2"))
            {
                per = datasource.getPerfil(prefs.getInt("id2",0));
            }
            else
            {
                if (prefs.getString("perfil","").equals("3"))
                {
                    per = datasource.getPerfil(prefs.getInt("id3",0));
                }
            }
        }

        datasource.close();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id_perfil", per.getId());
        editor.commit();

        //Alcatel HDPI
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.perfil)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.solo_peso)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.graficos)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onScrollChanged() {
        adapter.setIndex();
    }

}
package com.controlatupeso.nuevo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.controlatupeso.EditarPerfil;
import com.controlatupeso.Historico;
import com.controlatupeso.HistoricoDAO;
import com.controlatupeso.MenuPrincipal;
import com.controlatupeso.Perfil;
import com.controlatupeso.PerfilDAO;
import com.controlatupeso.R;
import com.controlatupeso.Registra;
import com.controlatupeso.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;

/**
 * Created by MSI on 15/01/2017.
 */

public class PerfilFragment extends Fragment implements Updateable{

    private TextView texto_bmi_2;
    private TextView texto_bmi_4;
    private TextView texto_perfil;
    private PerfilDAO perdao;
    private HistoricoDAO datasourceh;
    private InterstitialAd interstitial;
    private SharedPreferences prefs;

    LinearLayout li1;
    LinearLayout li2;
    LinearLayout li3;
    LinearLayout li4;
    LinearLayout li5;
    LinearLayout li6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        AdView adView;
        // Create an ad.
        adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-8178489486911148/2323256312");

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.ad);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
                .build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);

        // Create the interstitial.
        interstitial = new InterstitialAd(getActivity());
        interstitial.setAdUnitId("ca-app-pub-8178489486911148/5018061510");

        // Create ad request.
        AdRequest adRequest2 = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest2);

        /////////////////////////////////////////////////////////////////////////////////


        texto_bmi_2 = (TextView) view.findViewById(R.id.texto_bmi_2);
        texto_bmi_4 = (TextView) view.findViewById(R.id.texto_bmi_4);
        texto_perfil = (TextView) view.findViewById(R.id.texto_perfil);

        li1=(LinearLayout) view.findViewById(R.id.fila1_layout);
        li2=(LinearLayout) view.findViewById(R.id.fila2_layout);
        li3=(LinearLayout) view.findViewById(R.id.fila3_layout);
        li4=(LinearLayout) view.findViewById(R.id.fila4_layout);
        li5=(LinearLayout) view.findViewById(R.id.fila5_layout);
        li6=(LinearLayout) view.findViewById(R.id.fila6_layout);


        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        ImageView imageEditar = (ImageView) view.findViewById(R.id.imagen_editar);
        imageEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInterstitial();
                Intent i = new Intent(getActivity(), EditarPerfil.class);
                startActivity(i);
            }
        });

        return view;
    }


    @Override
    public void onResume()
    {
        super.onResume();

        refresh();
    }

    void refresh()
    {

        PerfilDAO datasource = new PerfilDAO(getActivity());
        datasource.open();

        Perfil perfil = new Perfil();

        perfil = datasource.getPerfil(prefs.getInt("id_perfil",0));

        datasource.close();

        texto_perfil.setText(perfil.getAlias());

        int dimc;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        perdao = new PerfilDAO(getActivity());
        perdao.open();
        Perfil per = perdao.getPerfil(prefs.getInt("id_perfil",0));
        datasourceh = new HistoricoDAO(getActivity());
        datasourceh.open();
        Historico his = datasourceh.getUltimoHistoricos(prefs.getInt("id_perfil",0));

        final Util util = ((Util) getActivity().getApplicationContext());
        dimc = (int) Math.round(util.calcularIMC(Float.parseFloat(his.getPeso()), per.getAltura()));

        int posIMC = util.posIMC(dimc);

        //DecimalFormat formateador = new DecimalFormat("00.00");
        texto_bmi_2.setText(""+dimc);
        texto_bmi_4.setText(his.getPeso().toString()+" Kg. ");

        li1.setBackgroundColor(getResources().getColor(R.color.white));
        li2.setBackgroundColor(getResources().getColor(R.color.white));
        li3.setBackgroundColor(getResources().getColor(R.color.white));
        li4.setBackgroundColor(getResources().getColor(R.color.white));
        li5.setBackgroundColor(getResources().getColor(R.color.white));
        li6.setBackgroundColor(getResources().getColor(R.color.white));

        switch (posIMC) {
            case 0:
                li1.setBackgroundColor(getResources().getColor(R.color.separador));
                break;
            case 1:
                li2.setBackgroundColor(getResources().getColor(R.color.separador));
                break;
            case 2:
                li3.setBackgroundColor(getResources().getColor(R.color.separador));
                break;
            case 3:
                li4.setBackgroundColor(getResources().getColor(R.color.separador));
                break;
            case 4:
                li5.setBackgroundColor(getResources().getColor(R.color.separador));
                break;
            case 5:
                li6.setBackgroundColor(getResources().getColor(R.color.separador));
                break;
        }

    }


    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    public void update() {
        refresh();
    }
}
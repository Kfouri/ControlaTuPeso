package com.controlatupeso.nuevo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.controlatupeso.Historico;
import com.controlatupeso.HistoricoDAO;
import com.controlatupeso.ItemHistorico;
import com.controlatupeso.R;
import com.controlatupeso.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.LineView;

public class GraficoFragment extends Fragment implements Updateable{

    private Context mContext;

    LineView lineViewFloat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();

        View view = inflater.inflate(R.layout.chart_layout, container, false);

        lineViewFloat = (LineView) view.findViewById(R.id.line_view_float);

        cargarGrafico();


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

        return view;
    }

    void cargarGrafico()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        HistoricoDAO datasourceh;

        datasourceh = new HistoricoDAO(mContext);
        datasourceh.open();
        List<Historico> listadoHistoricos = datasourceh.getTodosHistoricos(prefs.getInt("id_perfil",0),"N");

        ArrayList<ItemHistorico> results = new ArrayList<ItemHistorico>();

        final Util util = ((Util) mContext.getApplicationContext());

        DecimalFormat formateador = new DecimalFormat("0.00");

        ArrayList<Float> dataListF = new ArrayList<>();
        ArrayList<Float> dataListF2 = new ArrayList<>();
        ArrayList<String> test = new ArrayList<String>();

        for (int i=0;i<listadoHistoricos.size();i++)
        {
            ItemHistorico item_details = new ItemHistorico();
            test.add(listadoHistoricos.get(i).getFecha());
            float fPeso = Float.parseFloat(listadoHistoricos.get(i).getPeso());
            float fIMC = Float.parseFloat(listadoHistoricos.get(i).getImc());

            dataListF.add(fPeso);
            dataListF2.add((float) Math.round(fIMC));
        }

        lineViewFloat.setBottomTextList(test);
        lineViewFloat.setColorArray(new int[]{Color.parseColor("#F44336"),Color.parseColor("#9C27B0")});
        lineViewFloat.setDrawDotLine(true);
        lineViewFloat.setShowPopup(LineView.SHOW_POPUPS_All);


        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
        dataListFs.add(dataListF);
        dataListFs.add(dataListF2);

        lineViewFloat.setFloatDataList(dataListFs);
        lineViewFloat.setContentDescription("Hola");
        datasourceh.close();

    }

    @Override
    public void update() {
        cargarGrafico();
    }
}
package com.controlatupeso.nuevo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.controlatupeso.CrearPerfil;
import com.controlatupeso.Historico;
import com.controlatupeso.HistoricoDAO;
import com.controlatupeso.ItemHistorico;
import com.controlatupeso.ItemHistoricoBaseAdapter;
import com.controlatupeso.NuevoPeso;
import com.controlatupeso.Perfil;
import com.controlatupeso.PerfilDAO;
import com.controlatupeso.R;
import com.controlatupeso.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PesoFragment extends Fragment {

    private PerfilDAO perdao;
    private HistoricoDAO datasourceh;
    private InterstitialAd interstitial;
    private SharedPreferences prefs;
    private Context mContext;
    private ListView lv1;
    private float mPeso;
    Double dimc=0.0;
    private HistoricoDAO datasourceH;

    private ScrollListener mRefreshListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ScrollListener) {
            mRefreshListener = (ScrollListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        View view = inflater.inflate(R.layout.peso_fragment, container, false);

        lv1 = (ListView) view.findViewById(R.id.lista_pesos);

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

        Button btnAgregar = (Button) view.findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(mContext,NuevoPeso.class);
                //startActivity(intent);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                AlertDialog dialog;
                builder.setTitle(mContext.getResources().getString(R.string.nuevo_registro));

                // Set up the input
                final EditText input = new EditText(mContext);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);

                prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

                // Set up the buttons
                builder.setPositiveButton(mContext.getResources().getString(R.string.nuevo_guardar), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mPeso = Float.parseFloat(input.getText().toString());

                        PerfilDAO perdao = new PerfilDAO(mContext);
                        perdao.open();
                        int perfil = prefs.getInt("id_perfil",0);

                        Perfil per = perdao.getPerfil(perfil);
                        perdao.close();

                        final Util util = ((Util) mContext.getApplicationContext());

                        dimc = util.calcularIMC(mPeso, per.getAltura());

                        datasourceH = new HistoricoDAO(mContext);
                        datasourceH.open();

                        Historico his1 = datasourceH.crearHistorico(perfil,
                                mPeso,
                                dimc.toString());

                        datasourceH.close();

                        CargarLista();

                        dialog.cancel();
                    }
                });

                builder.setNegativeButton(mContext.getResources().getString(R.string.nuevo_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();

            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        CargarLista();
    }

    private void CargarLista()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        datasourceh = new HistoricoDAO(mContext);
        datasourceh.open();
        List<Historico> listadoHistoricos = datasourceh.getTodosHistoricos(prefs.getInt("id_perfil",0),"N");

        ArrayList<ItemHistorico> results = new ArrayList<ItemHistorico>();

        final Util util = ((Util) mContext.getApplicationContext());

        float pesoAnterior = 0f;

        DecimalFormat formateador = new DecimalFormat("0.00");

        for (int i=0;i<listadoHistoricos.size();i++)
        {
            ItemHistorico item_details = new ItemHistorico();

            if (i==0)
            {
                item_details.setDif("0");
            }
            else
            {
                float diff = Float.parseFloat(listadoHistoricos.get(i).getPeso())-pesoAnterior;
                item_details.setDif(""+formateador.format(diff));
            }

            pesoAnterior = Float.parseFloat(listadoHistoricos.get(i).getPeso());

            if (listadoHistoricos.get(i).getFecha().equals(""))
            {
                item_details.setFecha(listadoHistoricos.get(i).getFecha());
            }
            else
            {
                item_details.setFecha(listadoHistoricos.get(i).getFecha());
            }

            item_details.setPeso(formateador.format(Double.parseDouble(listadoHistoricos.get(i).getPeso()))+" Kg");

            item_details.setImc(""+(int) Math.round(Double.parseDouble(listadoHistoricos.get(i).getImc())));
            results.add(item_details);
        }

        datasourceh.close();

        lv1.setAdapter(new ItemHistoricoBaseAdapter(mContext, results));

        if (mRefreshListener != null) {
            mRefreshListener.onScrollChanged();
        }
    }
}

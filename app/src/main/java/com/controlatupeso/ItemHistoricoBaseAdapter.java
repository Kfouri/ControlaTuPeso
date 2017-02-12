package com.controlatupeso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemHistoricoBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemHistorico> itemDetailsrrayList;
		
	private LayoutInflater l_Inflater;
    private Util util;
    private Context mContext;

	public ItemHistoricoBaseAdapter(Context context, ArrayList<ItemHistorico> results)
	{
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
        util = ((Util) context.getApplicationContext());
        mContext = context;
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(getCount()-position-1);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.row_historico, null);
			holder = new ViewHolder();
			holder.txt_fecha = (TextView) convertView.findViewById(R.id.fecha);
			holder.txt_peso = (TextView) convertView.findViewById(R.id.peso);
			holder.txt_imc = (TextView) convertView.findViewById(R.id.imc);
            holder.txt_diff = (TextView) convertView.findViewById(R.id.peso_differencia);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_fecha.setText(itemDetailsrrayList.get(getCount()-position-1).getFecha());
		holder.txt_peso.setText(itemDetailsrrayList.get(getCount()-position-1).getPeso());

        int IMC = (int) Math.round(Double.parseDouble(itemDetailsrrayList.get(getCount()-position-1).getImc()));
        int posIMC = util.posIMC(IMC);

        int color = mContext.getResources().getColor(R.color.IMC_2);
        String textoIMC = "";

        switch (posIMC) {
            case 0:
                color = mContext.getResources().getColor(R.color.IMC_0);
                textoIMC = mContext.getString(R.string.texto_perfil_ibm_fila1_descripcion);
                break;
            case 1:
                color = mContext.getResources().getColor(R.color.IMC_1);
                textoIMC = mContext.getString(R.string.texto_perfil_ibm_fila2_descripcion);
                break;
            case 2:
                color = mContext.getResources().getColor(R.color.IMC_2);
                textoIMC = mContext.getString(R.string.texto_perfil_ibm_fila3_descripcion);
                break;
            case 3:
                color = mContext.getResources().getColor(R.color.IMC_3);
                textoIMC = mContext.getString(R.string.texto_perfil_ibm_fila4_descripcion);
                break;
            case 4:
                color = mContext.getResources().getColor(R.color.IMC_4);
                textoIMC = mContext.getString(R.string.texto_perfil_ibm_fila5_descripcion);
                break;
            case 5:
                color = mContext.getResources().getColor(R.color.IMC_5);
                textoIMC = mContext.getString(R.string.texto_perfil_ibm_fila6_descripcion);
                break;
        }

        holder.txt_imc.setText(itemDetailsrrayList.get(getCount()-position-1).getImc()+" ("+textoIMC+")");
        holder.txt_imc.setTextColor(color);

        holder.txt_diff.setText(itemDetailsrrayList.get(getCount()-position-1).getDif()+" Kg");

        String sDiff = itemDetailsrrayList.get(getCount()-position-1).getDif();
        sDiff = sDiff.replace(",",".");
        float diff = Float.parseFloat(sDiff);

        if (diff>0)
        {
            holder.txt_diff.setTextColor(mContext.getResources().getColor(R.color.IMC_5));
        }
        else
        {
            holder.txt_diff.setTextColor(mContext.getResources().getColor(R.color.IMC_2));
        }

		return convertView;
	}

	static class ViewHolder 
	{
		TextView txt_fecha;
		TextView txt_peso;
		TextView txt_imc;
        TextView txt_diff;
	}
}
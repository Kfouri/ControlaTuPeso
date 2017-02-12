package com.controlatupeso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemListBaseAdapter extends BaseAdapter {
	private static List<Historico> itemDetailsrrayList;
	
	private Integer[] imgid = {R.drawable.transparente,R.drawable.flechaverde,R.drawable.flecharoja};

	
	private LayoutInflater l_Inflater;

	public ItemListBaseAdapter(Context context, List<Historico> results) 
	{
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) 
	{
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = l_Inflater.inflate(R.layout.historico_row, null);
			holder = new ViewHolder();
			holder.txt_fecha = (TextView) convertView.findViewById(R.id.fecha);
			holder.txt_peso = (TextView) convertView.findViewById(R.id.peso);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);
			holder.txt_imc = (TextView) convertView.findViewById(R.id.imc);
			holder.txt_objetivo = (TextView) convertView.findViewById(R.id.objetivo);
			
			convertView.setTag(holder);
		}
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		DecimalFormat formateador = new DecimalFormat("00.00");
		
		
		holder.txt_fecha.setText(""+itemDetailsrrayList.get(position).getFecha());
		holder.txt_peso.setText(""+itemDetailsrrayList.get(position).getPeso());
		holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getDif() - 1]);
		if (itemDetailsrrayList.get(position).getImc().equals(" IMC"))
		{
			holder.txt_imc.setText(""+itemDetailsrrayList.get(position).getImc());
		}
		else
		{
			holder.txt_imc.setText(""+formateador.format(Double.parseDouble(itemDetailsrrayList.get(position).getImc())));	
		}
		
		holder.txt_objetivo.setText(""+itemDetailsrrayList.get(position).getPeso_objetivo());
		
		return convertView;
	}

	static class ViewHolder 
	{
		TextView txt_fecha;
		TextView txt_peso;
		ImageView itemImage;
		TextView txt_imc;
		TextView txt_objetivo;
	}
}

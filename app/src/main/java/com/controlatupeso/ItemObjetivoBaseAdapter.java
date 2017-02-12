package com.controlatupeso;

import java.util.ArrayList;

import com.controlatupeso.ItemHistoricoBaseAdapter.ViewHolder;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ItemObjetivoBaseAdapter extends BaseAdapter 
{
	private static ArrayList<ItemObjetivo> itemDetailsrrayList;
		
	private LayoutInflater l_Inflater;

	public ItemObjetivoBaseAdapter(Context context, ArrayList<ItemObjetivo> results)
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
			convertView = l_Inflater.inflate(R.layout.listado_objetivo_row, null);
			holder = new ViewHolder();
			holder.txt_fecha = (TextView) convertView.findViewById(R.id.fecha);
			holder.txt_peso = (TextView) convertView.findViewById(R.id.peso);
			holder.txt_peso_real = (TextView) convertView.findViewById(R.id.peso_real);
			holder.txt_estado = (TextView) convertView.findViewById(R.id.estado);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_fecha.setText(itemDetailsrrayList.get(position).getFecha());
		holder.txt_peso.setText(itemDetailsrrayList.get(position).getPeso());
		holder.txt_peso_real.setText(itemDetailsrrayList.get(position).getPesoReal());
		holder.txt_estado.setText(itemDetailsrrayList.get(position).getEstado());
		
		return convertView;
	}

	static class ViewHolder 
	{
		TextView txt_fecha;
		TextView txt_peso;
		TextView txt_peso_real;
		TextView txt_estado;
	}
}
package com.findyourplace.mimel.furp.objects;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findyourplace.mimel.furp.R;
import com.findyourplace.mimel.furp.models.Site;

import java.util.List;

/**
 * Created by mimel on 12/09/17.
 */

public class Adapter extends  RecyclerView.Adapter<Adapter.SitesViewHolder>{

    List<Site> sites;

    public Adapter(List<Site> sites) {
        this.sites = sites;
    }

    @Override
    public SitesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recicler,parent, false);
        SitesViewHolder holder = new SitesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SitesViewHolder holder, int position) {
        Site site = sites.get(position);
        holder.name.setText("Nombre: "+site.getName());
        holder.city.setText("Ciudad: "+site.getCity());
        holder.type.setText("Tipo: "+site.getType());
        holder.description.setText("Descripcion: "+site.getDescription());
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    public static class SitesViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView city;
        TextView type;
        TextView description;
        public SitesViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txtName);
            city = (TextView)itemView.findViewById(R.id.txtCity);
            type = (TextView)itemView.findViewById(R.id.txtType);
            description = (TextView)itemView.findViewById(R.id.txtDescription);
        }
    }
}

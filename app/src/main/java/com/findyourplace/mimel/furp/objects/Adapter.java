package com.findyourplace.mimel.furp.objects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findyourplace.mimel.furp.R;
import com.findyourplace.mimel.furp.models.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mimel on 12/09/17.
 */

public class Adapter extends  RecyclerView.Adapter<Adapter.SitesViewHolder> implements View.OnClickListener{

    List<Site> sites;
    private View.OnClickListener listener;
    Context context;

    public Adapter(List<Site> sites, Context context) {
        this.sites = sites;
        this.context = context;

    }

    @Override
    public SitesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recicler,parent, false);
        v.setOnClickListener(this);
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {

        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class SitesViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView city;
        TextView type;
        TextView description;
        ArrayList<Site> sites = new ArrayList<Site>();
        Context context;

        public SitesViewHolder(View itemView) {
            super(itemView);
            this.sites = sites;
            this.context = context;
            name = (TextView)itemView.findViewById(R.id.txtName);
            city = (TextView)itemView.findViewById(R.id.txtCity);
            type = (TextView)itemView.findViewById(R.id.txtType);
            description = (TextView)itemView.findViewById(R.id.txtDescription);
        }
    }
}

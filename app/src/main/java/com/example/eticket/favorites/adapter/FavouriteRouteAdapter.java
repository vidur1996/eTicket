package com.example.eticket.favorites.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket.R;

import java.util.ArrayList;

public class FavouriteRouteAdapter extends RecyclerView.Adapter<FavouriteRouteAdapter.ViewHolder> {
    private final ArrayList<Routes> userset;
    public FavouriteRouteAdapter(ArrayList<Routes> arrayList) {
        userset = arrayList;
    }
    int id = 0;

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_favourite_route, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Routes myListData = userset.get(position);
        holder.tv_to.setText(myListData.getTo());
        holder.tv_from.setText(myListData.getFrom());
        if(id<Integer.parseInt(myListData.getId())){
            id = Integer.parseInt(myListData.getId());
        }
    }

    @Override
    public int getItemCount() {
        return userset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_to;
        TextView tv_from;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_to = (TextView) itemView.findViewById(R.id.tv_favo_to);
            tv_from = (TextView) itemView.findViewById(R.id.tv_favo_from);


        }
    }
    public int getMax(){
        return id;
    }

}

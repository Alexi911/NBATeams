package com.example.nba.presentation.view.Cavaliers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.nba.R;
import com.example.nba.presentation.model.CavaliersPlayers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CavaliersAdapter extends RecyclerView.Adapter<CavaliersAdapter.ViewHolder> implements Filterable {

    private List<CavaliersPlayers> values;
    private List<CavaliersPlayers> valuesFull;
    private  OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CavaliersPlayers item);
    }

    public CavaliersAdapter(List<CavaliersPlayers> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
        valuesFull = new ArrayList<>(myDataset);
    }

    @Override
    public CavaliersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CavaliersPlayers currentPlayers = values.get(position);
        holder.txtHeader.setText(currentPlayers.getCavaliers_firstName());
        holder.txtFooter.setText(currentPlayers.getCavaliers_lastName());

        Picasso.get().load(currentPlayers.getCavaliers_image()).into(holder.urlimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentPlayers);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView urlimage;


        public ViewHolder(View v) {

            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            urlimage = (ImageView) v.findViewById(R.id.imageView3);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CavaliersPlayers> filterdList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterdList.addAll(valuesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CavaliersPlayers players : valuesFull){
                    if(players.getCavaliers_firstName().toLowerCase().contains(filterPattern)){
                        filterdList.add(players);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterdList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            values.clear();
            values.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}

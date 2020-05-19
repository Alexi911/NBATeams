package com.example.nba.presentation.view.Bulls;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.nba.R;
import com.example.nba.presentation.model.BullsPlayers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BullsAdapter extends RecyclerView.Adapter<BullsAdapter.ViewHolder> implements Filterable {

    private List<BullsPlayers> values;
    private List<BullsPlayers> valuesFull;
    private BullsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BullsPlayers item);
    }

    public BullsAdapter(List<BullsPlayers> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
        valuesFull = new ArrayList<>(myDataset);
    }

    @Override
    public BullsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        BullsAdapter.ViewHolder vh = new BullsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BullsPlayers currentPlayers = values.get(position);
        holder.txtHeader.setText(currentPlayers.getBulls_firstName());
        holder.txtFooter.setText(currentPlayers.getBulls_lastName());

        Picasso.get().load(currentPlayers.getBulls_image()).into(holder.urlimage);

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
            List<BullsPlayers> filterdList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterdList.addAll(valuesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BullsPlayers players : valuesFull){
                    if(players.getBulls_firstName().toLowerCase().contains(filterPattern)){
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
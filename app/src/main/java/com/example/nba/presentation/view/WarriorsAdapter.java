package com.example.nba.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nba.R;
import com.example.nba.presentation.model.WarriorsPlayers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WarriorsAdapter extends RecyclerView.Adapter<WarriorsAdapter.ViewHolder> implements Filterable {

    private List<WarriorsPlayers> values;
    private List<WarriorsPlayers> valuesFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WarriorsPlayers item);
    }

    public WarriorsAdapter(List<WarriorsPlayers> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
        valuesFull = new ArrayList<>(myDataset);
    }

    @Override
    public WarriorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WarriorsPlayers currentPlayers = values.get(position);
        holder.txtHeader.setText(currentPlayers.getWarriors_firstName());
        holder.txtFooter.setText(currentPlayers.getWarriors_lastName());

        Picasso.get().load(currentPlayers.getWarriors_image()).into(holder.urlimage);

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
            List<WarriorsPlayers> filterdList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterdList.addAll(valuesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (WarriorsPlayers players : valuesFull){
                    if(players.getWarriors_firstName().toLowerCase().contains(filterPattern)){
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

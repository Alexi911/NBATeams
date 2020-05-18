package com.example.nba.Bulls;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.nba.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BullsAdapter extends RecyclerView.Adapter<BullsAdapter.ViewHolder> {

    private List<BullsPlayers> values;
    private BullsOnNoteListener mOnNoteListener;

    public BullsAdapter(List<BullsPlayers> myDataset, BullsOnNoteListener BullsonNoteListener) {
        this.mOnNoteListener = BullsonNoteListener;
        values = myDataset;
    }

    @Override
    public BullsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        BullsAdapter.ViewHolder vh = new BullsAdapter.ViewHolder(v, mOnNoteListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(BullsAdapter.ViewHolder holder, final int position) {
        final BullsPlayers currentPlayers = values.get(position);
        holder.txtHeader.setText(currentPlayers.getBulls_firstName());
        holder.txtFooter.setText(currentPlayers.getBulls_lastName());

        Picasso.get().load(currentPlayers.getBulls_image()).into(holder.urlimage);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView urlimage;

        BullsAdapter.BullsOnNoteListener BullsonNoteListener;

        public ViewHolder(View v, BullsOnNoteListener BullsonNoteListener) {

            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            urlimage = (ImageView) v.findViewById(R.id.imageView3);
            this.BullsonNoteListener = BullsonNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            BullsonNoteListener.onNoteCLick(getAdapterPosition() );

        }
    }

    public interface BullsOnNoteListener{
        void onNoteCLick(int position);
    }

}

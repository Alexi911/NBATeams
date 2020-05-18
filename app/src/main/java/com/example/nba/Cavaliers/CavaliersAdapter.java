package com.example.nba.Cavaliers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.nba.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CavaliersAdapter extends RecyclerView.Adapter<CavaliersAdapter.ViewHolder> {

    private List<CavaliersPlayers> values;
    private CavaliersOnNoteListener mOnNoteListener;

    public CavaliersAdapter(List<CavaliersPlayers> myDataset, CavaliersOnNoteListener CavaliersonNoteListener) {
        this.mOnNoteListener = CavaliersonNoteListener;
        values = myDataset;
    }

    @Override
    public CavaliersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        CavaliersAdapter.ViewHolder vh = new CavaliersAdapter.ViewHolder(v, mOnNoteListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CavaliersAdapter.ViewHolder holder, final int position) {
        final CavaliersPlayers currentPlayers = values.get(position);
        holder.txtHeader.setText(currentPlayers.getCavaliers_firstName());
        holder.txtFooter.setText(currentPlayers.getCavaliers_lastName());

        Picasso.get().load(currentPlayers.getCavaliers_image()).into(holder.urlimage);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView urlimage;

        CavaliersAdapter.CavaliersOnNoteListener CavaliersonNoteListener;

        public ViewHolder(View v, CavaliersOnNoteListener CavaliersonNoteListener) {

            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            urlimage = (ImageView) v.findViewById(R.id.imageView3);
            this.CavaliersonNoteListener = CavaliersonNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            CavaliersonNoteListener.onNoteCLick(getAdapterPosition() );

        }
    }

    public interface CavaliersOnNoteListener{
        void onNoteCLick(int position);
    }

}

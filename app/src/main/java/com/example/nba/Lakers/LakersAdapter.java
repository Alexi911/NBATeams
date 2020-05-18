package com.example.nba.Lakers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nba.Lakers.LakersPlayers;
import com.example.nba.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LakersAdapter extends RecyclerView.Adapter<LakersAdapter.ViewHolder> {

    private List<LakersPlayers> values;
    private LakersOnNoteListener mOnNoteListener;

    public LakersAdapter(List<LakersPlayers> myDataset, LakersOnNoteListener LakersonNoteListener) {
        this.mOnNoteListener = LakersonNoteListener;
        values = myDataset;
    }

    @Override
    public LakersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        LakersAdapter.ViewHolder vh = new LakersAdapter.ViewHolder(v, mOnNoteListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(LakersAdapter.ViewHolder holder, final int position) {
        final LakersPlayers currentPlayers = values.get(position);
        holder.txtHeader.setText(currentPlayers.getLakers_firstName());
        holder.txtFooter.setText(currentPlayers.getLakers_lastName());

        Picasso.get().load(currentPlayers.getLakers_image()).into(holder.urlimage);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView urlimage;

        LakersAdapter.LakersOnNoteListener LakersonNoteListener;

        public ViewHolder(View v, LakersOnNoteListener LakersonNoteListener) {

            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            urlimage = (ImageView) v.findViewById(R.id.imageView3);
            this.LakersonNoteListener = LakersonNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            LakersonNoteListener.onNoteCLick(getAdapterPosition() );

        }
    }

    public interface LakersOnNoteListener{
        void onNoteCLick(int position);
    }

}

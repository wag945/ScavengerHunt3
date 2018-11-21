package com.example.bill.scavengerhunt3;

import android.support.v7.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {

    //private ArrayList<UserProfile> mData;
    private ArrayList<Game> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    GameRecyclerViewAdapter(Context context, ArrayList<Game> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.game_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = mData.get(position);
        holder.mGameIdTextView.setText("Name: "+game.getGameName());
        holder.mNumTeamsTextView.setText("Num teams: "+game.getNumTeams());
        holder.mGameStatusTextView.setText("Status: "+game.getGameStatus().toString());
        if (game.getGameStatus().toString().equals("NOT_STARTED")) {
            holder.mGameStatusTextView.setTextColor(mContext.getResources().getColor(R.color.gameNotStartedColor));
        }
        else if (game.getGameStatus().toString().equals("IN_PROGRESS")) {
            holder.mGameStatusTextView.setTextColor(mContext.getResources().getColor(R.color.gameStartedColor));
        }
        else {
            holder.mGameStatusTextView.setTextColor(mContext.getResources().getColor(R.color.gameStoppedColor));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mGameIdTextView;
        TextView mNumTeamsTextView;
        TextView mGameStatusTextView;



        ViewHolder(View itemView) {
            super(itemView);
            mGameIdTextView = (TextView)itemView.findViewById(R.id.gameId);
            mNumTeamsTextView = (TextView)itemView.findViewById(R.id.numTeams);
            mGameStatusTextView = (TextView)itemView.findViewById(R.id.gameStatus);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Game getItem(int id) {
        //return mData.get(id);
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
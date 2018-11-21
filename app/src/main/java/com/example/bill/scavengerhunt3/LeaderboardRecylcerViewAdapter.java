package com.example.bill.scavengerhunt3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;



public class LeaderboardRecylcerViewAdapter extends RecyclerView.Adapter<LeaderboardRecylcerViewAdapter.ViewHolder> {
    private ArrayList<Game> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    LeaderboardRecylcerViewAdapter(Context context, ArrayList<Game> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.teams_leaderboard_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = mData.get(position);
        holder.mGameIdTextView.setText(game.getGameName());
        holder.mNumTeamsTextView.setText(game.getNumTeams());
        holder.scavengeItem1Leaderboard.setText(game.getScavengeList().get(0).getName());
        holder.scavengeItem2Leaderboard.setText(game.getScavengeList().get(1).getName());
        holder.scavengeItem3Leaderboard.setText(game.getScavengeList().get(2).getName());
        holder.scavengeItem4Leaderboard.setText(game.getScavengeList().get(3).getName());
        holder.scavengeItem5Leaderboard.setText(game.getScavengeList().get(4).getName());

        ArrayList<CheckBox> scavengeItemsLeaderboardCheckList = new ArrayList<CheckBox>();
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem1Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem2Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem3Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem4Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem5Leaderboard);

        //cycle through each team and check thier own scavenger list if its found and check box as appropriate
        for(Team tm : game.getTeamList()){
            for(int i =0; i < tm.getTeamScavengeList().size(); i++){
                scavengeItemsLeaderboardCheckList.get(i).setChecked(tm.getTeamScavengeList().get(i).getFound()) ;
            }
        }

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

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mGameIdTextView;
        TextView mNumTeamsTextView;
        TextView mGameStatusTextView;
        CheckBox scavengeItem1Leaderboard;
        CheckBox scavengeItem2Leaderboard;
        CheckBox scavengeItem3Leaderboard;
        CheckBox scavengeItem4Leaderboard;
        CheckBox scavengeItem5Leaderboard;

        ViewHolder(View itemView) {
            super(itemView);
            mGameIdTextView = (TextView)itemView.findViewById(R.id.gameIdLeaderboard);
            mNumTeamsTextView = (TextView)itemView.findViewById(R.id.numTeamsLeaderboard);
            mGameStatusTextView = (TextView)itemView.findViewById(R.id.gameStatusLeaderboard);
            scavengeItem1Leaderboard = (CheckBox)itemView.findViewById(R.id.scavengeItem1Leaderboard);
            scavengeItem2Leaderboard = (CheckBox)itemView.findViewById(R.id.scavengeItem2Leaderboard);
            scavengeItem3Leaderboard = (CheckBox)itemView.findViewById(R.id.scavengeItem3Leaderboard);
            scavengeItem4Leaderboard = (CheckBox)itemView.findViewById(R.id.scavengeItem4Leaderboard);
            scavengeItem5Leaderboard = (CheckBox)itemView.findViewById(R.id.scavengeItem5Leaderboard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // allows clicks events to be caught
    void setClickListener(LeaderboardRecylcerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}

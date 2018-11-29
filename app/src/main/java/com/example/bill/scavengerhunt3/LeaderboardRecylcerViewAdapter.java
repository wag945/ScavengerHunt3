package com.example.bill.scavengerhunt3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;



public class LeaderboardRecylcerViewAdapter extends RecyclerView.Adapter<LeaderboardRecylcerViewAdapter.ViewHolder> {
    private ArrayList<Team> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    LeaderboardRecylcerViewAdapter(Context context, ArrayList<Team> data) {
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
        Team gameTeam = mData.get(position);

        holder.scavengeItem1Leaderboard.setText(gameTeam.getTeamScavengeList().get(0).getName());
        holder.scavengeItem2Leaderboard.setText(gameTeam.getTeamScavengeList().get(1).getName());
        holder.scavengeItem3Leaderboard.setText(gameTeam.getTeamScavengeList().get(2).getName());
        holder.scavengeItem4Leaderboard.setText(gameTeam.getTeamScavengeList().get(3).getName());
        holder.scavengeItem5Leaderboard.setText(gameTeam.getTeamScavengeList().get(4).getName());
        holder.mTeamName.setText(gameTeam.getName());

        ArrayList<CheckBox> scavengeItemsLeaderboardCheckList = new ArrayList<CheckBox>();
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem1Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem2Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem3Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem4Leaderboard);
        scavengeItemsLeaderboardCheckList.add(holder.scavengeItem5Leaderboard);

        //cycle through each team and check thier own scavenger list if its found and check box as appropriate

            for(int i =0; i < gameTeam.getTeamScavengeList().size(); i++){
                if(gameTeam.getTeamScavengeList().get(i).getFound()){
                    scavengeItemsLeaderboardCheckList.get(i).setChecked(true);
                }

            }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mGameIdTextView;
        TextView mGameStatusTextView;
        TextView mTeamName;
        CheckBox scavengeItem1Leaderboard;
        CheckBox scavengeItem2Leaderboard;
        CheckBox scavengeItem3Leaderboard;
        CheckBox scavengeItem4Leaderboard;
        CheckBox scavengeItem5Leaderboard;

        ViewHolder(View itemView) {
            super(itemView);
            mGameIdTextView = (TextView)itemView.findViewById(R.id.gameIdLeaderboard);
            mGameStatusTextView = (TextView)itemView.findViewById(R.id.gameStatusLeaderboard);
            mTeamName = (TextView)itemView.findViewById(R.id.teamNameLeaderboard);
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

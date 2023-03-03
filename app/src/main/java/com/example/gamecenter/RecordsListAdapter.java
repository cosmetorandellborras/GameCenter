package com.example.gamecenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordsListAdapter extends RecyclerView.Adapter<RecordsListAdapter.RecordViewHolder> {

    private ArrayList<Score> scoreArrayList;


    class RecordViewHolder extends RecyclerView.ViewHolder{
        public final TextView userView;
        public final TextView gameView;
        public final TextView modeView;
        public final TextView scoreView;

        public RecordViewHolder(View itemView){
            super(itemView);
            userView = (TextView) itemView.findViewById(R.id.records_user);
            gameView = (TextView) itemView.findViewById(R.id.records_game);
            modeView = (TextView) itemView.findViewById(R.id.records_mode);
            scoreView = (TextView) itemView.findViewById(R.id.records_score);
        }
    }

    Context mContext;
    private final LayoutInflater mInflater;

    public RecordsListAdapter(Context context,ArrayList<Score> scores){
        this.scoreArrayList = scores;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    /**
     * Put item layout
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.scores_item,parent,false);
        return new RecordViewHolder(itemView);
    }

    /**
     * Put into the recycler view all the items
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position){
        holder.userView.setText(scoreArrayList.get(position).getUser());
        holder.gameView.setText(scoreArrayList.get(position).getGameName());
        holder.modeView.setText(scoreArrayList.get(position).getMode());
        holder.scoreView.setText(String.valueOf(scoreArrayList.get(position).getScore()));
    }

    /**
     * Get the number of scores items
     * @return
     */
    @Override
    public int getItemCount(){
        int size = scoreArrayList.size();
        return size;
    }


}

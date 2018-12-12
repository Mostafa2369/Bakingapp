package com.example.mostafa.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsListAdapter extends RecyclerView.Adapter<DetailsListAdapter.DetailsListAdapterViewHolder>

{
    private String[] dataList;
    private DetailsListAdapterOnClickHandler mClickHandler = null;

    public DetailsListAdapter(DetailsListAdapterOnClickHandler click) {
        mClickHandler = click;
    }

    public interface DetailsListAdapterOnClickHandler {

        void onClick(int hello);
    }

    public class DetailsListAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.step) TextView  mStep;
        public DetailsListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mAdapterPosition = getAdapterPosition();
            mClickHandler.onClick(mAdapterPosition);
        }
    }

    @NonNull
    @Override
    public DetailsListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycle_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new DetailsListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsListAdapterViewHolder holder, int position) {
        String step = dataList[position];

        String[] stepName = step.split("@");
        int p = position + 1;

        holder.mStep.setText(p + ". " + stepName[0]);
    }

    @Override
    public int getItemCount() {

        return dataList.length;
    }

    public void getData(String[] data) {
        dataList = data;


    }


}

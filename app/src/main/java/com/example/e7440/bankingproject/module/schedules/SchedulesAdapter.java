package com.example.e7440.bankingproject.module.schedules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.MyViewHolder> {

    public Context mContext;
    public List<Schedules> mSchedules;
    public List<Schedules> mSchedulesfil = new ArrayList<>();
    public List<Schedules> mSchedulesFilter = new ArrayList<>();
    private OnItemClickListener listener;

    public SchedulesAdapter(Context mContext, List<Schedules> mSchedules) {
        this.mContext = mContext;
        this.mSchedules = mSchedules;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedules, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Schedules schedules = mSchedules.get(position);
        holder.mTextViewName.setText(schedules.getName());
        holder.mTextViewAddress.setText(schedules.getAddress());
        Picasso.with(mContext).load(schedules.getAvatar()).into(holder.mCircleImageView);
//        holder.mConstraintLayout.setOnClickListener(view -> listener.onClickListener(position));
        holder.setOnClick(listener, schedules, position);
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mCircleImageView;
        TextView mTextViewName, mTextViewAddress;
        ConstraintLayout mConstraintLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.iv_item_avatar);
            mTextViewName = (TextView) itemView.findViewById(R.id.tv_item_name);
            mTextViewAddress = (TextView) itemView.findViewById(R.id.tv_item_address);
            mConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item_schedules);
        }

        public void setOnClick(final OnItemClickListener onItemClickListener, final Schedules schedules, final int position) {
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickListener(schedules, position);
                }
            });
        }
    }

    public void getFilter(String text) {
        if (mSchedulesfil.size() <= 0) {
            mSchedulesfil.addAll(mSchedules);
        }
        mSchedulesFilter.clear();
        mSchedulesFilter.addAll(mSchedulesfil);
        mSchedules.clear();
        if (text.isEmpty()) {
            mSchedules.addAll(mSchedulesfil);
            mSchedulesFilter.clear();
        } else {
            text = text.toLowerCase();
            for (Schedules item : mSchedulesFilter) {
                if (item.name.toLowerCase().contains(text) || item.address.toLowerCase().contains(text)) {
                    mSchedules.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClickListener(Schedules schedules, int position);

    }
}

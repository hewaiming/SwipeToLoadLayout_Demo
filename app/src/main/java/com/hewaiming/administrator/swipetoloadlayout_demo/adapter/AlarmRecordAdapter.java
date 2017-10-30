package com.hewaiming.administrator.swipetoloadlayout_demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hewaiming.administrator.swipetoloadlayout_demo.R;
import com.hewaiming.administrator.swipetoloadlayout_demo.bean.AlarmRecord;

import java.util.List;

public class AlarmRecordAdapter extends RecyclerView.Adapter<AlarmRecordAdapter.MyViewHolder> {

    private final List<AlarmRecord> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public AlarmRecordAdapter(List<AlarmRecord> mDatas) {
        this.mDatas = mDatas;
    }

    //item 点击时件接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //item 长按点击时件接口
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    public void append(AlarmRecord m_bean) {
        mDatas.add(m_bean);
        notifyDataSetChanged();
    }

    public void addDate(int position, AlarmRecord m_id) {
        mDatas.add(position, m_id);
        notifyItemInserted(position); //有动画效果
    }

    public void removeDate(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);//有动画效果
    }

    public void setList(List<AlarmRecord> mData) {
        mDatas.clear();
        mDatas.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_cardview, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv_id.setText(mDatas.get(position).getPotNo());
        holder.tv_name.setText(mDatas.get(position).getDDate());
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id;
        TextView tv_name;

        public MyViewHolder(View view) {
            super(view);
            tv_id = (TextView) view.findViewById(R.id.id_num);
            tv_name=(TextView)view.findViewById(R.id.id_name);
        }
    }
}


package com.example.assignment2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> list;
    private List<Task> sublist;

    public OnClickListener onClickListener;

    public interface OnClickListener {
        void onDeleteClick(long i);
        void onItemClick(long i);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout layout;
        private final TextView textView1, textView2;
        private final ImageView btn1;

        public ViewHolder(View view) {
            super(view);

            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            btn1 = view.findViewById(R.id.btn1);
            layout = view.findViewById(R.id.layout);
        }
    }

    public TaskAdapter(List list) {
        this.list = list;
        this.sublist = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Task data = sublist.get(position);
        viewHolder.textView1.setText(data.getTitle());
        viewHolder.textView2.setText(data.getText());
        if (onClickListener != null) {
            viewHolder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onDeleteClick(data.getId());
                }
            });
            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(data.getId());
                }
            });
        }

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }

    public void updateDate(int day, int month, int year) {
        sublist.clear();
        notifyDataSetChanged();
        for (int i = 0; i < list.size(); i++) {
            Task item = list.get(i);
            if (item.getYear()==year&&item.getMonth()==month&&item.getDay()==day) {
                sublist.add(item);
                notifyItemInserted(i);
            }
        }
    }
}

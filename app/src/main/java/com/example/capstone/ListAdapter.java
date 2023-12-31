package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<ListLayout> itemList;
    private ListAdapter.OnItemClickListener listener;

    public ListAdapter(ArrayList<ListLayout> itemList) {
        this.itemList = itemList;
    }

    public interface OnItemClickListener {
        void onItemClick(ListLayout item);
    }

    public void setOnItemClickListener(ListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lenderNotifyText.setText(itemList.get(position).getLenderNotifyText());
        holder.lenderNotifyTitle.setText(itemList.get(position).getLenderNotifyTitle());

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lenderNotifyText;
        TextView lenderNotifyTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            lenderNotifyTitle = itemView.findViewById(R.id.title);
            lenderNotifyText = itemView.findViewById(R.id.body_1);
        }
    }
}
package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter3 extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<ListLayout3> itemList;
    private OnItemClickListener listener;

    public ListAdapter3(ArrayList<ListLayout3> itemList) {
        this.itemList = itemList;
    }

    public interface OnItemClickListener {
        void onItemClick(ListLayout3 item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.lenderNotifyText.setText(itemList.get(position).getMatchResultTitle());
        holder.lenderNotifyTitle.setText(itemList.get(position).getMatchResultText());

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
        TextView getNestInfo;
        TextView getNestInfoTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            getNestInfoTitle = itemView.findViewById(R.id.title);
            getNestInfo = itemView.findViewById(R.id.body_1);
        }
    }
}
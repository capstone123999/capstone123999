package com.example.capstone;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListView extends BaseAdapter
{
    LayoutInflater layoutInflater = null;
    private ArrayList<ListData> listViewData = null;
    private int count = 0;

    public CustomListView(ArrayList<ListData> listData)
    {
        listViewData = listData;
        count = listViewData.size();
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (layoutInflater == null)
            {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = layoutInflater.inflate(R.layout.custom_listview, parent, false);
        }

        //ImageView mainImage = convertView.findViewById(R.id.mainImage);
        TextView title = convertView.findViewById(R.id.title);
        TextView body_1 = convertView.findViewById(R.id.body_1);

        //mainImage.setImageResource(listViewData.get(position).mainImage);
        title.setText(listViewData.get(position).title);
        body_1.setText(listViewData.get(position).body_1);

        return convertView;
    }
    public static class ListData {
        public int mainImage = 0;
        public int opt_1 = 0;
        public int opt_2 = 0;
        public int opt_3 = 0;
        public int opt_4 = 0;
        public int star = 0;

        public String title = "";
        public String body_1 = "";
        public String body_2 = "";
    }
}

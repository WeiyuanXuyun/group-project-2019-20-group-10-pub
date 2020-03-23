package com.example.cs2001_group10;

import android.app.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private static final String TAG = "Adapter" ;

    private ArrayList<String> questions_list;
    private Activity activity;

    RecyclerViewAdapter(ArrayList<String> questions_list, Activity activity) {
        this.activity = activity;
        this.questions_list = questions_list;
        questions_list.add(0, "");
        //Log.d(TAG, "RecyclerViewAdapter: " + questions_list);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer, parent, false);
            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            //Log.d(TAG, "onBindViewHolder: " + position + " Header");
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "You clicked at Header View!", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            //Log.d(TAG, "onBindViewHolder: " + position + " Footer");
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.footerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "You clicked at Footer View", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, admin_add_question.class);
                    activity.startActivity(intent);
                }
            });
        } else if (holder instanceof ItemViewHolder) {
            //Log.d(TAG, "onBindViewHolder: " + position + " Questions");
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.itemText.setText(questions_list.get(position));
            itemViewHolder.itemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "You clicked at item " + position, Toast.LENGTH_SHORT).show();
                    PassValues(questions_list.get(position));
                }
            });
        }
    }

    private void PassValues(String question) {
        Intent intent = new Intent(activity, admin_show_question.class);
        intent.putExtra("question", question);
        activity.startActivity(intent);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == questions_list.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return questions_list.size() + 1;
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        HeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.header_text);
        }
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        FooterViewHolder(View view) {
            super(view);
            footerText = view.findViewById(R.id.footer_text);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.question_id);
        }
    }
}
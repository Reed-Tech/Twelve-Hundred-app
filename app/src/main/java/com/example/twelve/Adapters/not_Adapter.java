package com.example.twelve.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Model.Notifications;
import com.example.twelve.R;

import java.util.List;

public class not_Adapter extends RecyclerView.Adapter<not_Adapter.not_viewholder> {
    private Context ctc;
    private List<Notifications> not_list;

    public not_Adapter(Context ctc, List<Notifications> not_list) {
        this.ctc = ctc;
        this.not_list = not_list;
    }

    @NonNull
    @Override
    public not_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new not_viewholder(LayoutInflater.from(ctc).inflate(R.layout.not_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull not_viewholder holder, int position) {
        holder.not_title.setText(not_list.get(position).getTitle());
        holder.not_message.setText(not_list.get(position).getMessage());
        holder.not_time.setText(not_list.get(position).getTime());
        holder.not_refnumber.setText(not_list.get(position).getRef_number());
    }

    @Override
    public int getItemCount() {
        return not_list.size();
    }

    public class not_viewholder extends RecyclerView.ViewHolder {
        private TextView not_title;
        private TextView not_message;
        private TextView not_time;
        private TextView not_refnumber;
        public not_viewholder(@NonNull View itemView) {
            super(itemView);

            not_title = itemView.findViewById(R.id.not_title);
            not_message = itemView.findViewById(R.id.not_message);
            not_time = itemView.findViewById(R.id.not_time);
            not_refnumber = itemView.findViewById(R.id.ref_id);
        }
    }
}

package com.snapmint.merchantsdk.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snapmint.merchantsdk.R;

import java.util.ArrayList;
import java.util.List;

public class TermsAndConditionsAdapter extends RecyclerView.Adapter<TermsAndConditionsAdapter.ViewHolder> {
    private final List<String> termsList;

    // RecyclerView recyclerView;
    public TermsAndConditionsAdapter(List<String> termsList) {
        this.termsList = termsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_terms_and_condition, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String value = termsList.get(position);
        holder.tvTAndC.setText(value);
    }


    @Override
    public int getItemCount() {
        return termsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTAndC;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTAndC = itemView.findViewById(R.id.tvTAndC);
        }
    }

}

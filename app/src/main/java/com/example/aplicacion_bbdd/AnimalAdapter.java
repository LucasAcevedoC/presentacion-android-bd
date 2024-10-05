package com.example.aplicacion_bbdd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private ArrayList<String> animales;

    public AnimalAdapter(ArrayList<String> animales) {
        this.animales = animales;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        holder.bind(animales.get(position));
    }

    @Override
    public int getItemCount() {
        return animales.size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String animal) {
            textView.setText(animal);
        }
    }
}
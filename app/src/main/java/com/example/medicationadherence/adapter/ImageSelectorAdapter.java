package com.example.medicationadherence.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.medications.wizard.RootWizardFragment;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;

import java.util.ArrayList;

public class ImageSelectorAdapter extends RecyclerView.Adapter {
    private ArrayList<String> URLs;
    private int selected;
    private RootWizardFragment context;
    private SparseBooleanArray selectedItems;
    private ImageClickListener listener;

    public ImageSelectorAdapter(ArrayList<String> URLs, RootWizardViewModel model) {
        this.URLs = URLs;
        context = model.getFragment();
        selectedItems = new SparseBooleanArray();
        selectedItems.put(0,true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new SelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final SelectorViewHolder holderm = (SelectorViewHolder) holder;
        if (URLs.get(position) != null){
            Glide.with(context).load(URLs.get(position)).thumbnail(0.5f).transition(new DrawableTransitionOptions().crossFade()).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holderm.imageItem);
        } else {
            holderm.imageItem.setBackgroundColor(context.getResources().getColor(R.color.colorAccent, null));
            holderm.imageItem.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pill, null));
        }
        if (selectedItems.get(position))
            holderm.check.setVisibility(View.VISIBLE);
        else
            holderm.check.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return URLs == null ? -1 : URLs.size();
    }

    private class SelectorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageItem;
        private ImageView check;

        public SelectorViewHolder(View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.imageItem);
            check = itemView.findViewById(R.id.imageCheck);
            imageItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (selectedItems.get(getAdapterPosition(), false)){
                selectedItems.delete(getAdapterPosition());
                check.setVisibility(View.INVISIBLE);
            } else {
                selectedItems.put(selected, false);
                check.setVisibility(View.VISIBLE);
                selectedItems.put(getAdapterPosition(),true);
            }
            listener.onImageClick(getAdapterPosition());
        }
    }

    public interface ImageClickListener{
        void onImageClick(int position);
    }

    public void setSelected(int position){
        selected = position;
        notifyDataSetChanged();
    }

    public void setListener(ImageClickListener listener){
        this.listener = listener;
    }
}
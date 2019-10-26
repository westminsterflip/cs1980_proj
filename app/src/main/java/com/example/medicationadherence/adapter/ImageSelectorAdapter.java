package com.example.medicationadherence.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ImageSelectorAdapter extends RecyclerView.Adapter {
    ArrayList<String> URLs;

    public ImageSelectorAdapter(ArrayList<String> URLs) {
        this.URLs = URLs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new SelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SelectorViewHolder holderm = (SelectorViewHolder) holder;
        if (URLs.get(position) != null){
            try {
                Bitmap image = new SetImageTask(holderm, position).execute().get();
                if (image != null) {
                    holderm.imageItem.setImageBitmap(image);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        holderm.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(URLs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return URLs == null ? -1 : URLs.size();
    }

    private class SelectorViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageItem;

        public SelectorViewHolder(View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.imageItem);
        }
    }

    private class SetImageTask extends AsyncTask<Void, Void, Bitmap> {
        private SelectorViewHolder holder;
        private int position;

        public SetImageTask(SelectorViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                return BitmapFactory.decodeStream(new URL(URLs.get(position)).openStream());
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}

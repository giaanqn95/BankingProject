package com.example.e7440.bankingproject.module.upload.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.module.model.Image;


import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private List<Image> mImages;
    private Context mContext;
    private OnItemClickListener listener;

    public ImageAdapter(Context mContext, List<Image> mImages) {
        this.mContext = mContext;
        this.mImages = mImages;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Image image = mImages.get(position);
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(image.getImage()),400,400);
        holder.mImageView.setImageBitmap(bitmap);
        holder.mImageView.setOnClickListener(v -> listener.onClickImage(position));
        holder.mImageViewRemove.setOnClickListener(v -> listener.onClickListener(position));
    }

    @Override
    public int getItemCount() {
        return (mImages != null) ? mImages.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView, mImageViewRemove;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_img);
            mImageViewRemove = (ImageView) itemView.findViewById(R.id.item_img_remove);
        }
    }

    public interface OnItemClickListener {
        void onClickListener(int position);

        void onClickImage(int position);
    }
}

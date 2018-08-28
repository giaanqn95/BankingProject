package com.example.e7440.bankingproject.module.model;

import android.support.annotation.NonNull;

import java.io.File;

public class Image implements Comparable<Image> {
    public String Image;

    public int Size;

    public Image(String image, int Size) {
        this.Image = image;
        this.Size = Size;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    @Override
    public int compareTo(@NonNull Image image) {
        if (Size > image.getSize()) {
            return 1;
        } else if (Size < image.getSize()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return this.Image;
    }
}

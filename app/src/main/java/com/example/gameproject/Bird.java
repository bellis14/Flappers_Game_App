package com.example.gameproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class Bird extends BaseObject {
    private ArrayList<Bitmap> arrBms = new ArrayList<>();
    private int count, vFlap, idCurrentBitmap;
    private float drop;

    public Bird() {
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
        this.drop = 0;
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
        for (int i = 0; i< arrBms.size(); i++) {
            this.arrBms.set(i, Bitmap.createScaledBitmap(this.arrBms.get(i), this.width, this.height, true));
        }
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }

    @Override
    public Bitmap getBm() {
        count++;
        if (this.count == this.vFlap) {
            for (int i = 0; i < arrBms.size(); i++) {
                if(i == arrBms.size()-1) {
                    this.idCurrentBitmap = 0;
                    break;
                } else if (this.idCurrentBitmap == i) {
                    idCurrentBitmap = i+1;
                    break;
                }
            }
            count = 0;
        }
        if (this.drop < 0){
            Matrix matrix = new Matrix();
            matrix.postRotate(-Constants.BIRD_ANGLE_UP);
            return Bitmap.createBitmap(
                    arrBms.get(idCurrentBitmap),
                    0,
                    0,
                    arrBms.get(idCurrentBitmap).getWidth(),
                    arrBms.get(idCurrentBitmap).getHeight(),
                    matrix,
                    true
            );
        } else if (drop >= 0){
            Matrix matrix = new Matrix();
            if (drop < 70) {
                matrix.postRotate(-Constants.BIRD_ANGLE_UP+(drop*2));
            } else {
                matrix.postRotate(Constants.BIRD_ANGLE_DOWN);
            }
            return Bitmap.createBitmap(
                    arrBms.get(idCurrentBitmap),
                    0,
                    0,
                    arrBms.get(idCurrentBitmap).getWidth(),
                    arrBms.get(idCurrentBitmap).getHeight(),
                    matrix,
                    true
            );
        }
        return this.arrBms.get(idCurrentBitmap);
    }

    public void draw(Canvas canvas, Boolean falling){
        if (falling) {
            drop();
        }
        canvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    private void drop() {
        this.drop += Constants.BIRD_DROP_RATE;
        this.y += this.drop;
    }
}
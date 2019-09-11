package com.ybkj.videoaccess.mvp.data.bean;

public class DeviceFaceInfo {
    private int X;
    private int Y;
    private int Width;
    private int Height;
    private int FaceQualityScore;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getFaceQualityScore() {
        return FaceQualityScore;
    }

    public void setFaceQualityScore(int faceQualityScore) {
        FaceQualityScore = faceQualityScore;
    }
}

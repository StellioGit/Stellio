package com.example.anchit.stellio;

public class MyBounceInterpolator implements android.view.animation.Interpolator {
    private double mAmplitude = 1;
    private double mFrequency = 10;

    public MyBounceInterpolator(double mAmplitude, double mFrequency) {
        this.mAmplitude = mAmplitude;
        this.mFrequency = mFrequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}

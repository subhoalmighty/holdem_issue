package com.radikal.holdempoker.graphics;

import java.util.HashMap;

/**
 * Created by SubrataMondal on 03/12/17.
 */

public class DistributedRandomNumberGenerator {

    private HashMap<Integer, Double> distribution;
    private double distSum;

    public DistributedRandomNumberGenerator() {
        distribution = new HashMap<Integer, Double>();
    }

    public void addNumber(int value, double distribution) {
        if (this.distribution.get(value) != null) {
            distSum -= this.distribution.get(value);
        }
        this.distribution.put(value, distribution);
        distSum += distribution;
    }

    public int getDistributedRandomNumber() {
        double rand = Math.random();
        double ratio = 1.0f / distSum;
        double tempDist = 0;
        for (Integer i : distribution.keySet()) {
            tempDist += distribution.get(i);
            if (rand / ratio <= tempDist) {
                return i;
            }
        }
        return 0;
    }
}
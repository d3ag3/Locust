package com.caliban.helper;

import java.util.Random;

public class Randomizer {

    private Random random = new Random();

    public int generateRandom(int min, int max) {
        if (max <= min) {
            return min;  // Return min when max is not greater than min
        }
        return random.nextInt(min, max);
    }
}

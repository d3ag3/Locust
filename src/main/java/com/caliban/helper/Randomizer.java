package com.caliban.helper;

import java.util.Random;

public class Randomizer {

    private Random random = new Random();

    public int generateRandom(int min, int max) {
        return random.nextInt(min, max);
    }
}

package com.tetris.logic;

import java.util.Random;

public class RWSelection {
    private final double[] fitness;
    private final Random random;

    public RWSelection(int difficulty) {
        switch (difficulty) {
            case 0:
                this.fitness = new double[]{12, 10, 10, 10, 10, 10, 10};
                break;
            case 1:
                this.fitness = new double[]{10, 10, 10, 10, 10, 10, 10};
                break;
            case 2:
                this.fitness = new double[]{8, 10, 10, 10, 10, 10, 10};
                break;
            case 3:
                this.fitness = new double[]{10, 10, 10, 10, 10};
                break;
            default:
                throw new IllegalArgumentException("Invalid Difficulty: " + difficulty);
        }
        this.random = new Random();
    }

    public int select() {
        int N = fitness.length;
        double wMax = getMaxFitness();

        while (true) {
            int selectedIndex = random.nextInt(N);
            double selectedFitness = fitness[selectedIndex];

            if (random.nextDouble() < selectedFitness / wMax) {
                return selectedIndex;
            }
        }
    }

    private double getMaxFitness() {
        double max = Double.NEGATIVE_INFINITY;
        for (double f : fitness) {
            if (f > max) {
                max = f;
            }
        }
        return max;
    }

}

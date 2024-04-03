package logic;

import jdk.jfr.Description;
import model.*;

import java.util.Random;

public class RWSelection {
    private final double[] fitness;
    private final Random random;

    public RWSelection(int mode) {
        switch (mode) {
            case 0:
                this.fitness = new double[]{12, 10, 10, 10, 10, 10, 10};
                break;
            case 1:
                this.fitness = new double[]{10, 10, 10, 10, 10, 10, 10};
                break;
            case 2:
                this.fitness = new double[]{8, 10, 10, 10, 10, 10, 10};
                break;
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        this.random = new Random();
    }

    public RWSelection() {
        this.fitness = new double[]{0};
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

    public Block selectBlock(int mode) {
        Block[] blocks = switch (mode) {
            case 0 ->
                    new Block[]{new IBlock(), new JBlock(), new LBlock(), new OBlock(), new SBlock(), new TBlock(), new ZBlock()};
            default -> throw new IllegalArgumentException("Invalid mode: " + mode);
        };
        return blocks[select()];
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

    private boolean test(int testCount) {
        int N = fitness.length;
        int[] count = new int[N];
        for (int i = 0; i < testCount; i++) {
            count[select()]++;
        }

        double sumOfFitness = 0;
        for (double f : fitness) {
            sumOfFitness += f;
        }

        for (int i = 0; i < N; i++) {
            double expectedRate = (fitness[i] / sumOfFitness) * 100;
            double actualRate = (double) count[i] / testCount * 100;
            double lowerBound = expectedRate * 0.95;
            double upperBound = expectedRate * 1.05;

            if (actualRate < lowerBound || actualRate > upperBound) {
                return false;
            }
        }
        return true;
    }

    public boolean testAll(int testCount) {
        for (int mode = 0; mode <= 2; mode++) {
            RWSelection rwSelection = new RWSelection(mode);
            if (!rwSelection.test(testCount)) {
                return false; // Return false if any mode fails
            }
        }
        return true; // All tests passed
    }
}

package logic;

import jdk.jfr.Description;
import model.*;

import java.awt.*;
import java.util.ArrayList;
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
            default:
                throw new IllegalArgumentException("Invalid Difficulty: " + difficulty);
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
        for (int difficulty = 0; difficulty <= 2; difficulty++) {
            RWSelection rwSelection = new RWSelection(difficulty);
            if (!rwSelection.test(testCount)) {
                return false; // Return false if any difficulty fails
            }
        }
        return true; // All tests passed
    }

    //select for the block
    public Block selectBlock(boolean isItem, int eraseLineCount){
        int blockType = select();
        Random random = new Random();
        Block basicBlock = switch (blockType) {
            case 0 -> new IBlock();
            case 1 -> new JBlock();
            case 2 -> new LBlock();
            case 3 -> new OBlock();
            case 4 -> new SBlock();
            case 5 -> new TBlock();
            case 6 -> new ZBlock();
            default -> throw new IllegalArgumentException("Invalid block type.");
        };
        // 10줄이 삭제될 때마다 아이템 블록 생성
        if (isItem && eraseLineCount % 10 == 0 && eraseLineCount != 0) {
            // 값이 0보다 큰 인덱스들을 저장할 리스트 생성
            ArrayList<Point> greaterThanZeroIndices = new ArrayList<>();
            for (int y = 0; y < basicBlock.shape.length; y++) {
                for (int x = 0; x < basicBlock.shape[y].length; x++) {
                    if (basicBlock.shape[y][x] > 0) {
                        greaterThanZeroIndices.add(new Point(x, y));
                    }
                }
            }

            // 리스트 내 랜덤 인덱스 선택하여 해당 값 8로 변경
            if (!greaterThanZeroIndices.isEmpty()) {
                Point selectedPoint = greaterThanZeroIndices.get(random.nextInt(greaterThanZeroIndices.size()));
                basicBlock.shape[selectedPoint.y][selectedPoint.x] = 8;
            }

            return basicBlock;
        } else {
            // 일반 블록 선택 로직 (10줄이 삭제되지 않았을 때)
            return basicBlock;
        }
    }
}

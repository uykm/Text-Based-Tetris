package model;

import logic.Block;

import java.awt.*;

public class BombBlock extends Block {
    // 움직여도 되는지
    public BombBlock() {
        super(new int[][] {
                {0, 0, 0, 0},
                {0, 11, 11, 0},
                {11, 11, 11, 11},
                {0, 11, 11, 0},
        }, new Color[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}); // 색상은 예시, 실제 게임에 맞게 조정
    }

    @Override
    public void rotate() {
        // 이 블록은 회전할 수 없습니다.
    }

    @Override
    public void rotateBack() {
        // 이 블록은 회전할 수 없습니다.
    }
}

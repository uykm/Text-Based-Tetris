package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.*;

public class WeightItemBlock extends Block {
    public WeightItemBlock() {
        super(new int[][] {
                {0, 0, 0, 0},
                {0, 9, 9, 0},
                {9, 9, 9, 9},
                {9, 9, 9, 9},
        }, new Color[]{Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY}); // 색상은 예시, 실제 게임에 맞게 조정
    }
}

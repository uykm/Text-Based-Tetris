package com.tetris.logic;

public class ItemBlockController {
    private Board grid;
    private int width;
    private int height;

    public ItemBlockController(Board grid, int width, int height) {
        this.grid = grid;
        this.width = width;
        this.height = height;
    }

    // 아이템 블록 관련 메소드들 (예: 폭탄 블록 처리, 확장 블록 처리 등)
    public void handleItemBlock(Block block, int x, int y) {
        // 각 아이템 블록의 특성에 따라 처리
        switch (block.getType()) {
            case LineEraseItemBlock:
                applyBombItem(x, y);
                break;
            case WeightItemBlock:
                applyWeightItem(x, y, block);
                break;
            case BombItemBlock:
                flowWaterBlock();
                break;
            case ExtensionItemBlock:
                extendBlocks(x, y);
                break;
            default:
                // 물 아이템 블럭 처리
                break;
        }
    }

    // 폭탄 블록 처리
    private void applyBombItem(int x, int y) {
        // 로직 구현
    }

    // 무게 블록 처리
    private void applyWeightItem(int x, int y, Block block) {
        // 로직 구현
    }

    // 물 블록 흐름 처리
    public void flowWaterBlock() {
        // 로직 구현
    }

    // 확장 블록 처리
    private void extendBlocks(int x, int y) {
        // 로직 구현
    }
}


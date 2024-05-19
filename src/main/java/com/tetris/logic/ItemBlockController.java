package com.tetris.logic;

import com.tetris.model.Direction;

public class ItemBlockController {
    public static final int BOMB_BODY = 11;
    public static final int BOMB_EVENT = 13;
    public static final int EXTEND_BLOCK = 14;
    public static final int EXTEND_BLOCK_EVENT = 15;
    private Board grid;
    final private int WIDTH;
    final private int HEIGHT;
    private boolean waterBlockMoved;

    public ItemBlockController(Board grid, int width, int height) {
        this.grid = grid;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.waterBlockMoved = false;
    }

    // 아이템 블록 관련 메소드들 (예: 폭탄 블록 처리, 확장 블록 처리 등)
    public void handleItemBlock(Block block, int x, int y) {
        // 각 아이템 블록의 특성에 따라 처리
        switch (block.getType()) {
            case WaterItemBlock:
                applyWaterItem();
                break;
            case WeightItemBlock:
                applyWeightItem(block, x, y);
                break;
            case BombItemBlock:
                applyBombItem(x, y);
                break;
            case ExtensionItemBlock:
                applyExtensionItem(x, y);
                break;
            case LineEraseItemBlock:
                // 줄 삭제 아이템인 경우
                break;
            default:
                break;
        }
    }

    // ToDo: 폭탄 블록 로직
    private void applyBombItem(int x, int y) {
        // 로직 구현
        // 폭탄 블록 주변 8칸을 지우기 위한 방향 배열
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

        // 상하 좌우로 한 칸씩 더 지우기 위한 방향 배열
        int[] dx2 = {0, 1, 2, 1, 0, -1, -2, -1};
        int[] dy2 = {-2, -1, 0, 1, 2, 1, 0, -1};

        boolean[][] toRemove = new boolean[26][16];
        for (int height = 3; height < 23; height++) {
            for (int width = 3; width < 13; width++) {

                if (grid.getBoard()[height][width] == BOMB_BODY) {  // 폭탄 블록 발견
                    toRemove[height][width] = true;
                    for(int i = 0; i < 8; ++i) {
                        int nx = width + dx[i];
                        int ny = height + dy[i];
                        if(nx >= 3 && nx < 13 && ny >= 3 && ny < 23) {
                            toRemove[ny][nx] = true;
                        }

                        // 상하좌우로 한 칸을 더 탐색하여 블럭 지우기
                        int nx2 = width + dx2[i];
                        int ny2 = height + dy2[i];
                        if(nx2 >= 3 && nx2 < 13 && ny2 >= 3 && ny2 < 23) {
                            toRemove[ny2][nx2] = true;
                        }
                    }
                }
            }
        }

        for (int height = 3; height < 23; height++) {
            for (int width = 3; width < 13; width++) {
                if (toRemove[height][width]) {  // 폭탄 블록 발견
                    grid.getBoard()[height][width] = 13;
                    // eraseOneBlock(width, height);
                }
            }
        }
    }

    // ToDo: 무게 추 블록 로직
    private void applyWeightItem(Block block, int x, int y) {
        // 로직 구현
        int length = block.width();
        y += block.height() - 1;

        // 경계선에 닿은 경우
        if (x <= 2 || x >= WIDTH + 3 || y <= 2 || y >= HEIGHT + 2) {
            block.stopCountToTwo();
            block.limitCountToTwo();
            return;
        }

        for (int i = x; i < x + length; i++) {
            grid.getBoard()[y + 1][i] = 0;
        }

        for (int i = y; i > 3; i--) {
            System.arraycopy(grid.getBoard()[i - 1], x, grid.getBoard()[i], x, length);
        }
        block.moveDown();
        block.canMoveSide = false;
    }

    // ToDo: 물 블록 로직
    public void applyWaterItem() {
        do {
            waterBlockMoved = false;
            for (int height = 3; height < 23; height++) {
                for (int width = 3; width < 13; width++) {
                    if (grid.getBoard()[height][width] == 10) {  // 물 블록 발견
                        // 아래로 흐를 수 있는지 확인
                        if (grid.getBoard()[height + 1][width] == 0) {
                            grid.eraseOneBlock(width, height);
                            grid.placeOneBlock(width, height + 1, 10);
                            waterBlockMoved = true;
                        } else {
                            if (grid.getBoard()[height + 1][width] == 10) {
                                // 옆으로 흐를 수 있는지 확인
                                flowSide(width, height + 1);
                            }
                        }
                    }
                }
            }
        } while (waterBlockMoved);  // 블록이 이동하는 동안 계속 반복
    }

    //물 블록 좌우 흐름 가능 여부
    private boolean canFlowSide(int x, int y, Direction direction) {
        // 현재 위치에서 방향을 따른 옆 칸이 보드 안에 있는지 확인
        if (direction == Direction.LEFT && (x - 1 >= 3)) {
            return grid.getBoard()[y][x - 1] == 0;  // 왼쪽으로 확장 가능한지 확인
        }
        else if (direction == Direction.RIGHT && (x + 1 <= 12)) {
            return grid.getBoard()[y][x + 1] == 0;  // 오른쪽으로 확장 가능한지 확인
        }
        return false;
    }

    //물 블록 좌우 흐름
    private void flowSide(int x, int y) {
        int newX = x;
        while (grid.getBoard()[y][newX + 1] == 10){
            newX++;
        }
        if(canFlowSide(newX, y, Direction.RIGHT)) {
            for(int j=newX; j>=x; j--) {
                grid.eraseOneBlock(j, y);
                grid.placeOneBlock(j + 1, y, 10);
            }
            grid.eraseOneBlock(x, y-1);
            grid.placeOneBlock(x, y, 10);
            waterBlockMoved = true;
            return;
        }
        newX = x;
        while (grid.getBoard()[y][newX - 1] == 10){
            newX--;
        }
        if(canFlowSide(newX, y, Direction.LEFT)) {
            for(int j=newX; j<=x; j++) {
                grid.eraseOneBlock(j, y);
                grid.placeOneBlock(j - 1, y, 10);
            }
            grid.eraseOneBlock(x, y-1);
            grid.placeOneBlock(x, y, 10);
            waterBlockMoved = true;
        }
    }

    // ToDo: 확장 블록 로직
    private void applyExtensionItem(int x, int y) {
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
        boolean[][] toCreate = new boolean[26][16];
        for (int height = 3; height < 23; height++) {
            for (int width = 3; width < 13; width++) {
                if (grid.getBoard()[height][width] == EXTEND_BLOCK) {  // 확장 아이템 블록 발견
                    toCreate[height][width] = true;
                    for(int i = 0; i < 8; ++i) {
                        int nx = width + dx[i];
                        int ny = height + dy[i];
                        if(nx >= 3 && nx < 13 && ny >= 3 && ny < 23) {
                            toCreate[ny][nx] = true;
                        }
                    }
                }
            }
        }

        for (int height = 3; height < 23; height++) {
            for (int width = 3; width < 13; width++) {
                if (toCreate[height][width]
                        && (grid.getBoard()[height][width] == 0 || grid.getBoard()[height][width] == EXTEND_BLOCK)) {  // 확장 아이템 블록 발견
                    grid.getBoard()[height][width] = EXTEND_BLOCK_EVENT;
                    toCreate[height][width] = false;
                }
            }
        }
    }
}


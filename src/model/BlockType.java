package model;

public enum BlockType {
    IBlock,
    JBlock,
    LBlock,
    OBlock,
    SBlock,
    TBlock,
    ZBlock;
    public static BlockType getRandomBlockType() {
        return values()[(int) (Math.random() * values().length)];
    }
    public static BlockType getBlockTypeByIndex(int index) {
        return values()[index];
    }
}

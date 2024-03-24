import game.Blocks.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;
import java.util.Random;

public class Board extends JFrame {
    @Serial
    private static final long serialVersionUID = 2434035659171694595L;
    public static final int EXTEND = 2;
    public static final int EXTENDED_HEIGHT = 26;
    public static final int EXTENDED_WIDTH = 16;
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    final private JTextPane pane;
    private int[][] board;
    private KeyListener playerKeyListener;
    final private SimpleAttributeSet styleSet;
    final private Timer timer;
    private Block curr;
    int x = 6; //Default Position.
    int y = 3;

    int lockCount=0;
    int lockTime=0;
    int placedBlock = 0;
    int eliminatedLine = 0;
    int score = 0;

    private static final int initInterval = 1000;

    public Board() {
        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Board display setting.
        pane = new JTextPane();
        pane.setEditable(false);
        pane.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 5),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        pane.setBorder(border);
        this.getContentPane().add(pane, BorderLayout.CENTER);

        //Document default style.
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "Courier");
        StyleConstants.setBold(styleSet, true);
        StyleConstants.setForeground(styleSet, Color.WHITE);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

        //Set timer for block drops.
        timer = new Timer(initInterval, e -> {
            moveDown();
            drawBoard();
        });

        //Initialize board for the game.
        board = new int[EXTENDED_HEIGHT][EXTENDED_WIDTH];
        initializeBoard();
        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();

        //Create the first block and draw.
        curr = getRandomBlock();
        if(!canMove(x, y, curr)) {
            EndGame();
        }
        placeBlock();
        drawBoard();
        timer.start();
    }

    private void initializeBoard() {
        for(int i=0; i<EXTENDED_HEIGHT; i++) {
            for(int j=0; j<EXTENDED_WIDTH; j++) {
                board[i][j] = -1;
            }
        }
        for(int i=2; i<HEIGHT+4; i++) {
            for(int j=2; j<WIDTH+4; j++) {
                if(i == 2 || i == HEIGHT+3 || j == 2 || j == WIDTH+3) {
                    board[i][j] = 10;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    private void printBoard() {
        for(int i=0; i<EXTENDED_HEIGHT; i++) {
            for(int j=0; j<EXTENDED_WIDTH; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private Block getRandomBlock() {
        Random rnd = new Random(System.currentTimeMillis());
        int block = rnd.nextInt(7);
        return getBlock(block);
    }

    private Block getBlock(int block) {
        return switch (block) {
            case 0 -> new IBlock();
            case 1 -> new JBlock();
            case 2 -> new LBlock();
            case 3 -> new ZBlock();
            case 4 -> new SBlock();
            case 5 -> new TBlock();
            default -> new OBlock();
        };
    }

    private void placeBlock() {
        isGameOver();
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet styles = new SimpleAttributeSet();
        StyleConstants.setForeground(styles, curr.getColor());
        for(int j=0; j<curr.height(); j++) {
            int rows = y+j == 0 ? 0 : y+j-1;
            int offset = rows * (WIDTH+3) + x + 1;
            doc.setCharacterAttributes(offset, curr.width(), styles, true);
            for(int i=0; i<curr.width(); i++) {
                board[y+j][x+i] += curr.getShape(i, j);
            }
        }
    }

    private void eraseCurr() {
        for(int i=0; i<curr.width(); i++) {
            for(int j=0; j<curr.height(); j++) {
                if (board[y+j][x+i] - curr.getShape(i, j) >= 0){
                    board[y+j][x+i] -= curr.getShape(i, j);
                }
            }
        }
    }

    private void eraseLine(int line) {
        for(int i=3; i<WIDTH+3; i++) {
            board[line][i] = 0;
        }
        for(int i=line; i>3; i--) {
            System.arraycopy(board[i - 1], 3, board[i], 3, WIDTH + 3 - 3);
        }
    }

    private void canErase() {
        for(int i=3; i<HEIGHT+3; i++) {
            boolean erase = true;
            for(int j=3; j<WIDTH+3; j++) {
                if(board[i][j] == 0) {
                    erase = false;
                    break;
                }
            }
            if(erase) {
                eraseLine(i);
                addEliminatedLine();
            }
        }
    }

    private void canRotate() {
        eraseCurr();
        curr.rotate();
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(canMove(x+i, y+j, curr)){
                    x += i;
                    y += j;
                    return;
                }
                if(canMove(x-i, y-j, curr)){
                    x -= i;
                    y -= j;
                    return;
                }
            }
        }
        curr.rotateBack();
    }

    private void addPlacedBlock() {
        placedBlock++;
        if(placedBlock % 10 == 0 && timer.getDelay() > 200) {
            timer.setDelay(timer.getDelay() - 50);
            System.out.println(timer.getDelay());
        }
    }

    private void addEliminatedLine() {
        eliminatedLine++;
        System.out.println("Eliminated Line: " + eliminatedLine);
        if(eliminatedLine % 5 == 0 && timer.getDelay() > 200) {
            timer.setDelay(timer.getDelay() - 50);
            System.out.println(timer.getDelay());
        }
    }

    private void calculateScore() {
        score = 50 * eliminatedLine + 10 * placedBlock;
    }


    private boolean canMove(int newX, int newY, Block newBlock) {
        for (int i = 0; i < newBlock.height(); i++) {
            for (int j = 0; j < newBlock.width(); j++) {
                if (newBlock.getShape(j, i) != 0) { // Check if part of the block
                    int boardX = newX + j;
                    int boardY = newY + i;
                    if (boardX < 3 || boardX >= WIDTH + 3 || boardY < 3 || boardY >= HEIGHT + 3) {
                        return false; // Out of bounds
                    }
                    if (board[boardY][boardX] != 0) {
                        return false; // Position already occupied
                    }
                }
            }
        }
        lockCount = 0;
        return true; // No collision detected
    }

    protected void moveDown() {
        eraseCurr();
        if(canMove(x, y+1, curr)) {
            eraseCurr();
            y++;
            placeBlock();
        }
        else {
            lockCount++;
            lockTime++;
            placeBlock();
            if(lockCount > 0 || lockTime >= 3){
                eraseCurr();
                lockCount = 0;
                lockTime = 0;
                placeBlock();
                addPlacedBlock();
                canErase();
                curr = getRandomBlock();
                x = 6;
                y = 2;
                drawBoard();
                if(!canMove(6, 2, curr)) {
                    printBoard();
                    EndGame();
                }

            }
        }
    }

    protected void isGameOver() {
        for(int i=3; i<WIDTH+3; i++) {
            if(board[3][i] != 0) {
                EndGame();
            }
        }
    }

    protected void EndGame() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!");
        reset();
    }

    protected void pushDown() {
        eraseCurr();
        while(canMove(x, y+1, curr)) {
            y++;
        }
        moveDown();
    }

    protected void moveRight() {
        eraseCurr();
        if (canMove(x + 1, y, curr))
            x++;
        placeBlock();
    }

    protected void moveLeft() {
        eraseCurr();
        if (canMove(x - 1, y, curr))
            x--;
        placeBlock();
    }


    public void drawBoard(){
        calculateScore();
        StyledDocument doc = pane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        pane.setStyledDocument(doc);
        pane.setText(""); // Clear existing text

        SimpleAttributeSet whiteAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(whiteAttr, Color.WHITE);

        try {
            for (int i = EXTEND; i < board.length-EXTEND; i++) {
                for (int j = EXTEND; j < board[i].length-EXTEND; j++) {
                    if (board[i][j] >= 10) {
                        doc.insertString(doc.getLength(), "X", whiteAttr);
                        continue;
                    }
                    if (board[i][j] > 0){
                        SimpleAttributeSet Attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(Attr, getBlock(board[i][j]-1).getColor());
                        doc.insertString(doc.getLength(), "O", Attr);
                    } else {
                        doc.insertString(doc.getLength(), " ", whiteAttr);
                    }
                }
                doc.insertString(doc.getLength(), "\n", null); // New line after each row
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        try {
            doc.insertString(doc.getLength(), "\n", null);
            doc.insertString(doc.getLength(), "Score: " + score, whiteAttr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void reset() {

        removeKeyListener(playerKeyListener);

        // Stop the timer to prevent the current game from proceeding.
        timer.stop();

        // Re-initialize the board to its default state.
        board = new int[EXTENDED_HEIGHT][EXTENDED_WIDTH];
        initializeBoard(); // Call the method that sets up the board.

        // Reset game state variables.
        x = 6; // Reset position to default.
        y = 2; // Reset position to default.
        lockCount = 0; // Reset lock count.
        lockTime = 0; // Reset lock time.
        placedBlock = 0; // Reset placed block count.
        eliminatedLine = 0; // Reset eliminated line count.
        score = 0; // Reset score.

        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);

        curr = getRandomBlock(); // Get a new starting block.

        // Redraw the board to reflect the reset state.
        drawBoard();

        // Restart the timer for the new game.
        timer.start();
    }

    public class PlayerKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    moveDown();
                    drawBoard();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight();
                    drawBoard();
                    break;
                case KeyEvent.VK_LEFT:
                    moveLeft();
                    drawBoard();
                    break;
                case KeyEvent.VK_UP:
                    canRotate();
                    drawBoard();
                    break;
                case KeyEvent.VK_SPACE:
                    pushDown();
                    drawBoard();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}

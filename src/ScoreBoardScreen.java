import score.Score;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ScoreBoardScreen extends JFrame {

    public ScoreBoardScreen(List<Score> scores) {
        setTitle("Score Board");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 컬럼 이름
        String[] columnNames = {"Rank", "Name", "Score"};

        // 데이터 변환
        Object[][] data = new Object[scores.size()][3];
        for (int i = 0; i < scores.size(); i++) {
            data[i][0] = i + 1; // 순위는 리스트의 인덱스 + 1
            data[i][1] = scores.get(i).getPlayerName();
            data[i][2] = scores.get(i).getScore();
        }

        // JTable 생성
        JTable table = new JTable(data, columnNames);
        // 선 보이게 설정
        table.setShowGrid(false);
        // 선 색상 설정 (옵션)
        table.setGridColor(Color.GRAY);

        // 모든 셀 내용을 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // JTable을 JScrollPane에 추가
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // JScrollPane을 프레임에 추가
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        // 예시 데이터
        List<Score> scores = List.of(
                new Score("Player01", 5000),
                new Score("Player02", 4500),
                new Score("Player03", 4000),
                new Score("Player04", 3500),
                new Score("Player05", 3000),
                new Score("Player06", 2500),
                new Score("Player07", 2000),
                new Score("Player08", 1500),
                new Score("Player09", 1000),
                new Score("Player10", 500)
        );

        SwingUtilities.invokeLater(() -> new ScoreBoardScreen(scores));
    }
}

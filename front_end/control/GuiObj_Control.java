package front_end.control;

//ライブラリ参照
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

//ファイル参照
import front_end.gui.*;

public class GuiObj_Control {
    private static JPanel centerPanel;
    public static void main(String[] args) {
        // ウィンドウ作成
        JFrame base_frame = new JFrame();
        base_frame.setFont(new Font("Meiryo", Font.PLAIN, 14));
        base_frame.setTitle("株式＿ポートフォリオ分析ツール");

        // ファイルを閉じたらプログラム終了（アプリ終了）
        base_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base_frame.setSize(1000, 700);

        // whindowsの中央に配置
        base_frame.setLocationRelativeTo((null));
        base_frame.setLayout(new BorderLayout());

        // タイトルラベル
        JLabel label_title = new JLabel("株式＿ポートフォリオ分析ツール");
        label_title.setFont(new Font("Meiryo", Font.PLAIN, 24));
        label_title.setForeground(Color.BLACK);
        label_title.setPreferredSize(new Dimension(400, 50));
        label_title.setHorizontalAlignment(JLabel.CENTER);

        JButton TOP_button = new JButton("TOPページ");
        TOP_button.setFont(new Font("Meiryo", Font.PLAIN, 14));
        TOP_button.setPreferredSize(new Dimension(160, 50));
        TOP_button.addActionListener(_ -> desp_top());
        TOP_button.setHorizontalAlignment(JButton.CENTER);

        JButton portfolio_button = new JButton("ポートフォリオ");
        portfolio_button.setFont(new Font("Meiryo", Font.PLAIN, 14));
        portfolio_button.setPreferredSize(new Dimension(160, 50));
        portfolio_button.addActionListener(_ -> desp_portfolio());
        portfolio_button.setHorizontalAlignment(JButton.CENTER);

        JButton analysis = new JButton("分析");
        analysis.setFont(new Font("Meiryo", Font.PLAIN, 14));
        analysis.setPreferredSize(new Dimension(160, 50));
        analysis.addActionListener(_ -> desp_analysis());
        analysis.setHorizontalAlignment(JButton.CENTER);

        JButton config_button = new JButton("設定");
        config_button.setFont(new Font("Meiryo", Font.PLAIN, 14));
        config_button.setPreferredSize(new Dimension(160, 50));
        config_button.addActionListener(_ -> desp_config());
        config_button.setHorizontalAlignment(JButton.CENTER);

        JButton exitbutton = new JButton("終了");
        exitbutton.setFont(new Font("Meiryo", Font.PLAIN, 14));
        exitbutton.setPreferredSize(new Dimension(160, 30));
        exitbutton.addActionListener(_ -> exit_app());
        exitbutton.setHorizontalAlignment(JButton.CENTER);

        // 中央部をページ遷移させる
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        // centerPanelのレイアウトを設定
        base_frame.add(centerPanel, BorderLayout.CENTER);

        // 諸々base_frameに追加
        JPanel panel_item = new JPanel();
        panel_item.add(TOP_button);
        panel_item.add(portfolio_button);
        panel_item.add(analysis);
        panel_item.add(config_button);
        JPanel panel_above = new JPanel(new BorderLayout());

        panel_above.add(panel_item, BorderLayout.SOUTH);
        panel_above.add(label_title, BorderLayout.NORTH);
        JPanel exit = new JPanel();
        exit.add(exitbutton);
        base_frame.add(panel_above, BorderLayout.NORTH);
        base_frame.add(exit, BorderLayout.SOUTH);
        base_frame.add(centerPanel, BorderLayout.CENTER);

        // base_frame表示
        desp_default();
        base_frame.setVisible(true);
    }

    public static void exit_app() {
        System.exit(0);
    }

    public static void switchPanel(JPanel newPanel) {
        centerPanel.removeAll();

        // デバック
        centerPanel.add(newPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    // 初期画面
    public static void desp_default() {
        switchPanel(new Top_page());
    }

    public static void desp_top() {
        switchPanel(new Top_page());
    }

    public static void desp_portfolio() {
        switchPanel(new Portfolio());
    }

    public static void desp_analysis() {
        switchPanel(new Analysis());
    }

    public static void desp_config() {
        switchPanel(new Config());
    }
<<<<<<< HEAD

=======
    public static void requestPortfolioTableUpdate() {
    Component currentView = centerPanel.getComponent(0);
    if (currentView instanceof Portfolio) {
        ((Portfolio) currentView).updatePortfolioTable();
    }
}
>>>>>>> 4a974710a3c5b499bc70b14df9578187d572c918
}

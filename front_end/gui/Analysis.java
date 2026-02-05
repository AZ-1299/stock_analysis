package stock_analysis.front_end.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.applet.Applet;

public class Analysis extends JPanel {
    
    private JScrollPane sp; 

    public Analysis() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 400));

        JPanel headerPanel = new JPanel();
        headerPanel.setFont(new Font("Meiryo", Font.PLAIN, 10));

        JLabel titleLabel = new JLabel("分析グラフ表示");
        titleLabel.setFont(new Font("Meiryo", Font.PLAIN, 18));
        
        JButton reloadButton = new JButton("グラフ更新");
        reloadButton.setFont(new Font("Meiryo", Font.PLAIN, 10));
        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateAnalysisGraph();
            }
        });

        headerPanel.add(titleLabel);
        headerPanel.add(reloadButton);
        
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel graphPanel = createGraphPanel();
        this.sp = new JScrollPane(graphPanel);
        this.add(this.sp, BorderLayout.CENTER);
    }

    private JPanel createGraphPanel() {
        // 画像1つ目
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Path all_graph_Path = Paths.get(
            "back_end", "analysis", "img", "all_graph.png"
        ).toAbsolutePath().normalize();
        
        JLabel all_graph = new JLabel();
        if (all_graph_Path.toFile().exists()) {
            all_graph.setIcon(new ImageIcon(all_graph_Path.toString()));
        } else {
            all_graph.setText("all_graph.png not found");
        }

        Path industry_graph_Path = Paths.get(
            "back_end", "analysis", "img", "industry_graph.png"
        ).toAbsolutePath().normalize();

        //画像2つ目
        JLabel industry_graph = new JLabel();
        if (industry_graph_Path.toFile().exists()) {
            // ImageIcon icon = (ImageIcon) industry_graph.getIcon();
            // Image originalImage = icon.getImage();
            // int originalWidth = originalImage.getWidth(industry_graph);
            // int originalHeight = originalImage.getHeight(industry_graph);
            // int newWidth = (int) (originalWidth * 0.5);
            // int newHeight = (int) (originalHeight * 0.5);
            industry_graph.setIcon(new ImageIcon(industry_graph_Path.toString()));

        } else {
            industry_graph.setText("industry_graph.png not found");
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(all_graph, gbc);

        gbc.gridy = 1;
        panel.add(industry_graph, gbc);

        return panel;
    }

    public void updateAnalysisGraph() {
        this.remove(this.sp);

        JPanel newGraphPanel = createGraphPanel();
        
        this.sp = new JScrollPane(newGraphPanel);
        this.add(this.sp, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();

        System.out.println("分析グラフを再描画しました");
    }
}
package stock_analysis.front_end.gui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;

public class Analysis extends JPanel {
    public Analysis() {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 400));

        JLabel ment_label = new JLabel("分析-メンテナンス中・・・");
        ment_label.setForeground(Color.BLACK);
        ment_label.setHorizontalAlignment(JLabel.CENTER);
        ment_label.setFont(new Font("Meiryo", Font.PLAIN, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(ment_label, gbc);

    }
}

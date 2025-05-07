package stock_analysis.front_end.Gui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class Config extends JPanel{
    public Config(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 400));

        JLabel ment_label = new JLabel("設定-メンテナンス中・・・") ;
        ment_label.setForeground(Color.BLACK);
        ment_label.setHorizontalAlignment(JLabel.CENTER);
        ment_label.setFont(new Font("Meiryo",Font.PLAIN,18));
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(ment_label,gbc);


    }
}

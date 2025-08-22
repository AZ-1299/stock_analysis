package stock_analysis.front_end.gui;

import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class Portfolio extends JPanel {
    public Portfolio() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 400));

        // JLabel ment_label = new JLabel("^ポートフォリオ-メンテナンス中・・・") ;
        // ment_label.setForeground(Color.BLACK);
        // ment_label.setHorizontalAlignment(JLabel.CENTER);
        // ment_label.setFont(new Font("Meiryo",Font.PLAIN,18));

        // this.add(ment_label,BorderLayout.CENTER);

        JLabel file_choice = new JLabel("CSVファイル選択");
        file_choice.setForeground(Color.BLACK);
        file_choice.setFont(new Font("Meiryo", Font.PLAIN, 18));

        JButton file_choice_button = new JButton("選択"); // ボタンにラベルをつける
        file_choice_button.setFont(new Font("Meiryo", Font.PLAIN, 10));
        // file_choice_button.setPreferredSize(new Dimension(100, 20));

        Controller ctrl = new Controller();
        file_choice_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 他のフォルダのメソッド実行
                ctrl.java2python(null);
                System.out.println("Controller.javaにアクセス");
                
            }
            public void desp_portfolio(ActionEvent n){
                
            }
        });

        

        JPanel InputFile_Panel = new JPanel();
        InputFile_Panel.add(file_choice, BorderLayout.WEST);
        InputFile_Panel.add(file_choice_button, BorderLayout.SOUTH);

        this.add(InputFile_Panel, BorderLayout.NORTH);

        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.gridx = 0;
        // gbc.gridy = 0;
        // this.add(InputFile__Panel,gbc);

    }

}

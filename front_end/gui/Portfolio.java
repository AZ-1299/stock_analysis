package front_end.gui;

//ライブラリ参照
import java.awt.Dimension;
import java.awt.event.*;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

//ファイル参照
import front_end.control.Main_Controller;
import front_end.control.Make_Table;;

public class Portfolio extends JPanel {
    public Portfolio() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 400));



        JLabel file_choice = new JLabel("CSVファイル選択");
        file_choice.setForeground(Color.BLACK);
        file_choice.setFont(new Font("Meiryo", Font.PLAIN, 18));

        JButton file_choice_button = new JButton("選択"); // ボタンにラベルをつける
        file_choice_button.setFont(new Font("Meiryo", Font.PLAIN, 10));
        // file_choice_button.setPreferredSize(new Dimension(100, 20));

        Main_Controller ctrl = new Main_Controller();
        file_choice_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.java2python(null);
                System.out.println("Controller.javaにアクセス");
                // ctrl.desp_portfolio();
            }
        });

        JPanel InputFile_Panel = new JPanel();
        InputFile_Panel.add(file_choice, BorderLayout.WEST);
        InputFile_Panel.add(file_choice_button, BorderLayout.SOUTH);

        this.add(InputFile_Panel, BorderLayout.NORTH);

        JTable portfolioTable = Make_Table.createPortfolioTable();
        JScrollPane sp = new JScrollPane(portfolioTable);
        this.add(sp);

    }

}

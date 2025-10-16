package gui;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

public class Page_tmp extends JPanel{
    public void main_page_tmp(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 400));
        
        JLabel ment_label = new JLabel("-メンテナンス中・・・") ;
        ment_label.setForeground(Color.BLACK);
        ment_label.setHorizontalAlignment(JLabel.CENTER);
        ment_label.setFont(new Font("Meiryo",Font.PLAIN,18));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(ment_label,gbc);


    }
}
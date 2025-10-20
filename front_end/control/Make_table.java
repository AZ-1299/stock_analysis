package front_end.control;

//ライブラリ参照
import java.awt.Component;
import java.io.*;
import java.util.List;

import javax.swing.JTable;

//ファイル参照
import front_end.control.Connect_DB;
import front_end.gui.Portfolio;
import front_end.control.Make_Table;
import front_end.control.DataUpdate;

public class Make_Table {
    public static JTable createPortfolioTable() {        
    System.out.println("Portfolio_Table起動");
        List<Connect_DB.PortfolioRow> rawData = Connect_DB.input_db();
        DataUpdate tableModel = new DataUpdate(rawData);
        JTable portfolioTable = new JTable(tableModel);

        System.out.println("JTableを作成");
        return portfolioTable;
    }
}

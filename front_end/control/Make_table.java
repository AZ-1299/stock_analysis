package front_end.control;

//ライブラリ参照
import java.awt.Component;
import java.io.*;
import java.util.List;

//ファイル参照
import front_end.control.Connect_DB;


public class Make_Table {
    public static void Portfolio_Table(List<Connect_DB.PortfolioRow> portfolio_data) {
        System.out.println("Portfolio_Table起動");
        System.out.println(portfolio_data);
    }
}

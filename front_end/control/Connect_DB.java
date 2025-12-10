package stock_analysis.front_end.control;
//ライブラリ参照
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import stock_analysis.front_end.control.Connect_DB.PortfolioRow;

//ファイル参照

//DB接続と変数代入
public class Connect_DB {
public static List<PortfolioRow> input_db() {
        System.out.println("Connect_DB.java : input_db 実行");
        Path RootDir = Paths.get("").toAbsolutePath();
        String string_RootDir = RootDir.toString();
        // System.out.println("RootDir is : " + string_RootDir);
        Path dbPath = RootDir.resolve(Paths.get("back_end","database","user_data","user_database.db")).normalize();
        String url = "jdbc:sqlite:" + dbPath.toString();
        // System.out.println("url is : " + url);

        // DB接続
        String string_dbpath = dbPath.toString();
        System.out.println("dbPath is : " + string_dbpath);
        String sql = "SELECT * FROM user_database";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            System.out.println("DB接続成功: " + dbPath);

            List<PortfolioRow> rows = new ArrayList<>(); 
            while (rs.next()) {
                rows.add(new PortfolioRow(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
            System.out.println(rows);
            //自動で切断される
            System.out.printf("DB切断\n");
            return rows;

        } catch (SQLException e) {
            System.err.println(e);
            return new ArrayList<>();
        }

    }
    public record PortfolioRow(String code, String name, Integer qty, Double unit, String industry, String account, Integer total_value) {
        Object[] toArray() {return new Object[]{code,name,qty,unit,industry,account};
        }
    }

}

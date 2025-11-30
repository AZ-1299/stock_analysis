package stock_analysis.front_end.gui;
//ライブラリ参照
import javax.swing.SwingUtilities;

//ファイル参照
import stock_analysis.front_end.control.GuiObj_Control;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLiteドライバの読み込み成功");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBCドライバが見つかりません。");
            e.printStackTrace();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            GuiObj_Control.main(null);
        });
    }
}

package gui;
import javax.swing.SwingUtilities;

import front_end.control.Common_obj;

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
            Common_obj.main(null);
        });
    }
}

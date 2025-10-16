package front_end.control;

import java.awt.Component;
import java.io.*;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
import java.nio.file.*;
import javax.swing.JFileChooser;

import front_end.control.Controller.PortfolioRow;
import gui.Control_table;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Statement;

public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    static {
        logger.setLevel(Level.FINE);
    }

    // ユーザのポートフォリオ情報が記載されているSCVをコピー
    public void java2python(Component parent) {
        System.out.println("Coll out Java2Python");
        String selfPath = new File("controller.java").getAbsolutePath();
        logger.info("controller.java path is " + selfPath);
        JFileChooser filechooser = new JFileChooser();
        int result = filechooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            try {
                Path input_path = Paths.get(
                        selfPath,
                        "..",
                        "back_end",
                        "input_data");
                logger.info("INFO: input_path is:" + input_path);
                File inputDir = input_path.toFile();
                if (!inputDir.exists()) {
                    inputDir.mkdirs();
                }

                File copiedFile = new File(inputDir, "New_file.csv");
                Files.copy(
                        selectedFile.toPath(),
                        copiedFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                System.out.println("ファイルをコピー成功: " + copiedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ファイルコピー失敗");
            }
        }
        // JavaからPythonを実行：input_portfolio.py
        Path scriptPath = Paths.get(
                selfPath,
                "..",
                "back_end",
                "input_data",
                "input_portfolio.py").normalize();
        logger.info("scriptPath is:" + scriptPath);
        // File go2py_path = currentDir_next.toFile();
        String pythonScriptPath = scriptPath.toAbsolutePath().toString();
        ProcessBuilder go2py = new ProcessBuilder("python", "-u", pythonScriptPath);

        go2py.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        go2py.redirectError(ProcessBuilder.Redirect.INHERIT);
        System.out.println("INFO:実行フォイル" + pythonScriptPath);
        try {
            Process process = go2py.start();
            int exitCode = process.waitFor();
            // desp_portfolio();
            if (exitCode == 0) {
                System.out.println("Python script executed successfully.");
                input_db();
            } else {
                System.out.println("Python script failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pythonが正常に動作しませんでした");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Pythonは正常に動作しました");
            input_db();
        }
    }

    // DBを画面に表示
    public static void input_db() {
        System.out.println("input_DB 実行");
        Path selfPath = Paths.get("").toAbsolutePath();
        String string_seflPath = selfPath.toString();
        System.out.println("string_seflPath is : " + string_seflPath);
        Path dbPath = selfPath.resolve(Paths.get(
                "back_end",
                "database",
                "user_data",
                "user_database.db")).normalize();
        String url = "jdbc:sqlite:" + dbPath.toString();

        // DB接続
        String string_dbpath = dbPath.toString();
        System.out.println("dbPath is : " + string_dbpath);
        String sql = "SELECT コード, 銘柄名, 数量, 取得単価, \"33業種区分\" AS 業種,口座 FROM user_database";

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
                        rs.getString(6)));
            }
            System.out.println(rows);
            
        } catch (SQLException e) {
            System.err.println("DB取得失敗");
            return;
        }
        //自動で切断される
        System.out.printf("DB切断\n");
        Control_table.Portfolio_Table(rows);
    }
    public record PortfolioRow(String code, String name, Integer qty, Double unit, String industry, String account) {
        Object[] toArray() {return new Object[]{code,name,qty,unit,industry,account};
        }
    }

}
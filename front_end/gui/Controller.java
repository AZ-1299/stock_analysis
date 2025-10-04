package front_end.gui;

import java.awt.Component;
import java.io.*;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
import java.nio.file.*;
import javax.swing.JFileChooser;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.Connection;
import java.sql.DriverManager;
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
                desp_portfolio();
            } else {
                System.out.println("Python script failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pythonが正常に動作しませんでした");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Pythonは正常に動作しました");
            desp_portfolio();
        }
    }

    // DBを画面に表示
    public void desp_portfolio() {
        System.out.println("desp_portfolio 実行");
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
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("DB接続成功: " + dbPath);
        } catch (Exception e) {
            System.out.println("DB接続エラー(1): " + e.getMessage());
        } finally {
            try {

                if (conn != null) {
                    conn.close();
                    System.out.println("DB切断");
                }
            } catch (SQLException e) {
                System.out.println("接続エラー(2): " + e.getMessage());
            }
        }
    }
}

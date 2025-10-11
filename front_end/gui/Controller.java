package front_end.gui;

import java.awt.Component;
import java.io.*;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
import java.nio.file.*;
import javax.swing.JFileChooser;

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
                input_DB();
            } else {
                System.out.println("Python script failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pythonが正常に動作しませんでした");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Pythonは正常に動作しました");
            input_DB();
        }
    }

    // DBを画面に表示
    public void input_DB() {
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

        // DB接続とDB情報の受け渡し
        String string_dbpath = dbPath.toString();
        System.out.println("dbPath is : " + string_dbpath);
        public record PortfolioRow(
            String code,
            String name,
            int qty,
            double unitPrice,
            String industry,
            String account
        ){}
        List<PortfolioRow> loadPortfolio() throws SQLException{
            String sql = "SELECT コード, 銘柄名, 数量, 取得単価, \"33業種区分\" AS 業種,口座 FROM user_database";
            List<PortfolioRow> list = new ArrayList<>();

            try (Connection conn = DriverManager.getConnection(url);
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                System.out.println("DB接続成功: " + dbPath);
                while (rs.next()) {
                    PortfolioRow row = new PortfolioRow(
                            rs.getString("コード"),
                            rs.getString("銘柄名"),
                            rs.getInt("数量"),
                            rs.getDouble("取得単価"),
                            rs.getString("業種"),
                            rs.getString("口座"));
                    list.add(row);
                }

            }
            System.out.printf("DB切断\n");
            }
        }
}

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

        // DB接続
        String string_dbpath = dbPath.toString();
        System.out.println("dbPath is : " + string_dbpath);
        // Connection conn = null;
        String sql = "SELECT コード, 銘柄名, 数量, 取得単価, \"33業種区分\" AS 業種,口座 FROM user_database";
        

        List<String> code = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<Integer> qty = new ArrayList<>();
        List<Double> unit = new ArrayList<>();
        List<String> industry = new ArrayList<>();
        List<String> account = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("DB接続成功: " + dbPath);

            while (rs.next()) {
                code.add(rs.getString("コード"));
                name.add(rs.getString("銘柄名"));
                qty.add(rs.getInt("数量"));
                unit.add(rs.getDouble("取得単価"));
                industry.add(rs.getString("業種"));
                account.add(rs.getString("口座"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        String[] codeArr = code.toArray(new String[0]);
        String[] nameArr = name.toArray(new String[0]);
        int[] qtyArr = qty.stream().mapToInt(Integer::intValue).toArray();
        double[] unitArr = unit.stream().mapToDouble(Double::doubleValue).toArray();
        String[] indArr = industry.toArray(new String[0]);
        String[] acctArr = account.toArray(new String[0]);

        System.out.printf("レコード数は%dです%n", codeArr.length);
        System.out.printf("|コード|銘柄名  |数 量| 取得単価 |業     種|口座区分|\n");
        for (int i = 0; i < codeArr.length; i++) {
            System.out.printf("| %s | %4s | %2d | %.2f | %5s | %s |%n",
                    codeArr[i], nameArr[i], qtyArr[i], unitArr[i], indArr[i], acctArr[i]);
        }
        System.out.printf("DB切断\n");

    }
}

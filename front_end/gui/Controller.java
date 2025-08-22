package stock_analysis.front_end.gui;

import java.awt.Component;
import java.io.*;
import java.nio.file.*;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    static {
        logger.setLevel(Level.FINE);
    }

    public void java2python(Component parent) {
        System.out.println("Coll out Java2Python");
        String selfPath = new File("controller.java").getAbsolutePath();
        logger.info("controller.java path is " + selfPath);
        JFileChooser filechooser = new JFileChooser();
        int result = filechooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            try {
                // アプリの実行ディレクトリを取得
                Path input_path = Paths.get(selfPath, "..", "back_end", "input_data");
                logger.info("INFO: input_path is:" + input_path);
                File inputDir = input_path.toFile();
                if (!inputDir.exists()) {
                    inputDir.mkdirs(); // フォルダがなければ作成
                }

                File copiedFile = new File(inputDir, "New_file.csv");
                Files.copy(
                        selectedFile.toPath(),
                        copiedFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                System.out.println("ファイルをコピーしました: " + copiedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ファイルコピーに失敗しました");
            }
        }
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
        // Pythonコードの標準出力をJavaの標準出力に出す
        go2py.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        go2py.redirectError(ProcessBuilder.Redirect.INHERIT);
        System.out.println("INFO:実行フォイル" + pythonScriptPath);
        try {
            Process process = go2py.start();
            int exitCode = process.waitFor();
            desp_portfolio();
            if (exitCode == 0) {
                System.out.println("Python script executed successfully.");
                // desp_portfolio();
            } else {
                System.out.println("Python script failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Python実行時に異常が発生しました");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Pythonは正常に動作しました");
            desp_portfolio();
        }

    }
    public void desp_portfolio(){
        System.out.println("desp_portfolio 実行");
        String selfPath = new File("controller.java").getAbsolutePath();
        Path desp_Path = Paths.get(
            selfPath,
            "..",
            "back_end"
        );
        System.out.println(desp_Path);
        try{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:"+desp_Path);
        System.out.println("DB接続成功");
        
        }catch(Exception e){
            System.out.println("DB接続エラー:" + e);
        }
    }
}

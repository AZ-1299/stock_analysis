package front_end.control;

//ライブラリ参照
import java.awt.Component;
import java.io.*;
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
import java.sql.Statement;


//ファイル参照
import front_end.control.Main_Controller;
import front_end.control.Make_Table;


public class Main_Controller {

    private static final Logger logger = Logger.getLogger(Main_Controller.class.getName());
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
                Connect_DB.input_db();
            } else {
                System.out.println("Python script failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pythonが正常に動作しませんでした");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Pythonは正常に動作しました");
            Connect_DB.input_db();
        }
    }

    
}
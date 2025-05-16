package stock_analysis.front_end.gui;

import java.awt.Component;
import java.io.*;
import java.nio.file.*;
import javax.swing.JFileChooser;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Controller  {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    static {
        logger.setLevel(Level.FINE);
    }


    public void java2python(Component parent) {
        String selfPath = new File("controller.java").getAbsolutePath();
        logger.info("controller.java path is " + selfPath);
        JFileChooser filechooser = new JFileChooser();
        int result = filechooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            try {
                // アプリの実行ディレクトリを取得
                Path input_path = Paths.get(selfPath,"..","..","back_end","input_data");
                                            // .getParent()
                                            // .resolve("..")
                                            // .resolve("..")
                                            // .resolve("back_end")
                                            // .resolve("input_data")
                                            // .normalize();
                File inputDir  = input_path.toFile();
                if (!inputDir .exists()) {
                    inputDir .mkdirs(); // フォルダがなければ作成
                }
    
                File copiedFile = new File(inputDir, "New_file.csv");
                Files.copy(
                    selectedFile.toPath(),
                    copiedFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println("ファイルをコピーしました: " + copiedFile.getAbsolutePath());
            }   
            catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ファイルコピーに失敗しました");
                }
        }
                Path scriptPath = Paths.get(selfPath)
                       .getParent() // controller.java の親ディレクトリ
                       .resolve("..") // 一つ上へ
                       .resolve("..")
                       .resolve("back_end")
                       .resolve("input_data")
                       .resolve("input_portfolio.py")
                       .normalize(); // 相対パスを正規化
                //File go2py_path = currentDir_next.toFile();
                String pythonScriptPath = scriptPath.toAbsolutePath().toString();
                ProcessBuilder go2py = new ProcessBuilder("python", pythonScriptPath);                
                // Pythonコードの標準出力をJavaの標準出力に出す
                go2py.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                go2py.redirectError(ProcessBuilder.Redirect.INHERIT);
                System.out.println("INFO:実行フォイル"+pythonScriptPath);
                try {
                    // pythonスクリプトを実行する
                    Process process = go2py.start();
                    // 実行完了までJava側のコードを待機させ、終了コードを出す
                    int exitCode = process.waitFor();
                    // 正常終了したか、終了コードで判定
                    if (exitCode == 0) {
                        System.out.println("Python script executed successfully.");
                    } else {
                        System.out.println("Python script failed with exit code: " + exitCode);
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Python実行時に異常が発生しました");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Pythonは正常に動作しました");
                    }
                    
            }
}

package stock_analysis.front_end.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.logging.Logger;


public class TOP extends JPanel{
    JPasswordField account_pw = new JPasswordField(30);
    JTextField account_id = new JTextField(30);
    Logger logger_store_id_pw = Logger.getLogger("Input_id_pw");
        
    
    public TOP(){

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 400));


        // JLabel ment_label = new JLabel("メンテナンス中・・・") ;
        // ment_label.setForeground(Color.BLACK);
        // ment_label.setHorizontalAlignment(JLabel.CENTER);
        // ment_label.setFont(new Font("Meiryo",Font.PLAIN,18));
        
        // this.add(ment_label,BorderLayout.CENTER);

        // 証券口座ID
        JLabel account_id_label = new JLabel("証券口座ID：");
        account_id_label.setForeground(Color.BLACK);
        account_id_label.setFont(new Font("Meiryo",Font.PLAIN,18));
        account_id.setPreferredSize(new Dimension(200, 30));
        JPanel id_set = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        id_set.add(account_id_label);
        id_set.add(account_id);
        
        // パスワード
        JLabel account_pw_label = new JLabel("パスワード：");
        account_pw_label.setForeground(Color.BLACK);
        account_pw_label.setFont(new Font("Meiryo",Font.PLAIN,18));
        account_pw.setPreferredSize(new Dimension(200, 30));

        //IDとパスワード統合
        JPanel pw_set = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pw_set.add(account_pw_label);
        pw_set.add(account_pw);

        //ログインボタン
        JButton login_button = new JButton();
        login_button.setText("ログイン");
        login_button.addActionListener(this::input_login_id_pw);
        

        // 縦並び
        JPanel login_id_pw = new JPanel();
        login_id_pw.setLayout(new BoxLayout(login_id_pw, BoxLayout.Y_AXIS));
        login_id_pw.add(id_set);
        login_id_pw.add(pw_set);
        login_id_pw.add(login_button);

        // ここで中央に置く！
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty =1.0;

        this.add(login_id_pw, gbc);

        }
        public void input_login_id_pw(ActionEvent e) {
            String id_text = account_id.getText();
            String pw_text = new String(account_pw.getPassword());
        
            try {
                // このクラスファイルの場所からプロジェクトルートを推測
                String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                File rootDir = new File(path)
                                    .getParentFile()  // 1階層上
                                    .getParentFile()  // 2階層上
                                    .getParentFile()  // 3階層上
                                    .getParentFile(); // 4階層上
                File credentialsDir = new File(rootDir, "back_end/Input_api");
        
                if (!credentialsDir.exists()) {
                    credentialsDir.mkdirs();
                }
                File credentialsFile = new File(credentialsDir, "credentials.txt");
        
                System.out.println("書き込もうとしているファイルパス：" + credentialsFile.getAbsolutePath());
        
                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(credentialsFile)))) {
                    writer.println(id_text);
                    writer.println(pw_text);
                }
        
                logger_store_id_pw.info("INFO:Input ID and PW");
        
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        public void callPythonScript() {
            try {
        // 実行するPythonコマンド
                ProcessBuilder pb = new ProcessBuilder(
            "python", 
                "stock_analysis/back_end/Input_api/main.py"  // ここは実際のmain.pyのフルパス or 相対パス
        );

                // カレントディレクトリを指定（必要なら）
                pb.directory(new File("stock_analysis/back_end/Input_api/main.py"));

                // 標準出力・標準エラーを同時に出力
                pb.redirectErrorStream(true);

                Process process = pb.start();

                // Pythonスクリプトの標準出力を読み取る
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                System.out.println(line);  // ここでPython側のログが見える
            }

            int exitCode = process.waitFor();
            System.out.println("Pythonスクリプト終了コード：" + exitCode);

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}

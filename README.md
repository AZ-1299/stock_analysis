# 個別株ポートフォリオ分析ツール

## 概要

日本の個人投資家向けに開発中の「個別株ポートフォリオ分析ツール」です。

## 主な機能（実装済み）

+ CSV読込：ユーザーが指定したCSVファイルから銘柄情報を抽出

+ ポートフォリオを表として表示

+ 東証データ抽出が発行している各銘柄の業種を抽出
## 今後の開発予定（未実装）

- **構成比表示**：業種別・銘柄別の保有比率を円グラフ・棒グラフで可視化  

- **損益・配当トレンド**：損益推移と配当実績を時系列グラフで表示  

- **指標取得**：PER／PBR／EPSを外部APIまたはローカルDBから自動取得  

- **配当予測**：想定年間配当額および配当利回りを算出  

- **株価チャート**：株価推移をMatplotlibで描画  

## システム概要
本システムの概要図を下記に示す。
図を挿入


## 動作環境・前提条件

- **Java 8以上（JDKがインストールされていること）**

* **Python 3.8以上**

* **Pythonライブラリ**：pandas, matplotlib, sqlite3

+ **バージョン管理**：Git

## インストール手順

1. **リポジトリをクローン**

```bash
git clone https://github.com/AZ-1299/stock_analysis.git
cd stock_analysis
```

2. **Python仮想環境の作成と有効化**

```bash
python3 -m venv venv
source venv/bin/activate    # Linux/Mac
# Windows (PowerShell): .\\venv\\Scripts\\Activate.ps1
```

3. **依存パッケージのインストール**

```bash
pip install -r requirements.txt
```

4. **Java プロジェクトをビルド**

```bash
cd front_end
# Unix系シェル
javac -d bin src/**/*.java
# Windows PowerShell
Get-ChildItem -Recurse -Filter "*.java" | %{ javac -d bin $_.FullName }
cd ..
```

## 実行方法

1. **リポジトリのルートへ移動**

```bash
cd stock_analysis
```

2. **Java GUI を起動**

```bash
# Linux/Mac
java -cp front_end/bin stock_analysis.front_end.Gui.Main
# Windows
java -cp front_end\\bin stock_analysis.front_end.Gui.Main
```

3. **CSVを選択**
- GUI上の「CSV読込」ボタンをクリックすると、Pythonスクリプトがバックエンドで実行されます。

4. **結果の確認**

- 分析結果はGUI上に表示。

+ グラフは output/ フォルダにPNG形式で保存されます。

## ライセンス

All Rights Reserved
本リポジトリのコード・ドキュメントは無断転載・再配布を禁じます。


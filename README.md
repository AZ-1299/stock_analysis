# 個別株ポートフォリオ分析ツール

## 概要

日本の個人投資家向けに開発中の「個別株ポートフォリオ分析ツール」です。

## 主な機能（実装済み）

+ CSV読込：ユーザーが指定したCSVファイルから銘柄情報を抽出

+ ポートフォリオを表示：ポートフォリオを表として表示

+ 東証データ抽出が発行している各銘柄の業種を抽出

+ 業種別・銘柄別の保有比率を円グラフ・棒グラフで可視化 

+ 現在の株価を取得APIを用いて現在の株価を取得し、評価額を算出

<!-- ## 今後の開発予定（未実装）

- **現在の株価を取得**APIを用いて現在の株価を取得し、評価額を算出。

- **配当金の予測**：過去の配当実績から自身の配当金額・利回りを算出

- **指標取得**：PER／PBR／EPSを外部APIまたはローカルDBから自動取得   -->

<!-- - **配当予測**：想定年間配当額および配当利回りを算出   -->

<!-- - **株価チャート**：株価推移をMatplotlibで描画   -->

<!-- ## システム概要
本システムの概要図を下記に示す。
図を挿入
 -->

# 動作環境・前提条件
- **OS:Windows10/11**

- **Java 8以上（JDKがインストールされていること）**

* **Python 3.8以上**

* **Pythonライブラリ**：
```bash
# 基本データ解析・株価取得
pip install pandas yfinance

# グラフ描画関連
pip install matplotlib japanize-matplotlib

# ビルド・環境管理用
pip install setuptools
```

# ディレクトリ構成
```Plaintext
stock_analysis/
├── back_end/
│   ├── api/
│   ├── database/
│   └── input_data/
├── front_end/
│   ├── bin/
│   ├── control/
│   └── gui/
├── lib/
│   └── sqlite-jdbc.jar
└── run.bat (起動用スクリプト)
```
# 導入・環境構築
## 1. リポジトリのクローン
任意のディレクトリで以下のコマンドを実行し、ソースコードをローカルに取得する。
```Bash
git clone https://github.com/AZ-1299/stock_analysis.git
cd stock_analysis
```

## 2. Python/仮想環境のセットアップ
データ解析および株価取得に必要なライブラリをインストールする。
```Bash
python.exe -m pip install --upgrade pip
. .\venv\Scripts\activate
python .venv/bin
pip install pandas
pip install pathlib
pip install setuptools
pip install matplotlib
pip install yfinance
pip install japanize-matplotlib
```
## 3.Java環境の確認
+ JDK17以上がインストールされていることを確認する

+ ```lib/sqlite-jdbc.jar```が存在することを確認すること（JDBCドライバがSQLiteとの接続に必要である）

## 4.実行
プロジェクトルートにある run.bat をダブルクリック、またはターミナルから実行する。

```
.\run.bat
```

## ライセンス

All Rights Reserved
本リポジトリのコード・ドキュメントは無断転載・再配布を禁じます。


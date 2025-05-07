# 個別株ポートフォリオ分析ツール



## 概要

本リポジトリは、日本の個人投資家向けに開発中の「個別株ポートフォリオ分析ツール」です。
Java Swing を用いた gui と、Python（pandas）＋SQLite を組み合わせた分析エンジンで構成されています。

## 主な機能

1.CSV 読込：東証データやユーザー指定 CSV から銘柄情報を抽出

2.構成比表示：業種別・銘柄別の保有比率をグラフで可視化

3.損益・配当トレンド：損益推移と配当実績を時系列で表示

4.指標取得：PER/PBR/EPS を外部 API またはローカル DB から自動取得

5.配当予測：想定年間配当額と配当利回りを算出

6.株価チャート：Matplotlib による銘柄別株価推移グラフ生成

## システム構成

```
[CSV入力] → [Java Swing gui]
                   ↘
                 [Python 分析エンジン] → [SQLite DB]
                     ↘
              [グラフ描画 (Matplotlib)]
```

## 動作環境・前提条件

- Windows11

* Java 17 以上

* Python 3.11 以上

* pandas, matplotlib, sqlite3

+ Git

## インストール手順


1. リポジトリをクローン

```bash
git clone https://github.com/AZ-1299/stock_analysis.git
```

2. Python 仮想環境を作成・有効化

```bash
python3 -m venv venv
source venv/bin/activate
```

3. 依存パッケージをインストール

```bash
pip install -r requirements.txt
```

4. Java プロジェクトをビルド

```bash
cd stock_analysis
javac -encoding UTF-8 -d bin front_end/gui/*.java
```

## 実行方法

1. ルートディレクトリで実行

```bash
cd stock_analysis
```

2. Java Guiを起動

```bash
java -cp front_end/gui/Common_obj.java
```

1. Java gui から CSV を選択すると、Python スクリプトがバックエンドで実行されます

2. 分析結果は gui 上に表示され、グラフは output/ フォルダに保存されます

## 今後の開発予定

- 株価 API 連携／自動更新機能

* Web アプリ化（Flask + REST API）

+ 機械学習によるレコメンデーション

## ライセンス

All Rights Reserved
本リポジトリのコード・ドキュメントは無断転載・再配布を禁じます。

## 提出用備考

- 本リポジトリはインターン応募用に限定公開しています。アクセス権限が必要です。

+ ご質問・ご要望はIssue機能からお問い合わせください。


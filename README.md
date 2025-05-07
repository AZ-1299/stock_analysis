# 個別株ポートフォリオ分析ツール



## 概要

本リポジトリは、日本の個人投資家向けに開発中の「個別株ポートフォリオ分析ツール」です。
Java Swing を用いた GUI と、Python（pandas）＋SQLite を組み合わせた分析エンジンで構成されています。

## 主な機能

1.CSV 読込：東証データやユーザー指定 CSV から銘柄情報を抽出

2.構成比表示：業種別・銘柄別の保有比率をグラフで可視化

3.損益・配当トレンド：損益推移と配当実績を時系列で表示

4.指標取得：PER/PBR/EPS を外部 API またはローカル DB から自動取得

5.配当予測：想定年間配当額と配当利回りを算出

6.株価チャート：Matplotlib による銘柄別株価推移グラフ生成

## システム構成

```

[CSV入力] → [Java Swing GUI]
                   ↘
                 [Python 分析エンジン] → [SQLite DB]
                     ↘
              [グラフ描画 (Matplotlib)]

```

## 動作環境・前提条件

- Java 8 以上

* Python 3.8 以上

* pandas, matplotlib, sqlite3

+ Git

## インストール手順

```

#１．リポジトリをクローン

git clone https://github.com/AZ-1299/stock_analysis.git

#２．Python 仮想環境を作成・有効化

python3 -m venv venv
source venv/bin/activate

#３．依存パッケージをインストール

pip install -r requirements.txt

#４．Java プロジェクトをビルド

cd front_end
javac -d bin src/**/*.java

```

## 実行方法

```

# ルートディレクトリで実行
cd stock_analysis
# Java GUIを起動
java -cp front_end/bin stock_analysis.front_end.Gui.Main
```

1. Java GUI から CSV を選択すると、Python スクリプトがバックエンドで実行されます

2. 分析結果は GUI 上に表示され、グラフは output/ フォルダに保存されます

## 今後の開発予定

- 株価 API 連携／自動更新機能

* Web アプリ化（Flask + REST API）

+ 機械学習によるレコメンデーション

## ライセンス

All Rights Reserved本リポジトリのコード・ドキュメントは無断転載・再配布を禁じます。

## 提出用備考

本リポジトリはインターン応募用に限定公開しています。閲覧には権限が必要です。

ご質問・ご要望があれば README の Issues までお問い合わせください。


import os
import pandas as pd
import logging
import unicodedata
import csv

logging.basicConfig(level=logging.INFO)
print("input_portfolio 開始")
# ——— 全角⇔半角などの揺れを吸収 ———
def normalize(text):
    return unicodedata.normalize("NFKC", str(text))

# ——— パス設定 ———
self_path = os.path.dirname(os.path.abspath(__file__))
csv_path = os.path.join(self_path, "New_file.csv")

# ——— カラム名（10列） ———
koumoku = [
    "コード", "銘柄名",
     "買付日", "数量", "取得単価", "現在値",
     "前日比", "前日比（％）", "損益", "損益（％）", "評価額"
]

# ——— CSVを行ごとに読込 ———
with open(csv_path, "r", encoding="cp932") as f:
    reader = csv.reader(f)
    lines = [row for row in reader if row]

# ——— raw 行を DataFrame にしてロギング用に保持 ———
raw_lines = [" ".join(row) for row in lines]
df_all = pd.DataFrame({"raw": raw_lines})
df_all["raw"] = df_all["raw"].map(normalize)
logging.info("ファイル読み込み成功")

# ——— セクション抽出関数 ———
def extract_section(df, keyword, output_filename):
    keyword = normalize(keyword)

    # セクション見出し行を探す
    section_start = df[df["raw"].str.contains(keyword, na=False)]
    if section_start.empty:
        print(f"「{keyword}」を含むデータは存在しません。")
        return

    start_idx = section_start.index[0]
    logging.info(f"「{keyword}」開始行: {start_idx}")

    # 直後から「銘柄（コード）」行を探す
    code_line_idx = None
    for i in range(start_idx + 1, min(start_idx + 15, len(df))):
        if normalize("銘柄（コード）") in df.loc[i, "raw"]:
            code_line_idx = i
            break

    if code_line_idx is None:
        print(f"「銘柄（コード）」が {keyword} セクション内に見つかりませんでした。")
        return

    logging.info(f"「銘柄（コード）」の行番号: {code_line_idx}")

    # ——— 実データ行を抽出（次のセクションで break） ———
    data_lines = lines[code_line_idx + 1:]
    records = []
    for row in data_lines:
        first_col = normalize(row[0])
        if "投資信託" in first_col or first_col in ("ファンド名", "総合計"):
            break

        # 10列 or 11列末尾空文字 の行だけ処理
        if len(row) == 10 or (len(row) == 11 and row[-1] == ""):
            # スペースがない行はスキップ
            if " " not in row[0]:
                continue

            # 「コード」「銘柄名」に分割
            code, name = row[0].split(" ", 1)
            # 11列なら末尾空文字カラムを削除して10列に整形
            trimmed = row[:-1] if len(row) == 11 else row
            # 新しい行を作成：コード, 銘柄名, 2列目以降
            new_row = [code, name] + trimmed[1:]
            records.append(new_row)
        
    if not records:
        print(f"{keyword} の個別株データが見つかりませんでした。")
        return

    df_data = pd.DataFrame(records, columns=koumoku)

    # ——— CSV出力 ———
    output_path = os.path.join(self_path,output_filename)
    df_data.to_csv(output_path, index=False, encoding="utf-8-sig")
    logging.info(f"{keyword} 出力：{output_path}")

# ——— 各セクションを個別株として抽出 ———
extract_section(df_all, "成長投資枠", "user_portfolio_special.csv")
extract_section(df_all, "つみたて投資枠", "user_portfolio_accumulate.csv")
extract_section(df_all, "特定預り","user_portfolio_spot.csv")

logging.info("株式データの抽出完了")
print("input_portfolio 終了")

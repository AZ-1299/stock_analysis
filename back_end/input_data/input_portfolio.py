import os
import pandas as pd
import unicodedata
import csv
import sys
import re

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from database import control

#全角/半角などの揺れを吸収
def normalize(text):
    return unicodedata.normalize("NFKC", str(text))

#パス設定
self_path = os.path.dirname(os.path.abspath(__file__))
csv_path = os.path.join(self_path, "New_file.csv")
back_end_path = os.path.dirname(self_path)
# print("back_end_path is : "+ back_end_path)

#カラム名
koumoku = [
    "コード", "銘柄名",
     "買付日", "数量", "取得単価", "現在値",
     "前日比", "前日比（％）", "損益", "損益（％）", "評価額"
]

#  CSVを行ごとに読込
with open(csv_path, "r", encoding="cp932") as f:
    reader = csv.reader(f)
    lines = [row for row in reader if row]

#  raw 行をDFにしてログ用に保持
raw_lines = [" ".join(row) for row in lines]
df_all = pd.DataFrame({"raw": raw_lines})
df_all["raw"] = df_all["raw"].map(normalize)
print("ファイル読み込み成功")

def extract_section(df, keyword, end_owed_1, end_owed_2, output_filename):
    keyword = normalize(keyword)
    df["raw"] = df["raw"].astype(str)
    section_start = df[df["raw"].str.contains(keyword, na=False)]
    if section_start.empty:
        output_path = os.path.join(self_path, output_filename)
        drop_csv(self_path, output_filename)
        print(f"「{keyword}」を含むデータは存在しません。")
        return
    start_idx = section_start.index[0]
    print(f"「{keyword}」開始行: {start_idx}")

    code_line_idx = None
    for i in range(start_idx + 1, min(start_idx + 15, len(df))):
        if re.search(r"銘柄.*コード", df.loc[i, "raw"]):
            code_line_idx = i
            break
    if code_line_idx is None:
        print(f"「銘柄（コード）」が {keyword} セクション内に見つかりませんでした。")
        return

    # セクション終端を検出
    pattern = f"{normalize(end_owed_1)}|{normalize(end_owed_2)}"
    section_after = df.iloc[code_line_idx+1:]
    section_end = section_after[section_after["raw"].str.contains(pattern, na=False, regex=True)]
    end_idx = section_end.index[0] if not section_end.empty else len(df)
    print(f"終了キーワード検出行: {end_idx}")

    # 実データ抽出
    data_lines = lines[code_line_idx + 1:end_idx]
    records = []
    for row in data_lines:
        normalized_row = [normalize(cell) for cell in row]
        first_col = normalized_row[0]

        if "投資信託" in first_col or first_col in ("ファンド名", "総合計"):
            break

        if len(normalized_row) in [10, 11]:
            if " " not in first_col:
                print(f"スキップ: コードと銘柄名の分離不可: {first_col}")
                continue

            code, name = first_col.split(" ", 1)
            trimmed = normalized_row[:-1] if len(normalized_row) == 11 else normalized_row
            new_row = [code, name] + trimmed[1:]
            records.append(new_row)

    if not records:
        print(f"{keyword} の個別株データが見つかりませんでした。")
        return

    df_data = pd.DataFrame(records, columns=koumoku)
    output_path = os.path.join(self_path,output_filename)
    df_data.to_csv(output_path, index=False, encoding="utf-8-sig")
    print(f"{keyword} 出力：{output_path}")

def drop_csv(file_dir,file_name):
    print("drop csv 実行")
    file_path = os.path.join(file_dir,file_name)
    with open(file_path, 'w', newline='') as f:
        pass


print("input_portfolio 開始")
self_path = os.path.dirname(os.path.abspath(__file__))
# print("self_path is : "+self_path)
drop_csv(self_path,"user_portfolio_special.csv")
drop_csv(self_path,"user_portfolio_accumulate.csv")
drop_csv(self_path,"user_portfolio_spot.csv")

extract_section(df_all, "成長投資枠","つみたて投資枠","特定預り","user_portfolio_special.csv")
extract_section(df_all, "つみたて投資枠","特定預り","成長投資枠","user_portfolio_accumulate.csv")
extract_section(df_all, "特定預り","成長投資枠","つみたて投資枠","user_portfolio_spot.csv")

print("株式データの抽出完了")
print("input_portfolio 終了\n")
control.main()

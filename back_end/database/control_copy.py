from pathlib import Path
import sqlite3
import csv
import logging
import pandas as pd

def basicConfig():
    logging.basicConfig(
        level=logging.INFO,
        format="%(asctime)s [%(levelname)s] %(message)s",
    )

def init_DF2DB(parents_dir):
    sql_path = parents_dir/"init.sql"
    out_path = parents_dir/"user_data"/"user_database.db"
    with open(sql_path, "r", encoding="cp932") as f:
        sql_text = f.read
    # データベース作成
    conn = sqlite3.connect(out_path)
    cursor = conn.cursor()

    # SQLスクリプトを実行
    cursor.executescript(sql_text)

    # 保存して閉じる
    conn.commit()
    conn.close()


def CSV2DF(input_FileDir,input_FilePath,TSE_data_path):
    logging.info("CSV2DF読み込み完了")
    try:
        input_FileAbspath = input_FileDir/input_FilePath

        if input_FilePath == "user_portfolio_special.csv":
            acoount = "NISA"
        elif input_FilePath == "user_portfolio_accumulate.csv":
            acoount = "NISA"
        else:
            acoount = "非NISA"

        # TSEデータの下処理
        in_col_TSE = ["コード","33業種区分",]
        TSE_df = pd.read_csv(TSE_data_path, encoding="cp932", dtype={"コード": str})
        # print(TSE_df)
        logging.info("TSEデータ読み込み完了")
        new_TSE_df = TSE_df.loc[:,in_col_TSE]

        # ユーザポートフォリオcsv
        in_col = ["コード"]
        df_main = pd.read_csv(input_FileAbspath, encoding="utf-8", dtype={"コード": str})
        df_main_code = df_main.loc[:,in_col]
        # print(df_main)
        logging.info("ユーザポートフォリオ読み込み完了")

        merged_df = pd.merge(df_main_code, new_TSE_df, on="コード", how="left")
        merged_df["口座"] = acoount

        # print(merged_df)

        merged_df = pd.merge(df_main, merged_df, on="コード", how="left")
        merged_df = merged_df.drop(columns=["買付日","前日比", "前日比（％）"])
        print(merged_df)
    except:
        print("入力したファイルにデータはありませんでした。")

if __name__ == "__main__":
    basicConfig()
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    print(f"inpout is   {parents_dir}")
    TSE_data_path = self_path/".."/"static"/"TSE_data.csv"

    input_FileDir = parents_dir /"input_data"
    CSV2DF(input_FileDir,"user_portfolio_special.csv",TSE_data_path)
    CSV2DF(input_FileDir,"user_portfolio_accumulate.csv",TSE_data_path)
    CSV2DF(input_FileDir,"user_portfolio_spot.csv",TSE_data_path)

    


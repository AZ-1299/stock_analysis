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


def CSV2DB(input_FileDir,input_FilePath,TSE_data_path):
    logging.info("CSV2DB読み込み完了")
    input_FileAbspath = input_FileDir/input_FilePath

    # TSEデータの下処理
    in_col_TSE = ["コード","17業種区分"]
    in_col_TSE_search = "コード"
    TSE_df = pd.read_csv(TSE_data_path, encoding="cp932", dtype={"コード": str})
    logging.info("TSEデータ読み込み完了")
    new_TSE_df = TSE_df.loc[:,in_col_TSE]
    # search_TSE_df =TSE_df.loc[:,in_col_TSE_search]

    # ユーザポートフォリオcsv
    in_col = ["コード"]
    df_main = pd.read_csv(input_FileAbspath, encoding="utf-8", dtype={"コード": str})
    df_main_code = df_main.loc[:,in_col]
    # print(df_main)
    # print(df_main_code)
    logging.info("ユーザポートフォリオ読み込み完了")

    merged_df = pd.merge(df_main_code, new_TSE_df, on="コード", how="right")

    # print(merged_df)

    # print(search_TSE_df)
    # for i in df_main_code:
        # print(i)

    # #検索
    # for i in df_main_code:
    #     logging.info(f"検索内容は {i}")
    #     for j in in_col_TSE:
    #         if i==j:
    #             logging.info(f"検索にヒット。ヒット= {j}")
    # logging.info("検索終了")

if __name__ == "__main__":
    basicConfig()
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    print(f"inpout is   {parents_dir}")
    TSE_data_path = self_path/".."/"static"/"TSE_data.csv"

    input_FileDir = parents_dir /"input_data"
    CSV2DB(input_FileDir,"user_portfolio_special.csv",TSE_data_path)

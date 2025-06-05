from pathlib import Path
import sqlite3
import csv
import logging
import pandas as pd

def basicConfig():
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)


def CSV2DB(input_FileDir,input_FilePath,TSE_data_path):
    logging.info("CSV2DB読み込み完了")
    input_FileAbspath = input_FileDir/input_FilePath
    Output_df = []

    # TSEデータの下処理
    in_col_TSE = ["コード","17業種区分"]
    in_col_TSE_search = "コード"
    TSE_df = pd.read_csv(TSE_data_path,encoding="cp932")
    logging.info("TSEデータ読み込み完了")
    new_TSE_df = TSE_df.loc[:,in_col_TSE]
    search_TSE_df =TSE_df.loc[:,in_col_TSE_search]

    # ユーザポートフォリオcsv
    in_col = ["コード"]
    df_main = pd.read_csv(input_FileAbspath,encoding="utf-8")
    logging.info("ユーザポートフォリオ読み込み完了")
    
    #検索
    for i in df_main:
        for j in search_TSE_df:
            if i==j:
                

if __name__ == "__main__":
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    print(f"inpout is   {parents_dir}")
    TSE_data_path = self_path/".."/"static"/"TSE_data.csv"

    input_FileDir = parents_dir /"input_data"
    CSV2DB(input_FileDir,"user_portfolio_special.csv",TSE_data_path)

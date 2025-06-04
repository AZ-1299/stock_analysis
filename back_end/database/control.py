from pathlib import Path
import sqlite3
import csv
import logging
import pandas as pd

def basicConfig():
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)


def CSV2DB(input_FileDir,input_FilePath):
    in_col = ["コード"]
    input_FileAbspath = input_FileDir/input_FilePath
    print(f"INFO: {input_FileAbspath}")
    df_main = pd.read_csv(input_FileAbspath,encoding="utf-8")
    print(df_main)
    
    

if __name__ == "__main__":
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    print(f"inpout is   {parents_dir}")
    TSE_data_path = self_path/".."/"static"/"TSE_data.csv"

    input_FileDir = parents_dir /"input_data"
    CSV2DB(input_FileDir,"user_portfolio_special.csv")

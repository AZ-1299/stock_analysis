from pathlib import Path
import sqlite3
import pandas as pd
import os

def CSV2DF(input_FileDir,input_FilePath,TSE_data_path,parents_dir):
    print("CSV2DF読み込み完了")
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
        print("TSEデータ読み込み完了")
        new_TSE_df = TSE_df.loc[:,in_col_TSE]
        # print(new_TSE_df+"\n")

        # ユーザポートフォリオcsv
        in_col = ["コード"]
        df_main = pd.read_csv(input_FileAbspath, encoding="utf-8", dtype={"コード": str})
        df_main_code = df_main.loc[:,in_col]
        print(df_main)
        print("ユーザポートフォリオ読み込み完了")

        merged_df = pd.merge(df_main_code, new_TSE_df, on="コード", how="left")
        merged_df["口座"] = acoount

        merged_df = pd.merge(df_main, merged_df, on="コード", how="left")
        merged_df = merged_df.drop(columns=["買付日","前日比", "前日比（％）","損益","損益（％）","評価額"])
        print(merged_df)
        

        if input_FilePath=='user_portfolio_special.csv':
            init_dorp_db(parents_dir)
            key = 0
            init_DF2DB(parents_dir,merged_df,key)
        else:
            key = 1
            init_DF2DB(parents_dir,merged_df,key)

    except:
        print("入力したファイルにデータはありませんでした。")

def init_dorp_db(parents_dir):
    out_path = parents_dir/"database"/"user_data"/"user_database.db"
    os.remove(out_path)

def init_DF2DB(parents_dir,df,key):
    
    print("init_DF2DB開始")
    out_path = parents_dir/"database"/"user_data"/"user_database.db" 
    print(out_path)

    conn = sqlite3.connect(out_path)
    cur = conn.cursor()
    if key == 0:  
        df.to_sql('user_database',conn,if_exists='replace')
        select_sql = 'SELECT * FROM user_database'
        print("DF2DB完了")
        for row in cur.execute(select_sql):
            print(row)
        cur.close()
        conn.close()
    else:
        df.to_sql('user_database',conn,if_exists='append')
        select_sql = 'SELECT * FROM user_database'
        print("DF2DB完了")
        for row in cur.execute(select_sql):
            print(row)
        cur.close()
        conn.close()
    

        
def main():
    # basicConfig()
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    print(f"inpout is   {parents_dir}")
    TSE_data_path = self_path/".."/"static"/"TSE_data.csv"

    input_FileDir = parents_dir /"input_data"
    CSV2DF(input_FileDir,"user_portfolio_special.csv",TSE_data_path,parents_dir)
    CSV2DF(input_FileDir,"user_portfolio_accumulate.csv",TSE_data_path,parents_dir)
    CSV2DF(input_FileDir,"user_portfolio_spot.csv",TSE_data_path,parents_dir)
    

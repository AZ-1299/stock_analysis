from pathlib import Path
import sqlite3
import pandas as pd
import os

def CSV2DF(input_FileDir,input_FilePath,TSE_data_path,parents_dir):
    print("\ncontrole.py : CSV2DF読み込み開始")
    try:
        input_FileAbspath = Path(input_FileDir).joinpath(input_FilePath)        
        print(input_FileAbspath)
        df_main = pd.read_csv(str(input_FileAbspath), encoding="utf-8", dtype={"コード": str}) 
        if input_FilePath == "user_portfolio_special.csv":
            acoount = "NISA"
        elif input_FilePath == "user_portfolio_accumulate.csv":
            acoount = "NISA"
        else:
            acoount = "非NISA"
        print("NISA判別完了")

        # TSEデータの下処理
        in_col_TSE = ["コード","33業種区分",]
        TSE_df = pd.read_csv(TSE_data_path, encoding="cp932", dtype={"コード": str})
        print("TSEデータ読み込み完了")
        new_TSE_df = TSE_df.loc[:,in_col_TSE]

        # ユーザポートフォリオcsv
        in_col = ["コード"]
        df_main_code = df_main.loc[:,in_col]
        # print(df_main)
        print("ユーザポートフォリオ読み込み完了")

        merged_df = pd.merge(df_main_code, new_TSE_df, on="コード", how="left")
        merged_df["口座"] = acoount

        merged_df = pd.merge(df_main, merged_df, on="コード", how="left")
        drop_cols = ["買付日", "前日比", "前日比（％）", "損益", "損益（％）", "評価額", "現在値"]        
        merged_df = merged_df.rename(columns={
            'コード':'code',
            '銘柄名':'name',
            '数量': 'qty',     
            '取得単価':'unit_value',
            '33業種区分': 'industry',
            '口座': 'account'
        })
        print("rename merged_df is : ", merged_df)        

        target_columns = ['code', 'name', 'qty', 'unit_value', 'industry', 'account']
        merged_df = merged_df[target_columns]
        print("Merged DataFrame Sample:\n", merged_df.head(2))
        
        if input_FilePath=='user_portfolio_special.csv':
            init_dorp_db(parents_dir)
            key = 0
            init_DF2DB(parents_dir,merged_df,key)
        else:
            key = 1
            db_path = parents_dir.joinpath("database", "user_data", "user_database.db")
            if not db_path.exists():
                key = 0
            init_DF2DB(parents_dir,merged_df,key)

    except Exception as e:
        print(f"エラー: ファイル処理中に予期せぬ問題が発生しました: {e}")

def init_dorp_db(parents_dir):
    out_path = parents_dir.joinpath("database", "user_data", "user_database.db")    
    os.remove(out_path)

def init_DF2DB(parents_dir,df,key):
    
    print("init_DF2DB開始")
    connect_path = parents_dir.joinpath("database", "user_data", "user_database.db")    
    print(connect_path)

    conn = sqlite3.connect(connect_path)
    cur = conn.cursor()
    if key == 0:  
        df.to_sql('user_database', conn, if_exists='replace', index=False)        

        select_sql = 'SELECT * FROM user_database'
        for row in cur.execute(select_sql):
            print(row)

        add_column = 'ALTER TABLE user_database ADD total_value FLOAT'
        cur.execute(add_column)
        conn.commit()

        update_sql = 'UPDATE user_database SET total_value = qty * unit_value'
        cur.execute(update_sql)
        conn.commit()

        cur.close()
        conn.close()
        print("DF2DB完了")
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
    TSE_data_path = parents_dir.joinpath("database", "static", "TSE_data.csv")
    input_FileDir = parents_dir.joinpath("input_data")

    CSV2DF(input_FileDir,"user_portfolio_special.csv",TSE_data_path,parents_dir)
    CSV2DF(input_FileDir,"user_portfolio_accumulate.csv",TSE_data_path,parents_dir)
    CSV2DF(input_FileDir,"user_portfolio_spot.csv",TSE_data_path,parents_dir)
    

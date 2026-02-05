from pathlib import Path
import sqlite3
import pandas as pd
import os

def CSV2DF(input_FileDir,input_FilePath,TSE_data_path,parents_dir):
    print("\ncontrole.py : CSV2DF読み込み開始")
    try:
        input_FileAbspath = Path(input_FileDir).joinpath(input_FilePath)        
        print(input_FileAbspath)
        
        if os.stat(input_FileAbspath).st_size == 0:
            print(f"スキップ: {input_FilePath} は空ファイルです。")
            return

        df_main = pd.read_csv(str(input_FileAbspath), encoding="utf-8", dtype={"コード": str}) 
        
        if df_main.empty:
            print(f"スキップ: {input_FilePath} にデータ行がありません。")
            return

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
        print("ユーザポートフォリオ読み込み完了")

        merged_df = pd.merge(df_main_code, new_TSE_df, on="コード", how="left")
        merged_df["口座"] = acoount

        merged_df = pd.merge(df_main, merged_df, on="コード", how="left")
        
        merged_df = merged_df.rename(columns={
            'コード':'code',
            '銘柄名':'name',
            '数量': 'qty',     
            '取得単価':'unit',
            '33業種区分': 'industry',
            '口座': 'account',
            '評価額':'total_value'
        })
        
        target_columns = ['code', 'name', 'qty', 'unit', 'industry', 'account','total_value']
        merged_df = merged_df[target_columns]
        print("Merged DataFrame Sample:\n", merged_df.head())

        if input_FilePath=='user_portfolio_special.csv':
            key = 0
            DF2DB(parents_dir,merged_df,key)
        else:
            key = 1
            DF2DB(parents_dir,merged_df,key)

    except pd.errors.EmptyDataError:
        print(f"スキップ: {input_FilePath} はデータを含んでいません。")
    except Exception as e:
        print(f"エラー: {e}")


def DF2DB(parents_dir,df,key):
    print("DF2DB開始")
    connect_path = parents_dir.joinpath("database", "user_data", "user_database.db")    

    #データベース作成
    if key == 0: 
        try:
            print("インフォ：key=0")
            out_path = parents_dir.joinpath("database","user_data","user_database.db")
            
            if os.path.exists(out_path):
                dropDB(out_path)

            conn = sqlite3.connect(connect_path)
            cur = conn.cursor()

            df.to_sql('user_database', conn, if_exists='replace', index=False)        

            select_sql = 'SELECT * FROM user_database'
            for row in cur.execute(select_sql):
                print(row)

            update_sql = 'UPDATE user_database SET total_value = CAST(qty AS REAL) * CAST(unit AS REAL) WHERE total_value IS NULL;'
            cur.execute(update_sql)
            conn.commit()
            cur.close()
            conn.close()
            print("DF2DB完了")
        except Exception as e:
            print(f"key=0 : エラー {e}")

    #データベース追加
    elif key == 1:
        try:
            print("インフォ：key=1")
            conn = sqlite3.connect(connect_path)
            cur = conn.cursor()
            df.to_sql('user_database',conn,if_exists='append',index=False)
            
            select_sql = 'SELECT * FROM user_database'
            update_sql = 'UPDATE user_database SET total_value = qty * unit WHERE total_value IS NULL'
            cur.execute(update_sql)
            conn.commit()
            print("DF2DB完了")
            for row in cur.execute(select_sql):
                print(row)
            cur.close()
            conn.close()
        except Exception as e:
            print(f"key=1 : エラー {e}")

def dropDB(out_path):
    print("dropDB")
    try:
        os.remove(out_path)
    except Exception as e:
        print(f"削除失敗: {e}")

def main():
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    TSE_data_path = parents_dir.joinpath("database", "static", "TSE_data.csv")
    input_FileDir = parents_dir.joinpath("input_data")

    CSV2DF(input_FileDir,"user_portfolio_special.csv",TSE_data_path,parents_dir)
    CSV2DF(input_FileDir,"user_portfolio_accumulate.csv",TSE_data_path,parents_dir)
    CSV2DF(input_FileDir,"user_portfolio_spot.csv",TSE_data_path,parents_dir)

#年間配当予想
#セクター別配当比率
#業種

import numpy as np
from pathlib import Path
import sqlite3
def desp_db(parents_dir):
    open_path = parents_dir.joinpath("database", "user_data", "user_database.db")    
    desp_dbconn  = 'SELECT * FROM user_database'
    try:
        conn = sqlite3.connect(open_path)
        cur = conn.cursor()
        cur.execute(desp_dbconn)
        rows = cur.fetchall()
        print(f"--- 取得データ ({len(rows)}件) ---")
        for row in rows:
            print(row)

    except:
        print("エラー")
    

if __name__ =='__main__':
    print("dividend_analysis.pyに接続")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    desp_db(parents_dir)


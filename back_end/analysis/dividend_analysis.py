#年間配当予想
#セクター別配当比率
#業種
import numpy as np
from pathlib import Path
import sqlite3

def connect_db():
    open_path = parents_dir/"database"/"user_data"/"user_database.db" 
    print(open_path)
    conn = sqlite3.connect(open_path)
    cur = conn.cursor()
    Industry_ratio ="SELECT NAME "

def desp_db(parents_dir):
    open_path = parents_dir/"database"/"user_data"/"user_database.db" 
    desp_dbconn  = "SELECT * FROM user_database"
    conn = sqlite3.connect(open_path)
    cur = conn.cursor()
    cur.execute(desp_dbconn)
    
    # for row in cur.execute(desp_dbconn):
    #         print(row)

if __name__ =='__main__':
    print("dividend_analysis.pyに接続")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    desp_db(parents_dir)


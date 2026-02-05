import pandas as pd
import sqlite3
from pathlib import Path

def db_connect(con_path):
    print("db_connect 関数開始")
    get_code = "SELECT code FROM user_database"
    con = sqlite3.connect(con_path)
    cur = con.cursor()
    cur.execute(get_code)
    rows = cur.fetchall()
    select_sql = 'SELECT * FROM user_database'
    for row in cur.execute(select_sql):
        print(row)

def now_price():
    print("now_price関数開始")

if __name__ == "__main__":
    print("input_data.py 開始")
    root_dir = Path(__file__).resolve().parent.parent
    print(f"Root Directory: {root_dir}")
    con_path = root_dir.joinpath(
        "database",
        "user_data",
        "user_database.db"
    )
    print(f"DB Path: {con_path}")
    db_connect(con_path)

import yfinance as yf
import sqlite3
import pandas as pd
from pathlib import Path
import sys


def on_db(parents_dir):
    print("on_db 起動")
    connect_path = parents_dir.joinpath("database", "user_data", "user_database.db")
    conn = sqlite3.connect(connect_path)
    cur = conn.cursor()
    desp_dbconn = 'SELECT code FROM user_database'
    ticker_code = []

    try:
        conn = sqlite3.connect(connect_path)
        cur = conn.cursor()
        cur.execute(desp_dbconn)
        rows = cur.fetchall()
        print(f"--- 取得データ ({len(rows)}件) ---")
        for row in rows:
            code = row[0]
            ticker_code.append(str(code) + ".T")
        for i in ticker_code:
            print(i)
    except:
        print(sys.exc_info())


if __name__ == "__main__":
    print("api.py 起動")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    on_db(parents_dir)

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
        print(ticker_code)
        
        now_val(ticker_code)
    except:
        print(sys.exc_info())

def now_val(ticker_code):
    print("now val起動")
    try:
        for i in ticker_code:
            STOCK = yf.Ticker(i) 
            STOCK_dividends = STOCK.dividends
            
            if not STOCK_dividends.empty:
                latest_dividend_amount = STOCK_dividends.iloc[-1]
                latest_dividend_date = STOCK_dividends.index[-1]
                print(f"`code:{i} 最新配当金額: {latest_dividend_amount}")


    except:
        print("エラー")
        print(sys.exc_info())
   

if __name__ == "__main__":
    print("api.py 起動")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    on_db(parents_dir)

import pandas as pd
import numpy as np
import sqlite3
from pathlib import Path
import yfinance as yf

def db_getCode(con_path):
    print("db_connect 関数開始")

    codes = []

    get_code = "SELECT code FROM user_database"
    con = sqlite3.connect(con_path)
    cur = con.cursor()
    cur.execute(get_code)
    codes = [row[0] for row in cur.fetchall()]
    return codes

def now_price(codes):
    print("now_price関数開始")
    close_prices = {}

    for code in codes:
        ticker = f"{code}.T"
        print(ticker)
        try:
            df = yf.download(ticker,period="1d", progress=False)
            if not df.empty:
                val = float(df['Close'].iloc[-1])
                print(val)
                close_prices[code] = val
            else:
                print(f"{ticker}のデータがありません")

        except Exception as e:
            print(f"終値取得時にエラー:{ticker},{e}")

    print("#------------------------------------------------------------#")
    print(f"取得件数: {len(close_prices)}件")
    print("株価取得完了")
    
    return close_prices

def main_update():
    print("input_data.py 開始")
    print("main_update 開始")
    root_dir = Path(__file__).resolve().parent.parent
    # print(f"Root Directory: {root_dir}")
    con_path = root_dir.joinpath(
        "database",
        "user_data",
        "user_database.db"
    )
    print(f"DB Path: {con_path}")
    codes = db_getCode(con_path)
    print("get codes")
    print(codes)

    current_value = now_price(codes)
    print(current_value)
    return current_value

def main_mkDB(ticker_codes):
    print("input_data.py 開始")
    print("main_kmDB 開始")
    codes = ticker_codes 
    print(f"受け取ったコード: {codes}")

    current_value = now_price(codes)
    return current_value

if __name__ == "__main__":
    main_update()

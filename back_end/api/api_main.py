import pandas as pd
import sqlite3
from pathlib import Path
import yfinance as yf
import datetime


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
    ticker_codes = [f"{code}.T" for code in codes]
    print(ticker_codes)

    all_inf = []
    all_inf.append(yf.download(ticker_codes,period='1d',interval="1d"))
    now_price = all_inf.info.get('currentPrice')
    print(now_price)
    return 

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
    codes = db_getCode(con_path)
    print("get codes")
    print(codes)

    dt = datetime.datetime.today()
    ut = dt.date()
    print(f"Now time is {ut}")
    new_now_price = now_price(codes)
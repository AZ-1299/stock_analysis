import numpy as np
from pathlib import Path
import sqlite3
import matplotlib.pyplot as plt
import japanize_matplotlib

self_path = Path(__file__)
parents_dir = self_path.resolve().parents[1]
graph_output_dir = parents_dir.joinpath("analysis","img")

open_path = parents_dir.joinpath("database", "user_data", "user_database.db")
desp_dbconn = 'SELECT * FROM user_database'

conn = sqlite3.connect(open_path)
cur = conn.cursor()
cur.execute(desp_dbconn)
rows = cur.fetchall()
print(f"--- 取得データ ({len(rows)}件) ---")
for row in rows:
    print(row)
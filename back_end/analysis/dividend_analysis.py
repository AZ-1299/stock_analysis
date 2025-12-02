#年間配当予想
#セクター別配当比率
#業種

import numpy as np
from pathlib import Path
import sqlite3
import matplotlib.pyplot as plt

def DB(parents_dir):
    graph_output_dir = parents_dir.joinpath("img")

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

        #変数定義
        height,x = [],[]
        num = len(rows)
        for i in range(num):
            x.append(i)

        #構成比率
        
        print("\n#------構成比率のグラフを描画開始------#")
        #銘柄ごと
        query_label = "SELECT name FROM user_database"
        query_data = "SELECT total_value FROM user_database"
        cur.execute(query_label)
        label = cur.fetchall()
        label = np.array(label).flatten()
        print(label)
        print("ステップ1 完了")

        cur.execute(query_data)
        height  = cur.fetchall()
        height = np.array(height).flatten()
        print(height)
        print("ステップ2 完了")

        plt.bar(x,height,tick_label=label,align="center") 
        print("ステップ3.1 完了")
        graph_output_path = parents_dir.joinpath("graph.png")
        plt.savefig(graph_output_path)
        print("ステップ3.2 完了")

    except Exception as e:
        print(f"エラー：{e}")

if __name__ =='__main__':
    print("\ndividend_analysis.pyに接続")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    DB(parents_dir)
    


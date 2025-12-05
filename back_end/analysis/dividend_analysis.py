#年間配当予想
#セクター別配当比率
#業種

import numpy as np
from pathlib import Path
import sqlite3
import matplotlib.pyplot as plt  
import japanize_matplotlib  

def DB(parents_dir):
    graph_output_dir = parents_dir.joinpath("analysis","img")

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
        
        print("\n#------全体を対象とした棒グラフを描画開始------#")
        #銘柄ごと
        query_label = "SELECT name FROM user_database"
        query_data = "SELECT total_value FROM user_database"
        cur.execute(query_label)
        label = cur.fetchall()
        label = np.array(label).flatten()

        cur.execute(query_data)
        height  = cur.fetchall()
        height = np.array(height).flatten()
    
        plt.xticks(rotation=90)
        plt.bar(x,height,tick_label=label,align="center") 
        graph_output_path = graph_output_dir.joinpath("all_graph.png")
        plt.savefig(graph_output_path,bbox_inches='tight')
        print("\n#------完了------#\n")

        query_label = "SELECT industry FROM user_database"
        query_data = "SELECT industry, SUM(total_value) AS industry_total_value FROM user_database GROUP BY industry;"
        cur.execute(query_label)
        label = cur.fetchall()
        label = np.array(label).flatten()

        cur.execute(query_data)
        height  = cur.fetchall()
        height = np.array(height).flatten()
    
        plt.xticks(rotation=90)
        plt.bar(x,height,tick_label=label,align="center") 
        graph_output_path = graph_output_dir.joinpath("graph.png")
        plt.savefig(graph_output_path,bbox_inches='tight')


    except Exception as e:
        print(f"エラー：{e}")

if __name__ =='__main__':
    print("\ndividend_analysis.pyに接続")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    DB(parents_dir)

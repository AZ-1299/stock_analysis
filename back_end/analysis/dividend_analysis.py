import numpy as np
from pathlib import Path
import sqlite3
import matplotlib.pyplot as plt
import japanize_matplotlib

#銘柄ごと構成棒グラフ
def plot_stock_bar_chart(cur, graph_output_dir, rows):
    height,x = [],[]
    num = len(rows)
    for i in range(num):
        x.append(i)

    print("\n#------全体を対象とした棒グラフを描画開始------#")
    query_label = "SELECT name FROM user_database"
    query_data = "SELECT total_value FROM user_database"
    cur.execute(query_label)
    label = cur.fetchall()
    label = np.array(label).flatten()

    cur.execute(query_data)
    height = cur.fetchall()
    height = np.array(height).flatten()

    sorted_indices = np.argsort(height)[::-1]
    height = height[sorted_indices]
    label = label[sorted_indices]
    
    plt.xticks(x, label, rotation=90,)
    plt.bar(x, height, tick_label=label, align="center") 
    graph_output_path = graph_output_dir.joinpath("all_graph.png")
    plt.savefig(graph_output_path, bbox_inches='tight')
    plt.close()
    print("\n#------完了------#\n")
    

# --- 業種別円グラフの描画 ---
def plot_industry_pie_chart(cur, graph_output_dir):
    
    query_data = "SELECT industry, SUM(total_value) AS industry_total_value FROM user_database GROUP BY industry;"
    cur.execute(query_data)
    raw_data = cur.fetchall()
    raw_data_np = np.array(raw_data)
    height = raw_data_np[:, 1].astype(float)
    label = raw_data_np[:, 0]
    
    plt.figure(figsize=(10, 8))
    plt.pie(
        height,
        labels=label,
        autopct='%1.1f%%',
        startangle=90, 
        textprops={'fontsize': 20}
    )
    plt.title("業種別ポートフォリオ構成比率")
    plt.axis('equal')
    graph_output_path = graph_output_dir.joinpath("industry_graph.png")
    plt.savefig(graph_output_path, bbox_inches='tight')
    plt.close()


def DB(parents_dir):
    graph_output_dir = parents_dir.joinpath("analysis","img")

    open_path = parents_dir.joinpath("database", "user_data", "user_database.db")
    desp_dbconn = 'SELECT * FROM user_database'
    try:
        conn = sqlite3.connect(open_path)
        cur = conn.cursor()
        cur.execute(desp_dbconn)
        rows = cur.fetchall()
        print(f"--- 取得データ ({len(rows)}件) ---")
        for row in rows:
            print(row)

        #グラフの描画
        plot_stock_bar_chart(cur, graph_output_dir, rows)
        plot_industry_pie_chart(cur, graph_output_dir)

    except Exception as e:
        print(f"エラー：{e}")

if __name__ =="__main__":
    print("\ndividend_analysis.pyに接続")
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    DB(parents_dir)

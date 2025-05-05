import requests
from bs4 import BeautifulSoup
import os
import logging

# ログ
logging.basicConfig(level=logging.INFO)

# credentials.txtからID, PW読み込み
credentials_FilePath = os.path.join(os.path.dirname(__file__), 'Input_api', 'credentials.txt')
with open(credentials_FilePath, "r") as f:
    lines = f.readlines()
    try_userID = lines[0].strip()
    try_userPW = lines[1].strip()

# セッション開始
session = requests.Session()
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36'
}
session.headers.update(headers)

# ログインページへGET（トークンとか取るため）
login_page_url = "https://www.sbisec.co.jp/ETGate"
res = session.get(login_page_url)
soup = BeautifulSoup(res.text, 'html.parser')

# POSTデータ作成
data = {
    'user_id': try_userID,
    'user_password': try_userPW,
    'ACT_login': 'ログイン'
}

# ログインPOST
res = session.post(login_page_url, data=data)
if res.status_code == 200:
    logging.info('ログインPOST送信完了')
else:
    logging.error(f'ログイン失敗: {res.status_code}')

# ログイン後ページへアクセス
mypage_url = "https://site2.sbisec.co.jp/ETGate/"
res = session.get(mypage_url)

# 結果チェック
soup = BeautifulSoup(res.text, 'html.parser')
info = soup.find('div', {'class': 'profile'})
if info:
    print(info.text)
else:
    print("ログイン失敗または情報が見つかりませんでした")

# credentials.txt初期化
with open(credentials_FilePath, "w") as f:
    f.write("")
logging.info('ID, PWの削除完了')
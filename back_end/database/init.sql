--UNIQUE=重複しない
--INTEGER=8bit整数
-- TEXT=テキストで保存
--PRIMARY KEY=主キー、
--DEFAULT= 初めに設定したものから変更不可
--DATETIME= 日付及び時間に関する型
--FOREIGN KEY=外部キー、別のテーブルと一貫性のある参照関係を確立する
--FOREIGN KEY(user_id) REFERENCES users(id)=user_idに入る値は必ずusersテーブルのidに依存する


CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT, --ID:全体紐づけ
  username TEXT UNIQUE, --ユーザネーム
  password_hash TEXT   NOT NULL, --パスワード
  email TEXT UNIQUE, --登録メールアドレス
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP --登録日
  );

CREATE TABLE assets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    symbol_num TEXT,        -- 銘柄コード
    symbol_name TEXT,
    purchase_date DATE, --購入日
    purchase_price REAL, --購入価格(単価)
    shares INTEGER,  --購入株数
    account_type TEXT, --口座タイプ
    nisa_type TEXT, --NISAタイプ
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE prices (
    symbol TEXT,  --銘柄名
    date DATE, --取引日
    close_price REAL,--終値
    PER REAL, --その日、銘柄のPER
    PBR REAL, --その日、銘柄のPBR
    EPS REAL --その日、銘柄のEPS
);

CREATE TABLE portfolio_stats (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    date DATE,
    total_value REAL,     -- 総資産
    profit REAL,          -- 累積損益
    unrealized_gain REAL,  -- 含み益
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE transactions(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id TEXT,
    symbol TEXT,
    date DATE, --情報を取得した日付
    sale_type TEXT,
    quantity INTEGER,
    unit_price REAL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

from pathlib import Path
import sqlite3
import csv
import logging
def basicConfig():
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)

def CSV2DB(input_FileDir,input_FilePath):
    input_FileAbspath = input_FileDir/input_FilePath
    print(f"INFO: {input_FileAbspath}")
    with open(input_FileAbspath,"r",encoding = "UTF-8_sig") as f:
        reader = csv.reader(f)
        for row in reader:
            print(row)
    
    

if __name__ == "__main__":
    self_path = Path(__file__)
    parents_dir = self_path.resolve().parents[1]
    print(f"inpout is   {parents_dir}")
    input_FileDir = parents_dir /"input_data"
    CSV2DB(input_FileDir,"user_portfolio_special.csv")

package front_end.control;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import front_end.control.Connect_DB.PortfolioRow;

public class DataUpdate extends AbstractTableModel {
    private List<PortfolioRow> data;
    private final String[] columnNames = {"コード", "銘柄名", "数量", "取得単価", "業種", "口座"};

    public DataUpdate(List<PortfolioRow> data) {
        this.data = data != null ? data : new java.util.ArrayList<>();
    }
    //列数把握
    @Override
    public int getColumnCount() { return columnNames.length; }
    //行数把握
    @Override
    public int getRowCount() { return data.size(); }
    //ヘッダー名把握
    @Override
    public String getColumnName(int col) { return columnNames[col]; }
    //有効な行番号把握
    @Override
    public Object getValueAt(int row, int col) {
        if (row >= 0 && row < data.size()) {
            return data.get(row).toArray()[col];
        }
        return null;
    }
    public void setData(List<PortfolioRow> newData) {
    this.data = newData != null ? newData : new java.util.ArrayList<>();
    fireTableDataChanged(); 
}
}

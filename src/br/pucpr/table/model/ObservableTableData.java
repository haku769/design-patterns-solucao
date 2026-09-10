package br.pucpr.table.model;

public interface ObservableTableData extends TableData {
    void addObserver(TableDataObserver observer);

    void removeObserver(TableDataObserver observer);
}

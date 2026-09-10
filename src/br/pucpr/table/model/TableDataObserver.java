package br.pucpr.table.model;

@FunctionalInterface
public interface TableDataObserver {
    void dataChanged(TableData data);
}

package br.pucpr.table.model;

import java.util.Objects;

public final class PaginatedTableData implements ObservableTableData {
    private final TableData data;
    private final int page;
    private final int pageSize;
    private final TableDataChangeSupport changeSupport = new TableDataChangeSupport(this);

    public PaginatedTableData(TableData data, int page, int pageSize) {
        this.data = Objects.requireNonNull(data, "TableData não pode ser nulo");
        if (page < 0) {
            throw new IllegalArgumentException("A página não pode ser negativa");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("O tamanho da página deve ser maior que zero");
        }
        int pageCount = Math.max(1, (data.rowCount() + pageSize - 1) / pageSize);
        if (page >= pageCount) {
            throw new IllegalArgumentException("Página inexistente");
        }
        this.page = page;
        this.pageSize = pageSize;

        if (data instanceof ObservableTableData observableData) {
            observableData.addObserver(ignored -> changeSupport.notifyObservers());
        }
    }

    @Override
    public int rowCount() {
        return Math.min(pageSize, data.rowCount() - firstRow());
    }

    @Override
    public int colCount() {
        return data.colCount();
    }

    @Override
    public String header(int col) {
        return data.header(col);
    }

    @Override
    public String get(int row, int col) {
        if (row < 0 || row >= rowCount()) {
            throw new IndexOutOfBoundsException("Linha fora da página");
        }
        return data.get(firstRow() + row, col);
    }

    public int page() {
        return page;
    }

    public int pageCount() {
        return Math.max(1, (data.rowCount() + pageSize - 1) / pageSize);
    }

    @Override
    public void addObserver(TableDataObserver observer) {
        changeSupport.addObserver(observer);
    }

    @Override
    public void removeObserver(TableDataObserver observer) {
        changeSupport.removeObserver(observer);
    }

    private int firstRow() {
        return page * pageSize;
    }
}

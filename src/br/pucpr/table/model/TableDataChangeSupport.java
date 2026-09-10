package br.pucpr.table.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TableDataChangeSupport {
    private final TableData source;
    private final List<TableDataObserver> observers = new ArrayList<>();

    public TableDataChangeSupport(TableData source) {
        this.source = Objects.requireNonNull(source);
    }

    public void addObserver(TableDataObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    public void removeObserver(TableDataObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (var observer : List.copyOf(observers)) {
            observer.dataChanged(source);
        }
    }
}

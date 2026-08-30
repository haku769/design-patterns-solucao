package br.pucpr.planetas;

public interface ColumnData<T> {
    String header();

    String get(T object);
}

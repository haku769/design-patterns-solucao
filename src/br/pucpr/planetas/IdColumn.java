package br.pucpr.planetas;

import br.pucpr.planetas.PlanetaPrinter.Planeta;

public class IdColumn implements ColumnData<Planeta> {
    @Override
    public String header() {
        return "%-5s".formatted("ID");
    }

    @Override
    public String get(Planeta planeta) {
        return planeta.id() == null ? "0" : planeta.id().toString();
    }
}

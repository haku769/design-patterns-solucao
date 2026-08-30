package br.pucpr.planetas;

import br.pucpr.planetas.PlanetaPrinter.Planeta;

public class NameColumn implements ColumnData<Planeta> {
    @Override
    public String header() {
        return "%-20s".formatted("NOME");
    }

    @Override
    public String get(Planeta planeta) {
        var nome = planeta.nome();
        return nome == null || nome.isEmpty() ? "NÃO INFORMADO" : nome;
    }
}

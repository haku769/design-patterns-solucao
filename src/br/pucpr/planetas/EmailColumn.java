package br.pucpr.planetas;

import br.pucpr.planetas.PlanetaPrinter.Planeta;

public class EmailColumn implements ColumnData<Planeta> {
    @Override
    public String header() {
        return "%-22s".formatted("EMAIL");
    }

    @Override
    public String get(Planeta planeta) {
        var email = planeta.email();
        return email == null || !email.contains("@") ? "INVALIDO" : email;
    }
}

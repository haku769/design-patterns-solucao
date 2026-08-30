package br.pucpr.planetas;

import br.pucpr.planetas.PlanetaPrinter.Planeta;

public class CpfColumn implements ColumnData<Planeta> {
    private final boolean mascarar;

    public CpfColumn(boolean mascarar) {
        this.mascarar = mascarar;
    }

    public CpfColumn() {
        this(false);
    }

    @Override
    public String header() {
        return "%-14s".formatted("CPF");
    }

    @Override
    public String get(Planeta planeta) {
        var cpf = planeta.cpf();
        if (cpf == null || cpf.length() != 11) {
            return "CPF INVALIDO";
        }
        if (mascarar) {
            return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
        }
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}

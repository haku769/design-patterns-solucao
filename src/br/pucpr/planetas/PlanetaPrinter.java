package br.pucpr.planetas;

import java.util.ArrayList;
import java.util.List;

public class PlanetaPrinter {
    public record Planeta(Long id, String nome, String email, String cpf) {
    }

    public enum Tema {
        PADRAO("="),
        DARK("#"),
        LIGHT("-");

        private final String caractereBorda;

        Tema(String caractereBorda) {
            this.caractereBorda = caractereBorda;
        }

        public String caractereBorda() {
            return caractereBorda;
        }
    }

    public void print(List<Planeta> lista, boolean maskCpf, boolean alignRight, Tema tema) {
        if (lista != null && !lista.isEmpty()) {
            var borderChar = tema == null
                    ? Tema.PADRAO.caractereBorda()
                    : tema.caractereBorda();
            List<ColumnData<Planeta>> colunas = List.of(
                    new IdColumn(),
                    new NameColumn(),
                    new EmailColumn(),
                    new CpfColumn(maskCpf)
            );
            int larguraTabela = colunas.stream()
                    .mapToInt(coluna -> coluna.header().length() + 3)
                    .sum() + 1;

            var sb = new StringBuilder();
            adicionarBorda(sb, borderChar, larguraTabela);
            adicionarLinha(sb, colunas, null);
            adicionarBorda(sb, borderChar, larguraTabela);
            for (var planeta : lista) {
                if (planeta != null) {
                    adicionarLinha(sb, colunas, planeta);
                }
            }
            adicionarBorda(sb, borderChar, larguraTabela);

            if (alignRight) {
                var lines = sb.toString().split("\n");
                for (var line : lines) {
                    System.out.println("                    " + line);
                }
            } else {
                System.out.print(sb);
            }
        } else {
            System.out.println("ERRO: Lista de planetas vazia ou nula.");
        }
    }

    private void adicionarBorda(StringBuilder sb, String caractere, int largura) {
        sb.repeat(caractere, largura).append('\n');
    }

    private void adicionarLinha(StringBuilder sb, List<ColumnData<Planeta>> colunas, Planeta planeta) {
        sb.append('|');
        for (var coluna : colunas) {
            var conteudo = planeta == null ? coluna.header() : ajustar(coluna.get(planeta), coluna.header().length());
            sb.append(String.format(" %-" + coluna.header().length() + "s |", conteudo));
        }
        sb.append('\n');
    }

    private String ajustar(String valor, int largura) {
        if (valor.length() <= largura) {
            return valor;
        }
        return largura < 3 ? ".".repeat(largura) : valor.substring(0, largura - 3) + "...";
    }

    public static void main(String[] args) {
        var planetas = new ArrayList<Planeta>();
        planetas.add(new Planeta(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"));
        planetas.add(new Planeta(102L, "Ana Maria Silva", "ana.silva@email.com", "98765432100"));
        planetas.add(new Planeta(103L, "João Pedro de Alcântara Bragança", "joao.pedro@email.com", "45678912345"));
        planetas.add(new Planeta(104L, "Mariana Costa", "marianacosta.email.com", "11122233344"));
        planetas.add(new Planeta(105L, "Lucas Mendes", "lucas@email.com", "12345"));
        planetas.add(new Planeta(106L, "", "beatriz@email.com", "55566677788"));

        var printer = new PlanetaPrinter();
        printer.print(planetas, true, true, Tema.LIGHT);
    }
}

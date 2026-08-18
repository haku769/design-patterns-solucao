package br.pucpr.usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioPrinter {
    public record Usuario(Long id, String nome, String email, String cpf) {
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

    public void print(List<Usuario> lista, boolean maskCpf, boolean alignRight, Tema tema) {
        if (lista != null && !lista.isEmpty()) {
            var borderChar = tema == null
                    ? Tema.PADRAO.caractereBorda()
                    : tema.caractereBorda();


            var sb = new StringBuilder();
            sb.repeat(borderChar, 74).append("\n");
            sb.append(String.format("| %-5s | %-20s | %-22s | %-14s |\n", "ID", "NOME", "EMAIL", "CPF"));
            sb.repeat(borderChar, 74).append("\n");
            for (var u : lista) {
                if (u != null) {

                    var n = formatarNome(u.nome());
                    var e = formatarEmail(u.email());
                    var c = formatarCpf(u.cpf(), maskCpf);
                    var idStr = u.id() != null ? u.id().toString() : "0";
                    sb.append(String.format("| %-5s | %-20s | %-22s | %-14s |\n", idStr, n, e, c));
                }
            }

                sb.repeat(borderChar, 74).append("\n");

                if (alignRight) {
                    var lines = sb.toString().split("\n");
                    for (var line : lines) {
                        System.out.println("                    " + line);
                    }
                } else {
                    System.out.print(sb);
                }
        } else {
            System.out.println("ERRO: Lista de usuários vazia ou nula.");
        }

    }
    private String formatarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return "NÃO INFORMADO";
        }

        if (nome.length() > 20) {
            return nome.substring(0, 17) + "...";
        }

        return nome;
    }

    private String formatarEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "INVALIDO";
        }

        return email;
    }

    private String formatarCpf(String cpf, boolean mascarar) {
        if (cpf == null || cpf.length() != 11) {
            return "CPF INVALIDO";
        }

        if (mascarar) {
            return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
        }

        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public static void main(String[] args) {
        var usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"));
        usuarios.add(new Usuario(102L, "Ana Maria Silva", "ana.silva@email.com", "98765432100"));
        usuarios.add(new Usuario(103L, "João Pedro de Alcântara Bragança", "joao.pedro@email.com", "45678912345"));
        usuarios.add(new Usuario(104L, "Mariana Costa", "marianacosta.email.com", "11122233344"));
        usuarios.add(new Usuario(105L, "Lucas Mendes", "lucas@email.com", "12345"));
        usuarios.add(new Usuario(106L, "", "beatriz@email.com", "55566677788"));

        var printer = new UsuarioPrinter();
        printer.print(usuarios, true, true, Tema.LIGHT);
    }
}

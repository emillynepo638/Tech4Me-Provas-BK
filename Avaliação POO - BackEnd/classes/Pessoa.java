package classes;

public class Pessoa {

    private String nome;
    private String cpf;

    // Getter e Setter do nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter do CPF
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {

        if (cpf == null || !cpf.matches("[0-9]{11}")) {
            throw new IllegalArgumentException(
                "O CPF deve possuir exatamente 11 dígitos."
            );
        }

        this.cpf = cpf;
    }
}
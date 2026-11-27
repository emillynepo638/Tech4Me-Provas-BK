package classes;

public class Piloto extends Pessoa {

    private String breve;
    private Aeronave[] aeronaves = new Aeronave[10];
    private int qtdAeronaves = 0;

    // Getter e Setter do brevê
    public String getBreve() {
        return breve;
    }

    public void setBreve(String breve) {
        this.breve = breve;
    }

    public Aeronave[] getAeronaves() {
        return aeronaves;
    }

    public int getQtdAeronaves() {
        return qtdAeronaves;
    }

    // Verificar capacidade de armazenamento
    public boolean temEspacoAeronave() {
        return qtdAeronaves < aeronaves.length;
    }

    // Vincular aeronave ao piloto
    public void adicionarAeronave(Aeronave aeronave) {

        if (!temEspacoAeronave()) {
            throw new IllegalStateException(
                "Limite de aeronaves atingido."
            );
        }

        aeronaves[qtdAeronaves] = aeronave;
        qtdAeronaves++;
    }

    @Override
    public String toString() {

        String dados = "Nome: " + getNome()
            + "\nCPF: " + getCpf()
            + "\nBrevê: " + breve
            + "\nQuantidade de aeronaves: " + qtdAeronaves;

        if (qtdAeronaves == 0) {

            dados += "\nNenhuma aeronave vinculada.";

        } else {

            for (int i = 0; i < qtdAeronaves; i++) {

                dados += "\n\nAeronave " + (i + 1) + ":\n"
                    + aeronaves[i].toString();
            }
        }

        return dados;
    }
}
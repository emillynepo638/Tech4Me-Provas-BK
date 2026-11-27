package classes;

public class Aeronave {

    private String modelo;
    private String numeroSerie;

    // Construtor
    public Aeronave(String modelo, String numeroSerie) {
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
    }

    // Getters e Setters
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Override
    public String toString() {
        return "Modelo: " + modelo
            + "\nNúmero de série: " + numeroSerie;
    }
}
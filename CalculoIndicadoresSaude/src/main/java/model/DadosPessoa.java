package model;

public class DadosPessoa {

    /*
     * Dados de altura do usuario
     */
    private double altura;

    /*
     * Dados de peso do usuario
     */
    private double peso;

    /*
     * Verifica se usuario eh homem
     */
    private boolean masculino;

    // Construtor privado para ser usado pelo Builder
    private DadosPessoa(Builder builder) {
        this.altura = builder.altura;
        this.peso = builder.peso;
        this.masculino = builder.masculino;
    }

    // Getters
    public double getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public boolean isMasculino() {
        return masculino;
    }

    // Classe Builder
    public static class Builder {
        private double altura;
        private double peso;
        private boolean masculino;

        // Método para setar a altura
        public Builder altura(double altura) {
            this.altura = altura;
            return this; // Retorna o próprio builder para encadeamento de métodos
        }

        // Método para setar o peso
        public Builder peso(double peso) {
            this.peso = peso;
            return this;
        }

        // Método para setar o sexo
        public Builder masculino(boolean masculino) {
            this.masculino = masculino;
            return this;
        }

        // Método para construir a instância de DadosPessoa
        public DadosPessoa build() {
            return new DadosPessoa(this);
        }
    }

}

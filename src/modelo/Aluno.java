package modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Aluno {
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private double peso;
    private double altura;
    private double imc;

    public Aluno(String cpf, String nome, LocalDate dataNascimento, double peso, double altura) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void calculaIMC() {
        this.imc = peso / (altura * altura);
    }

    public void gravarIMCEmArquivo() throws IOException {
        String resultadoImc = (imc < 18.5) ? "Abaixo do peso" :
                (imc < 24.9) ? "Peso normal" :
                        (imc < 29.9) ? "Sobrepeso" : "Obesidade";

        String nomeArquivo = "IMC_" + nome + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write("Data do Cálculo: " + LocalDate.now());
            writer.newLine();
            writer.write("CPF: " + cpf);
            writer.newLine();
            writer.write("Nome: " + nome);
            writer.newLine();
            writer.write("IMC: " + String.format("%.2f", imc));
            writer.newLine();
            writer.write("Resultado: " + resultadoImc);
            writer.newLine();
            writer.newLine();
        }
    }
}
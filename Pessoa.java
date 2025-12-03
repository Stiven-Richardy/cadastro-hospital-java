/*
IFSP - CAMPUS CUBATÃO
TURMA: ADS 471 - LINGUAGEM DE PROGRAMAÇÃO II
INTEGRANTES:
-> Guilherme Mendes de Sousa
-> Stiven Richardy Silva Rodrigues
*/

import java.util.UUID;

class Pessoa {
    private UUID uuid;
    private String nome;
    private int idade;
    private float peso;
    private float altura;

    public String getUuid() {
        return uuid.toString();
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public float getPeso() {
        return peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public Pessoa(String nome, int idade, float peso, float altura) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: Nome vazio.");
        }

        if (idade < 0 || idade > 100) {
            throw new IllegalArgumentException("Erro: Idade inválida.");
        }

        if (peso < 0 || peso > 635) {
            throw new IllegalArgumentException("Erro: Peso inválido.");
        }

        if (altura < 0 || altura > 2.72) {
            throw new IllegalArgumentException("Erro: Altura inválida.");
        }

        uuid = UUID.randomUUID();
        setNome(nome);
        setIdade(idade);
        setPeso(peso);
        setAltura(altura);
    }
}
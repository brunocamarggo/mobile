package com.transcargo.model;

public class Funcionario {
    private  int id;
    private String nome;
    private String dataNasc;
    private String telefone;
    private int sexo;
    private int cargo;
    private int estado;

    public Funcionario() {}

    public Funcionario(int id, String nome, String dataNasc, String telefone, int sexo, int cargo, int estado) {
        this.id = id;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.sexo = sexo;
        this.cargo = cargo;
        this.estado = estado;
    }

    public Funcionario(String nome, String dataNasc, String telefone, int sexo, int cargo, int estado) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.sexo = sexo;
        this.cargo = cargo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}

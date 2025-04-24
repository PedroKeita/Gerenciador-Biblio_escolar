package org.model;

import java.sql.Date;
import java.time.LocalDate;

public class Emprestimo {
    private int id_emprestimo;
    private int id_aluno;
    private int id_livro;
    private LocalDate  data_emprestimo;
    private LocalDate  data_devolucao;

    public Emprestimo() {}

    public Emprestimo(int id_emprestimo, int id_aluno, int id_livro, LocalDate  data_emprestimo, LocalDate  data_devolucao) {
        this.id_emprestimo = id_emprestimo;
        this.id_aluno = id_aluno;
        this.id_livro = id_livro;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
    }

    public int getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(int id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public int getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public LocalDate  getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(LocalDate  data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public LocalDate  getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(LocalDate  data_devolucao) {
        this.data_devolucao = data_devolucao;
    }
}

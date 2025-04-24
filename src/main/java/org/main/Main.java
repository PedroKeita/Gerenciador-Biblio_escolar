package org.main;


import org.dao.AlunoDAO;
import org.dao.EmprestimoDAO;
import org.dao.LivroDAO;
import org.model.Aluno;
import org.model.Emprestimo;
import org.model.Livro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      AlunoDAO alunoDAO = new AlunoDAO();
      EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
      LivroDAO livroDAO = new LivroDAO();


    while(true) {
        System.out.println("\n=== GERENCIADOR DE BIBLIOTECA ===");
        System.out.println("1. Cadastrar Aluno");
        System.out.println("2. Cadastrar Livro");
        System.out.println("3. Realizar Empréstimo");
        System.out.println("4. Devolver Empréstimo");
        System.out.println("5. Listar Alunos");
        System.out.println("6. Listar Livros");
        System.out.println("7. Listar Empréstimos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch(escolha) {
            case 1:
                Aluno aluno = new Aluno();
                System.out.print("Digite o nome do aluno: ");
                aluno.setNome(scanner.nextLine());
                System.out.print("Digite a matricula do aluno: ");
                aluno.setMatricula(scanner.nextLine());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.print("Digite a data de nascimento do aluno no formato (dd/MM/yyyy): ");
                String dataStr = scanner.nextLine();
                LocalDate data = LocalDate.parse(dataStr, formatter);
                aluno.setDataNascimento(data);
                alunoDAO.cadastrar_aluno(aluno);
                break;

            case 2:
                Livro livro = new Livro();
                System.out.print("Título: ");
                livro.setTitulo(scanner.nextLine());
                System.out.print("Autor: ");
                livro.setAutor(scanner.nextLine());
                System.out.print("Ano de Publicação: ");
                livro.setAno_publicacao(scanner.nextInt());
                System.out.print("Quantidade em estoque: ");
                livro.setQuantidade_estoque(scanner.nextInt());
                livroDAO.cadastrarLivro(livro);
                break;

            case 3:
                Emprestimo emp = new Emprestimo();
                System.out.print("ID do Aluno: ");
                emp.setId_aluno(scanner.nextInt());
                System.out.print("ID do Livro: ");
                emp.setId_livro(scanner.nextInt());
                emp.setData_emprestimo(LocalDate.now());
                emp.setData_devolucao(LocalDate.now().plusDays(7));
                boolean sucesso = emprestimoDAO.fazerEmprestimo(emp);
                if (sucesso) System.out.println("Empréstimo realizado com sucesso.");
                break;

            case 4:
                System.out.print("ID do Empréstimo: ");
                int idEmprestimo = scanner.nextInt();
                boolean devolvido = emprestimoDAO.devolverEmprestimo(idEmprestimo);
                if (devolvido) System.out.println("Livro devolvido com sucesso.");
                break;

            case 5:
                List<Aluno> alunos = alunoDAO.listagemAlunos();
                alunos.forEach(a -> System.out.println(
                        "ID: " + a.getId() + " | Nome: " + a.getNome() +
                                " | Matrícula: " + a.getMatricula() + " | Nascimento: " + a.getDataNascimento()));
                break;

            case 6:
                List<Livro> livros = livroDAO.listagemLivros();
                livros.forEach(l -> System.out.println(
                        "ID: " + l.getId_livro() + " | Título: " + l.getTitulo() +
                                " | Autor: " + l.getAutor() + " | Ano: " + l.getAno_publicacao() +
                                " | Estoque: " + l.getQuantidade_estoque()));
                break;

            case 7:
                List<Emprestimo> emprestimos = emprestimoDAO.ListarEmprestimos();
                emprestimos.forEach(e -> System.out.println(
                        "ID: " + e.getId_emprestimo() + " | Aluno: " + e.getId_aluno() +
                                " | Livro: " + e.getId_livro() +
                                " | Empréstimo: " + e.getData_emprestimo() +
                                " | Devolução: " + e.getData_devolucao()));
                break;

            case 0:
                System.out.println("Saindo....");
                break;

            default:
                System.out.println("Opção invalida");
                break;
        }

    }
    }
}
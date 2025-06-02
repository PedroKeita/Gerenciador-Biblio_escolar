package org.dao;

import org.model.Emprestimo;
import org.model.Livro;
import org.util.ConnectionDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    private LivroDAO livroDAO = new LivroDAO();

    public boolean fazerEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO Emprestimos (id_aluno, id_livro, data_emprestimo, data_devolucao) VALUES(?,?,?,?)";

        try(Connection connection = ConnectionDataBase.getConnection()) {

            Livro livro = livroDAO.pesquisaIdLivro(emprestimo.getId_livro());
            if (livro == null || livro.getQuantidade_estoque() <= 0) {
                System.out.println("Livro indisponivel ");
                return false;
            }

            boolean atualizadoEstoque = livroDAO.atualizarEstoque(livro.getId_livro(), livro.getQuantidade_estoque() -1);
            if (!atualizadoEstoque) {
                System.out.println("Erro atualizar estoque");
                return false;
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, emprestimo.getId_aluno());
                statement.setInt(2, emprestimo.getId_livro());
                statement.setDate(3, Date.valueOf(emprestimo.getData_emprestimo()));
                statement.setDate(4, Date.valueOf(emprestimo.getData_devolucao()));

            statement.executeUpdate();
            return true;
            }



        } catch (SQLException e) {
            System.out.println("Erro ao fazer o emprestimo: " + e.getMessage());
            return false;
        }
    }

    public boolean devolverEmprestimo(int idEmprestimo) {
        String sql = "SELECT id_livro FROM Emprestimos WHERE id_emprestimo = ?";

        try(Connection connection = ConnectionDataBase.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, idEmprestimo);
            ResultSet result_set = statement.executeQuery();

            if(result_set.next()){
                int idLivro = result_set.getInt("id_livro");

                Livro livro = livroDAO.pesquisaIdLivro(idLivro);
                if (livro != null) {
                    livroDAO.atualizarEstoque(idLivro, livro.getQuantidade_estoque() + 1);
                }

                String deletarSQL = "DELETE FROM emprestimos WHERE id_emprestimo = ?";
                try (PreparedStatement deletar_statement = connection.prepareStatement(deletarSQL)) {
                    deletar_statement.setInt(1, idEmprestimo);
                    deletar_statement.executeUpdate();
                }
                return true;

            }

        } catch (SQLException e) {
            System.out.println("Erro ao devolver o emprestimo: " + e.getMessage());

        }
        return false;

    }

    public List<Emprestimo> ListarEmprestimos() {
        List<Emprestimo> lista_emprestimo = new ArrayList<>();
        String sql = "Select * FROM Emprestimos";

        try (Connection connection = ConnectionDataBase.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result_set = statement.executeQuery(sql)) {

            while (result_set.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId_emprestimo(result_set.getInt("id_emprestimo"));
                emprestimo.setId_aluno(result_set.getInt("id_aluno"));
                emprestimo.setId_livro(result_set.getInt("id_livro"));
                emprestimo.setData_emprestimo(result_set.getDate("data_emprestimo").toLocalDate());
                emprestimo.setData_devolucao(result_set.getDate("data_devolucao").toLocalDate());

                lista_emprestimo.add(emprestimo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os emprestimos: " + e.getMessage());
        }

        return lista_emprestimo;
    }


}

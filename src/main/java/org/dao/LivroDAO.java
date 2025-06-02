package org.dao;

import org.model.Aluno;
import org.model.Livro;
import org.util.ConnectionDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public boolean cadastrarLivro(Livro livro) {
        String sql = "INSERT INTO Livros (titulo, autor, ano_publicacao, quantidade_estoque) VALUES (?,?,?,?)";

        try (Connection connection = ConnectionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getAutor());
            statement.setInt(3, livro.getAno_publicacao());
            statement.setInt(4, livro.getQuantidade_estoque());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
            return false;
        }
    }

    public List<Livro> listagemLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";

        try (Connection connection = ConnectionDataBase.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result_set = statement.executeQuery(sql)) {

            while(result_set.next()) {
                Livro livro = new Livro();
                livro.setId_livro(result_set.getInt("id_livro"));
                livro.setTitulo(result_set.getString("titulo"));
                livro.setAutor(result_set.getString("autor"));
                livro.setAno_publicacao(result_set.getInt("ano_publicacao"));
                livro.setQuantidade_estoque(result_set.getInt("quantidade_estoque"));

                livros.add(livro);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar os Livros: " + e.getMessage());

        }
        return livros;
    }

    public Livro pesquisaIdLivro(int id) {
        Livro livro = null;
        String sql = "SELECT * FROM Livros WHERE id_livro = ?";
        try (Connection connection = ConnectionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);
            ResultSet result_set = statement.executeQuery();

            if (result_set.next()) {
                livro = new Livro();
                livro.setId_livro(result_set.getInt("id_livro"));
                livro.setTitulo(result_set.getString("titulo"));
                livro.setAutor(result_set.getString("autor"));
                livro.setAno_publicacao(result_set.getInt("ano_publicacao"));
                livro.setQuantidade_estoque(result_set.getInt("quantidade_estoque"));
            }

        } catch (SQLException e){
            System.out.println("Erro ao pesquisar livro: " + e.getMessage());
        }

    return livro;

    }

    public boolean excluirLivro(int id) {
        Livro livro = null;
        String sql = "DELETE FROM Livros WHERE id_livro = ?";

        try(Connection connection = ConnectionDataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir esse livro: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarEstoque(int idLivro, int estoque_atualizado) {
        Livro livro = null;
        String sql = "UPDATE Livros SET quantidade_estoque = ? WHERE id_livro = ?";

        try(Connection connection = ConnectionDataBase.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, estoque_atualizado);
            statement.setInt(2, idLivro);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar estoque livro: " +e.getMessage());
            return false;
        }

    }

}

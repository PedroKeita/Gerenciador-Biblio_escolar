package org.dao;

import org.model.Aluno;
import org.util.ConnectionDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public boolean cadastrar_aluno(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome_aluno, matricula, data_nascimento) VALUES (?,?,?)";

        try (Connection connection = ConnectionDataBase.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getMatricula());
            statement.setDate(3, Date.valueOf(aluno.getDataNascimento()));

            statement.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");
            return true;


        } catch(SQLException e) {
            System.out.println("Erro ao cadastrar esse aluno: " +e.getMessage());
            return false;
        }
    }

    public List<Aluno> listagemAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";

        try (Connection connection = ConnectionDataBase.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result_set = statement.executeQuery(sql)) {

            while (result_set.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(result_set.getInt("id_aluno"));
                aluno.setNome(result_set.getString("nome_aluno"));
                aluno.setMatricula(result_set.getString("matricula"));
                aluno.setDataNascimento(result_set.getDate("data_nascimento").toLocalDate());

                alunos.add(aluno);
            }

        } catch(SQLException e) {
            System.out.println("Erro ao listar os alunos: " +e.getMessage());
        }
        return alunos;
    }


    public Aluno pesquisaIdAluno(int id) {
        Aluno aluno = null;
        String sql = "SELECT * FROM alunos WHERE id_aluno = ?";

        try (Connection connection = ConnectionDataBase.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result_set = statement.executeQuery();

            if(result_set.next()) {
                aluno = new Aluno();
                aluno.setId(result_set.getInt("id_aluno"));
                aluno.setNome(result_set.getString("nome_aluno"));
                aluno.setMatricula(result_set.getString("matricula"));
                aluno.setDataNascimento(result_set.getDate("data_nascimento").toLocalDate());

            }

        } catch(SQLException e) {
            System.out.println("Erro ao pesquisar esse aluno: " +e.getMessage());
        }

        return aluno;
    }

    public boolean excluirAluno(int id) {
        Aluno aluno = null;
        String sql = "DELETE FROM alunos WHERE id_aluno = ?";

        try(Connection connection = ConnectionDataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir esse aluno: " +e.getMessage());
            return false;
        }
    }
}

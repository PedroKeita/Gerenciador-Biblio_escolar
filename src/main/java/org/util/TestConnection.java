package org.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Tenta obter a conexão
            Connection connection = ConnectionDataBase.getConnection();

            // Verifica se a conexão foi estabelecida
            if (connection != null) {
                System.out.println("Conexão bem-sucedida com o banco de dados!");
            }

            // Fecha a conexão
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}

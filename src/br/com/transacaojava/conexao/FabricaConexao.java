package br.com.transacaojava.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    
    public static Connection getConnection() {
        //utilizando o padrão factory, que encapsula a criacao de um objeto
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/banco?useTimezone=true&serverTimezone=UTC","root",""
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
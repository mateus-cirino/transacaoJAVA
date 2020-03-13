package br.com.transacaojava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import br.com.transacaojava.conexao.FabricaConexao;
import br.com.transacaojava.modelos.ContaModel;

public class ContaDAO {

    public static void inserir(ContaModel conta) {
        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into conta( descricao, saldo ) values (?, ?)";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, conta.getDescricao());
            pst.setDouble(2, conta.getSaldo());

            pst.execute();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o objeto: " + e.getMessage());
        }

        FabricaConexao.closeConnection(connection);
    }

    public static void update(ContaModel conta) {
        Connection connection = FabricaConexao.getConnection();

        String sql = "update conta set descricao = ?, saldo = ? where id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, conta.getDescricao());
            pst.setDouble(2, conta.getSaldo());
            pst.setInt(3, conta.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o objeto: " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection(connection);
        }
    }

    public static void delete(ContaModel conta) {
        Connection connection = FabricaConexao.getConnection();

        String sql = "delete from conta where id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setInt(1, conta.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o objeto: " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection(connection);
        }
    }

    public static Collection<ContaModel> select(int id) {
        Connection connection = FabricaConexao.getConnection();

        ContaModel conta = new ContaModel();
        
        Collection<ContaModel> contas = new LinkedList<>();
    
        String sql = "select * from conta where id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setInt(1, id);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()){
                conta.setId(Resultado.getInt("id"));
                conta.setDescricao(Resultado.getString("descricao"));
                conta.setSaldo(Resultado.getDouble("saldo"));
                contas.add(conta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o objeto " + id  + " : " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection(connection);
        }
        return contas;
    }

    public static Collection<ContaModel> selectAll() {
        Connection connection = FabricaConexao.getConnection();

        ContaModel conta = new ContaModel();

        Collection<ContaModel> contas = new LinkedList<>();
        
        String sql = "select * from conta";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            
            ResultSet Resultado = pst.executeQuery();

            while(Resultado.next()){
                conta.setId(Resultado.getInt("id"));
                conta.setDescricao(Resultado.getString("descricao"));
                conta.setSaldo(Resultado.getDouble("saldo"));

                contas.add(conta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar os objetos : " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection(connection);
        }
        return contas;
    }
}
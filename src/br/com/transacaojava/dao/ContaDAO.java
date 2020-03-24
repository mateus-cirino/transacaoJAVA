package br.com.transacaojava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import br.com.transacaojava.conexao.FabricaConexao;
import br.com.transacaojava.modelos.Conta;

public class ContaDao {

    public static void inserir(Conta conta) {
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

        FabricaConexao.closeConnection();
    }

    public static void update(Conta conta) {
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
            FabricaConexao.closeConnection();
        }
    }

    public static void delete(Conta conta) {
        Connection connection = FabricaConexao.getConnection();

        String sql = "delete from conta where id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setInt(1, conta.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o objeto: " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection();
        }
    }

    public static Collection<Conta> select(int id) {
        Connection connection = FabricaConexao.getConnection();
        
        Collection<Conta> contas = new LinkedList<>();
    
        String sql = "select * from conta where id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setInt(1, id);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()){
                Conta conta = new Conta();

                conta.setId(Resultado.getInt("id"));
                conta.setDescricao(Resultado.getString("descricao"));
                conta.setSaldo(Resultado.getDouble("saldo"));

                contas.add(conta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o objeto " + id  + " : " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection();
        }
        return contas;
    }

    public static Collection<Conta> selectAll() {
        Connection connection = FabricaConexao.getConnection();

        Collection<Conta> contas = new LinkedList<>();
        
        String sql = "select * from conta";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()) {
                Conta conta = new Conta();
                do {
                    conta.setId(Resultado.getInt("id"));
                    conta.setDescricao(Resultado.getString("descricao"));
                    conta.setSaldo(Resultado.getDouble("saldo"));
    
                    contas.add(conta);
                
                }while(Resultado.next());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar os objetos : " + e.getMessage());
        }finally {
            FabricaConexao.closeConnection();
        }
        return contas;
    }
}
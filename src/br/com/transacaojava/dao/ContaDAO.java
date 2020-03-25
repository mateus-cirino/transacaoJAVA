package br.com.transacaojava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import br.com.transacaojava.conexao.FabricaConexaoSingleton;
import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.modelos.Conta;

public class ContaDao {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    //sobrecarga de construtor
    public ContaDao() {
        this.connection = FabricaConexaoSingleton.getConnection();
    }

    public ContaDao(int nivelIsolamento) {
        FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();
        this.connection = fabricaConexaoTransacional.getConnection(nivelIsolamento);
    }

    public void inserir(Conta conta) {
        String sql = "insert into conta( descricao, saldo ) values (?, ?)";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);

            pst.setString(1, conta.getDescricao());
            pst.setDouble(2, conta.getSaldo());

            pst.execute();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o objeto: " + e.getMessage());
        }

        //FabricaConexaoSingleton.closeConnection();
    }

    public void update(Conta conta) {
        String sql = "update conta set descricao = ?, saldo = ? where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);

            pst.setString(1, conta.getDescricao());
            pst.setDouble(2, conta.getSaldo());
            pst.setInt(3, conta.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o objeto: " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
    }

    public void delete(Conta conta) {
        String sql = "delete from conta where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);

            pst.setInt(1, conta.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o objeto: " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
    }

    public Collection<Conta> select(int id) {
        Collection<Conta> contas = new LinkedList<>();
    
        String sql = "select * from conta where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);
            
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
            //FabricaConexaoSingleton.closeConnection();
        }
        return contas;
    }

    public Collection<Conta> selectAll() {
        Collection<Conta> contas = new LinkedList<>();
        
        String sql = "select * from conta";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()) {
                do {
                    Conta conta = new Conta();

                    conta.setId(Resultado.getInt("id"));
                    conta.setDescricao(Resultado.getString("descricao"));
                    conta.setSaldo(Resultado.getDouble("saldo"));

                    contas.add(conta);
                
                }while(Resultado.next());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar os objetos : " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
        return contas;
    }
}
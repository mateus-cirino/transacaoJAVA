package br.com.transacaojava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import br.com.transacaojava.conexao.FabricaConexaoSingleton;
import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.modelos.Extrato;

public class ExtratoDao {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    //sobrecarga de construtor
    public ExtratoDao() {
        this.connection = FabricaConexaoSingleton.getConnection();
    }

    public ExtratoDao(int nivelIsolamento) {
        FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();
        this.connection = fabricaConexaoTransacional.getConnection(nivelIsolamento);
    }
    //sobrecarga de método para trabalhar com uma conexao transacional existente
    public ExtratoDao(Connection conexao) {
        this.connection = conexao;
    }

    public void inserir(Extrato extrato) {
        String sql = "insert into extrato( descricao, valor, tipo, idConta ) values (?, ?, ?, ?)";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);

            pst.setString(1, extrato.getDescricao());
            pst.setDouble(2, extrato.getValor());
            pst.setString(3, extrato.getTipo().name());
            pst.setInt(4, extrato.getidConta());

            pst.execute();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o objeto: " + e.getMessage());
        }

        //FabricaConexaoSingleton.closeConnection();
    }

    public void update(Extrato extrato) {
        String sql = "update extrato set descricao = ?, valor = ?, tipo = ?, idConta = ? where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);

            pst.setString(1, extrato.getDescricao());
            pst.setDouble(2, extrato.getValor());
            pst.setString(3, extrato.getTipo().name());
            pst.setInt(4, extrato.getidConta());
            pst.setInt(5, extrato.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o objeto: " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
    }

    public void delete(Extrato extrato) {
        String sql = "delete from extrato where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);

            pst.setInt(1, extrato.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o objeto: " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
    }

    public Collection<Extrato> select(int id) {
        Collection<Extrato> extratos = new LinkedList<>();
    
        String sql = "select * from extrato where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);
            
            pst.setInt(1, id);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()){
                Extrato extrato = new Extrato();

                extrato.setId(Resultado.getInt("id"));
                extrato.setDescricao(Resultado.getString("descricao"));
                extrato.setValor(Resultado.getDouble("valor"));
                extrato.setTipo(Extrato.Operacao.valueOf(Resultado.getString("tipo")));
                extrato.setidConta(Resultado.getInt("idConta"));

                extratos.add(extrato);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o objeto " + id  + " : " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
        return extratos;
    }

    public Collection<Extrato> selectAll() {
        Collection<Extrato> extratos = new LinkedList<>();
        
        String sql = "select * from extrato";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()) {
                do {
                    Extrato extrato = new Extrato();

                    extrato.setId(Resultado.getInt("id"));
                    extrato.setDescricao(Resultado.getString("descricao"));
                    extrato.setValor(Resultado.getDouble("valor"));
                    extrato.setTipo(Extrato.Operacao.valueOf(Resultado.getString("tipo")));
                    extrato.setidConta(Resultado.getInt("idConta"));

                    extratos.add(extrato);
                
                }while(Resultado.next());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar os objetos : " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
        return extratos;
    }
}
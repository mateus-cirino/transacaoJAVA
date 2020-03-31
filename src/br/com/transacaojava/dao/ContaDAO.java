package br.com.transacaojava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import br.com.transacaojava.conexao.FabricaConexaoSingleton;
import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.dao.fachadas.ContaFachada;
import br.com.transacaojava.modelos.Conta;

public class ContaDao {
    private Connection connection;
    private int nivelIsolamento = Connection.TRANSACTION_REPEATABLE_READ; //valor default do nível do
                                                                          //isolamento

    public Connection getConnection() {
        return connection;
    }

    //sobrecarga de construtor
    public ContaDao() {
        this.connection = FabricaConexaoSingleton.getConnection();
    }

    public ContaDao(int nivelIsolamento) {
        this.nivelIsolamento = nivelIsolamento;
        FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();
        this.connection = fabricaConexaoTransacional.getConnection(this.nivelIsolamento);
    }

    //sobrecarga de método para trabalhar com uma conexao transacional existente
    public ContaDao(Connection conexao) {
        this.connection = conexao;
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

    public Optional<Conta> select(int id) {
        Conta conta = new Conta();
    
        String sql = "select * from conta where id = ?";

        try {
            PreparedStatement pst = this.connection.prepareStatement(sql);
            
            pst.setInt(1, id);
            
            ResultSet Resultado = pst.executeQuery();

            if(Resultado.next()){
                conta.setId(Resultado.getInt("id"));
                conta.setDescricao(Resultado.getString("descricao"));
                conta.setSaldo(Resultado.getDouble("saldo"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o objeto " + id  + " : " + e.getMessage());
        }finally {
            //FabricaConexaoSingleton.closeConnection();
        }
        return Optional.ofNullable(conta);
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
    
    /* 
        Atenção:
        Os métodos abaixo utilizam uma nova conexão, isolada da conexão principal da classe
        (this.connection).
        Por que disso? Os métodos abaixo utilizam uma conexão Transacional, então iremos
        evitar possíveis conflitos com instâncias da classe que estejam operando no formato Singleton.
    */

    public void saca (Conta contaASerSacada, Double valorASerSacado) {
        try {
            FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();

            Connection conexao = fabricaConexaoTransacional.getConnection(this.nivelIsolamento);

            ContaFachada.saca(contaASerSacada, valorASerSacado, conexao);
        } catch (Exception e) {
            System.err.println("Erro ao utilizar o método deposita : " + e.getMessage());
        }
    }

    public void deposita (Conta contaASerDepositada, Double valorASerDepositado) {
        try {
            FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();

            Connection conexao = fabricaConexaoTransacional.getConnection(this.nivelIsolamento);

            ContaFachada.deposita(contaASerDepositada, valorASerDepositado, conexao);
        } catch (Exception e) {
            System.err.println("Erro ao utilizar o método deposita : " + e.getMessage());
        }
    }

    public void transferencia (Conta contaASerSacada, Conta contaASerDepositada, Double valorASerSacado) {
        try {
            FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();

            Connection conexao = fabricaConexaoTransacional.getConnection(this.nivelIsolamento);

            ContaFachada.transferencia(contaASerSacada, contaASerDepositada, valorASerSacado, conexao);
        } catch (Exception e) {
            System.err.println("Erro ao utilizar o método transferencia : " + e.getMessage());
        }
    }

    public void rendimento (Conta contaASerDepositada, Double rendimento) {
        try {
            FabricaConexaoTransacional fabricaConexaoTransacional = new FabricaConexaoTransacional();

            Connection conexao = fabricaConexaoTransacional.getConnection(this.nivelIsolamento);

            ContaFachada.rendimento(contaASerDepositada, rendimento, conexao);
        } catch (Exception e) {
            System.err.println("Erro ao utilizar o método deposita : " + e.getMessage());
        }
    }
}
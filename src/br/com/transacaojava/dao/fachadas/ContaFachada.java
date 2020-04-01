package br.com.transacaojava.dao.fachadas;

import java.sql.Connection;

import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.dao.ExtratoDao;
import br.com.transacaojava.modelos.Conta;
import br.com.transacaojava.modelos.Extrato;

public class ContaFachada {
    public static void saca (Conta contaASerSacada, Double valorASerSacado, Connection conexao) {         
        try {
            Extrato extrato = new Extrato();
            extrato.setDescricao("Saque da conta "
                                + contaASerSacada.getId());
            extrato.setTipo(Extrato.Operacao.S);  
            extrato.setValor(valorASerSacado);
            extrato.setidConta(contaASerSacada.getId());
    
            ExtratoDao extratoDao = new ExtratoDao(conexao);
            extratoDao.inserir(extrato);
    
            contaASerSacada.setSaldo(contaASerSacada.getSaldo() - valorASerSacado);

            ContaDao contaDao = new ContaDao(conexao);
            contaDao.update(contaASerSacada);
    
            FabricaConexaoTransacional.commitTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
        } catch (Exception e) {
            FabricaConexaoTransacional.rollbackTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
            throw new RuntimeException("Não foi possível efetuar a operacao de "
                                        + "saque"
                                        + "\nError: " + e.getMessage());
        }
            


        
    }

    public static void deposita (Conta contaASerDepositada, Double valorASerDepositado, Connection conexao) {
        try {
            Extrato extrato = new Extrato();
            extrato.setDescricao("Deposito na conta "
                                + contaASerDepositada.getId());
            extrato.setTipo(Extrato.Operacao.E);  
            extrato.setValor(valorASerDepositado);
            extrato.setidConta(contaASerDepositada.getId());

            ExtratoDao extratoDao = new ExtratoDao(conexao);
            extratoDao.inserir(extrato);

            contaASerDepositada.setSaldo(contaASerDepositada.getSaldo() + valorASerDepositado);

            ContaDao contaDao = new ContaDao(conexao);
            contaDao.update(contaASerDepositada);
    
            FabricaConexaoTransacional.commitTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
        } catch (Exception e) {
            FabricaConexaoTransacional.rollbackTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
            throw new RuntimeException("Não foi possível efetuar a operacao de "
                                        + "deposito"
                                        + "\nError: " + e.getMessage());
        }
    }

    public static void transferencia (Conta contaASerSacada, Conta contaASerDepositada, Double valorASerSacado, Connection conexao) {
        try {
            ExtratoDao extratoDao = new ExtratoDao(conexao);

            Extrato extrato = new Extrato();
            extrato.setDescricao("Saque na conta "
                                + contaASerSacada.getId());
            extrato.setTipo(Extrato.Operacao.S);  
            extrato.setValor(valorASerSacado);
            extrato.setidConta(contaASerSacada.getId());

            extratoDao.inserir(extrato);

            extrato.setDescricao("Deposito na conta "
                                + contaASerDepositada.getId());
            extrato.setTipo(Extrato.Operacao.E);  
            extrato.setValor(valorASerSacado);
            extrato.setidConta(contaASerDepositada.getId());

            extratoDao.inserir(extrato);

            contaASerSacada.setSaldo(contaASerSacada.getSaldo() - valorASerSacado);
            contaASerDepositada.setSaldo(contaASerDepositada.getSaldo() + valorASerSacado);

            ContaDao contaDao = new ContaDao(conexao);

            contaDao.update(contaASerSacada);
            contaDao.update(contaASerDepositada);
    
            FabricaConexaoTransacional.commitTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
        } catch (Exception e) {
            FabricaConexaoTransacional.rollbackTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
            throw new RuntimeException("Não foi possível efetuar a operacao de "
                                        + "transferencia"
                                        + "\nError: " + e.getMessage());
        }
            
    }

    public static void rendimento (Conta contaASerDepositada, Double rendimento, Connection conexao) {
        try {
            Extrato extrato = new Extrato();
            extrato.setDescricao("Deposito na conta "
                                + contaASerDepositada.getId());
            extrato.setTipo(Extrato.Operacao.E);  
            extrato.setValor(contaASerDepositada.getSaldo() * rendimento);
            extrato.setidConta(contaASerDepositada.getId());
    
            ExtratoDao extratoDao = new ExtratoDao(conexao);
            extratoDao.inserir(extrato);

            contaASerDepositada.setSaldo(contaASerDepositada.getSaldo() * rendimento);
            
            ContaDao contaDao = new ContaDao(conexao);
            contaDao.update(contaASerDepositada);

            FabricaConexaoTransacional.commitTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
        } catch (Exception e) {
            FabricaConexaoTransacional.rollbackTransacao(conexao);
            FabricaConexaoTransacional.closeConnection(conexao);
            throw new RuntimeException("Não foi possível efetuar a operacao de "
                                        + "rendimento"
                                        + "\nError: " + e.getMessage());
        } 
    }
}
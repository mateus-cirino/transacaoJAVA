package br.com.transacaojava.dao.fachadas;

import java.sql.Connection;

import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.dao.ExtratoDao;
import br.com.transacaojava.modelos.Conta;
import br.com.transacaojava.modelos.Extrato;

public class ContaFachada {
    public static void saca (Conta contaASerSacada, Double valorASerSacado, Connection conexao) {
        ContaDao contaDao = new ContaDao(conexao);
         
        if(contaDao.select(contaASerSacada.getId()).isPresent()){
            try {
                Extrato extrato = new Extrato();
                extrato.setDescricao("Saque da conta "
                                    + contaASerSacada.getId());
                extrato.setTipo(Extrato.Operacao.S);  
                extrato.setValor(valorASerSacado);
                extrato.setidConta(contaASerSacada.getId());
    
                ExtratoDao extratoDao = new ExtratoDao(conexao);
                extratoDao.inserir(extrato);
    
                Conta conta = contaDao.select(contaASerSacada.getId()).get();
                conta.setSaldo(conta.getSaldo() - valorASerSacado);
                contaDao.update(conta);
    
                FabricaConexaoTransacional.commitTransacao(conexao);
                FabricaConexaoTransacional.closeConnection(conexao);
            } catch (Exception e) {
                FabricaConexaoTransacional.rollbackTransacao(conexao);
                FabricaConexaoTransacional.closeConnection(conexao);
                throw new RuntimeException("Não foi possível efetuar a operacao de "
                                            + "saque"
                                            + "\nError: " + e.getMessage());
            }
            
        }else{
            throw new RuntimeException("Não existe uma conta associada a este"
                                + " id no banco");
        }


        
    }

    public static void deposita (Conta contaASerDepositada, Double valorASerDepositado, Connection conexao) {
        ContaDao contaDao = new ContaDao(conexao);
         
        if(contaDao.select(contaASerDepositada.getId()).isPresent()){
            try {
                Extrato extrato = new Extrato();
                extrato.setDescricao("Deposito na conta "
                                    + contaASerDepositada.getId());
                extrato.setTipo(Extrato.Operacao.E);  
                extrato.setValor(valorASerDepositado);
                extrato.setidConta(contaASerDepositada.getId());
    
                ExtratoDao extratoDao = new ExtratoDao(conexao);
                extratoDao.inserir(extrato);
    
                Conta conta = contaDao.select(contaASerDepositada.getId()).get();
                conta.setSaldo(conta.getSaldo() + valorASerDepositado);
                contaDao.update(conta);
    
                FabricaConexaoTransacional.commitTransacao(conexao);
                FabricaConexaoTransacional.closeConnection(conexao);
            } catch (Exception e) {
                FabricaConexaoTransacional.rollbackTransacao(conexao);
                FabricaConexaoTransacional.closeConnection(conexao);
                throw new RuntimeException("Não foi possível efetuar a operacao de "
                                            + "deposito"
                                            + "\nError: " + e.getMessage());
            }
            
        }else{
            throw new RuntimeException("Não existe uma conta associada a este"
                                + " id no banco");
        }


        
    }
}
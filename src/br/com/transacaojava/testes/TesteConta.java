package br.com.transacaojava.testes;

import java.util.Collection;

import br.com.transacaojava.dao.ContaDAO;
import br.com.transacaojava.modelos.*;

public class TesteConta {
    public static void main(String[] args) {
        Collection<Conta> contas = ContaDAO.selectAll();
        
    }
}
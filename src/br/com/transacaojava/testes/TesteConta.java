package br.com.transacaojava.testes;

import java.util.Collection;

import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.modelos.*;

public class TesteConta {
    public static void main(String[] args) {
        Collection<Conta> contas = ContaDao.selectAll();
        
    }
}
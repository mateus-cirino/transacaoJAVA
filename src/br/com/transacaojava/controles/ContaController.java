package br.com.transacaojava.controles;

import java.sql.Connection;
import java.util.Collection;
import java.util.LinkedList;

import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.modelos.*;

public class ContaController {

    public void testeTransacao() {
        ContaDao contaDao = new ContaDao(Connection.TRANSACTION_SERIALIZABLE);

        try {
            Conta contaMateus = new Conta();
            Conta contaFelipe = new Conta();

            LinkedList<Conta> contas =  (LinkedList<Conta>) contaDao.select(17);

            contaMateus = contas.getFirst();

            contas =  (LinkedList<Conta>) contaDao.select(19);

            contaFelipe = contas.getFirst();

            Double valorASerRetirado = 200.0;

            contaMateus.setSaldo(contaMateus.getSaldo() - valorASerRetirado);

            contaDao.update(contaMateus);

            contaFelipe.setSaldo(contaFelipe.getSaldo() + valorASerRetirado);

            contaDao.update(contaFelipe);

            FabricaConexaoTransacional.commitTransacao(contaDao.getConnection());
            FabricaConexaoTransacional.closeConnection(contaDao.getConnection());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
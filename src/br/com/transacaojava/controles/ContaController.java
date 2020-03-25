package br.com.transacaojava.controles;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Optional;

import br.com.transacaojava.conexao.FabricaConexaoTransacional;
import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.modelos.*;

public class ContaController {

    public void testeTransacao() {
        ContaDao contaDao = new ContaDao(Connection.TRANSACTION_SERIALIZABLE);

        try {            
            contaDao.select(17)
                    .ifPresent(c1 -> {
                        contaDao.select(19)
                                .ifPresent(c2 -> {
                                    Double valorASerRetirado = 100.0;
                                    c1.setSaldo(c1.getSaldo() - valorASerRetirado);

                                    contaDao.update(c1);

                                    c2.setSaldo(c2.getSaldo() + valorASerRetirado);

                                    contaDao.update(c2);

                                    FabricaConexaoTransacional.commitTransacao(contaDao.getConnection());
                                    FabricaConexaoTransacional.closeConnection(contaDao.getConnection());
                                });
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
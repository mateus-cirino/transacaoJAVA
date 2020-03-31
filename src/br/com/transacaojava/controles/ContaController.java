package br.com.transacaojava.controles;


import java.util.Optional;

import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.modelos.Conta;

public class ContaController {

    public void testeTransacao(int idContaASerDepositada, Double rendimento) {
        ContaDao contaDao = new ContaDao();
        
        //Optional<Conta> conta = contaDao.select(idContaASerSacada);
        Optional<Conta> conta2 = contaDao.select(idContaASerDepositada);

        if(conta2.isPresent()) {
            Conta contaASerDepositada = conta2.get();
            contaDao.rendimento(contaASerDepositada, rendimento);
        }

        /*if(conta.isPresent() && conta2.isPresent()) {
            Conta contaASerSacada = conta.get();
            Conta contaASerDepositada = conta2.get();

            contaDao.transferencia(contaASerSacada, contaASerDepositada, valorASerSacado);
        }*/

        
        /*if(conta.isPresent()) {
            Conta contaASerDepositada = conta.get();
            Double valorASerDepositado = 1054.0;
            
            contaDao.deposita(contaASerDepositada, valorASerDepositado);
        }*/

        /*if(conta.isPresent()) {
            Conta contaASerSacada = conta.get();
            Double valorASerSacado = 100.0;
            
            contaDao.saca(contaASerSacada, valorASerSacado);
        }else {
            throw new RuntimeException("Não foi encontrada a conta de id: "
                            + idContaASerSacada);
        }*/

    }
}
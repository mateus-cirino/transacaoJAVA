package br.com.transacaojava.controles;


import java.util.Optional;

import br.com.transacaojava.dao.ContaDao;
import br.com.transacaojava.modelos.Conta;

public class ContaController {

    public void testeTransacao() {
        ContaDao contaDao = new ContaDao();
        
        Optional<Conta> conta = contaDao.select(28);
        
        if(conta.isPresent()) {
            Conta contaASerSacada = conta.get();
            Double valorASerSacado = 200.0;
            
            contaDao.saca(contaASerSacada, valorASerSacado);
        }else {
            throw new RuntimeException("Não foi encontrada a conta de id: "
                            + 28);
        }

    }
}
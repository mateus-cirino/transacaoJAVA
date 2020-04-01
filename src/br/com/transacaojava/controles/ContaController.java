package br.com.transacaojava.controles;

import br.com.transacaojava.dao.ContaDao;

public class ContaController {

    public void saca(int idContaASerSacada, double valorASerSacado) {
        ContaDao contaDao = new ContaDao(); 
        contaDao.saca(idContaASerSacada, valorASerSacado);
    }

    public void deposita(int idContaASerDepositada, double valorASerDepositado) {
        ContaDao contaDao = new ContaDao(); 
        contaDao.deposita(idContaASerDepositada, valorASerDepositado);
    }

    public void transferencia(int idContaASerSacada, int idContaASerDepositada, double valorASerSacado) {
        ContaDao contaDao = new ContaDao();
        contaDao.transferencia(idContaASerSacada, idContaASerDepositada, valorASerSacado);
    }

    public void rendimento(int idContaASerDepositada, double rendimento) {
        ContaDao contaDao = new ContaDao();
        contaDao.rendimento(idContaASerDepositada, rendimento);
    }

}
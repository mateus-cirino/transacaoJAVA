package br.com.transacaojava.testes;

import java.util.Collection;
import br.com.transacaojava.dao.*;
import br.com.transacaojava.modelos.*;

public class TesteExtrato {
    public static void main(String[] args) {

        ExtratoDao extratoDao = new ExtratoDao();

        Collection<Extrato> extratos = extratoDao.select(1);

        extratos.forEach((e) -> System.out.println(e.getTipo()));

        Extrato extrato = new Extrato();

        extrato.setId(2);
        extrato.setDescricao("Extrato2");
        extrato.setValor(10.0);
        extrato.setTipo(Extrato.Operacao.E);
        extrato.setidConta(17);

        extratoDao.update(extrato);
    }
}
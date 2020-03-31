package br.com.transacaojava.modelos;

public class Extrato {
    private Integer id;
    private String descricao;
    private Double valor;
    public enum Conta {
        E("Entrada"),
        S("Saída");
     
        private String descricao;
     
        Conta(String descricao) {
            this.descricao = descricao;
        }
     
        public String getDescricao() {
            return descricao;
        }
    }
    private Conta tipo;
    private int idConta;

    public Integer getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Double getValor() {
        return this.valor;
    }

    public Conta getTipo() {
        return this.tipo;
    }

    public int getidConta() {
        return this.idConta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setTipo(Conta tipo) {
        this.tipo = tipo;
    }

    public void setidConta(int idConta) {
        this.idConta = idConta;
    }
}
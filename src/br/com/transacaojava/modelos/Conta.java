package br.com.transacaojava.modelos;

public class Conta {
    private int id;
    private String descricao;
    private Double saldo;

    /**
     * @return the descricao
     */
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * @return the saldo
     */
    public Double getSaldo() {
        return saldo;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(Double saldo) {
        if(saldo < 0){
            throw new RuntimeException("saldo inválido");
        }
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "codigo: " + this.id + " descricao: " + this.descricao + " saldo: " + this.saldo;
    }
}
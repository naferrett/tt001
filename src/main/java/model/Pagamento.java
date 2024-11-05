package model;

import lombok.Data;

@Data
public class Pagamento {
    private int codigo;
    private double valor;
    private double valorPago;
    private boolean consultaPaga;
    private String dataPagamento;
    private int consulta_id;
}

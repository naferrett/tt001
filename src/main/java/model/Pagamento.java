package model;

import lombok.Data;

@Data
public class Pagamento {
    private int codigo;
    private double valor;
    private boolean consultaPaga;
    private int consulta_id;
}

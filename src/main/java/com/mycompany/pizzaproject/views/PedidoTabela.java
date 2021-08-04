/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.PedidoTb;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class PedidoTabela extends TableModel<PedidoTb>{
    
    public PedidoTabela(List<PedidoTb> lista){
        super(lista);
        this.colunas = new String[]{"ID","Cliente", "Status"};
    }

    public PedidoTabela(){
        super();
        this.colunas = new String[]{"ID","Cliente", "Status"};
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PedidoTb pedido = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return pedido.getPedidoId();
            case 1: return pedido.getClienteId().getNome();
            case 2: return pedido.getStatus();
            default : return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        this.fireTableCellUpdated(row, col);
    }
}

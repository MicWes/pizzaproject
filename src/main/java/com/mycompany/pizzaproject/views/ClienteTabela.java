/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.ClienteTb;
import java.util.List;

public class ClienteTabela extends TableModel<ClienteTb>{
    
    public ClienteTabela(List<ClienteTb> lista){
        super(lista);
        this.colunas = new String[]{"Id","Nome", "Sobrenome", "Telefone"};
    }
    
    public ClienteTabela(){
        super();
        this.colunas = new String[]{"Id","Nome", "Sobrenome", "Telefone"};
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClienteTb cliente = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return cliente.getClienteId();
            case 1: return cliente.getNome();
            case 2: return cliente.getSobrenome();
            case 3: return cliente.getTelefone();
            default : return null;
        }
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        ClienteTb cliente = lista.get(row);
        switch (col) {
            case 0:
                cliente.setClienteId((Integer) value);
                break;
            case 1:
                cliente.setNome((String) value);
                break;
            case 2:
                cliente.setSobrenome((String) value);
                break;
            case 3:
                cliente.setTelefone((String) value);
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }
}

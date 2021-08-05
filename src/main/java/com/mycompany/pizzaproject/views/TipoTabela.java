/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.TipoTb;
import java.util.List;

public class TipoTabela extends TableModel<TipoTb>{
    
    public TipoTabela(List<TipoTb> lista){
        super(lista);
        this.colunas = new String[]{"id","Titulo", "Preço"};
    }

    public TipoTabela(){
        super();
        this.colunas = new String[]{"id","Titulo", "Preço"};
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoTb tipo = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return tipo.getTipoId();//if column 0 (code)
            case 1: return tipo.getTitulo();//if column 1 (name)
            case 2: return tipo.getPreco();//if column 2 (birthday)
            default : return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        TipoTb tipo = lista.get(row);
        switch (col) {
            case 0:
                tipo.setTipoId((Integer) value); //if column 0 (code)
                break;
            case 1:
                tipo.setTitulo((String) value);
                break;
            case 2:
//                customer.setTipoId();
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }
}

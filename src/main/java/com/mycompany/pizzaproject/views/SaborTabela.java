/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.SaborTb;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class SaborTabela extends TableModel<SaborTb>{
    
    public SaborTabela(List<SaborTb> lista){
        super(lista);
        this.colunas = new String[]{"id","Descrição", "Tipo"};
    }

    public SaborTabela(){
        super();
        this.colunas = new String[]{"id","Descrição", "Tipo"};
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SaborTb sabor = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return sabor.getSaborId();//if column 0 (code)
            case 1: return sabor.getDescricao();//if column 1 (name)
            case 2: return sabor.getTipoTb().getTitulo();//if column 2 (birthday)
            default : return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        SaborTb sabor = lista.get(row);
        switch (col) {
            case 0:
                sabor.setSaborId((Integer) value); //if column 0 (code)
                break;
            case 1:
                sabor.setDescricao((String) value);
                break;
            case 2:
//                customer.setTipoId();
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }
}

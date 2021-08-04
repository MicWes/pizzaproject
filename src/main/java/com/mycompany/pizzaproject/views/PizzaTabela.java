/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.PizzaTb;
import com.mycompany.pizzaproject.models.SaborTb;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class PizzaTabela extends TableModel<PizzaTb>{
    
    public PizzaTabela(List<PizzaTb> lista){
        super(lista);
        this.colunas = new String[]{"Formato","Sabor 1", "Sabor 2", "Área(cm²)", "Preço"};
    }

    public PizzaTabela(){
        super();
        this.colunas = new String[]{"Formato","Sabor 1", "Sabor 2", "Área(cm²)", "Preço"};
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PizzaTb pizza = lista.get(rowIndex);
        Collection<SaborTb> sabores = pizza.getSaborTbCollection();
        String sabor1 = "";
        String sabor2 = "";
        
        try {
            sabor1 = sabores.iterator().next().getDescricao();
            sabor2 = sabores.iterator().next().getDescricao();
        } catch(Exception ex) {
            //TODO
        }
        
        switch (columnIndex) {
            case 0: return pizza.getForma();
            case 1: return sabor1;
            case 2: return sabor2;
            case 3: return pizza.getTamanho();
            case 4: return pizza.getTotalPizza();
            default : return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        this.fireTableCellUpdated(row, col);
    }
}

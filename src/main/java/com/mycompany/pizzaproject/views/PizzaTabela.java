/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.PizzaTb;
import com.mycompany.pizzaproject.models.SaborTb;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class PizzaTabela extends TableModel<PizzaTb>{
    
    public PizzaTabela(List<PizzaTb> lista){
        super(lista);
        this.colunas = new String[]{"Formato","Sabor 1", "Sabor 2", "Lado/Raio(cm)", "Preço"};
    }

    public PizzaTabela(){
        super();
        this.colunas = new String[]{"Formato","Sabor 1", "Sabor 2", "Lado/Raio(cm)", "Preço"};
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PizzaTb pizza = lista.get(rowIndex);
        List<SaborTb> sabores = (List<SaborTb>) pizza.getSaborTbCollection();
        String sabor1 = "";
        String sabor2 = "";
        
        try {
            sabor1 = sabores.get(0).getDescricao();
            sabor2 = sabores.get(1).getDescricao();
        } catch(Exception ex) {
            sabor2 = "";
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

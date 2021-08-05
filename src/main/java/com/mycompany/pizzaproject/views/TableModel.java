/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.models.Model;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class TableModel<T extends Model> extends AbstractTableModel{
    public String[] colunas;
    public List<T> lista=new ArrayList();

    
    public TableModel(List<T> lista){
        this.lista=lista;
    }

    public TableModel(){
    }


    @Override
    public int getRowCount() {
        return this.lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }


    public boolean remove(T object) {
        int linha = this.lista.indexOf(object);
        boolean result = this.lista.remove(object);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void insert(T object) {
        this.lista.add((T) object);
        this.fireTableRowsInserted(lista.size()-1,lista.size()-1);//update JTable
    }

    public void setListaContatos(List<T> models) {
        this.lista = models;
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public T getModel(int linha){
        return lista.get(linha);
    }

    void removeModels(List<T> listaParaExcluir) {
        listaParaExcluir.forEach((contato) -> {
            remove(contato);
        });
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

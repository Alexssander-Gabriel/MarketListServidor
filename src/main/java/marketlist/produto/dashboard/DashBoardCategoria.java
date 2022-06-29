/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marketlist.produto.dashboard;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author User
 */
public class DashBoardCategoria implements Serializable {
    private String categoria;
    private int quantidade;

    public DashBoardCategoria() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
}

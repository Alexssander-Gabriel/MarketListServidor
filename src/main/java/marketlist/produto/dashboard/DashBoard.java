package marketlist.produto.dashboard;

import java.io.Serializable;
import java.util.List;


public class DashBoard implements Serializable{
    private List<DashBoardCategoria> categorias;
    private int listasQuantidade;
    private double precoMedioListas;
    private int produtosQuantidade;
    private double precoMedioProdutos;
    private int mercadosQuantidade;

    public DashBoard(){
    }

    public DashBoard(List<DashBoardCategoria> categorias, int listasQuantidade, double precoMedioListas, int produtosQuantidade, double precoMedioProdutos, int mercadosQuantidade) {
        this.categorias = categorias;
        this.listasQuantidade = listasQuantidade;
        this.precoMedioListas = precoMedioListas;
        this.produtosQuantidade = produtosQuantidade;
        this.precoMedioProdutos = precoMedioProdutos;
        this.mercadosQuantidade = mercadosQuantidade;
    }

    public List<DashBoardCategoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<DashBoardCategoria> categorias) {
        this.categorias = categorias;
    }
    

    public int getListasQuantidade() {
        return listasQuantidade;
    }

    public void setListasQuantidade(int listasQuantidade) {
        this.listasQuantidade = listasQuantidade;
    }

    public double getPrecoMedioListas() {
        return precoMedioListas;
    }

    public void setPrecoMedioListas(double precoMedioListas) {
        this.precoMedioListas = precoMedioListas;
    }

    public int getProdutosQuantidade() {
        return produtosQuantidade;
    }

    public void setProdutosQuantidade(int produtosQuantidade) {
        this.produtosQuantidade = produtosQuantidade;
    }

    public double getPrecoMedioProdutos() {
        return precoMedioProdutos;
    }

    public void setPrecoMedioProdutos(double precoMedioProdutos) {
        this.precoMedioProdutos = precoMedioProdutos;
    }

    public int getMercadosQuantidade() {
        return mercadosQuantidade;
    }

    public void setMercadosQuantidade(int mercadosQuantidade) {
        this.mercadosQuantidade = mercadosQuantidade;
    }
    
    
    
    
    
    
    
}

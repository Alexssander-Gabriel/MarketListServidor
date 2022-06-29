/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marketlist.produto.dashboard;

import javax.ejb.Stateless;
import javax.inject.Inject;
import marketlist.lista.ListaService;
import marketlist.mercado.MercadoService;
import marketlist.produto.ProdutoService;

/**
 *
 * @author User
 */
@Stateless
public class DashBoardService {
    
    @Inject
    private ProdutoService produtoService;
    
    @Inject
    private MercadoService mercadoService;
    
    @Inject 
    private ListaService listaService;
    
    public DashBoard getResume(){
        DashBoard resume = new DashBoard();
        resume.setCategorias(produtoService.findCategories());
        
        resume.setProdutosQuantidade(produtoService.findAll().size());
        resume.setPrecoMedioProdutos(produtoService.getPrecoMedio());
        
        resume.setListasQuantidade(listaService.findAll().size());
        resume.setPrecoMedioListas(listaService.getPrecoMedio());
        
        resume.setMercadosQuantidade(mercadoService.findAll().size());
        
        
        return resume;
    }
    
    
}

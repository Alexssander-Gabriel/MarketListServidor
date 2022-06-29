package marketlist.lista;

import marketlist.produto.*;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import marketlist.mercado.Mercado;

@Entity
@Table(name = "lista", schema = "public")
public class Lista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String descricao;
   
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name="lista_produtos",
        joinColumns = @JoinColumn(name = "id_produto"),
        inverseJoinColumns = @JoinColumn(name = "id_lista"),
        foreignKey = @ForeignKey(name = "fk_produto"),
        inverseForeignKey = @ForeignKey(name = "fk_lista_produto")
    )
    private Set<Produto> produtos;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)    
    @JoinTable(
        name="lista_mercados",
        joinColumns = @JoinColumn(name = "id_mercado"),
        inverseJoinColumns = @JoinColumn(name = "id_lista"),
        foreignKey = @ForeignKey(name = "fk_mercado"),
        inverseForeignKey = @ForeignKey(name = "fk_lista_mercado")
    )
    private Set<Mercado> mercados;
    
    
    @Column(nullable = false)
    private String categoria;

    public Lista() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

   
    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<Mercado> getMercados() {
        return mercados;       
    }

    public void setMercados(Set<Mercado> mercados) {
        this.mercados = mercados;       
    }
      
}

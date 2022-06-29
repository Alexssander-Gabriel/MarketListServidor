package marketlist.mercado;


import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import marketlist.lista.Lista;

@Entity
@Table(name = "mercado", schema = "public")
public class Mercado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nome;
 
    @Column(nullable = false)
    private String endereco;
       
    @Column(nullable = false)
    private String contato;
    
    @Column(nullable = false)
    private String urlFoto;
    
    @Column(nullable = false)   
    private String atendimento;
    
    @Column(nullable = false)
    private String ativo;
    
    /*
    @OneToMany(targetEntity = Lista.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mercado", referencedColumnName = "id")
    private List mercados;
    */

    public Mercado() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlfoto) {
        this.urlFoto = urlfoto;
    }

    public String getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(String atendimento) {
        this.atendimento = atendimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
    
    
      
}

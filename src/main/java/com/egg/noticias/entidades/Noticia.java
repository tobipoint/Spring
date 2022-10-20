
package com.egg.noticias.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Noticia {
   
    @Id
    @GeneratedValue(generator = "uuid")
    private int id;
    private String titulo;
    private String cuerpo;
 
    @OneToOne
    private Imagen imagen;

    public Noticia() {
    }

    public Noticia(String titulo, String cuerpo, Imagen imagen) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.imagen = imagen;
    }
    

    public Noticia(int id, String titulo, String cuerpo, Imagen imagen) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
    
   
    
}

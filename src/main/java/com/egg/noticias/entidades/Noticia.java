package com.egg.noticias.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Noticia {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)

//    @Id
//    @GeneratedValue(generator = "uuid")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String titulo;
    private String cuerpo;

    @OneToOne
    private Imagen imagen;

    @ManyToOne
    private Periodista creador;

    public Noticia() {
    }

    public Noticia(String titulo, String cuerpo, Imagen imagen) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.imagen = imagen;
    }

    public Noticia(String id, String titulo, String cuerpo, Imagen imagen) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.imagen = imagen;
    }

//    public Periodista getCreador() {
//        return creador;
//    }
//
//    public void setCreador(Periodista creador) {
//        this.creador = creador;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

package com.egg.noticias.servicio;

import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.repositorios.noticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class noticiaService {

    @Autowired
    private noticiaRepositorio noticiaRepositorio;
    
    @Autowired
    private imagenServicio imagenServicio;
    

    @Transactional
    public void crearNoticia(MultipartFile foto, String titulo, String cuerpo) throws excepciones {
        validar(titulo, cuerpo, foto);
        Noticia noticia = new Noticia();
        try {
            noticia.setCuerpo(cuerpo);
            Imagen imagen = imagenServicio.guardar(foto);
            System.out.println(imagen);
            noticia.setImagen(imagen);
            noticia.setTitulo(titulo);

            noticiaRepositorio.save(noticia);
        } catch (excepciones e) {
            System.err.println("error ");
        }
    }

    @Transactional
    public void actualizarNoticia(String titulo, String cuerpo, MultipartFile foto) throws excepciones {
        validar(titulo, cuerpo, foto);
        Noticia noticia = new Noticia();
        noticia.setCuerpo(cuerpo);
        noticia.setImagen((Imagen) foto);
        noticia.setTitulo(titulo);

        noticiaRepositorio.save(noticia);
    }

    private void validar(String titulo, String cuerpo, MultipartFile foto) throws excepciones {

        if (titulo == null || titulo.isEmpty()) {
            throw new excepciones("titulo nulo");
        }
        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new excepciones("cuerpo nulo");
        }
        if (foto == null || foto.isEmpty()) {
            throw new excepciones("foto nulo");
        }

    }

    @Transactional
    public void elimiarPorTitulo(String titulo) {
        Noticia noticia = noticiaRepositorio.buscarPorTitulo(titulo);
        noticiaRepositorio.delete(noticia);
    }

    @Transactional
    public Noticia getOne(String titulo) {
        return noticiaRepositorio.getOne(titulo);
    }

    @Transactional
    public List<Noticia> listar() {
//        List<Noticia> lista = noticiaRepositorio.listar();
        List<Noticia> lista = new ArrayList();
        lista = noticiaRepositorio.findAll();

        return lista;
    }

}

package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface noticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("Select n FROM Noticia n Where n.titulo = :titulo")
    Noticia buscarPorTitulo(@Param("titulo") String titulo);

    @Query("Select n FROM Noticia n")
    List<Noticia> listar();

}

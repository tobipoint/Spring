package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface imagenRepositorio extends JpaRepository<Imagen, String> {

}

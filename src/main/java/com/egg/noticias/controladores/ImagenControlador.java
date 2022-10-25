package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.servicio.noticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private noticiaService noticiaServicio;

    @GetMapping("/Noticias/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id) throws excepciones{
       
       Noticia noticias = noticiaServicio.getOne(id);
       byte[] imagen= noticias.getImagen().getContenido();
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.IMAGE_JPEG);
        
       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }    
    
}

package com.egg.noticias.controladores;

import com.egg.noticias.servicio.noticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private noticiaService noticiaServicio;

    
    
}

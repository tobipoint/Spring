package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.servicio.noticiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Noticia")
public class NoticiaControlador {

    @Autowired
    private noticiaService noticiaServicio;

    @GetMapping("/Crear") //localhost8080/Noticia/Crear
    public String NoticiaControlador() {
        return "crearNoticia.html";
    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam String titulo, @RequestParam String cuerpo, @RequestParam MultipartFile foto,
            ModelMap modelo) throws excepciones {
        try {
            noticiaServicio.crearNoticia(foto, titulo, cuerpo);
            modelo.put("exito", "la noticia se cargo con exito");
        } catch (excepciones e) {
            modelo.put("error", "la noticia no se ha cargado");
            return "crearNoticia.html";
        }
        return "index.html";
    }

    @GetMapping("/Editar") //localhost8080/Noticia/editar
    public String NoticiaEditar(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listar();
        modelo.addAttribute("noticias", noticias);

        return "editarNoticia";
    }

//    @PostMapping("/modificar/{titulo}") 
    @PostMapping("/modificar")
    public String modificar(@RequestParam String titulo, @RequestParam String cuerpo,
            @RequestParam MultipartFile foto, ModelMap modelo) throws excepciones {

        try {
            noticiaServicio.actualizarNoticia(titulo, cuerpo, foto);
            return "index.html";
        } catch (excepciones e) {
            List<Noticia> noticias = noticiaServicio.listar();
            modelo.addAttribute("noticias", noticias);
            return "/editarNoticia";
        }
    }

    @GetMapping("/Eliminar") //localhost8080/Noticia/eliminar
    public String NoticiaEliminar(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listar();
        modelo.addAttribute("noticias", noticias);

        return "EliminarNoticia";
    }

    @PostMapping("/darDeBaja")
    public String Eliminar(@PathVariable String titulo, ModelMap modelo) {
        try {
            noticiaServicio.elimiarPorTitulo(titulo);
            modelo.put("exito", "el libro se elimino correctamente");
            return "index.html";
        } catch (Exception e) {
            modelo.put("error", "el libro no se ha eliminado");
            return "/Eliminar";
        }
    }

    @GetMapping("/listar") //localhost8080/Noticia/listar
    public String listarNoticias(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listar();
        modelo.addAttribute("noticias", noticias);

        return "listarNoticias";
    }

}

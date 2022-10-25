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
            return "redirect:/";
        } catch (excepciones e) {
            modelo.put("error", "la noticia no se ha cargado");
            return "crearNoticia.html";
        }
    }

    @GetMapping("/Editar/{id}") //localhost8080/Noticia/editar
    public String NoticiaEditar(@PathVariable String id, ModelMap modelo) throws excepciones {
        Noticia noticias = noticiaServicio.getOne(id);
        modelo.addAttribute("noticias", noticias);

        return "editarNoticia";
    }

//    @PostMapping("/modificar/{titulo}") 
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String titulo, @RequestParam String cuerpo,
            @RequestParam MultipartFile foto, ModelMap modelo) throws excepciones {
        try {
            Noticia noticias = noticiaServicio.getOne(id);
            modelo.addAttribute("noticias", noticias);
            noticiaServicio.actualizarNoticia(id, titulo, cuerpo, foto);
            return "verNoticia.html";
        } catch (excepciones e) {
            List<Noticia> noticias = noticiaServicio.listar();
            modelo.addAttribute("noticias", noticias);
            return "/listarNoticias";
        }
    }

    @GetMapping("/Eliminar/{id}") //localhost8080/Noticia/eliminar
    public String NoticiaEliminar(ModelMap modelo, @PathVariable String id) throws excepciones {
        modelo.put("noticias", noticiaServicio.getOne(id));

        return "EliminarNoticia";
    }

    @PostMapping("/darDeBaja/{id}")
    public String Eliminar(@PathVariable String id, ModelMap modelo) throws excepciones {
        System.out.println("eliminando...");
        noticiaServicio.getOne(id);
        try {
            noticiaServicio.elimiarPorTitulo(id);
            modelo.put("exito", "la noticia se elimino correctamente");
            return "redirect:/";
        } catch (Exception e) {
            modelo.put("error", "el libro no se ha eliminado");
            return "/EliminarNoticia";
        }
    }

    @GetMapping("/verNoticia/{id}") //localhost8080/Noticia/verNoticia
    public String verNoticias(@PathVariable String id, ModelMap modelo) throws excepciones {
        Noticia noticias = noticiaServicio.getOne(id);
        modelo.addAttribute("noticias", noticias);

        return "verNoticia";
    }

    @GetMapping("/listar") //localhost8080/Noticia/listar
    public String listarNoticias(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listar();
        modelo.addAttribute("noticias", noticias);

        return "listarNoticias";
    }

}

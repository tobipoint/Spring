package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.servicio.usuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private usuarioServicio usuarioServicio;

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listar();
        modelo.addAttribute("usuarios", usuarios);

        return "listarUsuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(ModelMap modelo, @PathVariable String id) {
        try {
            usuarioServicio.EliminarUsuario(id);
            modelo.put("exito", "usuario eliminado");
            return "panel";
        } catch (excepciones ex) {
            modelo.put("error", ex.getMessage());
            return "listarUsuarios";
        }
    }

}

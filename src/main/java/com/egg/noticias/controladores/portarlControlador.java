package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.servicio.PeriodistaServicio;
import com.egg.noticias.servicio.usuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class portarlControlador {

    @Autowired
    private usuarioServicio usuarioServicio;

    @Autowired
    private PeriodistaServicio periodistaServicio;

    @GetMapping("/")
    public String index() {
        System.out.println("hola");
        return "index";
    }

    @GetMapping("/registrar")
    public String registrar() {

        return "registrarUsuario";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, @RequestParam MultipartFile foto,
            ModelMap modelo) {
        try {
            if (nombre.equals("admin")) {
                usuarioServicio.crearUsuario(foto, nombre, email, password, password2);
//                usuarioServicio.cambiarRol(id);
            } else {
                usuarioServicio.crearUsuario(foto, nombre, email, password, password2);
            }
//            usuarioServicio.crearUsuario(foto, nombre, email, password, password2);
            modelo.put("exito", "usuario agregado");
            return "index";
        } catch (excepciones e) {
            modelo.put("error", e.getMessage());
            return "registrarUsuario";
        }
    }

    @GetMapping("/registrarPeriodista")
    public String registrarPeriodista() {

        return "crearPeriodista";
    }

    @PostMapping("/registroPeriodista")
    public String registroPeriodista(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, @RequestParam MultipartFile foto,
            ModelMap modelo) {
        System.out.println(nombre);
        try {
            periodistaServicio.crearPeriodista(foto, nombre, email, password, password2);
            modelo.put("exito", "periodista agregado");
            return "inicio";
        } catch (excepciones e) {
            modelo.put("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contraseña incorrecto");
        }
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "editarPerfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PERIODISTA')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam MultipartFile foto,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.actualizarUsuario(id, foto, nombre, email, password, password2);
            modelo.put("exito", "correctamente actualizado");
            return "inicio";
        } catch (excepciones ex) {
             modelo.put("error", ex.getMessage());
            return "editarPerfil";
        }
    }

    
  
    
}

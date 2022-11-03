package com.egg.noticias.servicio;

import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.enums.Rol;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

@Service
public class usuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private imagenServicio imagenServicio;

    @Transactional
    public void crearUsuario(MultipartFile foto, String nombre, String email, String password, String password2) throws excepciones {
        System.out.println("vamos a validar los datosS");
        validar(foto, nombre, email, password, password2);
        Usuario usuario = new Usuario();
        try {
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            Imagen imagen = imagenServicio.guardar(foto);
            usuario.setImagen(imagen);
            usuario.setAlta(true);
            usuario.setFechAlta(new Date());
            usuario.setRol(Rol.USER);

            usuarioRepositorio.save(usuario);
        } catch (excepciones e) {
            System.err.println("error ");
        }
    }

    @Transactional
    private void validar(MultipartFile foto, String nombre, String email, String password, String password2) throws excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new excepciones("nombre nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new excepciones("email nulo");
        }
        if (password == null || password.isEmpty()) {
            throw new excepciones("password nulo");
        }
        if (foto == null || foto.isEmpty()) {
            throw new excepciones("foto nula");
        }
        if (!password.equals(password2)) {
            throw new excepciones("Las contraseñas no coinciden");
        }

    }

    @Transactional
    public void actualizarUsuario(String id, MultipartFile foto, String nombre, String email, String password, String password2)
            throws excepciones {
        validar(foto, nombre, email, password, password2);
        Usuario usuario = getOne(id);
        try {
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            Imagen imagen = imagenServicio.guardar(foto);
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        } catch (excepciones e) {
            System.err.println("error ");
        }
    }

    @Transactional
    public Usuario getOne(String id)  {
        Usuario usuario = usuarioRepositorio.getOne(id);
        return usuario;
    }

    @Transactional
    public List<Usuario> listar() {
        List<Usuario> lista = usuarioRepositorio.findAll();

        return lista;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            if (usuario.getRol().toString().equals("USER")) {
                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().toString().equals("ADMIN")) {
                usuario.setRol(Rol.USER);
            }
        } else {
            System.err.println("el usuario no se encontro");
        }
    }

    @Transactional
    public void EliminarUsuario(String id) throws excepciones {
        Usuario usuario = getOne(id);
        usuarioRepositorio.delete(usuario);
        
    }

}

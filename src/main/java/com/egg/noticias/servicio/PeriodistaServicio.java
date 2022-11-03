package com.egg.noticias.servicio;

import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.entidades.Periodista;
import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.enums.Rol;
import com.egg.noticias.exception.excepciones;
import com.egg.noticias.repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeriodistaServicio {

    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;

    @Autowired
    private imagenServicio imagenServicio;

    @Transactional
    public void crearPeriodista(MultipartFile foto, String nombre, String email, String password, String password2)
            throws excepciones {
        System.out.println("vamos a validar los datosS");
        validar(foto, nombre, email, password, password2);
        Periodista periodista = new Periodista();
        try {
            periodista.setNombre(nombre);
            periodista.setEmail(email);
            periodista.setPassword(new BCryptPasswordEncoder().encode(password));
            Imagen imagen = imagenServicio.guardar(foto);
            periodista.setImagen(imagen);
            periodista.setAlta(true);
            periodista.setFechAlta(new Date());
            periodista.setRol(Rol.PERIODISTA);

            periodistaRepositorio.save(periodista);
        } catch (excepciones e) {
            System.err.println("error ");
        }
    }

    @Transactional
    private void validar(MultipartFile foto, String nombre, String email, String password, String password2)
            throws excepciones {

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

    
    
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Periodista periodista = periodistaRepositorio.buscarPorEmail(email);
        
        if (periodista != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + periodista.getRol().toString());
            permisos.add(p);
            return new User(periodista.getEmail(), periodista.getPassword(), permisos);
        } else {
            return null;
        }
    }
    
}

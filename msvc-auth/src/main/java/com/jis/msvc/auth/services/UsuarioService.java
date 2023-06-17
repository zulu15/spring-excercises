package com.jis.msvc.auth.services;

import com.jis.msvc.auth.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private WebClient webClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            Usuario u = webClient.get()
                    .uri("http://msvc-usuarios:8001/api/usuarios/login", uri -> uri.queryParam("email",username).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();

            return new User(username, u.getPassword(), true, true,true,true,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        }catch (RuntimeException e){
            throw new UsernameNotFoundException("Error not found username "+username);
        }

    }
}

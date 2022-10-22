package com.pedrobacchini.imdbcardgame.adapter.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final List<Player> players = new ArrayList<>();
    final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("PLAYER"));

    public AppUserDetailsService(final BCryptPasswordEncoder bCryptPasswordEncoder) {
        players.add(new Player(new User("player1", bCryptPasswordEncoder.encode("player1Pass"), authorities)));
        players.add(new Player(new User("player2", bCryptPasswordEncoder.encode("player2Pass"), authorities)));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return players.stream().filter(player -> player.getUsername().equals(email)).findFirst()
            .orElseThrow(() -> new UsernameNotFoundException("Incorrect username and/or password"));
    }

}

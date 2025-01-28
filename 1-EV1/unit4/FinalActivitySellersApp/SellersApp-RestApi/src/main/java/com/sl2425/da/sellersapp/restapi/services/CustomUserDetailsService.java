package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ISellerEntityDAO sellerDAO;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SellerEntity seller = sellerDAO.findByCif(username);
        if (seller == null)
            throw new UsernameNotFoundException("User not found");
        return User.builder()
                .username(seller.getCif())
                .password(seller.getPassword())
                .roles("USER")
                .build();
    }
}
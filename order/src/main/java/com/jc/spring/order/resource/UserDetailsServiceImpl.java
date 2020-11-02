//package com.jc.spring.order.resource;
//
//import com.jc.spring.order.pojo.vo.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
///**
// * @author jincheng.zhang
// */
//@Component
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = new User();
//        user.setUsername(username);
//        user.setId(1L);
//        return user;
//    }
//}

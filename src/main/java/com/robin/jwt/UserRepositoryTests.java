//package com.robin.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.robin.jwt.model.User;
//import com.robin.jwt.repository.UserRepository;
//
//public class UserRepositoryTests {
// 
//    @Autowired private UserRepository repo;
//     
//    public void testCreateUser() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("nam2020");
//         
//        User newUser = new User("nam@codejava.net", password);
//        User savedUser = repo.save(newUser);
//         
//       
//    }
//}
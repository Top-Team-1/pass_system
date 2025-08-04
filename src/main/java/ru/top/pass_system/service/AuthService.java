package ru.top.pass_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.top.pass_system.dto.userDTO.SignInRequest;
import ru.top.pass_system.dto.userDTO.SignUpRequest;
import ru.top.pass_system.mapper.UserMapper;
import ru.top.pass_system.model.User;
import ru.top.pass_system.security.JwtAuthResponse;
import ru.top.pass_system.security.JwtService;

@RequiredArgsConstructor
@Service
public class AuthService {

   private final UserService userService;
   private final JwtService jwtService;
   private final AuthenticationManager authenticationManager;
   private final UserMapper userMapper;

   public JwtAuthResponse signUp(SignUpRequest signUpRequest){

      User user = userMapper.toUser(signUpRequest);

      userService.signUp(user);

      var jwt =  jwtService.generateToken(user);

      return new JwtAuthResponse(jwt);
   }

   public JwtAuthResponse signIn(SignInRequest signInRequest){

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              signInRequest.getPhone(),
              signInRequest.getPassword()
      ));

      var user = userService.userDetailsService()
              .loadUserByUsername(signInRequest.getPhone());

      var jwt = jwtService.generateToken(user);
      return new JwtAuthResponse(jwt);
   }
}

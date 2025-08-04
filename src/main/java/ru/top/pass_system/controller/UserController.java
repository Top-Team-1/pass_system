package ru.top.pass_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.pass_system.dto.userDTO.SignUpRequest;
import ru.top.pass_system.dto.userDTO.UserFilterDTO;
import ru.top.pass_system.dto.userDTO.UserResponseDTO;
import ru.top.pass_system.dto.userDTO.UserUpdateDTO;
import ru.top.pass_system.service.UserService;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody SignUpRequest signUpRequest) {

        return ResponseEntity.ok(userService.create(signUpRequest));

    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(@ModelAttribute UserFilterDTO filter,
                                                         @PageableDefault(size = 5, sort = "id",
                                                                 direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(userService.findAll(filter, pageable));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update (@RequestBody UserUpdateDTO userUpdateDTO){

        return ResponseEntity.ok(userService.update(userUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        userService.delete(id);

        return ResponseEntity.ok("Пользователь с id : " + id + " удалён");
    }

}

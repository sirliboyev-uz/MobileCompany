package com.example.mobilecompany.Controller;

import com.example.mobilecompany.DTO.*;
import com.example.mobilecompany.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registerUser")
    public HttpEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO){
        ApiResponse apiResponse=userService.registerUser(registerUserDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PutMapping("/updateUser")
    public HttpEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        ApiResponse apiResponse=userService.updateUser(updateUserDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/registerDef")
    public HttpEntity<?> registerUser(@RequestBody RegisterDefDTO registerDefDTO){
        ApiResponse apiResponse=userService.registerDefinition(registerDefDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PostMapping("/registerSim")
    public HttpEntity<?> registerUser(@RequestBody RegisterSimDTO registerSimDTO){
        ApiResponse apiResponse=userService.registerSIM(registerSimDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PutMapping("/updateDef")
    public HttpEntity<?> registerUser(@RequestBody UpdateDefDTO updateDefDTO){
        ApiResponse apiResponse=userService.updateDef(updateDefDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/login")
    public HttpEntity<?> registerUser(@RequestBody LoginUssdDTO loginUssdDTO){
        return ResponseEntity.ok(userService.login(loginUssdDTO));
    }
}

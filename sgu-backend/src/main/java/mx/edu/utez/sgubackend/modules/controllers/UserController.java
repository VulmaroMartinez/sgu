package mx.edu.utez.sgubackend.modules.controllers;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.sgubackend.modules.models.UserModel;
import mx.edu.utez.sgubackend.modules.services.UserService;
import mx.edu.utez.sgubackend.utils.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<APIResponse<List<UserModel>>> getAllUsers() {
        APIResponse<List<UserModel>> response = userService.findAll();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserModel>> getUserById(@PathVariable Long id) {
        APIResponse<UserModel> response = userService.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<APIResponse<UserModel>> getUserByName(@PathVariable String email) {
        APIResponse<UserModel> response = userService.findByName(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<UserModel>> createUser(@RequestBody UserModel user) {
        APIResponse<UserModel> response = userService.save(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserModel>> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        APIResponse<UserModel> response = userService.update(id, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteUser(@PathVariable Long id) {
        APIResponse<String> response = userService.deleteById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

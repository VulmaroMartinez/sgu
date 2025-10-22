package mx.edu.utez.sgubackend.modules.services;

import mx.edu.utez.sgubackend.modules.models.UserModel;
import mx.edu.utez.sgubackend.utils.APIResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    APIResponse<List<UserModel>> findAll();
    APIResponse<UserModel> findById(Long id);
    APIResponse<UserModel> save(UserModel user);
    APIResponse<UserModel> update(Long id, UserModel user);
    APIResponse<String> deleteById(Long id);
    APIResponse<UserModel> findByName(String name);
}

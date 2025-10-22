package mx.edu.utez.sgubackend.modules.services.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.sgubackend.modules.models.UserModel;
import mx.edu.utez.sgubackend.modules.repositories.UserRepository;
import mx.edu.utez.sgubackend.modules.services.UserService;
import mx.edu.utez.sgubackend.utils.APIResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public APIResponse<List<UserModel>> findAll() {
        try {
            List<UserModel> users = userRepository.findAll();
            return new APIResponse<>(users, "Usuarios obtenidos exitosamente", HttpStatus.OK, null);
        } catch (DataAccessException e) {
            return new APIResponse<>("Error al obtener la lista de usuarios", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>("Error inesperado al obtener usuarios", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public APIResponse<UserModel> findById(Long id) {
        try {
            Optional<UserModel> user = userRepository.findById(id);
            if (user.isPresent()) {
                return new APIResponse<>(user.get(), "Usuario encontrado", HttpStatus.OK, null);
            } else {
                return new APIResponse<>("Usuario no encontrado con id: " + id, HttpStatus.NOT_FOUND, null);
            }
        } catch (DataAccessException e) {
            return new APIResponse<>("Error al buscar el usuario", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>("Error inesperado al buscar el usuario", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public APIResponse<UserModel> save(UserModel user) {
        try {
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                return new APIResponse<>("El nombre es obligatorio", HttpStatus.BAD_REQUEST, "Campo requerido");
            }
            if (user.getLastname() == null || user.getLastname().trim().isEmpty()) {
                return new APIResponse<>("El apellido es obligatorio", HttpStatus.BAD_REQUEST, "Campo requerido");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return new APIResponse<>("El email es obligatorio", HttpStatus.BAD_REQUEST, "Campo requerido");
            }
            if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return new APIResponse<>("El formato del email no es válido", HttpStatus.BAD_REQUEST, "Email inválido");
            }

            Optional<UserModel> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return new APIResponse<>("Ya existe un usuario con el email: " + user.getEmail(), HttpStatus.BAD_REQUEST, "Email duplicado");
            }

            UserModel savedUser = userRepository.save(user);
            return new APIResponse<>(savedUser, "Usuario creado exitosamente", HttpStatus.CREATED, null);

        } catch (DataAccessException e) {
            return new APIResponse<>("Error al guardar el usuario en la base de datos", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>("Error inesperado al crear el usuario", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public APIResponse<UserModel> update(Long id, UserModel user) {
        try {
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                return new APIResponse<>("El nombre es obligatorio", HttpStatus.BAD_REQUEST, "Campo requerido");
            }
            if (user.getLastname() == null || user.getLastname().trim().isEmpty()) {
                return new APIResponse<>("El apellido es obligatorio", HttpStatus.BAD_REQUEST, "Campo requerido");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return new APIResponse<>("El email es obligatorio", HttpStatus.BAD_REQUEST, "Campo requerido");
            }
            if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return new APIResponse<>("El formato del email no es válido", HttpStatus.BAD_REQUEST, "Email inválido");
            }

            Optional<UserModel> existingUserOpt = userRepository.findById(id);
            if (!existingUserOpt.isPresent()) {
                return new APIResponse<>("Usuario no encontrado con id: " + id, HttpStatus.NOT_FOUND, null);
            }
            Optional<UserModel> userWithEmail = userRepository.findByEmail(user.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
                return new APIResponse<>("El email ya está en uso por otro usuario", HttpStatus.BAD_REQUEST, "Email duplicado");
            }

            UserModel existingUser = existingUserOpt.get();
            existingUser.setName(user.getName());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());

            UserModel updatedUser = userRepository.save(existingUser);
            return new APIResponse<>(updatedUser, "Usuario actualizado exitosamente", HttpStatus.OK, null);

        } catch (DataAccessException e) {
            return new APIResponse<>("Error al actualizar el usuario en la base de datos", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>("Error inesperado al actualizar el usuario", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public APIResponse<String> deleteById(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return new APIResponse<>("Usuario no encontrado con id: " + id, HttpStatus.NOT_FOUND, null);
            }
            userRepository.deleteById(id);
            return new APIResponse<>("Usuario eliminado exitosamente", HttpStatus.OK, null);

        } catch (DataAccessException e) {
            return new APIResponse<>("Error al eliminar el usuario de la base de datos", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>("Error inesperado al eliminar el usuario", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public APIResponse<UserModel> findByName(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return new APIResponse<>("El email no puede estar vacío", HttpStatus.BAD_REQUEST, "Email requerido");
            }

            Optional<UserModel> user = userRepository.findByName(email);
            if (user.isPresent()) {
                return new APIResponse<>(user.get(), "Usuario encontrado", HttpStatus.OK, null);
            } else {
                return new APIResponse<>("Usuario no encontrado con email: " + email, HttpStatus.NOT_FOUND, null);
            }

        } catch (DataAccessException e) {
            return new APIResponse<>("Error al buscar el usuario por email", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>("Error inesperado al buscar el usuario", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

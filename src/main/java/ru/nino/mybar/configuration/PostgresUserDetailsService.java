package ru.nino.mybar.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

@Service
@RequiredArgsConstructor
public class PostgresUserDetailsService implements UserDetailsManager {

    private final UserRepositoryImpl userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User byName = userRepository.findByName(username);
        if(byName == null)
            throw new UsernameNotFoundException("User " + username + " not found");
        return byName;
    }

    @Override
    public void createUser(UserDetails user) {
        User byName = userRepository.findByName(user.getUsername());
        if (byName == null) {
            userRepository.save(new User(user));
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        User byName = userRepository.findByName(user.getUsername());
        if (byName != null) {
            userRepository.save(new User(user));
        }
    }

    @Override
    public void deleteUser(String username) {
        User byName = userRepository.findByName(username);
        userRepository.delete(byName);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        return;
    }

    @Override
    public boolean userExists(String username) {
        return true;
    }
}

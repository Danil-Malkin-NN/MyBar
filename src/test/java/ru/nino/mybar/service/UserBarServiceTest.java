package ru.nino.mybar.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.mapper.impl.IngredientMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBarServiceTest {
    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private IngredientMapperImpl ingredientMapper;
    @Mock
    private CocktailRepositoryImpl cocktailRepository;
    @Mock
    private CocktailMapperImpl cocktailMapper;
    @Mock
    private IngredientRepositoryImpl ingredientRepository;
    @InjectMocks
    private UserBarService userBarService;

    @Test
    void createsLocalProfileWhenKeycloakAccountHasNoEmail() {
        when(userRepository.findByEmail("admin")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userBarService.getOrCreateUser(null, "admin", "Admin", "User");

        assertEquals("admin", user.getEmail());
        assertEquals("admin", user.getUsername());
        assertEquals("Admin", user.getFirstName());
        assertEquals("User", user.getLastName());
        verify(userRepository).save(user);
    }

    @Test
    void preservesExistingLocalProfile() {
        User existing = new User();
        existing.setEmail("admin@example.com");
        existing.setUsername("admin");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(existing));

        User result = userBarService.getOrCreateUser("admin@example.com", "admin", "Updated", "Name");

        assertEquals(existing, result);
        verify(userRepository, never()).save(any(User.class));
    }
}

package com.netcetera.codingchallenge.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplUnitTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserAuthorityRepository userAuthorityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addNewUser() {
        this.userRepository = Mockito.spy(UserRepository.class);
        this.passwordEncoder = Mockito.spy(PasswordEncoder.class);
        this.userService = new UserServiceImpl(this.userRepository, this.userAuthorityRepository, this.passwordEncoder);

        this.userService.addNewUser("testUser", "testPassword");

        Mockito.verify(this.passwordEncoder, Mockito.times(1)).encode("testPassword");
        Mockito.verify(this.userRepository, Mockito.times(1)).existsById("testUser");
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void getUserByUsername() {
        this.userService = new UserServiceImpl(this.userRepository, this.userAuthorityRepository, this.passwordEncoder);
        final User testUser = User.builder().username("testUser").password("testPassword").build();
        Mockito.when(this.userRepository.getOne("testUser")).thenReturn(testUser);

        final User resultUser = this.userService.getUserByUsername("testUser");

        assertEquals(resultUser.getUsername(), testUser.getUsername());
    }

    @Test
    void addAuthorityToUser() {
        final String testAuthorityId = "testAuthrotiy";
        final String testUserId = "testUser";
        final User testUser = User.builder().username("testUser").password("testPassword").build();
        this.userAuthorityRepository = Mockito.spy(UserAuthorityRepository.class);
        this.userRepository = Mockito.spy(UserRepository.class);
        Mockito.when(this.userRepository.existsById(testUserId)).thenReturn(true);
        Mockito.when(this.userRepository.getOne(testUserId)).thenReturn(testUser);
        this.userService = new UserServiceImpl(this.userRepository, this.userAuthorityRepository, this.passwordEncoder);

        this.userService.addAuthorityToUser(testAuthorityId, testUserId);

        Mockito.verify(this.userRepository, Mockito.times(1)).existsById(testUserId);
        Mockito.verify(this.userRepository, Mockito.times(1)).getOne(testUserId);
        Mockito.verify(this.userAuthorityRepository, Mockito.times(1)).existsById(testAuthorityId);
        Mockito.verify(this.userAuthorityRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void getUserDetails() {
        final User testUser = User.builder().username("testUser").password("testPassword").build();
        Mockito.when(this.userRepository.getOne("testUser")).thenReturn(testUser);
        this.userService = new UserServiceImpl(this.userRepository, this.userAuthorityRepository, this.passwordEncoder);

        assertEquals(testUser, this.userService.loadUserByUsername("testUser"));
    }

    @Test
    void getInvalidUserDetails() {
        Mockito.when(this.userRepository.getOne("testUser")).thenReturn(null);
        this.userService = new UserServiceImpl(this.userRepository, this.userAuthorityRepository, this.passwordEncoder);


        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername("testUser"));
    }
}
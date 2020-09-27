package com.netcetera.codingchallenge.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
//        final UserAuthority testAuthority = new UserAuthority("testAuthority");
//
//
//        this.userService = new UserServiceImpl(this.userRepository, this.userAuthorityRepository, this.passwordEncoder);
//
//        this.userService.addAuthorityToUser("testAuthrotiy", "testUser");
//        final User resultUser = this.userService.getUserByUsername("testUser");
//        final UserAuthority resultUserAuthority = this.userAuthorityRepository.getOne("testAuthority");
//
//        final var authorities = resultUser.getAuthorities();
//        final List<String> userAuthorities = authorities.stream().map(x -> x.getAuthority()).collect(Collectors.toList());
//
//        assertTrue(userAuthorities.contains("testAuthority"));
    }
}
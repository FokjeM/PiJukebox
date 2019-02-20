package com.pijukebox.service.impl;


import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.user.User;
import com.pijukebox.repository.IPlaylistWithTracksRepository;
import com.pijukebox.repository.ISimplePlaylistRepository;
import com.pijukebox.repository.IUserRepository;
import com.pijukebox.service.IUserService;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Testing class for the UserServiceImplementation.
 * Makes sure all components required to for a UserService are implemented
 * and in a correct, working order.
 */
public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;
    @Mock
    private ISimplePlaylistRepository simplePlaylistRepository;
    @Mock
    private IPlaylistWithTracksRepository playlistRepository;

    private IUserService userService;


    /**
     * Before each test, set up the required Mocks and a new instance of the
     * UserServiceImplementation to test with.
     */
    @BeforeEach
    void setUp() {
        initMocks(this);
        userService = new UserServiceImpl(userRepository, playlistRepository, simplePlaylistRepository);
    }

    /**
     * Test if finding a playlist by user works.
     * Creates a new Set with a {@link SimpleTrack} and a new List with a {@link PlaylistWithTracks}.
     * These objects are immediately populated with testing data and an optional
     * expected result is specified. Then the optional result is populated
     * and checked against the assertion that it matches the expected result.
     * For verification, both the expected and actual results are printed.
     */
    @Test
    void testFindPlaylistsByUser() {

        Set<SimpleTrack> simpleTracks = new HashSet<>();
        simpleTracks.add(new SimpleTrack(1L, "trackName", "trackDescription", "trackFileName"));

        List<PlaylistWithTracks> playListWithTracks = new ArrayList<>();
        playListWithTracks.add(new PlaylistWithTracks(1L, "title", "description", 1L, simpleTracks));


        final Optional<List<PlaylistWithTracks>> expectedResult = Optional.of(playListWithTracks);
        when(playlistRepository.findAllByUserID(anyLong())).thenReturn(expectedResult);

        final List<PlaylistWithTracks> result = userService.findPlaylistsByUserId(1L).getBody();

        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(playlistRepository).findAllByUserID(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

    }

    /**
     * Tests if a user can be found based on Email and Password.
     * Sets the expectedresult to a new userinsrance and pushes that into the
     * UserRepository Mock object. Then proceeeds to populate the actual result
     * by calling the correct search function and checks wether the result is
     * correct. Finally prints the data for verification.
     */
    @Test
    void testFindByEmailAndPassword() {

        final Optional<User> expectedResult = Optional.of(new User(1L, "firstName", "lastName", "email", "token", "password", "roleId"));

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(expectedResult);

        final User result = userService.findByEmailAndPassword("email", "password").getBody();

        assertThat("reason", result, is(IsSame.sameInstance(expectedResult)));

        verify(userRepository).findByEmailAndPassword("email", "password");

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}

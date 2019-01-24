package com.pijukebox.service.impl;


import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.model.user.User;
import com.pijukebox.repository.*;
import com.pijukebox.service.IUserService;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;
    @Mock
    private ISimplePlaylistRepository simplePlaylistRepository;
    @Mock
    private IPlaylistWithTracksRepository playlistRepository;

    private IUserService userService;


    @BeforeEach
    void setUp() {
        initMocks(this);
        userService = new UserServiceImpl(userRepository,playlistRepository,simplePlaylistRepository);
    }

    @Test
    void testFindPlaylistsByUser() {

        Set<SimpleTrack> simpleTracks = new HashSet<>();
        simpleTracks.add(new SimpleTrack(1L,"trackName","trackDescription","trackFileName"));

        List<PlaylistWithTracks> playListWithTracks = new ArrayList<>();
        playListWithTracks.add(new PlaylistWithTracks(1L,"title","description",1L,simpleTracks));


        final Optional<List<PlaylistWithTracks>> expectedResult = Optional.of(playListWithTracks);
        when(playlistRepository.findAllByUserID(anyLong())).thenReturn(expectedResult);

        final Optional<List<PlaylistWithTracks>> result = userService.findPlaylistsByUserId(1L);

        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(playlistRepository).findAllByUserID(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

    }

    @Test
    void testFindByEmailAndPassword(){

        final Optional<User> expectedResult = Optional.of(new User(1L,"firstName","lastName","email","token","password","roleId"));

        when(userRepository.findByEmailAndPassword(anyString(),anyString())).thenReturn(expectedResult);

        final Optional<User> result = userService.findByEmailAndPassword("email","password");

        assertThat("reason",result, is(IsSame.sameInstance(expectedResult)));

        verify(userRepository).findByEmailAndPassword("email","password");

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");


    }
    

}

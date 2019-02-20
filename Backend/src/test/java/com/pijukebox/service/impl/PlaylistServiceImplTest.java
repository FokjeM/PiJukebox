package com.pijukebox.service.impl;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.repository.IPlaylistWithTracksRepository;
import com.pijukebox.repository.ISimplePlaylistRepository;
import com.pijukebox.service.IPlaylistService;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Testing class to ensure correct implementation and functioning of the PlaylistServiceImplementation
 */
public class PlaylistServiceImplTest {
    @Mock
    IPlaylistWithTracksRepository playlistWithTracksRepository;
    @Mock
    ISimplePlaylistRepository simplePlaylistRepository;

    IPlaylistService playlistService;
    
    /**
     * Before each test, initialize the Mock objects and instantiate a clean
     * PlaylistServiceImplementation to work with.
     */
    @BeforeEach
    void setUp(){
        initMocks(this);
        playlistService = new PlaylistServiceImpl(playlistWithTracksRepository,simplePlaylistRepository);
    }

    /**
     * Test finding a SimplePlaylist based on the assigned ID.
     * Sets the expectedresult to a new SimplePlaylist and then tests if the find
     * method matching this usecase can find the correct object.
     */
    @Test
    void testFindSimplePlaylistById(){
        final Optional<SimplePlaylist> expectedResult = Optional.of(new SimplePlaylist(1L,"title","description",1L));

        when(simplePlaylistRepository.findById(anyLong())).thenReturn(expectedResult);

        final SimplePlaylist result = playlistService.findSimplePlaylistById(1L).getBody();

        assertThat("reason",result, is(IsSame.sameInstance(expectedResult)));

        verify(simplePlaylistRepository).findById(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

    }

    /**
     * Test finding a Playlist based on the assigned ID.
     * Sets the expectedresult to a new Playlist and then tests if the find
     * method matching this usecase can find the correct object.
     */
    @Test
    void testFindById(){
        Set<SimpleTrack> tracks = new HashSet<>();
        SimpleTrack simpleTracks = new SimpleTrack(1L, "trackName", "song","song.mp3");
        tracks.add(simpleTracks);

        final Optional<PlaylistWithTracks> expectedResult = Optional.of(new PlaylistWithTracks(1L,"title","description",1L, tracks));

        when(playlistWithTracksRepository.findById(anyLong())).thenReturn(expectedResult);

        final PlaylistWithTracks result = playlistService.findById(1L).getBody();

        assertThat("reason",result, is(IsSame.sameInstance(expectedResult)));

        verify(playlistWithTracksRepository).findById(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

    }


}

package com.pijukebox.service.impl;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.repository.IArtistRepository;
import com.pijukebox.repository.ISimpleArtistRepository;
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

class ArtistServiceImplTest {

    @Mock
    private ISimpleArtistRepository simpleArtistRepository;
    @Mock
    private IArtistRepository artistRepository;

    private ArtistServiceImpl artistServiceImplUnderTest;

    /**
     * Before each test, set up the Mock objects and initialize an ArtistServiceImplementation
     */
    @BeforeEach
    void setUp() {
        initMocks(this);
        artistServiceImplUnderTest = new ArtistServiceImpl(artistRepository, simpleArtistRepository);
    }

    /**
     * Test for finding a SimpleArtist by the assigned ID
     * This code has been provided by an external source and has only been
     * changed in the areas applicable to our usecase.
     * Original source: https://stackoverflow.com/a/36004293
     */
    @Test
    void testFindSimpleArtistById() {
        String newLine = System.getProperty("line.separator");
        System.out.println("Initiating testFindSimpleArtistById...");
        System.out.println(newLine);

        // Given
        final Long id = 1L;
        final Optional<SimpleArtist> expectedResult = Optional.of(new SimpleArtist(1L, "yo"));
        when(simpleArtistRepository.findById(anyLong())).thenReturn(expectedResult);

        // When
        final SimpleArtist result = artistServiceImplUnderTest.findSimpleArtistById(id).getBody();

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(simpleArtistRepository).findById(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(newLine);
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Test for finding an ExtendedArtist by the assigned ID
     * This code has been provided by an external source and has only been
     * changed in the areas applicable to our usecase.
     * Original source: https://stackoverflow.com/a/36004293
     */
    @Test
    void testFindExtendedArtistById() {
        // Given

        Set<SimpleAlbum> albums = new HashSet<>();
        SimpleAlbum simpleAlbum = new SimpleAlbum(1L, "albumName", "");
        albums.add(simpleAlbum);

        Set<SimpleTrack> tracks = new HashSet<>();
        SimpleTrack simpleTracks = new SimpleTrack(1L, "trackName", "song", "song.mp3");
        tracks.add(simpleTracks);

        final Long id = 1L;
        final Optional<Artist> expectedResult = Optional.of(new Artist(1L, "ArtistName", albums, tracks));
        when(artistRepository.findById(anyLong())).thenReturn(expectedResult);

        // When
        final Artist result = artistServiceImplUnderTest.findExtendedArtistById(id).getBody();

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(artistRepository).findById(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Test for getting the ExtendedArtist by the assigned Name or a part of it
     * This code has been provided by an external source and has only been
     * changed in the areas applicable to our usecase.
     * Original source: https://stackoverflow.com/a/36004293
     */
    @Test
    void findExtendedArtistsByNameContaining() {
        // Given

        Set<SimpleAlbum> albums = new HashSet<>();
        SimpleAlbum simpleAlbum = new SimpleAlbum(1L, "albumName", "");
        albums.add(simpleAlbum);

        Set<SimpleTrack> tracks = new HashSet<>();
        SimpleTrack simpleTracks = new SimpleTrack(1L, "trackName", "song", "song.mp3");
        tracks.add(simpleTracks);

        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist(1L, "ArtistName", albums, tracks));

        final String name = "trackName";
        final Optional<List<Artist>> expectedResult = Optional.of(artists);
        when(artistRepository.findExtendedArtistsByNameContaining(anyString())).thenReturn(expectedResult);

        // When
        final List<Artist> result = artistServiceImplUnderTest.findExtendedArtistsByNameContaining(name).getBody();

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(artistRepository).findExtendedArtistsByNameContaining("trackName");

        System.out.println("Expected result: " + expectedResult);
        System.out.println(System.getProperty("line.separator"));
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}

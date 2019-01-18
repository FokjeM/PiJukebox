//package com.pijukebox.service.impl;
//
//import com.pijukebox.model.album.Album;
//import com.pijukebox.model.simple.SimpleAlbum;
//import com.pijukebox.model.simple.SimpleArtist;
//import com.pijukebox.model.simple.SimpleGenre;
//import com.pijukebox.model.simple.SimpleTrack;
//import com.pijukebox.repository.*;
//import org.hamcrest.core.IsSame;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import java.util.*;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//class GenreServiceImplTest {
//
//    @Mock
//    private IGenreRepository mockGenreWithAlbumsRepository;
//
//    private AlbumServiceImpl albumServiceImplUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        initMocks(this);
//        albumServiceImplUnderTest = new AlbumServiceImpl(mockAlbumRepository, mockSimpleAlbumRepository, mockGenreWithAlbumsRepository, mockArtistWithAlbumsRepository, mockAlbumWithTracksRepository, mockSimpleTrackRepository, mockSimpleArtistRepository, mockSimpleGenreRepository, mockAlbumWithArtistsRepository, mockAlbumWithGenreRepository);
//    }
//
//    @Test
//    void testFindSimpleAlbumById() {
//        // Source: https://stackoverflow.com/a/36004293
//        String newLine = System.getProperty("line.separator");
//        System.out.println("Initiating testFindSimpleAlbumById...");
//        System.out.println(newLine);
//
//        // Given
//        final Long id = 1L;
//        final Optional<SimpleAlbum> expectedResult = Optional.of(new SimpleAlbum(1L, "Sheep Reliability", null));
//        when(mockSimpleAlbumRepository.findById(anyLong())).thenReturn(expectedResult);
//
//        // When
//        final Optional<SimpleAlbum> result = albumServiceImplUnderTest.findSimpleAlbumById(id);
//
//        // Then
//        // You are expecting service to return whatever returned by repo
//        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));
//
//        // You are expecting repo to be called once with correct param
//        verify(mockSimpleAlbumRepository).findById(1L);
//
//        System.out.println("Expected result: " + expectedResult);
//        System.out.println(newLine);
//        System.out.println("Given result: " + result);
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
//    }
//
//    @Test
//    void testFindExtendedAlbumById() {
//        // Source: https://stackoverflow.com/a/36004293
//
//        String newLine = System.getProperty("line.separator");
//
//        // Predefined Data
//        final Set<SimpleGenre> genres = new HashSet<>();
//        final SimpleGenre simpleGenre = new SimpleGenre(1L,"GenreName");
//        genres.add(simpleGenre);
//
//        final Set<SimpleTrack> tracks = new HashSet<>();
//        final SimpleTrack simpleTrack = new SimpleTrack(1L , "TrackName", "TrackDescription", "TrackFilename.mp3" );
//        tracks.add(simpleTrack);
//
//        final Set<SimpleArtist> artists = new HashSet<>();
//        final SimpleArtist simpleArtist = new SimpleArtist(1L,"ArtistName");
//        artists.add(simpleArtist);
//
//        System.out.println("Initiating testFindAlbumById...");
//        System.out.println(newLine);
//
//        // Given
//        final Long id = 1L;
//        final Optional<Album> expectedResult = Optional.of(new Album(1L, "Sheep Reliability", null, genres, tracks, artists));
//        when(mockAlbumRepository.findById(anyLong())).thenReturn(expectedResult);
//
//        // When
//        final Optional<Album> result = albumServiceImplUnderTest.findExtendedAlbumById(id);
//
//        // Then
//        // You are expecting service to return whatever returned by repo
//        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));
//
//        // You are expecting repo to be called once with correct param
//        verify(mockAlbumRepository).findById(1L);
//
//        System.out.println("Expected result: " + expectedResult);
//        System.out.println(newLine);
//        System.out.println("Given result: " + result);
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
//    }
//
//    @Test
//    void testFindAlbumsByNameContaining() {
//        // Source: https://stackoverflow.com/a/36004293
//
//        String newLine = System.getProperty("line.separator");
//
//        System.out.println("Initiating testFindAlbumsByNameContaining...");
//        System.out.println(newLine);
//
//        // Predefined Data
//        final Set<SimpleGenre> genres = new HashSet<>();
//        final SimpleGenre simpleGenre = new SimpleGenre(1L,"GenreName");
//        genres.add(simpleGenre);
//
//        final Set<SimpleTrack> tracks = new HashSet<>();
//        final SimpleTrack simpleTrack = new SimpleTrack(1L , "TrackName", "TrackDescription", "TrackFilename.mp3" );
//        tracks.add(simpleTrack);
//
//        final Set<SimpleArtist> artists = new HashSet<>();
//        final SimpleArtist simpleArtist = new SimpleArtist(1L,"ArtistName");
//        artists.add(simpleArtist);
//
//        // Given
//        final String name = "Sheep Reliability";
//        List<Album> res = new ArrayList<>();
//        res.add(new Album(1L, "Sheep Reliability", null, genres, tracks, artists));
//        final Optional<List<Album>> expectedResult = Optional.of(res); //genre , track , artist
//        when(mockAlbumRepository.findAlbumsByNameContaining(anyString())).thenReturn(expectedResult);
//
//        // When
//        final Optional<List<Album>> result = albumServiceImplUnderTest.findAlbumsByNameContaining(name);
//
//        // Then
//        // You are expecting service to return whatever returned by repo
//        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));
//
//        // You are expecting repo to be called once with correct param
//        verify(mockAlbumRepository).findAlbumsByNameContaining("Sheep Reliability");
//
//        System.out.println("Expected result: " + expectedResult);
//        System.out.println(newLine);
//        System.out.println("Given result: " + result);
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
//    }
//}

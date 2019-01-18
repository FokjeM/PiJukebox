//package com.pijukebox.service.impl;
//
//import com.pijukebox.model.simple.SimpleAlbum;
//import com.pijukebox.model.simple.SimpleArtist;
//import com.pijukebox.repository.IArtistRepository;
//import org.hamcrest.core.IsSame;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import java.util.Optional;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//public class ArtistServiceImplTest {
//
//    @Mock
//    private IArtistRepository artistRepository;
//
//    private ArtistServiceImpl artistServiceImplUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        initMocks(this);
//       // albumServiceImplUnderTest = new AlbumServiceImpl(mockAlbumRepository, mockSimpleAlbumRepository, mockGenreWithAlbumsRepository, mockArtistWithAlbumsRepository, mockAlbumWithTracksRepository, mockSimpleTrackRepository, mockSimpleArtistRepository, mockSimpleGenreRepository, mockAlbumWithArtistsRepository, mockAlbumWithGenreRepository);
//        artistServiceImplUnderTest = new ArtistServiceImpl(artistRepository);
//    }
//
//    @Test
//    void testFindById(){
//        // Given
//        final Long id = 1L;
//        final Optional<SimpleArtist> expectedResult = Optional.of(new SimpleArtist(1L,"yo"));
//        when(artistRepository.findById(anyLong())).thenReturn(expectedResult);
//
//        // When
//        final Optional<SimpleArtist> result = artistServiceImplUnderTest.findById(id);
//
//        // Then
//        // You are expecting service to return whatever returned by repo
//        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));
//
//        // You are expecting repo to be called once with correct param
//        verify(artistRepository).findById(1L);
//    }
//}

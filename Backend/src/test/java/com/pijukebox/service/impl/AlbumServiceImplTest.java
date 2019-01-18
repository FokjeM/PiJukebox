package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.repository.*;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AlbumServiceImplTest {

    @Mock
    private IAlbumRepository mockAlbumRepository;
    @Mock
    private ISimpleAlbumRepository mockSimpleAlbumRepository;
    @Mock
    private IGenreWithAlbumsRepository mockGenreWithAlbumsRepository;
    @Mock
    private IArtistWithAlbumsRepository mockArtistWithAlbumsRepository;
    @Mock
    private IAlbumWithTracksRepository mockAlbumWithTracksRepository;
    @Mock
    private ISimpleTrackRepository mockSimpleTrackRepository;
    @Mock
    private ISimpleArtistRepository mockSimpleArtistRepository;
    @Mock
    private ISimpleGenreRepository mockSimpleGenreRepository;
    @Mock
    private IAlbumWithArtistsRepository mockAlbumWithArtistsRepository;
    @Mock
    private IAlbumWithGenreRepository mockAlbumWithGenreRepository;

    private AlbumServiceImpl albumServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        albumServiceImplUnderTest = new AlbumServiceImpl(mockAlbumRepository, mockSimpleAlbumRepository, mockGenreWithAlbumsRepository, mockArtistWithAlbumsRepository, mockAlbumWithTracksRepository, mockSimpleTrackRepository, mockSimpleArtistRepository, mockSimpleGenreRepository, mockAlbumWithArtistsRepository, mockAlbumWithGenreRepository);
    }

    @Test
    void testFindSimpleAlbumById() {
        // Source: https://stackoverflow.com/a/36004293

        // Given
        final Long id = 1L;
        final Optional<SimpleAlbum> expectedResult = Optional.of(new SimpleAlbum(1L, "Sheep Reliability", null));
        when(mockSimpleAlbumRepository.findById(anyLong())).thenReturn(expectedResult);

        // When
        final Optional<SimpleAlbum> result = albumServiceImplUnderTest.findSimpleAlbumById(id);

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(mockSimpleAlbumRepository).findById(1L);
    }
}

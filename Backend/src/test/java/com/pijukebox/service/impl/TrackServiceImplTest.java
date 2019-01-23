package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.repository.*;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TrackServiceImplTest {

    @Mock
    private ITrackRepository mockTrackRepository;
    @Mock
    private IArtistWithTracksRepository mockArtistWithTracksRepository;
    @Mock
    private IGenreWithTracksRepository mockGenreWithTracksRepository;
    @Mock
    private ISimpleTrackRepository mockSimpleTrackRepository;

    private TrackServiceImpl trackServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        trackServiceImplUnderTest = new TrackServiceImpl(mockTrackRepository, mockArtistWithTracksRepository, mockGenreWithTracksRepository, mockSimpleTrackRepository);
    }

    @Test
    void testFindSimpleTrackById() {
        // Source: https://stackoverflow.com/a/36004293
        String newLine = System.getProperty("line.separator");
        System.out.println("Initiating testFindSimpleTrackById...");
        System.out.println(newLine);

        // Given
        final Long id = 1L;
        final Optional<SimpleTrack> expectedResult = Optional.of(new SimpleTrack(1L, "WWE Intro", "John Cena", "WWEIntro.mp3"));
        when(mockSimpleTrackRepository.findById(anyLong())).thenReturn(expectedResult);

        // When
        final Optional<SimpleTrack> result = trackServiceImplUnderTest.findSimpleTrackById(id);

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(mockSimpleTrackRepository).findById(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(newLine);
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

//    @Test
//    void testFindGenresByNameContaining() {
//        // Source: https://stackoverflow.com/a/36004293
//
//        String newLine = System.getProperty("line.separator");
//
//        System.out.println("Initiating testFindGenresByNameContaining...");
//        System.out.println(newLine);
//
//        // Given
//        final String name = "Hard Rock";
//        List<SimpleGenre> res = new ArrayList<>();
//        res.add(new SimpleGenre(1L, "Hard Rock"));
//        final Optional<List<SimpleGenre>> expectedResult = Optional.of(res);
//        when(mockGenreWithAlbumsRepository.findGenresByNameContaining(anyString())).thenReturn(expectedResult);
//
//        // When
//        final Optional<List<SimpleGenre>> result = mockGenreWithAlbumsRepository.findGenresByNameContaining(name);
//
//        // Then
//        // You are expecting service to return whatever returned by repo
//        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));
//
//        // You are expecting repo to be called once with correct param
//        verify(mockGenreWithAlbumsRepository).findGenresByNameContaining("Hard Rock");
//
//        System.out.println("Expected result: " + expectedResult);
//        System.out.println(newLine);
//        System.out.println("Given result: " + result);
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
//    }
}

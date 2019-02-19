package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.repository.IArtistWithTracksRepository;
import com.pijukebox.repository.IGenreWithTracksRepository;
import com.pijukebox.repository.ISimpleTrackRepository;
import com.pijukebox.repository.ITrackRepository;
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

/**
 * Testing class to verify the TrackServiceImplementation is implemented and
 * functions in the intended manner.
 */
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

    /**
     * Before each test, set up the Mock objects and a clean TrackServiceImplementation.
     */
    @BeforeEach
    void setUp() {
        initMocks(this);
        trackServiceImplUnderTest = new TrackServiceImpl(mockTrackRepository, mockArtistWithTracksRepository, mockGenreWithTracksRepository, mockSimpleTrackRepository);
    }

    /**
     * Test for finding a SimpleTrack based on the assigned ID.
     * This code has been provided by an external source and has only been
     * changed in the areas applicable to our usecase.
     * Original source: https://stackoverflow.com/a/36004293
     */
    @Test
    void testFindSimpleTrackById() {
        String newLine = System.getProperty("line.separator");
        System.out.println("Initiating testFindSimpleTrackById...");
        System.out.println(newLine);

        // Given
        final Long id = 1L;
        final Optional<SimpleTrack> expectedResult = Optional.of(new SimpleTrack(1L, "WWE Intro", "John Cena", "WWEIntro.mp3"));
        when(mockSimpleTrackRepository.findById(anyLong())).thenReturn(expectedResult);

        // When
        final SimpleTrack result = trackServiceImplUnderTest.findSimpleTrackById(id).getBody();

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
}

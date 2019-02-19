package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.repository.IGenreRepository;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GenreServiceImplTest {

    @Mock
    private IGenreRepository mockGenreWithAlbumsRepository;

    private GenreServiceImpl genreServiceImplUnderTest;

    /**
     * Setup before every test to initialize the Mock objects and GenreServiceImplementation
     */
    @BeforeEach
    void setUp() {
        initMocks(this);
        genreServiceImplUnderTest = new GenreServiceImpl(mockGenreWithAlbumsRepository);
    }

    /**
     * Test for getting a genre by the assigned ID
     * This code has been provided by an external source and has only been
     * changed in the areas applicable to our usecase.
     * Original source: https://stackoverflow.com/a/36004293
     */
    @Test
    void testGenreById() {
        String newLine = System.getProperty("line.separator");
        System.out.println("Initiating testGenreById...");
        System.out.println(newLine);

        // Given
        final Long id = 1L;
        final Optional<SimpleGenre> expectedResult = Optional.of(new SimpleGenre(1L, "Rock 'n Roll"));
        when(mockGenreWithAlbumsRepository.findById(anyLong())).thenReturn(expectedResult);

        // When
        final SimpleGenre result = genreServiceImplUnderTest.findById(id).getBody();

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(mockGenreWithAlbumsRepository).findById(1L);

        System.out.println("Expected result: " + expectedResult);
        System.out.println(newLine);
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Test for getting a genre by the assigned Name
     * This code has been provided by an external source and has only been
     * changed in the areas applicable to our usecase.
     * Original source: https://stackoverflow.com/a/36004293
     */
    @Test
    void testFindGenresByNameContaining() {

        String newLine = System.getProperty("line.separator");

        System.out.println("Initiating testFindGenresByNameContaining...");
        System.out.println(newLine);

        // Given
        final String name = "Hard Rock";
        List<SimpleGenre> res = new ArrayList<>();
        res.add(new SimpleGenre(1L, "Hard Rock"));
        final Optional<List<SimpleGenre>> expectedResult = Optional.of(res);
        when(mockGenreWithAlbumsRepository.findGenresByNameContaining(anyString())).thenReturn(expectedResult);

        // When
        final Optional<List<SimpleGenre>> result = mockGenreWithAlbumsRepository.findGenresByNameContaining(name);

        // Then
        // You are expecting service to return whatever returned by repo
        assertThat("result", result, is(IsSame.sameInstance(expectedResult)));

        // You are expecting repo to be called once with correct param
        verify(mockGenreWithAlbumsRepository).findGenresByNameContaining("Hard Rock");

        System.out.println("Expected result: " + expectedResult);
        System.out.println(newLine);
        System.out.println("Given result: " + result);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}

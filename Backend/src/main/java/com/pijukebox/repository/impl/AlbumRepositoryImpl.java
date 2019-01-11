package com.pijukebox.repository.impl;

import com.pijukebox.model.*;
import com.pijukebox.repository.IAlbumRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class AlbumRepositoryImpl implements IAlbumRepository {
    /* This gives us an EntityManager proxy, which gives or creates a thread-safe EntityManager for us every time we use it. */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

//    @Override
//    public List<Album> getAll() {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        query.select(table);
//
//        return em.createQuery(query).getResultList();
//    }
//
//    @Override
//    public Album getById(Long id) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        ParameterExpression<Long> parameter = cb.parameter(Long.class);
//        query.select(table).where(cb.equal(table.get("id"), parameter));
//
//        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
//    }

    @Override
    public Optional<Album> findById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> table = query.from(Album.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        ParameterExpression<Long> parameter = cb.parameter(Long.class);
//        query.multiselect(table).where(cb.equal(table.get("id"), parameter));
        return Optional.of(em.createQuery(query).setParameter(parameter, id).getSingleResult());
    }
    @Override
    public List<Album> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        SetJoin<Album, Genre> albumGenreSetJoin = query.from(Album.class).join(Album_.genres);
//        SetJoin<Album, Artist> albumArtistSetJoin = .join(Album_.artists);
//        cq.where( cb.equal(itemNode.get(Item_.id), 5 ) )
//                .distinct(true);
//        query.select(table);
        /*Join<Album, Genre> albumGenreJoin = */query.from(Album.class).join(Album_.genres);
        List<Album> album_genres = em.createQuery(query).getResultList();
        query = cb.createQuery(Album.class);
        /*Join<Album, Artist> albumArtistJoin =*/ query.from(Album.class).join(Album_.artists, JoinType.INNER);
        List<Album> album_artist = em.createQuery(query).getResultList();
        query = cb.createQuery(Album.class);
        /*Join<Album, Track> albumTrackJoin = */query.from(Album.class).join(Album_.tracks, JoinType.INNER);
        List<Album> album_tracks = em.createQuery(query).getResultList();
        int size = album_genres.size();
        for(int i = 0; i < size; i++)
        {
            album_genres.get(i).getTracks().addAll(album_tracks.get(i).getTracks());
            album_genres.get(i).getArtists().addAll(album_artist.get(i).getArtists());
        }
        return album_genres;
    }




    @Override
    public Album getAlbumGenre(Long id) {
//        Map<Album, Genre> albumGenreRes = new HashMap<>();
//        Metamodel m = em.getMetamodel();
//        EntityType<Album> Album_ = m.entity(Album.class);
//        EntityType<Genre> Genre_ = m.entity(Genre.class);
//        Root<Genre> albumGenre = query.from(Genre.class);
//        Join<Album, Genre> genreJoin = table.join("genres", JoinType.INNER);
//        Join<Genre, AlbumGenre> albumGenreJoin = table.join("id", JoinType.INNER);
//        ParameterExpression<Long> parameter = cb.parameter(Long.class);
//        ListJoin<Album, Genre>  albumGenre = table.joinList("genres");
//        query.select(table).where()
//        albumGenreJoin.on(cb.equal(albumAlbumGenreJoin.get("album_id"), id))
//        query.multiselect(table,
//                          genreJoin);
//        Album album = new Album(1L, "Sheep Reliability", "NULL");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> table = query.from(Album.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));
        Album album = em.createQuery(query).setParameter(parameter, id).getSingleResult();


        CriteriaQuery<AlbumGenre> query1 = cb.createQuery(AlbumGenre.class);
        Root<AlbumGenre> table1 = query1.from(AlbumGenre.class);
        query1.select(table1).where(cb.equal(table1.get("album_id"), parameter));

        List<AlbumGenre> albumGenres = em.createQuery(query1).setParameter(parameter, id).getResultList();

        CriteriaQuery<Genre> query2 = cb.createQuery(Genre.class);
        Root<Genre> table2 = query2.from(Genre.class);
        query2.select(table2);

        List<Genre> genres = em.createQuery(query2).getResultList();

        for(AlbumGenre albumGenre : albumGenres)
        {
            for(Genre genre : genres)
            {
                if(albumGenre.getGenre_id() == genre.getId())
                {
                    album.getGenres().add(genre);
                    System.out.println(album.getName() + " \n " +
                                       album.getId() + " \n " +
                                       genre.getName()+ " \n " +
                                       genre.getId());
                }
            }
        }
        return album;
//        return new Album();
    }

    @Override
    public List<Album> getAlbumsDetails() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> table = query.from(Album.class);
        query.select(table);
        List<Album> albums = em.createQuery(query).getResultList();


        CriteriaQuery<AlbumGenre> query1 = cb.createQuery(AlbumGenre.class);
        Root<AlbumGenre> table1 = query1.from(AlbumGenre.class);
        query1.select(table1);

        List<AlbumGenre> albumGenres = em.createQuery(query1).getResultList();

        CriteriaQuery<Genre> query2 = cb.createQuery(Genre.class);
        Root<Genre> table2 = query2.from(Genre.class);
        query2.select(table2);

        List<Genre> genres = em.createQuery(query2).getResultList();


        CriteriaQuery<AlbumTrack> query3 = cb.createQuery(AlbumTrack.class);
        Root<AlbumTrack> table3 = query3.from(AlbumTrack.class);
        query3.select(table3);

        List<AlbumTrack> albumTracks = em.createQuery(query3).getResultList();

        CriteriaQuery<Track> query4 = cb.createQuery(Track.class);
        Root<Track> table4 = query4.from(Track.class);
        query4.select(table4);

        List<Track> tracks = em.createQuery(query4).getResultList();

        CriteriaQuery<AlbumArtist> query5 = cb.createQuery(AlbumArtist.class);
        Root<AlbumArtist> table5 = query5.from(AlbumArtist.class);
        query5.select(table5);

        List<AlbumArtist> albumArtists = em.createQuery(query5).getResultList();

        CriteriaQuery<Artist> query6 = cb.createQuery(Artist.class);
        Root<Artist> table6 = query6.from(Artist.class);
        query6.select(table6);

        List<Artist> artists = em.createQuery(query6).getResultList();


        for(Album album : albums)
        {
            for(AlbumGenre albumGenre : albumGenres)
            {
                if(albumGenre.getAlbum_id() == album.getId())
                {
                    for(Genre genre : genres)
                    {
                        if(albumGenre.getGenre_id() == genre.getId())
                        {
                            album.getGenres().add(genre);
                        }
                    }
                }
            }
            for(AlbumTrack albumTrack : albumTracks)
            {
                if(albumTrack.getAlbum_id() == album.getId())
                {
                    for(Track track : tracks)
                    {
                        if(albumTrack.getTrack_id() == track.getId())
                        {
                            album.getTracks().add(track);
                        }
                    }
                }
            }
            for(AlbumArtist albumArtist : albumArtists)
            {
                if(albumArtist.getAlbum_id() == album.getId())
                {
                    for(Artist artist : artists)
                    {
                        if(albumArtist.getArtist_id() == artist.getId())
                        {
                            album.getArtists().add(artist);
                    }
                    }
                }
            }
        }
        return albums;
    }
}

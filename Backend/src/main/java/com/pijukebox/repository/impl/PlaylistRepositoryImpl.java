package com.pijukebox.repository.impl;

import com.pijukebox.model.Album;
import com.pijukebox.model.Playlist;
import com.pijukebox.model.Track;
import com.pijukebox.repository.IPlaylistRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Map;

public class PlaylistRepositoryImpl {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;
    
    public Map<Playlist, Track> getAllPlaylistTracks(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Playlist> query = cb.createQuery(Playlist.class);
        Root<Playlist> table = query.from(Playlist.class);
        Join<Playlist, Track> trackJoin = table.join("mappedB", JoinType.LEFT);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));
        return null;
    }
}

package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import static org.junit.jupiter.api.Assertions.*;

import com.amazon.ata.music.playlist.service.models.SongModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ModelConverterTest {
    Playlist playlist;
    AlbumTrack albumTrack;
    @BeforeEach
    public void setup() {
        playlist = new Playlist();
        playlist.setName("name");
        playlist.setCustomerId("String");
        playlist.setSongList(new ArrayList<>());
        playlist.setId("id");
        playlist.setSongCount(0);
        playlist.setTags(new HashSet<>(List.of(new String[]{"tags"})));

        albumTrack = new AlbumTrack();
        albumTrack.setAlbumName("name");
        albumTrack.setAsin("asin");
        albumTrack.setSongTitle("title");
        albumTrack.setTrackNumber(1);

    }

    @Test
    public void toPlaylistModel_validCall_returnsPlaylistModel() {
        // WHEN
        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(playlist);

        // THEN
        assertEquals(playlist.getName(), playlistModel.getName());
        assertTrue(playlist.getTags().contains(playlistModel.getTags().get(0)));
    }

    @Test
    public void toPlaylistModel_nullTags_tagsRemainNull() {
        // GIVEN
         playlist.setTags(null);

        // WHEN
        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(playlist);

        // THEN
        assertNull(playlistModel.getTags());
    }

    @Test
    public void toSongModel_validCall_returnsSongModel() {
        // WHEN
        SongModel songModel = new ModelConverter().toSongModel(albumTrack);

        // THEN
        assertEquals(albumTrack.getAsin(), songModel.getAsin());
    }
}

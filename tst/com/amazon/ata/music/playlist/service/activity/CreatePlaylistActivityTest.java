package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    private CreatePlaylistActivity createPlaylistActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        createPlaylistActivity = new CreatePlaylistActivity(playlistDao);
    }

    @Test
    public void handleRequest_validValues_returnsPlaylistModelInResult() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        int expectedSongCount = 0;
        List<String> expectedTags = Lists.newArrayList("tag");

        Playlist playlist = new Playlist();
        playlist.setName(expectedName);
        playlist.setCustomerId(expectedCustomerId);
        playlist.setSongCount(expectedSongCount);
        playlist.setTags(Sets.newHashSet(expectedTags));

        when(playlistDao.savePlaylist(playlist)).thenReturn(playlist);

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN
        CreatePlaylistResult result = createPlaylistActivity.handleRequest(request, null);

        // THEN
        assertNotNull(result.getPlaylist().getId());
        assertEquals(expectedName, result.getPlaylist().getName());
        assertEquals(expectedCustomerId, result.getPlaylist().getCustomerId());
        assertEquals(expectedSongCount, result.getPlaylist().getSongCount());
        assertEquals(expectedTags, result.getPlaylist().getTags());
    }

    @Test
    public void handleRequest_invalidCustomerId_throwsInvalidAttributeValueException() {
        // GIVEN
        String expectedName = "expectedName";
        String invalidCustomerId = "";

        Playlist playlist = new Playlist();

        when(playlistDao.savePlaylist(playlist)).thenReturn(playlist);

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(invalidCustomerId)
                .build();

        assertThrows(InvalidAttributeValueException.class, () ->
                createPlaylistActivity.handleRequest(request, null));

    }

    @Test
    public void handleRequest_invalidName_throwsInvalidAttributeValueException() {
        // GIVEN
        String expectedName = "";
        String invalidCustomerId = "customerId";

        Playlist playlist = new Playlist();

        when(playlistDao.savePlaylist(playlist)).thenReturn(playlist);

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(invalidCustomerId)
                .build();

        assertThrows(InvalidAttributeValueException.class, () ->
                createPlaylistActivity.handleRequest(request, null));

    }

    @Test
    public void handleRequest_nullTags_returnsPlaylistModelWithNullTags() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        int expectedSongCount = 0;

        Playlist playlist = new Playlist();
        playlist.setName(expectedName);
        playlist.setCustomerId(expectedCustomerId);
        playlist.setSongCount(expectedSongCount);

        when(playlistDao.savePlaylist(playlist)).thenReturn(playlist);

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .build();

        // WHEN
        CreatePlaylistResult result = createPlaylistActivity.handleRequest(request, null);

        // THEN
        assertNull(result.getPlaylist().getTags());
    }
}

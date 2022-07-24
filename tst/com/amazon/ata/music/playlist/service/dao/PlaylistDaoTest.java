package com.amazon.ata.music.playlist.service.dao;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDaoTest {
    @Mock
    private DynamoDBMapper dynamoDbMapper;

    @InjectMocks
    private PlaylistDao playlistDao;
    @BeforeEach
    public void setUp() {
        initMocks(this);
        playlistDao = new PlaylistDao(dynamoDbMapper);
    }

    @Test
    public void getPlaylist_validIdPassed_ReturnsPlaylist() {
        // Given
        String validId = "1234";
        Playlist playlist = new Playlist();
        playlist.setId(validId);
        // When
        when(dynamoDbMapper.load(Playlist.class, validId)).thenReturn(playlist);
        Playlist result = playlistDao.getPlaylist(validId);

        // Then
        assertEquals(playlist.getId(), result.getId());
    }

    @Test
    public void getPlaylist_invalidIdPassed_ThrowsPlaylistNotFoundException() {
        // Given
        String invalidId = "1234";
        // When
        when(dynamoDbMapper.load(Playlist.class, invalidId)).thenReturn(null);

        // Then
        assertThrows(PlaylistNotFoundException.class, () ->
                playlistDao.getPlaylist(invalidId));
    }

    @Test
    public void savePlaylist_validCustomerIdPassed_ReturnsPlaylist() {
        // Given
        String customerId = "1234";
        String name = "song";
        List<String> tags = Lists.newArrayList("tag");
        Playlist playlist = new Playlist();
        playlist.setCustomerId(customerId);
        playlist.setName(name);
        playlist.setTags(Sets.newHashSet(tags));
        // When
        Playlist result = playlistDao.savePlaylist(playlist);

        // Then
        assertEquals(playlist.getName(), result.getName());
        assertEquals(playlist.getCustomerId(), result.getCustomerId());
        assertTrue(result.getTags().contains("tag"));
    }



}

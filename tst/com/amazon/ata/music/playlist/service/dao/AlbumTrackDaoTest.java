package com.amazon.ata.music.playlist.service.dao;

import com.amazon.ata.music.playlist.service.dynamodb.AlbumTrackDao;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlbumTrackDaoTest {
    @Mock
    private DynamoDBMapper dynamoDbMapper;

    private AlbumTrackDao albumTrackDao;
    @BeforeEach
    public void setUp() {
        initMocks(this);
        albumTrackDao = new AlbumTrackDao(dynamoDbMapper);
    }

    @Test
    public void getPlaylist_validIdPassed_ReturnsPlaylist() {
        // Given
        String asin = "1234";
        AlbumTrack albumTrack = new AlbumTrack();
        albumTrack.setAsin(asin);
        albumTrack.setTrackNumber(1);
        // When
        when(dynamoDbMapper.load(AlbumTrack.class, asin)).thenReturn(albumTrack);
        AlbumTrack result = albumTrackDao.getAlbumTrack(asin, albumTrack.getTrackNumber());

        // Then
        assertEquals(albumTrack.getAsin(), result.getAsin());
        assertEquals(albumTrack.getTrackNumber(), result.getTrackNumber());
    }
}

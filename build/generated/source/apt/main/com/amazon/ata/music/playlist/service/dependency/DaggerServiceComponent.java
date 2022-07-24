package com.amazon.ata.music.playlist.service.dependency;

import com.amazon.ata.music.playlist.service.activity.AddSongToPlaylistActivity;
import com.amazon.ata.music.playlist.service.activity.CreatePlaylistActivity;
import com.amazon.ata.music.playlist.service.activity.GetPlaylistActivity;
import com.amazon.ata.music.playlist.service.activity.GetPlaylistSongsActivity;
import com.amazon.ata.music.playlist.service.activity.UpdatePlaylistActivity;
import com.amazon.ata.music.playlist.service.dynamodb.AlbumTrackDao;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerServiceComponent implements ServiceComponent {
  private Provider<DynamoDBMapper> provideDynamoDBWrapperProvider;

  private DaggerServiceComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ServiceComponent create() {
    return new Builder().build();
  }

  private PlaylistDao getPlaylistDao() {
    return new PlaylistDao(provideDynamoDBWrapperProvider.get());
  }

  private AlbumTrackDao getAlbumTrackDao() {
    return new AlbumTrackDao(provideDynamoDBWrapperProvider.get());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideDynamoDBWrapperProvider =
        DoubleCheck.provider(DaoModule_ProvideDynamoDBWrapperFactory.create(builder.daoModule));
  }

  @Override
  public AddSongToPlaylistActivity provideAddSongToPlaylistActivity() {
    return new AddSongToPlaylistActivity(getPlaylistDao(), getAlbumTrackDao());
  }

  @Override
  public CreatePlaylistActivity provideCreatePlaylistActivity() {
    return new CreatePlaylistActivity(getPlaylistDao());
  }

  @Override
  public GetPlaylistActivity provideGetPlaylistActivity() {
    return new GetPlaylistActivity(getPlaylistDao());
  }

  @Override
  public UpdatePlaylistActivity provideUpdatePlaylistActivity() {
    return new UpdatePlaylistActivity(getPlaylistDao());
  }

  @Override
  public GetPlaylistSongsActivity provideGetPlaylistSongsActivity() {
    return new GetPlaylistSongsActivity(getPlaylistDao());
  }

  public static final class Builder {
    private DaoModule daoModule;

    private Builder() {}

    public ServiceComponent build() {
      if (daoModule == null) {
        this.daoModule = new DaoModule();
      }
      return new DaggerServiceComponent(this);
    }

    public Builder daoModule(DaoModule daoModule) {
      this.daoModule = Preconditions.checkNotNull(daoModule);
      return this;
    }
  }
}

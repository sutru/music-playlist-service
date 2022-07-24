package com.amazon.ata.music.playlist.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaoModule_ProvideDynamoDBWrapperFactory implements Factory<DynamoDBMapper> {
  private final DaoModule module;

  public DaoModule_ProvideDynamoDBWrapperFactory(DaoModule module) {
    this.module = module;
  }

  @Override
  public DynamoDBMapper get() {
    return Preconditions.checkNotNull(
        module.provideDynamoDBWrapper(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static DaoModule_ProvideDynamoDBWrapperFactory create(DaoModule module) {
    return new DaoModule_ProvideDynamoDBWrapperFactory(module);
  }

  public static DynamoDBMapper proxyProvideDynamoDBWrapper(DaoModule instance) {
    return Preconditions.checkNotNull(
        instance.provideDynamoDBWrapper(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}

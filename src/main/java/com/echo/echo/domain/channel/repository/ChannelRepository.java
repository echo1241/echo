package com.echo.echo.domain.channel.repository;

import com.echo.echo.domain.channel.entity.Channel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ChannelRepository extends ReactiveCrudRepository<Channel, Long> {
    Flux<Channel> findByChannelType(String channelType);
}

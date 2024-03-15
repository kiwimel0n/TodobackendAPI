package com.sparta.todolistmanage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Token {

    @Id
    private String username;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

}

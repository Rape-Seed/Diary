package com.example.diary.global.redis;

import lombok.Getter;

@Getter
public enum RedisKey {
    REFRESH("REFRESH_"),
    ACCESS("Bearer_"),
    BLACKLIST("Logout_");

    private String key;

    RedisKey(String key) {
        this.key = key;
    }

}

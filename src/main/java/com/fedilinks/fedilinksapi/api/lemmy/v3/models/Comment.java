package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Comment(
        Long id,
        Long creator_id,
        Long post_id,
        String content,
        boolean removed,
        String published,
        String updated,
        boolean deleted,
        String ap_id,
        boolean local,
        String path,
        boolean distinguished,
        Long language_id
) {
}
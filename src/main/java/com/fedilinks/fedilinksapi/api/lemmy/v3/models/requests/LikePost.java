package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record LikePost(
        int post_id,
        int score,
        String auth
) {
}
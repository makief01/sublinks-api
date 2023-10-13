package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeletePost(
        int post_id,
        boolean deleted,
        String auth
) {
}
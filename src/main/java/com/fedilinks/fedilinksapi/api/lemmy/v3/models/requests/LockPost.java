package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record LockPost(
        int post_id,
        boolean locked,
        String auth
) {
}
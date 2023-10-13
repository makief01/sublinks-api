package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DistinguishComment(
        int comment_id,
        boolean distinguished,
        String auth
) {
}
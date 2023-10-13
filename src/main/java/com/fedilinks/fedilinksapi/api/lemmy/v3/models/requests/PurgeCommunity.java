package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record PurgeCommunity(
        int community_id,
        String reason,
        String auth
) {
}
package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record GetUnreadCountResponse(
        int replies,
        int mentions,
        int private_messages
) {
}
package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record GetCaptchaResponse(
        CaptchaResponse ok
) {
}
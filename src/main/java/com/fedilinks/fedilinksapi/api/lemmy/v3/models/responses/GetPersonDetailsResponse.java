package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommentView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record GetPersonDetailsResponse(
        PersonView person_view,
        Collection<CommentView> comments,
        Collection<PostView> posts,
        Collection<CommunityModeratorView> moderates
) {
}
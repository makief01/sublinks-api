package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Comment;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.ModRemoveComment;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record ModRemoveCommentView(
        ModRemoveComment mod_remove_comment,
        Person moderator,
        Comment comment,
        Person commenter,
        Post post,
        Community community
) {
}
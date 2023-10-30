package com.sublinks.sublinksapi.person.event;

import com.sublinks.sublinksapi.person.LinkPersonPost;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LinkPersonPostCreatedEvent extends ApplicationEvent {
    private final LinkPersonPost linkPersonPost;

    public LinkPersonPostCreatedEvent(
            final Object source,
            final LinkPersonPost linkPersonPost
    ) {
        super(source);
        this.linkPersonPost = linkPersonPost;
    }
}
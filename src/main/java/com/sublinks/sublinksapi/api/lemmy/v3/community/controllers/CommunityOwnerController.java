package com.sublinks.sublinksapi.api.lemmy.v3.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.EditCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.services.CommunityService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.repositories.LinkPersonCommunityRepository;
import com.sublinks.sublinksapi.utils.SlugUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/community")
public class CommunityOwnerController {
    private final LocalInstanceContext localInstanceContext;
    private final LinkPersonCommunityRepository linkPersonCommunityRepository;
    private final CommunityService communityService;
    private final AuthorizationService authorizationService;
    private final LemmyCommunityService lemmyCommunityService;
    private final SlugUtil slugUtil;

    @PostMapping
    @Transactional
    public CommunityResponse create(@Valid @RequestBody final CreateCommunity createCommunityForm, JwtPerson principal) {

        Person person = (Person) principal.getPrincipal();
        authorizationService
                .canPerson(person)
                .performTheAction(AuthorizeAction.create)
                .onEntity(AuthorizedEntityType.community)
                .defaultingToAllow() // @todo use site setting to allow community creation
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        final List<Language> languages = new ArrayList<>();
        for (String languageCode : createCommunityForm.discussion_languages()) {
            final Optional<Language> language = localInstanceContext.languageRepository().findById(Long.valueOf(languageCode));
            language.ifPresent(languages::add);
        }

        Community community = Community.builder()
                .instance(localInstanceContext.instance())
                .title(createCommunityForm.title())
                .titleSlug(slugUtil.stringToSlug(createCommunityForm.title()))
                .description(createCommunityForm.description())
                .isPostingRestrictedToMods(createCommunityForm.posting_restricted_to_mods())
                .isNsfw(createCommunityForm.nsfw())
                .iconImageUrl("")
                .bannerImageUrl("")
                .languages(languages)
                .build();

        final Set<LinkPersonCommunity> linkPersonCommunities = new LinkedHashSet<>();
        linkPersonCommunities.add(LinkPersonCommunity.builder()
                .community(community)
                .person(person)
                .linkType(LinkPersonCommunityType.owner)
                .build());
        linkPersonCommunities.add(LinkPersonCommunity.builder()
                .community(community)
                .person(person)
                .linkType(LinkPersonCommunityType.follower)
                .build());

        communityService.createCommunity(community);
        linkPersonCommunityRepository.saveAllAndFlush(linkPersonCommunities);

        return lemmyCommunityService.createCommunityResponse(community, person);
    }

    @PutMapping
    CommunityResponse update(@Valid final EditCommunity editCommunityForm) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}

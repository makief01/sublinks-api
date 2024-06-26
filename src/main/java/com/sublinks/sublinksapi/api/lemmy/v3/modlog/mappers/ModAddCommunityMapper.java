package com.sublinks.sublinksapi.api.lemmy.v3.modlog.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.ModAddCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.moderation.entities.ModerationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModAddCommunityMapper extends Converter<ModerationLog, ModAddCommunity> {

  @Override
  @Mapping(target = "id", source = "moderationLog.id")
  @Mapping(target = "when_", source = "moderationLog.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "mod_person_id", source = "moderationLog.moderationPersonId")
  @Mapping(target = "other_person_id", source = "moderationLog.otherPersonId")
  @Mapping(target = "community_id", source = "moderationLog.communityId")
  @Mapping(target = "removed", source = "moderationLog.removed")
  ModAddCommunity convert(@Nullable ModerationLog moderationLog);
}

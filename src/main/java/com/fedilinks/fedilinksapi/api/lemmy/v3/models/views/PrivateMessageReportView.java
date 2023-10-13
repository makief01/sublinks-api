package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.PrivateMessage;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.PrivateMessageReport;
import lombok.Builder;

@Builder
public record PrivateMessageReportView(
        PrivateMessageReport private_message_report,
        PrivateMessage private_message,
        Person private_message_creator,
        Person creator,
        Person resolver
) {
}
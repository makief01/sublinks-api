package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtUtil;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.models.LoginResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.GetSiteResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.GetPersonDetailsResponseMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BanPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BannedPersonsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.BlockPersonResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.DeleteAccountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GenerateTotpSecretResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetCaptchaResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetails;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetailsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonMentionsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetRepliesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetReportCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetUnreadCountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Login;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PasswordResetResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMentionResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Register;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.UpdateTotpResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.VerifyEmailResponse;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonRepository;
import com.sublinks.sublinksapi.person.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping(path = "/api/v3/user")
public class UserController {
    private final JwtUtil jwtUtil;

    private final PersonService personService;

    private final PersonRepository personRepository;

    private final GetPersonDetailsResponseMapper getPersonDetailsResponseMapper;

    public UserController(
            JwtUtil jwtUtil,
            PersonService personService,
            PersonRepository personRepository,
            GetPersonDetailsResponseMapper getPersonDetailsResponseMapper) {
        this.jwtUtil = jwtUtil;
        this.personService = personService;
        this.personRepository = personRepository;
        this.getPersonDetailsResponseMapper = getPersonDetailsResponseMapper;
    }

    @PostMapping("register")
    LoginResponse create(@Valid @RequestBody Register registerForm) throws NoSuchAlgorithmException {
        Person person = personService.create(
                registerForm.username()
        );
        personRepository.save(person);
        String token = jwtUtil.generateToken(person);
        return LoginResponse.builder()
                .jwt(token)
                .registration_created(false)
                .verify_email_sent(false)
                .build();
    }

    @GetMapping("get_captcha")
    GetCaptchaResponse captcha() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping()
    GetPersonDetailsResponse show(@Valid GetPersonDetails getPersonDetailsForm) {
        Long userId = null;
        Person person = null;
        if (getPersonDetailsForm.person_id() != null) {
            userId = (long) getPersonDetailsForm.person_id();
            person = personRepository.findById(userId).orElseThrow();
        } else if (getPersonDetailsForm.username() != null) {
            person = personRepository.findOneByName(getPersonDetailsForm.username());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no_id_given");
        }

        return getPersonDetailsResponseMapper.PersonToGetPersonDetailsResponse(
                personService.getPersonContext(person)
        );
    }

    @GetMapping("mention")
    GetPersonMentionsResponse mention() {
        return GetPersonMentionsResponse.builder().build();
    }

    @PostMapping("mention/mark_as_read")
    PersonMentionResponse mentionMarkAsRead() {
        return PersonMentionResponse.builder().build();
    }

    @GetMapping("replies")
    GetRepliesResponse replies() {
        return GetRepliesResponse.builder().build();
    }

    @PostMapping("ban")
    BanPersonResponse ban() {
        return BanPersonResponse.builder().build();
    }

    @GetMapping("banned")
    BannedPersonsResponse bannedList() {
        Collection<PersonView> bannedPersons = new HashSet<>();
        return BannedPersonsResponse.builder()
                .banned(bannedPersons)
                .build();
    }

    @PostMapping("block")
    BlockPersonResponse block() {
        return BlockPersonResponse.builder().build();
    }

    @PostMapping("login")
    LoginResponse login(@Valid @RequestBody Login loginForm) {
        Person person = personRepository.findOneByName(loginForm.username_or_email());
        String token = jwtUtil.generateToken(person);
        return LoginResponse.builder()
                .jwt(token)
                .registration_created(false)
                .verify_email_sent(false)
                .build();
    }

    @PostMapping("delete_account")
    DeleteAccountResponse delete() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("password_reset")
    PasswordResetResponse passwordReset() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("password_change")
    LoginResponse passwordChange() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("mark_all_as_read")
    GetRepliesResponse markAllAsRead() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("save_user_settings")
    LoginResponse saveUserSettings() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("change_password")
    LoginResponse changePassword() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("report_count")
    GetReportCountResponse reportCount() {
        return GetReportCountResponse.builder().build();
    }

    @GetMapping("unread_count")
    GetUnreadCountResponse unreadCount() {
        return GetUnreadCountResponse.builder().build();
    }

    @PostMapping("verify_email")
    VerifyEmailResponse verifyEmail() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("leave_admin")
    GetSiteResponse leaveAdmin() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("totp/generate")
    GenerateTotpSecretResponse totpGenerate() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("totp/update")
    UpdateTotpResponse totpUpdate() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
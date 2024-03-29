package me.damascus2000.sockapplication.services;

import me.damascus2000.sockapplication.entity.assist.AssistMember;
import me.damascus2000.sockapplication.entity.assist.MembersResponseEntity;
import me.damascus2000.sockapplication.entity.assist.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AssistService {

    public static final String MODULAS_COOKIE = ".MODULAS";

    public static String SIGN_IN = "/signin.aspx";
    public static String MEMBERS_STRING =
        "api/member/members/%d/1?searchTerm=%s&teamId=0&functionId=0&activityId=undefined&includeDroppedOutMembersAnyway=false";

    public static String getMembersUri(int year) {
        return getMembersUri(year, "");
    }

    public static String getMembersUri(int year, String searchTerm) {
        return String.format(MEMBERS_STRING, year, searchTerm);
    }

    public static String PERSON = "/api/person/";
    public static String MEMBER = "/api/member/";

    public final MultiValueMap<String, ResponseCookie> cookies = new LinkedMultiValueMap<>();
    private String cookie;

    public WebClient webClient;
    private MultiValueMap<String, String> formData;

    public AssistService(@Autowired WebClient webClient, @Autowired MultiValueMap<String, String> formData) throws Exception {
        this.webClient = webClient;
        this.formData = formData;
        authenticate();
    }

    private void authenticate() throws Exception {
        webClient
            .post()
            .uri(SIGN_IN)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .onStatus(HttpStatusCode::is3xxRedirection, clientResponse -> {
                System.out.println(clientResponse.cookies());
                clientResponse.cookies().forEach(this.cookies::addAll);
                return Mono.empty();
            })
            .toBodilessEntity()
            .block();
    }

    public Mono<ResponseEntity<MembersResponseEntity>> getMonoMembers() {
        return webClient.get()
            .uri(getMembersUri(66446))
            .cookie(MODULAS_COOKIE, this.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(MembersResponseEntity.class)
            .doOnError(a -> {
                throw new RuntimeException("invalid");
            });
    }

    public Mono<ResponseEntity<MembersResponseEntity>> getMonoMembers(String searchTerm) {
        return webClient.get()
            .uri(getMembersUri(66446, searchTerm))
            .cookie(MODULAS_COOKIE, this.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(MembersResponseEntity.class)
            .doOnError(a -> {
                throw new RuntimeException("Invalid: " + a.getCause());
            });
    }

    public MembersResponseEntity getMembers() {
        return getMonoMembers().block().getBody();
    }

    public Person getPerson(int id) {
        ResponseEntity<Person> responseEntity = getMonoPerson(id)
            .block();
        return responseEntity.getBody();
    }

    public Mono<ResponseEntity<Person>> getMonoPerson(int id) {
        return webClient.get()
            .uri(PERSON + id)
            .cookie(MODULAS_COOKIE, this.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(Person.class)
            .doOnError(a -> {
                throw new RuntimeException("invalid person id");
            });
    }

    public void savePerson(Person person) {
        webClient.put()
            .uri(PERSON + person.getId())
            .cookie(MODULAS_COOKIE, this.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .bodyValue(person)
            .retrieve()
            .toBodilessEntity()
            .subscribe();
    }

    public Mono<ResponseEntity<AssistMember>> getMonoMember(int id) {
        return webClient.get()
            .uri(MEMBER + id)
            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .cookie(MODULAS_COOKIE, this.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(AssistMember.class)
            .doOnError(a -> {
                a.printStackTrace();
                throw new RuntimeException("invalid member id");
            });
    }
}

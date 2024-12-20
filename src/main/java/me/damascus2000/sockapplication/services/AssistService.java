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

import java.util.function.Consumer;

@Service
public class AssistService {

    public static final String APPLICATION_COOKIE = ".AspNet.ApplicationCookie";

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

    public void authenticate() {
        webClient
            .post()
            .uri(SIGN_IN)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .onStatus(HttpStatusCode::is1xxInformational, clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    System.out.println(clientResponse.cookies());
                    return clientResponse.createException();
                }
            )
            .onStatus(HttpStatusCode::is2xxSuccessful, clientResponse -> {
                    MultiValueMap<String, ResponseCookie> cookies = clientResponse.cookies();
                    clientResponse.cookies().forEach(this.cookies::addAll);
                    if (!cookies.containsKey(APPLICATION_COOKIE)) {
                        System.out.println(clientResponse.statusCode());
                        System.out.println(clientResponse.cookies());
                        return clientResponse.createException();
                    }
                    return Mono.empty();
                }
            )
            .onStatus(HttpStatusCode::is3xxRedirection, clientResponse -> {
                MultiValueMap<String, ResponseCookie> cookies = clientResponse.cookies();
                clientResponse.cookies().forEach(this.cookies::addAll);
                if (!cookies.containsKey(APPLICATION_COOKIE)) {
                    System.out.println(clientResponse.statusCode());
                    System.out.println(clientResponse.cookies());
                    return clientResponse.createException();
                }
                return clientResponse.createException();
            })
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                System.out.println(clientResponse.statusCode());
                System.out.println(clientResponse.cookies());
                return clientResponse.createException();
            })
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                System.out.println(clientResponse.statusCode());
                System.out.println(clientResponse.cookies());
                return clientResponse.createException();
            })
            .onStatus(HttpStatusCode::isError, clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    System.out.println(clientResponse.cookies());
                    return clientResponse.createException();
                }
            )
            .toBodilessEntity()
            .block();
    }

    public Mono<ResponseEntity<MembersResponseEntity>> getMonoMembers() {
        authenticate();
        return webClient.get()
            .uri(getMembersUri(94390))
            .cookie(APPLICATION_COOKIE, this.cookies.get(APPLICATION_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(MembersResponseEntity.class)
            .doOnError(a -> {
                a.printStackTrace();
            });
    }

    public Mono<ResponseEntity<MembersResponseEntity>> getMonoMembers(String searchTerm) {
        authenticate();
        return webClient.get()
            .uri(getMembersUri(94390, searchTerm))
            .cookie(APPLICATION_COOKIE, this.cookies.get(APPLICATION_COOKIE).getFirst().getValue())
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
        authenticate();
        return webClient.get()
            .uri(PERSON + id)
            .cookie(APPLICATION_COOKIE, this.cookies.get(APPLICATION_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(Person.class)
            .doOnError(a -> {
                throw new RuntimeException("invalid person id");
            });
    }

    public void savePerson(Person person, Consumer<? super ResponseEntity<Void>> onSucces, Consumer<? super Throwable> onError) {
        webClient.put()
            .uri(PERSON + person.getId())
            .cookie(APPLICATION_COOKIE, this.cookies.get(APPLICATION_COOKIE).getFirst().getValue())
            .bodyValue(person)
            .retrieve()
            .toBodilessEntity()
            .doOnSuccess(onSucces)
            .doOnError(onError)
            .subscribe();
    }

    public Mono<ResponseEntity<AssistMember>> getMonoMember(int id) {
        authenticate();
        return webClient.get()
            .uri(MEMBER + id)
            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .cookie(APPLICATION_COOKIE, this.cookies.get(APPLICATION_COOKIE).getFirst().getValue())
            .retrieve()
            .toEntity(AssistMember.class)
            .doOnError(a -> {
                a.printStackTrace();
                throw new RuntimeException("invalid member id");
            });
    }

    /*public void getBirthDays(){
        webClient.get().uri();
    }*/
}

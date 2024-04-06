package me.damascus2000.sockapplication.services;

import static me.damascus2000.sockapplication.services.AssistService.MODULAS_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.damascus2000.sockapplication.entity.assist.AssistMember;
import me.damascus2000.sockapplication.entity.assist.MembersResponseEntity;
import me.damascus2000.sockapplication.entity.assist.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class AssistServiceTest {

    @Autowired
    private AssistService assistService;

    AssistServiceTest() throws Exception {
    }

    @Test
    void shouldParsePersonCorrectly() throws Exception {
        String string = assistService.webClient.get()
            .uri(AssistService.PERSON + 988155)
            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .cookie(MODULAS_COOKIE, assistService.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        JsonNode expectedTree = new ObjectMapper().readTree(string);

        ResponseEntity<Person> person = assistService.getMonoPerson(988155).block();
        JsonNode mappedTree = new ObjectMapper().valueToTree(person.getBody());

        assertThat(mappedTree).isEqualTo(expectedTree);
    }

    @Test
    void shouldParseMemberCorrectly() throws Exception {
        int member = 1368818;
        String string = assistService.webClient.get()
            .uri(AssistService.MEMBER + member)
            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .cookie(MODULAS_COOKIE, assistService.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        JsonNode expectedTree = new ObjectMapper().readTree(string);

        ResponseEntity<AssistMember> person = assistService.getMonoMember(member).block();
        JsonNode mappedTree = new ObjectMapper().valueToTree(person.getBody());

        assertThat(mappedTree).isEqualTo(expectedTree);
    }

    @Test
    void shoudlParseMinimalMemberCorrectly() throws Exception {
        String string = assistService.webClient.get()
            .uri(AssistService.getMembersUri(66446))
            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .cookie(MODULAS_COOKIE, assistService.cookies.get(MODULAS_COOKIE).getFirst().getValue())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        JsonNode expectedJson = new ObjectMapper().readTree(string).get("Items").get(0);

        MembersResponseEntity members = assistService.getMembers();
        JsonNode mappedJson = new ObjectMapper().valueToTree(members).get("Items").get(0);

        assertThat(mappedJson).isEqualTo(expectedJson);
    }

    @Test
    void shouldCreateMemberSearchString() throws Exception {
        String membersUri = AssistService.getMembersUri(66446, "Daan Test");
        assertThat(membersUri).isEqualTo(
            "api/member/members/66446/1?searchTerm=Daan Test&teamId=0&functionId=0&activityId=undefined&includeDroppedOutMembersAnyway=false");
    }
}
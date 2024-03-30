package me.damascus2000.sockapplication.commands;

import me.damascus2000.sockapplication.entity.assist.AssistMember;
import me.damascus2000.sockapplication.entity.assist.MinimalAssistMember;
import me.damascus2000.sockapplication.entity.assist.Person;
import me.damascus2000.sockapplication.services.AssistService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MembersCommand extends ListenerAdapter {

    private static final long MEMBER_ROLE_ID = 934889688261611621L;
    public final AssistService assistService;
    public static final String GREEN_TICK = "✅";
    public static final String MONEY_MOUTH = ":money_mouth:";
    public static final String RED_TICK = "❌";

    public MembersCommand(AssistService assistService) {
        this.assistService = assistService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        if (event.getName().equals("members")) {
            handleAllMembersCommand(event);
        } else if (event.getName().equals("member")) {
            handleMemberCommand(event);
        }
    }

    public void handleAllMembersCommand(SlashCommandInteractionEvent event) {
        List<MinimalAssistMember> members = assistService.getMembers().getItems();
        List<Mono<ResponseEntity<AssistMember>>> monos = new ArrayList<>();
        members.forEach(minimalAssistMember -> {
            monos.add(assistService.getMonoMember(minimalAssistMember.getId()));
        });

        Flux.merge(monos).collectList().doOnSuccess(responseEntities -> {

            String memberList = createMemberListString(event, responseEntities);

            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Members");
            embedBuilder.setDescription(memberList);
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        }).subscribe();
    }

    @NotNull
    private static String createMemberListString(SlashCommandInteractionEvent event, List<ResponseEntity<AssistMember>> responseEntities) {
        StringBuilder sb = new StringBuilder();
        responseEntities.stream().sorted(Comparator.comparing(m -> m.getBody().getPerson().getName())).forEach(memberResponseEntity -> {
                AssistMember assistMember = memberResponseEntity.getBody();
                Person person = assistMember.getPerson();
                sb.append(assistMember.hasPayed() ? MONEY_MOUTH : RED_TICK).append(person.getName());
                addDiscordUserIfPresent(event, person, sb);
                sb.append("\n");
            }
        );
        return sb.toString();
    }

    private static void addDiscordUserIfPresent(SlashCommandInteractionEvent event, Person person, StringBuilder sb) {
        if (person.getDiscordId() != null && !person.getDiscordId().isEmpty()) {
            sb.append(" - ").append("<@%s>".formatted(person.getDiscordId()));
        } else if (person.getDiscordName() != null && !person.getDiscordName().isEmpty()) {
            event.getGuild().retrieveMembersByPrefix(person.getDiscordName(), 10).get();
            String name = person.getDiscordName().split("#")[0];
            List<Member> list = event.getGuild().retrieveMembersByPrefix(name, 10).get();
            sb.append(" - ").append(list.isEmpty() ? person.getDiscordName() : list.getFirst().getAsMention());
        }
    }

    public void handleMemberCommand(SlashCommandInteractionEvent event) {
        if (event.getSubcommandName().equals("claim")) {
            handleMemberClaimCommand(event);
        }
    }

    public void handleMemberClaimCommand(SlashCommandInteractionEvent event) {
        String id = event.getUser().getId();
        if (event.getOption("voornaam") == null || event.getOption("achternaam") == null) {
            searchAssistMemberAndSaveIfPossible(event.getMember().getEffectiveName(), event, "Geef een voornaam EN achternaam op");
        } else {
            searchAssistMemberAndSaveIfPossible(
                "%s %s".formatted(event.getOption("voornaam").getAsString(), event.getOption("achternaam").getAsString()),
                event,
                "Geen matchende gebruiker");
        }
    }

    private void searchAssistMemberAndSaveIfPossible(String memberName, SlashCommandInteractionEvent event, String errorMessage) {
        assistService.getMonoMembers(memberName).doOnSuccess(response -> {
            if (response.getBody().getCount() == 0) {
                sendMessage(event, errorMessage);
            } else {
                saveDiscordToUser(response.getBody().getItems().getFirst(), event,
                    event.getMember().getUser().getName());
            }
        }).subscribe();
    }

    public void saveDiscordToUser(MinimalAssistMember assistMember, SlashCommandInteractionEvent event, String
        newName) {
        assistService.getMonoPerson(assistMember.getPerson().getId()).doOnSuccess(personResponseEntity -> {
            Person person = personResponseEntity.getBody();
            if (person.getDiscordId() == null || person.getDiscordId().isEmpty()) {
                person.setDiscordName(newName);
                person.setDiscordUserId(event.getUser().getId());
                assistService.savePerson(
                    person,
                    s -> sendSuccessMessageAndModifyUserRoles(assistMember, event),
                    err -> sendMessage(event, err.getMessage()));
            } else {
                sendMessage(event, "There already is a user connected tot this account");
            }
        }).subscribe();
    }

    private static void sendMessage(SlashCommandInteractionEvent event, String message) {
        event.getHook().sendMessage(message).queue();
    }

    private static void sendSuccessMessageAndModifyUserRoles(MinimalAssistMember assistMember, SlashCommandInteractionEvent event) {
        sendMessage(event, String.format("Linked %s with %s", assistMember.getPerson().getName(), event.getMember().getAsMention()));
        if (assistMember.hasPayed()) {
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(MEMBER_ROLE_ID)).queue();
        } else {
            //TODO: Only remove role if member is x months "old"
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(MEMBER_ROLE_ID)).queue();
        }
        event.getMember().modifyNickname(assistMember.getPerson().getName()).queue();
    }
}

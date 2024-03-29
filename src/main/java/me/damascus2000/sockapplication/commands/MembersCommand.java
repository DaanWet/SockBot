package me.damascus2000.sockapplication.commands;

import me.damascus2000.sockapplication.entity.assist.AssistMember;
import me.damascus2000.sockapplication.entity.assist.MinimalAssistMember;
import me.damascus2000.sockapplication.entity.assist.Person;
import me.damascus2000.sockapplication.services.AssistService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MembersCommand extends ListenerAdapter {

    public final AssistService assistService;
    public static final String greenTick = "✅";
    public static final String redTick = "❌";

    public MembersCommand(AssistService assistService) {
        this.assistService = assistService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("members")) {
            handleAllMembersCommand(event);
        } else if (event.getName().equals("member")) {
            handleMemberCommand(event);
        }
    }

    public void handleAllMembersCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        List<MinimalAssistMember> members = assistService.getMembers().getItems();
        StringBuilder sb = new StringBuilder();
        List<Mono<ResponseEntity<AssistMember>>> monos = new ArrayList<>();
        members.forEach(minimalAssistMember -> {
            monos.add(assistService.getMonoMember(minimalAssistMember.getId()));
        });

        Flux.merge(monos).collectList().doOnSuccess(responseEntities -> {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Members");

            responseEntities.stream().sorted(Comparator.comparing(m -> m.getBody().getPerson().getName())).forEach(memberResponseEntity -> {
                    AssistMember assistMember = memberResponseEntity.getBody();
                    Person person = assistMember.getPerson();
                    sb.append(assistMember.hasPayed() ? ":money_mouth:" : "❌")
                        .append(person.getName()).append(" - ");

                    if (person.getDiscordName() != null && !person.getDiscordName().isEmpty()) {
                        event.getGuild().retrieveMembersByPrefix(person.getDiscordName(), 10).get();
                        String name = person.getDiscordName().split("#")[0];
                        List<Member> list = event.getGuild().retrieveMembersByPrefix(name, 10).get();

                        sb.append(list.isEmpty() ? person.getDiscordName() : list.getFirst().getAsMention());
                    }
                    sb.append("\n");
                }
            );

            embedBuilder.setDescription(sb);
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        }).subscribe();
    }

    public void handleMemberCommand(SlashCommandInteractionEvent event) {
        if (event.getSubcommandName().equals("claim")) {
            handleMemberClaimCommand(event);
        }
    }

    public void handleMemberClaimCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String id = event.getUser().getId();
        if (event.isFromGuild()) {
            if (event.getOption("voornaam") == null || event.getOption("achternaam") == null) {
                assistService.getMonoMembers(event.getMember().getEffectiveName()).doOnSuccess(response -> {
                    if (response.getBody().getCount() == 0) {
                        event.getHook().sendMessage("Geef een voornaam EN achternaam op").queue();
                    } else {
                        event.getHook().sendMessage("Should save in future").queue();
                    }
                }).subscribe();
            } else {
                System.out.println(event.getOption("voornaam").getAsString() + " " + event.getOption("achternaam").getAsString());
                assistService.getMonoMembers(event.getOption("voornaam").getAsString() + " " + event.getOption("achternaam").getAsString())
                    .doOnSuccess(response -> {
                        if (response.getBody().getCount() == 0) {
                            event.getHook().sendMessage("Geen matchende gebruiker").queue();
                        } else {
                            saveDiscordToUser(response.getBody().getItems().getFirst(), event,
                                event.getMember().getUser().getName());
                        }
                    }).subscribe();
            }
        }
    }

    public void saveDiscordToUser(MinimalAssistMember assistMember, SlashCommandInteractionEvent event, String newName) {
        assistService.getMonoPerson(assistMember.getPerson().getId()).doOnSuccess(person -> {
            person.getBody().setDiscordName(newName);
            assistService.savePerson(person.getBody());
            event.getHook().sendMessage("Saved").queue();
        }).subscribe();
    }
}

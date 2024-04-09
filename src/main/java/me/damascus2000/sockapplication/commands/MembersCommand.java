package me.damascus2000.sockapplication.commands;

import me.damascus2000.sockapplication.entity.assist.AssistMember;
import me.damascus2000.sockapplication.entity.assist.MinimalAssistMember;
import me.damascus2000.sockapplication.entity.assist.Person;
import me.damascus2000.sockapplication.services.AssistService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            List<AssistMember> assistMembers = responseEntities.stream().map(HttpEntity::getBody).collect(Collectors.toList());
            if (event.getSubcommandName().equals("list")) {
                String memberList = createMemberListString(event, assistMembers);

                EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Members");
                embedBuilder.setDescription(memberList);
                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            } else if (event.getSubcommandName().equals("prune")) {
                StringBuilder removed = new StringBuilder();
                Role lidRole = event.getGuild().getRoleById(MEMBER_ROLE_ID);
                List<Member> membersWithRoles = event.getGuild().getMembersWithRoles(lidRole);
                membersWithRoles.stream().sorted(Comparator.comparing(Member::getEffectiveName)).forEach(discordMember -> {
                    Optional<AssistMember> optionalAssistMember = assistMembers.stream().filter(member -> doesMatch(discordMember, member)).findFirst();
                    if (optionalAssistMember.isEmpty() || !optionalAssistMember.get().hasPayedOrIsNew()) {
                        //event.getGuild().removeRoleFromMember(discordMember, lidRole).queue();
                        removed.append(discordMember.getAsMention()).append("\n");
                    }
                });
                StringBuilder added = new StringBuilder();
                assistMembers.forEach(member -> {
                    Member discordMember = null;
                    if (member.getPerson().hasDiscordId()) {
                        discordMember = event.getGuild().getMemberById(member.getPerson().getDiscordId());
                    } else if (member.getPerson().hasDiscordName()) {
                        List<Member> membersByName = event.getGuild().getMembersByName(member.getPerson().getDiscordName(), true);
                        discordMember = membersByName.isEmpty() ? null : membersByName.getFirst();
                    }
                    if (discordMember != null && !discordMember.getRoles().contains(lidRole)) {
                        //event.getGuild().addRoleToMember(discordMember, lidRole).queue();
                        //discordMember.modifyNickname(member.getPerson().getName()).queue();
                        added.append(discordMember.getAsMention()).append("\n");
                    }
                });
                EmbedBuilder eb = new EmbedBuilder();
                eb.addField("Removed", removed.toString(), false);
                eb.addField("Added", added.toString(), false);
                event.getHook().sendMessageEmbeds(eb.build()).queue();
            }
        }).subscribe();
    }

    private boolean doesMatch(Member discordMember, AssistMember member) {
        if (member.getPerson().hasDiscordId()) {
            return member.getPerson().getDiscordId().equals(discordMember.getId());
        }
        return member.getPerson().getDiscordName() != null && member.getPerson().getDiscordName().equalsIgnoreCase(discordMember.getUser().getName());
    }

    @NotNull
    private String createMemberListString(SlashCommandInteractionEvent event, List<AssistMember> members) {
        StringBuilder sb = new StringBuilder();
        members.stream().sorted(Comparator.comparing(m -> m.getPerson().getName())).forEach(member -> {
                Person person = member.getPerson();
                sb.append(member.hasPayed() ? MONEY_MOUTH : RED_TICK).append(person.getName());
                addDiscordUserIfPresent(event, person, sb);
                sb.append("\n");
            }
        );
        return sb.toString();
    }

    private void addDiscordUserIfPresent(SlashCommandInteractionEvent event, Person person, StringBuilder sb) {
        if (person.hasDiscordId()) {
            sb.append(" - ").append("<@%s>".formatted(person.getDiscordId()));
        } else if (person.hasDiscordName()) {
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
                sendMessage(event.getHook(), errorMessage);
            } else {
                Member discordMember = event.getMember();
                if (event.getOption("user") != null) {
                    discordMember = event.getOption("user").getAsMember();
                }
                saveDiscordToUser(response.getBody().getItems().getFirst(), discordMember, event.getHook(), event.getGuild());
            }
        }).subscribe();
    }

    public void saveDiscordToUser(MinimalAssistMember assistMember, Member discordMember, InteractionHook hook, Guild guild) {
        assistService.getMonoMember(assistMember.getId()).doOnSuccess(memberResponseEntity -> {
            AssistMember member = memberResponseEntity.getBody();
            Person person = member.getPerson();
            if (!person.hasDiscordId()) {
                person.setDiscordName(discordMember.getUser().getName());
                person.setDiscordUserId(discordMember.getId());
                assistService.savePerson(
                    person,
                    s -> sendSuccessMessageAndModifyUserRoles(member, discordMember, guild, hook),
                    err -> sendMessage(hook, err.getMessage()));
            } else {
                sendMessage(hook, "There already is a user connected tot this account");
            }
        }).subscribe();
    }

    private void sendMessage(InteractionHook hook, String message) {
        hook.sendMessage(message).queue();
    }

    private void sendSuccessMessageAndModifyUserRoles(AssistMember assistMember, Member member, Guild guild, InteractionHook hook) {
        sendMessage(hook, String.format("Linked %s with %s", assistMember.getPerson().getName(), member.getAsMention()));
        if (assistMember.hasPayedOrIsNew()) {
            if (!assistMember.hasPayed()) {
                sendMessage(hook, member.getAsMention()
                    + " Je moet je lidgeld nog betalen, daar heb je nog heel even voor, in tussentijd kan je gewoon overal aan.");
            }
            guild.addRoleToMember(member, guild.getRoleById(MEMBER_ROLE_ID)).queue();
        } else {
            sendMessage(hook, "Jij bent geen lid meer van Jeugdhuis SOCK");
            guild.removeRoleFromMember(member, guild.getRoleById(MEMBER_ROLE_ID)).queue();
        }
        member.modifyNickname(assistMember.getPerson().getName()).queue();
    }
}

package me.damascus2000.sockapplication.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

public class Pronostieken extends ListenerAdapter {

    private HashMap<String, LocalDateTime> dates;
    private HashMap<String, ArrayList<Long>> pronostieken;

    public Pronostieken() {
        this.dates = new HashMap<>();
        this.pronostieken = new HashMap<>();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String userid = event.getUser().getId();
        if (event.getName().equals("pronostiek")) {
            if (event.getOption("question").getAsString().length() > 45) {
                event.reply("Question must be shorter than 45 characters").setEphemeral(true).queue();
                return;
            }
            String descr = String.format("%s %s - %s %s", event.getOption("emoji_a").getAsString(), event.getOption("country_a").getAsString(),
                event.getOption("country_b").getAsString(), event.getOption("emoji_b").getAsString());
            EmbedBuilder eb = new EmbedBuilder();
            String date = event.getOption("date").getAsString();
            eb.setTitle(String.format("WK Voetbal %s", date));
            int hours = event.getOption("hours").getAsInt();
            eb.setDescription(String.format("%s\nGeef jouw pronostiek en maak kans op een **gratis** consumptie. Dit sluit <t:%s:R>", descr,
                LocalDateTime.now().plusHours(hours).toEpochSecond(ZoneOffset.UTC)));
            eb.setImage(event.getOption("image").getAsString());
            dates.put(date, LocalDateTime.now().plusHours(hours));
            pronostieken.put(date, new ArrayList<>());
            event.replyEmbeds(eb.build()).addActionRow(Button.primary(
                String.format("%s_%s_%s", event.getOption("date").getAsString(), descr, event.getOption("question").getAsString()),
                "\uD83D\uDCDD Geef pronostiek")).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] split = event.getComponentId().split("_");
        if (dates.get(split[0]).isAfter(LocalDateTime.now())) {
            if (pronostieken.get(split[0]).contains(event.getUser().getIdLong())) {
                event.reply("Je hebt je pronostiek al gegeven").setEphemeral(true).queue();
                return;
            }
            TextInput t = TextInput.create("prono", "Wat is jouw pronostiek?", TextInputStyle.SHORT).build();
            TextInput t2 = TextInput.create("extra", split[2], TextInputStyle.SHORT).build();

            Modal m = Modal.create(event.getComponentId(), split[1]).addActionRows(ActionRow.of(t), ActionRow.of(t2)).build();
            event.replyModal(m).queue();
        } else {
            Button button = event.getButton().asDisabled();
            // edit button?
            event.reply("De tijd is verstreken").setEphemeral(true).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String[] split = event.getModalId().split("_");
        pronostieken.get(split[0]).add(event.getUser().getIdLong());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(event.getMember().getEffectiveName(), null, event.getUser().getAvatarUrl());
        eb.setTitle(split[1]);
        eb.addField("Pronostiek", event.getValue("prono").getAsString(), true);
        eb.addField(split[2], event.getValue("extra").getAsString(), true);
        event.replyEmbeds(eb.build()).queue();
    }
}

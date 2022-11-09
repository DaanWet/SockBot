package me.Damascus2000;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.Objects;

public class TestCommand extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        System.out.println(event.getCommandId());
        System.out.println(event.getCommandPath());
        System.out.println(event.getCommandString());
        System.out.println(event.getToken());
        String userid = event.getUser().getId();
        if (event.getName().equals("test")){
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("This is a test");
            eb.setDescription(event.getOption("ello").getAsString());
            event.replyEmbeds(eb.build()).addActionRow(Button.danger(userid, "Danger"), Button.primary(userid + "p", "Prim"), Button.secondary(userid + "s", "secon")).queue();
        } else if (event.getName().equals("reac")){
            Long l = event.getOption("id").getAsLong();

            //event.getMessageChannel().retrieveMessageById(l).queue(m -> m.editMessageComponents(ActionRow.of(StringSelectMenu.create(userid).addOption("one", "one").addOption("two", "two").build())).queue());
        }
    }


}

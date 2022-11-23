package me.Damascus2000;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class Main {
    public static void main(String[] args){
        JDA jda = JDABuilder.createDefault(args[0]).build();
        jda.addEventListener(new Pronostieken());
        CommandListUpdateAction commands = jda.updateCommands();


        commands.addCommands(Commands.slash("pronostiek", "this is a first test command")
                                     .addOptions(new OptionData(OptionType.STRING , "date", "Date of the match", true),
                                                 new OptionData(OptionType.STRING, "emoji_a", "Emoji for first country", true),
                                                 new OptionData(OptionType.STRING, "country_a", "First country", true),
                                                 new OptionData(OptionType.STRING, "emoji_b", "Emoji for second country", true),
                                                 new OptionData(OptionType.STRING, "country_b", "Second country", true),
                                                 new OptionData(OptionType.STRING, "image", "Image of the embed", true),
                                                 new OptionData(OptionType.STRING, "question", "Extra question", true),
                                                 new OptionData(OptionType.NUMBER, "hours", "How long to keep it open", true))
                                     .setGuildOnly(true)
                                     .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))).queue();
    }
}
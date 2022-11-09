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
        System.out.println("Hello world!");
        JDA jda = JDABuilder.createDefault(args[0]).build();
        jda.addEventListener(new TestCommand());
        jda.addEventListener(new ButtonListener());
        CommandListUpdateAction commands = jda.updateCommands();


        commands.addCommands(Commands.slash("test", "this is a first test command")
                                                 .addOptions(new OptionData(OptionType.STRING, "ello", "some description idk"))
                                                 .setGuildOnly(true)
                                                 .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))).queue();
        commands.addCommands(Commands.slash("reac", "addrow").addOptions(new OptionData(OptionType.STRING ,"id", "id")).setGuildOnly(true).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))).queue();
    }
}
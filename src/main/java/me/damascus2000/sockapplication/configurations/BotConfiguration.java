package me.damascus2000.sockapplication.configurations;

import me.damascus2000.sockapplication.services.Pronostieken;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"me.damascus2000.sockapplication"})
public class BotConfiguration {
    @Value("${token}")
    private String token;



    @Bean
    public JDA getDiscordClient(){
        System.out.println(token);
        JDA jda = JDABuilder.createDefault(token).build();
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
                                                 new OptionData(OptionType.INTEGER, "hours", "How long to keep it open", true))
                                     .setGuildOnly(true)
                                     .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))).queue();
        return jda;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

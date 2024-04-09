package me.damascus2000.sockapplication.configurations;

import me.damascus2000.sockapplication.commands.MembersCommand;
import me.damascus2000.sockapplication.commands.Pronostieken;
import me.damascus2000.sockapplication.services.AssistService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JDA getDiscordClient(@Autowired AssistService assistService) throws Exception {
        JDA jda = JDABuilder.createDefault(token)
            .setChunkingFilter(ChunkingFilter.ALL)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .build();
        jda.addEventListener(new Pronostieken());
        jda.addEventListener(new MembersCommand(assistService));
        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(Commands.slash("pronostiek", "this is a first test command")
                .addOptions(new OptionData(OptionType.STRING, "date", "Date of the match", true),
                    new OptionData(OptionType.STRING, "emoji_a", "Emoji for first country", true),
                    new OptionData(OptionType.STRING, "country_a", "First country", true),
                    new OptionData(OptionType.STRING, "emoji_b", "Emoji for second country", true),
                    new OptionData(OptionType.STRING, "country_b", "Second country", true),
                    new OptionData(OptionType.STRING, "image", "Image of the embed", true),
                    new OptionData(OptionType.STRING, "question", "Extra question", true),
                    new OptionData(OptionType.INTEGER, "hours", "How long to keep it open", true))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER)),
            Commands.slash("members", "get members from Assist")
                .addSubcommands(
                    new SubcommandData("list", "List all Assist Members"),
                    new SubcommandData("prune", "Match discord members roles with Assist members")
                )
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER)),
            Commands.slash("member", "Lidmaatschap beheren")
                .addSubcommands(
                    new SubcommandData("claim", "Link je lidmaatschap aan Discord").addOptions(
                        new OptionData(OptionType.STRING, "voornaam", "Voornaam (ingevuld in Assist)"),
                        new OptionData(OptionType.STRING, "achternaam", "Achternaam (ingevuld in Assist)"),
                        new OptionData(OptionType.USER, "user", "User")
                    ),
                    new SubcommandData("edit", "Pas je gegevens aan"))
        ).queue();

        return jda;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

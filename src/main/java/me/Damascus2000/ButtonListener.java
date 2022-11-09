package me.Damascus2000;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class ButtonListener extends ListenerAdapter {


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){

        Button b = Button.success(event.getComponentId(), "succ");
        StringSelectMenu s = StringSelectMenu.create("sa").addOption("a", "one").build();
        TextInput t = TextInput.create(event.getComponentId(), "l", TextInputStyle.SHORT).build();
        TextInput t2 = TextInput.create(event.getComponentId() + "s", "l", TextInputStyle.SHORT).build();

        System.out.println(event.getComponentId());
        Modal m = Modal.create(event.getComponentId(), event.getComponentId()).addActionRows(ActionRow.of(t), ActionRow.of(t2)).build();

        event.replyModal(m).queue();

    }

}

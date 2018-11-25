package de.soulhive.system.util.text;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@UtilityClass
public class TextComponentUtils {

    public TextComponent createClickableComponent(String message, String hover, String command) {
        TextComponent textComponent = new TextComponent();

        textComponent.setText(message);
        textComponent.setHoverEvent(
            new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(hover).create()
            )
        );
        textComponent.setClickEvent(
            new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                command
            )
        );

        return textComponent;
    }

}

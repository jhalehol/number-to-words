package com.jholguin.converter;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {

    private static final String SHELL_NAME = "converter:>";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(SHELL_NAME,
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}

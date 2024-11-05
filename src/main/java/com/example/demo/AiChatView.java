package com.example.demo;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.ai.chat.client.ChatClient;


@Route(value = "ai-chat", layout = MainLayout.class)
public class AiChatView extends VerticalLayout {

    public AiChatView(ChatClient.Builder builder) {
        var chatClient = builder.build();
        var ui = UI.getCurrent();
        var questionField = new TextField();
        var askButton = new Button("Ask");
        var answer = new TextArea();
        var inputLayout = new HorizontalLayout(questionField, askButton);

        questionField.setPlaceholder("Ask a question");

        askButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        askButton.addClickShortcut(Key.ENTER);

        askButton.addClickListener(e -> {
            chatClient.prompt()
                .user(questionField.getValue())
                .stream()
                .content()
                .subscribe(token -> {
                    ui.access(() -> answer.setValue(answer.getValue() + token));
                });
        });


        inputLayout.setWidthFull();
        inputLayout.expand(questionField);

        add(inputLayout, answer);
        expand(answer);
        answer.setWidthFull();
        setSizeFull();
    }
}

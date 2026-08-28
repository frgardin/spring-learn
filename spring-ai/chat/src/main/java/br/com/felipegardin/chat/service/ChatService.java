package br.com.felipegardin.chat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public String answerQuestion(String input) {
        return this.chatClient.prompt()
                .user(input)
                .call()
                .content();
    }
}

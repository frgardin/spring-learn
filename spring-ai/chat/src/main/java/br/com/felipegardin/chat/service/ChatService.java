package br.com.felipegardin.chat.service;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient simpleChatClient;
    private final ChatClient chatClientWithMemory;

    public String simpleChat(String input) {
        return this.simpleChatClient.prompt()
                .user(input)
                .call()
                .content();
    }

    public String chatWithMemory(String input, String conversationId) {
        if (conversationId == null || conversationId.isBlank()) {
            return simpleChat(input);
        }
        return chatClientWithMemory.prompt()
                .user(input)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

    public Flux<String> simpleChatStream(String input) {
        return simpleChatClient.prompt()
                        .user(input)
                        .stream()
                        .content();
    }

    public Flux<String> chatWithMemoryWithStream(String input, String conversationId) {
        if (conversationId == null || conversationId.isBlank()) {
            return simpleChatStream(input);
        }
        return chatClientWithMemory.prompt()
                .user(input)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .stream()
                .content();
    }
}

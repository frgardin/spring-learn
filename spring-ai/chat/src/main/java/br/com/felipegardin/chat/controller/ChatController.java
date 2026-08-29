package br.com.felipegardin.chat.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipegardin.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("simple")
    public HttpEntity<String> simpleChat(@RequestParam String input) {
        return ResponseEntity.ok(chatService.simpleChat(input));
    }

    @GetMapping("with-memory")
    public HttpEntity<String> chatWithMemory(@RequestParam String input, @RequestParam(required = false) String conversationId) {
        return ResponseEntity.ok(chatService.chatWithMemory(input, conversationId));   
    }

    @GetMapping("simple/stream")
    public HttpEntity<Flux<String>> simpleChatStream(@RequestParam String input) {
        return ResponseEntity.ok(chatService.simpleChatStream(input));
    }

    @GetMapping("with-memory/stream")
    public HttpEntity<Flux<String>> chatWithMemoryStream(@RequestParam String input, @RequestParam(required = false) String conversationId) {
        return ResponseEntity.ok(chatService.chatWithMemoryWithStream(input, conversationId));
    }
}

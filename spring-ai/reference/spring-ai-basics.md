# Spring AI Quick Reference

## Core Concepts

### ChatClient API
```java
// Basic usage
ChatClient chatClient = builder.build();
String response = chatClient.prompt("Your message").call().content();

// With options
String response = chatClient.prompt("Your message")
    .options(ChatOptionsBuilder.create().withModel("gpt-4").build())
    .call().content();
```

### Configuration Properties
```properties
# OpenAI Configuration
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-3.5-turbo
spring.ai.openai.chat.options.temperature=0.7

# Other providers
spring.ai.anthropic.api-key=${ANTHROPIC_API_KEY}
spring.ai.azure.openai.api-key=${AZURE_OPENAI_API_KEY}
```

## Common Patterns

### REST Controller
```java
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final ChatClient chatClient;
    
    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    
    @GetMapping("/simple")
    public String simpleChat(@RequestParam String message) {
        return chatClient.prompt(message).call().content();
    }
}
```

### Streaming Response
```java
@GetMapping("/stream")
public Flux<String> streamChat(@RequestParam String message) {
    return chatClient.prompt(message)
        .stream()
        .content();
}
```

## Dependencies (Maven)
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

## Dependencies (Gradle)
```groovy
implementation 'org.springframework.ai:spring-ai-openai-spring-boot-starter'
```

## Environment Variables
```bash
export OPENAI_API_KEY=your-api-key-here
```

## Testing Commands
```bash
# Start application
./mvnw spring-boot:run

# Test endpoint
curl "http://localhost:8080/api/chat/simple?message=Hello"

# With different model
curl "http://localhost:8080/api/chat/simple?message=Explain+quantum+computing"
```

## Key Annotations
- `@RestController` - Marks class as REST controller
- `@RequestMapping` - Maps HTTP requests to handler methods
- `@GetMapping` - Handles HTTP GET requests
- `@RequestParam` - Binds request parameters to method parameters
- `@Bean` - Declares a Spring bean

## Common Issues
1. **API Key Error**: Ensure `OPENAI_API_KEY` environment variable is set
2. **Model Not Found**: Check model name in `application.properties`
3. **Connection Timeout**: Verify network and API key validity
4. **Port Conflict**: Change `server.port` in `application.properties`

## Learning Progression
1. ✅ Basic chat endpoint
2. ✅ Conversation memory
3. ➡️ Streaming responses
4. ➡️ Educational content awareness
5. ➡️ Advanced prompt engineering

## Chat Memory

### Basic Memory Setup
```java
ChatMemory chatMemory = MessageWindowChatMemory.builder()
    .maxMessages(10)  // Keep last 10 messages
    .build();
```

### Using Memory with ChatClient
```java
ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

ChatClient chatClient = ChatClient.builder(chatModel)
    .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
    .build();

// With specific conversation ID
String response = chatClient.prompt()
    .user("My name is Alex")
    .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "user123"))
    .call()
    .content();
```

### Memory Configuration Properties
```properties
# Memory settings (if using custom configuration)
spring.ai.chat.memory.max-messages=20
spring.ai.chat.memory.conversation-id=user123
```

### Key Concepts
- **Conversation ID**: Identifies different conversations (use unique IDs per user/session)
- **Message Window**: Sliding window of messages kept in memory
- **Advisors**: Manage memory automatically for each request
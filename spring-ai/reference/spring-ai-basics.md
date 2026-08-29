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
3. ✅ Streaming responses
4. ✅ Tool calling for educational tools
5. ➡️ Advanced prompt engineering

## Streaming Responses

### Basic Streaming
```java
Flux<String> stream = chatClient.prompt("Tell me a joke")
    .stream()
    .content();
```

### Streaming with Memory
```java
Flux<String> stream = chatClient.prompt()
    .user("My name is Alex")
    .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "user123"))
    .stream()
    .content();
```

### Controller Setup
```java
@GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
@ResponseBody
public Flux<String> streamChat(@RequestParam String input) {
    return chatService.simpleChatStream(input);
}
```

### Key Points
- Use `.stream().content()` instead of `.call().content()`
- Set `MediaType.TEXT_EVENT_STREAM_VALUE` for streaming
- Return `Flux<String>` from controller methods
- Use `@ResponseBody` for reactive types

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

## Tool Calling

### Basic Tool Definition
```java
public class EducationalTools {
    
    @Tool(description = "Calculate mathematical expressions")
    public String calculate(@ToolParam(description = "Mathematical expression") String expression) {
        // Implementation
        return "Result: " + expression;
    }
}
```

### Using Tools with ChatClient
```java
EducationalTools tools = new EducationalTools();

String response = chatClient.prompt("What is 123 * 456?")
    .tools(tools)
    .call()
    .content();
```

### Tool Calling Flow
1. Define tools with @Tool annotation
2. Pass tools to ChatClient via .tools()
3. AI model decides when to call tools
4. Spring AI executes tools and returns results
5. Model continues conversation with tool results

### Key Concepts
- **@Tool annotation**: Marks methods as callable tools
- **@ToolParam annotation**: Describes tool parameters for the AI
- **ToolCallingAdvisor**: Auto-registered advisor that handles tool loop
- **Tool descriptions**: Help AI know when to use each tool
# Learning Notes

## User Preferences
- Advanced Spring Boot experience
- Focus on educational chatbot development
- No specific timeline, learning at own pace
- Prefers practical, hands-on learning

## Learning Style
- Prefers code examples and working projects
- Likes to understand underlying concepts
- Wants to build something useful while learning

## Session Notes
- Started with basic Spring AI setup
- Created educational chatbot project structure
- Focused on Verboo AI integration (custom base URL)
- Added conversation memory for context retention
- Learned about MessageWindowChatMemory and conversation IDs
- Fixed memory issue: MessageChatMemoryAdvisor must be added to ChatClient builder
- Implemented streaming responses for better UX
- Learned about Flux<String> and text/event-stream content type
- Added tool calling for educational tools (@Tool annotation)
- Learned about tool calling loop and ToolCallingAdvisor
#!/bin/bash

# Script to open the second lesson
echo "Opening Spring AI Learning Lesson 2: Conversation Memory..."
echo "If the browser doesn't open automatically, please open this file manually:"
echo "  $(pwd)/lessons/0002-conversation-memory.html"

# Try to open in default browser
if command -v xdg-open &> /dev/null; then
    xdg-open lessons/0002-conversation-memory.html
elif command -v open &> /dev/null; then
    open lessons/0002-conversation-memory.html
elif command -v start &> /dev/null; then
    start lessons/0002-conversation-memory.html
else
    echo "Could not automatically open browser. Please open the HTML file manually."
fi
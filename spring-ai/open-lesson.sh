#!/bin/bash

# Script to open the first lesson
echo "Opening Spring AI Learning Lesson 1..."
echo "If the browser doesn't open automatically, please open this file manually:"
echo "  $(pwd)/lessons/0001-setup-first-chatbot.html"

# Try to open in default browser
if command -v xdg-open &> /dev/null; then
    xdg-open lessons/0001-setup-first-chatbot.html
elif command -v open &> /dev/null; then
    open lessons/0001-setup-first-chatbot.html
elif command -v start &> /dev/null; then
    start lessons/0001-setup-first-chatbot.html
else
    echo "Could not automatically open browser. Please open the HTML file manually."
fi
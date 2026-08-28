#!/bin/bash

# Script to open the third lesson
echo "Opening Spring AI Learning Lesson 3: Streaming Responses..."
echo "If the browser doesn't open automatically, please open this file manually:"
echo "  $(pwd)/lessons/0003-streaming-responses.html"

# Try to open in default browser
if command -v xdg-open &> /dev/null; then
    xdg-open lessons/0003-streaming-responses.html
elif command -v open &> /dev/null; then
    open lessons/0003-streaming-responses.html
elif command -v start &> /dev/null; then
    start lessons/0003-streaming-responses.html
else
    echo "Could not automatically open browser. Please open the HTML file manually."
fi
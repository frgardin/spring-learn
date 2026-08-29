#!/bin/bash

# Script to open lesson 4
echo "Opening Spring AI Learning Lesson 4..."
echo "If the browser doesn't open automatically, please open this file manually:"
echo "  $(pwd)/lessons/0004-tool-calling.html"

# Try to open in default browser
if command -v xdg-open &> /dev/null; then
    xdg-open lessons/0004-tool-calling.html
elif command -v open &> /dev/null; then
    open lessons/0004-tool-calling.html
elif command -v start &> /dev/null; then
    start lessons/0004-tool-calling.html
else
    echo "Could not automatically open browser. Please open the HTML file manually."
fi
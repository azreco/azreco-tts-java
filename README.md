# AzReco Text To Speech API Java example
Example project in Java to help you integrate with our text-to-speech API.

This is an example Java project for uploading text file and saving the audio into a .wav file.

# Supporting languages
AZERBAIJANI (az-AZ)

TURKISH  (tr-TR)

# Requirements

You will need to have the Java compiler and Maven tool installed in your operation system.
Go into the project folder, execute "mvn compile" and "mvn install" commands in terminal. The last command creates executable jar in the target folder.

# Usage example:

java -jar azreco-tts-java-1.0.jar --input-type file -t text/example-tr.txt -l tr-TR -i api_user_id -k api_token -o example-tr.wav 

or

java -jar azreco-tts-java-1.0.jar --input-type text -t "any text" -l tr-TR -i api_user_id -k api_token -o example-tr.wav 

In this example for input type 'file' the application uploads 'example-tr.txt', synthesizes speech using our tr-TR text-to-speech engine and saves the resulting audio as 'example-tr.wav' when the synthesizing process finished. For input type 'text' the application sends text to the server, synthesizes speech using our tr-TR text-to-speech engine and saves the resulting audio as 'example-tr.wav' when the synthesizing process finished.


# How to get user id and token?

To get user id and API token, send a request to info@azreco.az.

To confirm your request, we will ask you some details about your purpose in using API.

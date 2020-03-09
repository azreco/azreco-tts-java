# AzReco Text To Speech API Java example
Sample project in Java to help you integrate with our text-to-speech API.

This is a sample Java project for uploading text file or text and saving the audio into a .wav file.

# Supported languages
Azerbaijani (az-AZ)

Turkish  (tr-TR)

Russian  (ru-Ru)

# Requirements

You will need to have the Java compiler and Maven tool installed in your operation system.
Go into the project folder, execute "mvn compile" and "mvn install" commands in terminal. The last command creates executable jar in the target folder.

# Usage example:

```sh
java -jar azreco-tts-java-1.0.jar --input-type file -t text/example-tr.txt -l tr-TR -i api_user_id -k api_token --tts-id tts_id -o example-tr.wav
```

or

```sh
java -jar azreco-tts-java-1.0.jar --input-type text -t "any text" -l tr-TR -i api_user_id -k api_token --tts-id tts_id -o example-tr.wav
```

The above command with input type 'file' the script uploads 'example-tr.txt', synthesizes speech using our tr-TR text-to-speech and saves the resulting audio as 'example-tr.wav' when the synthesizing process finished. The above command with input type 'text' the script sends text to the server, synthesizes speech using our tr-TR text-to-speech and saves the resulting audio as 'example-tr.wav' when the synthesizing process finished. 

# What is --tts-id option?

We have several voices for any language. Every voice has own identification. You can specify TTS identification with this option to get your audio file in different voices.
This option is optional and TTS service selects default voice for the given language.

# How to get voice identifications?

We added new REST get method [http://api.azreco.az/voices?api_id=YOUR_API_ID&api_token=YOUR_API_TOKEN](http://api.azreco.az/voices?api_id=YOUR_API_ID&api_token=YOUR_API_TOKEN). 
You can call this method in any browser. We also added this into the [Synthesizer](https://github.com/azreco/azreco-tts-java/blob/master/src/main/java/com/azreo/tts/api/Synthesizer.java) class as a method. The result is JSON array of voice informations. For example:
```json
[   
   {
      "id":TTS_ID,
      "ttsName":"VOICE_NAME",
      "ttsLanguage":"SHORT_LANGUAGE",
      "ttsGender":"GENDER"
   }
]
```

# How to get user id and token?

To get user id and API token, send a request to info@azreco.az.

To confirm your request, we will ask you some details about your purpose in using API.

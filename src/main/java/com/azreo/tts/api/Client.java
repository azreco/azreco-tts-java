package com.azreo.tts.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author AzReco
 */
public class Client 
{
    public static void main( String[] args ) throws IOException
    {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addRequiredOption("", "input-type", true, "Type of input. Must be one of 'text' or 'file'.");
        options.addRequiredOption("t", "text", true, "Text or text file to be processed");
        options.addOption("o", "output", true, "Output wave filename");
        options.addRequiredOption("i", "id", true, "Your text-to-speech API ID");
        options.addRequiredOption("k", "token", true, "Your text-to-speech API Token");
        options.addRequiredOption("l", "lang", true, "Code of language to use (e.g., az-AZ, tr-TR)");
        options.addOption("", "tts-id", true, "Identification of voice for given language. To see identification of voices call getVoices() method of Synthesizer class.");
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        }
        catch(ParseException ex) {
            System.err.println("Parsing commandline arguments failed: " + ex.getMessage());
            return;
        }
        Synthesizer synthesizer = new Synthesizer(cmd.getOptionValue("i"), 
                cmd.getOptionValue("k"), cmd.getOptionValue("l"));
//        System.err.println(synthesizer.getVoices());
        byte[] result = null;
        if(cmd.getOptionValue("input-type").equals("file")) {
            result = synthesizer.synthesize(cmd.getOptionValue("t"), cmd.getOptionValue("tts-id"));
        } else {
            result = synthesizer.synthesizeText(cmd.getOptionValue("t"), cmd.getOptionValue("tts-id"));
        }
        if(result == null) {
            System.err.println("Text-to-speech process failed.");
            System.err.println("Press any key to exit...");
            System.in.read();
            return;
        }
        OutputStream writer = new FileOutputStream(new File(cmd.getOptionValue("o")));
        writer.write(result);
        writer.close();
        System.err.println("Press any key to exit...");
        System.in.read();
    }
}

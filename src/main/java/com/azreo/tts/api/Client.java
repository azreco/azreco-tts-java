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
 * Hello world!
 *
 */
public class Client 
{
    public static void main( String[] args ) throws IOException
    {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addRequiredOption("t", "text", true, "Text file to be processed");
        options.addOption("o", "output", true, "Output wave filename");
        options.addRequiredOption("i", "id", true, "Your text-to-speech API ID");
        options.addRequiredOption("k", "token", true, "Your text-to-speech API Token");
        options.addRequiredOption("l", "lang", true, "Code of language to use (e.g., az-AZ, tr-TR)");
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
        byte[] result = synthesizer.synthesize(cmd.getOptionValue("t"));
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
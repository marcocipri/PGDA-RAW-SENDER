package com.bwinparty.sogei.pgda.utils;


import org.apache.commons.cli.*;

public class CliCmdParser
{

    private String url="";
    private String input="";
    private String output="";
    private String messageType="";
    private String operator = "";
    private String fsc = "";



    public String getUrl() {
        return url;
    }

    public String getOutput() {
        return output;
    }

    public String getMessageType() {
        return messageType;
    }



    public String getInput() {
        return input;
    }

    public String getOperator() {
        return operator;
    }

    public String getFsc() {
        return fsc;
    }


    public CliCmdParser(String[] args)
    {
        /**
         * Defaults
         */


        /**
         * CLI Options
         */
        Option help = new Option("h", "prints this message");
        Option urlOpt = OptionBuilder.isRequired()
                .hasArg()
                .withDescription("url where the PGDA service is exposed")
                .create("u");
        Option inputOpt = OptionBuilder.isRequired()
                .hasArg()
                .withDescription("CSV input file")
                .create("i");
        Option outputOpt = OptionBuilder.isRequired()
                .hasArg()
                .withDescription("CSV output file")
                .create("o");
        Option messageTypeOpt = OptionBuilder.isRequired()
                .hasArg()
                .withDescription("message type, admissible value : 830")
                .create("m");
        Option operatorOpt = OptionBuilder.isRequired()
                .hasArg()
                .withDescription("operator, admissible value : 15026 15028 15093")
                .create("p");
        Option fscOpt = OptionBuilder.isRequired()
                .hasArg()
                .withDescription("fsc, admissible value : 26 37")
                .create("f");


        Options options = new Options();
        options.addOption(help);
        options.addOption(urlOpt);
        options.addOption(inputOpt);
        options.addOption(outputOpt);
        options.addOption(messageTypeOpt);
        options.addOption(operatorOpt);
        options.addOption(fscOpt);


        /**
         * CLI Parser
         */
        CommandLineParser parser = new GnuParser();
        try
        {

            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h"))
            {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("PGDA helper", options);
            } else
            {


                if (line.hasOption("u"))
                {
                    url = line.getOptionValue("u");
                }

                if (line.hasOption("i"))
                {
                    input  = line.getOptionValue("i");
                }

                if (line.hasOption("o"))
                {
                    output = line.getOptionValue("o");
                }

                if (line.hasOption("m"))
                {
                    messageType = line.getOptionValue("m");
                }


                if (line.hasOption("p")) {
                    operator = line.getOptionValue("p");
                }

                if (line.hasOption("f")) {
                    fsc = line.getOptionValue("f");
                }

            }
        } catch (ParseException exp)
        {
            // oops, something went wrong
            System.err.println("Parsing failed. Reason: " + exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("PGDA helper", options);
        }

    }
}

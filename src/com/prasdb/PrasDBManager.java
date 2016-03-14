package com.prasdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * PrasDBManager
 */
public class PrasDBManager {

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        String input, output;
        processOutput("Started PrasDB");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while((input=br.readLine())!=null) {
                try {
                    output = processCommand(input);
                    processOutput(output);
                } catch (Exception e) {
                    processOutput("INVALID INPUT");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * process the command
     * @param input
     * @return
     */
    private static String processCommand(String input) {
        String[] inputs = input.split(" ");
        String commandString = inputs[0].toUpperCase();
        String keyString = inputs.length > 1 ? inputs[1] : null;
        String valueString = inputs.length > 2 ? inputs[2] : null;
        Commands command = Commands.valueOf(commandString);
        if (command == null) {
            throw new IllegalArgumentException("Not a valid Command");
        }
        Optional<String> commandOutput= command.execute(Optional.ofNullable(keyString), Optional.ofNullable(valueString));
        return commandOutput.isPresent() ? commandOutput.get(): null;
    }

    /**
     * process output
     * @param output
     */
    private static void processOutput(String output) {
        if(output != null){
            System.out.println(output);
        }
    }
}

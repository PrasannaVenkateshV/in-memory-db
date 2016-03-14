package com.prasdb;

import java.util.Optional;

/**
 * Enum of valid commands that PrasDB can accept.
 */
public enum Commands {
    //(command name, command type, Number of parameters, Documentation)
    BEGIN ("BEGIN", "COMMON", 0,"Open a new transaction block. Transaction blocks can be nested; a BEGIN can be issued inside of an existing block.") {
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            System.out.println("Starting Transaction ...");
            return Optional.empty();
        }
    },
    END("END", "COMMON", 0, "Exit the program. Your program will always receive this as its last command.") {
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            System.exit(0);
            return Optional.empty();
        }
    },
    COMMIT("COMMIT", "TRANSACTION", 0, "Close all open transaction blocks, permanently applying the changes made in them. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.") {
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            System.out.println("Committed Transaction");
            return Optional.empty();
        }
    },
    ROLLBACK("ROLLBACK", "TRANSACTION", 0, "Undo all of the commands issued in the most recent transaction block, and close the block. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.") {
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            System.out.println("Rolled Back Transaction");
            return Optional.empty();
        }
    },
    GET("GET", "DATA", 1, "Print out the value of the variable name, or NULL if that variable is not set.") {
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            return Optional.of(PrasDB.get(getOptionalData(key)));
        }
    },
    SET("SET", "DATA", 2, "Set the variable name to the value value. Neither variable names nor values will contain spaces.") {
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            PrasDB.set(getOptionalData(key), getOptionalData(value));
            return Optional.empty();
        }
    },
    UNSET("UNSET", "DATA", 1, "Unset the variable name, making it just like that variable was never set."){
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            PrasDB.unset(getOptionalData(key));
            return Optional.empty();
        }
    },
    NUMEQUALTO("NUMEQUALTO", "DATA", 1, "Print out the number of variables that are currently set to value. If no variables equal that value, print 0."){
        public Optional<String> execute(Optional<String> key, Optional<String> value) {
            return Optional.of(String.valueOf(PrasDB.numOccurancesOfValue(getOptionalData(key))));
        }
    };

    private final String commandName;
    private final String commandType;
    private final int numberOfArguments;
    private final String documentation;

    Commands(String commandName, String commandType, int numberOfArguments, String documentation) {
        this.commandName = commandName;
        this.commandType = commandType;
        this.numberOfArguments = numberOfArguments;
        this.documentation = documentation;
       // this.command = command;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandType() {
        return commandType;
    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }

    public String getDocumentation() {
        return documentation;
    }

    public abstract Optional<String> execute(Optional<String> key, Optional<String> Value);

    private static String getOptionalData(Optional<String> data){
        return data.isPresent() ? data.get().toString(): "";
    }

}

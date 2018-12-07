package com.hepsiburada.ecommerce;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        try {
            CommandExecutor commandExecutor = new CommandExecutor();
            Scanner scanner = new Scanner(new File(args[0]));

            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                parseCommand(commandExecutor, command);
            }
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println("file not found or invalid parameters");
            printUsage();
        }

        Scanner consoleScanner = new Scanner(System.in);
        System.out.println("PRESS ENTER TO EXIT");
        consoleScanner.nextLine();
    }

    private static void printUsage() {
        System.out.println("USAGE: java -jar ecommerce-1.0-SNAPSHOT-shaded.jar \"<command file path>\"");
        System.out.println("EXAMPLE: java -jar ecommerce-1.0-SNAPSHOT-shaded.jar \"/usr/gulteking/SCN1\"");
    }

    private static void parseCommand(CommandExecutor commandExecutor, String command) {
        System.out.println("---------------------------");
        System.out.println(command);
        String[] commandArgs = command.split(" ");
        executeCommand(commandExecutor, commandArgs);

    }

    private static void executeCommand(CommandExecutor commandExecutor, String[] commandArgs) {
        try {
            if (commandArgs[0].equals("create_product")) {
                commandExecutor.createProduct(commandArgs);
            } else if (commandArgs[0].equals("get_product_info")) {
                commandExecutor.getProductInfo(commandArgs);
            } else if (commandArgs[0].equals("create_order")) {
                commandExecutor.createOrder(commandArgs);
            } else if (commandArgs[0].equals("create_campaign")) {
                commandExecutor.createCampaign(commandArgs);
            } else if (commandArgs[0].equals("get_campaign_info")) {
                commandExecutor.getCampaignInfo(commandArgs);
            } else if (commandArgs[0].equals("increase_time")) {
                commandExecutor.increaseTime(commandArgs);
            } else {
                System.out.println("Unknown Command");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
}

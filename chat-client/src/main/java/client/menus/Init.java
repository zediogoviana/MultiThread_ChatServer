package client.menus;

import java.io.*;

public class Init{


    private void printCabecalho(){
        String t1 = "    ___ _  _   _ _____   ___ ___ _____   _____ ___ \n"
                  + "   / __| || | /_\\_   _| / __| __| _ \\ \\ / / __| _ \\\n"
                  + "  | (__| __ |/ _ \\| |   \\__ \\ _||   /\\ V /| _||   /\n"
                  + "   \\___|_||_/_/ \\_\\_|   |___/___|_|_\\ \\_/ |___|_|_\\";



        String desc =      "|             SD - Universidade do Minho              |\n"
                         + "|                a77312 José Viana                    |\n"
                         + "|                  2018 - 2019                        |\n";

        System.out.println("+-----------------------------------------------------+\n"
                            + desc
                         + "+-----------------------------------------------------+\n"
                         + t1
                         +  "\n\n");





    }

    public void printCannot() throws IOException{

        boolean flag = false;
        String s;
        BufferedReader sin = new BufferedReader( new InputStreamReader(System.in));

        System.out.print("\033[H\033[2J");  //CLEAR;
    
        printCabecalho();
        
        System.out.println("                 .--.                                 \n"
                         + "                |o_o |   COULDN'T REACH               \n"
                         + "                |:_/ |       THE WEB                  \n"
                         + "               //   \\ \\                             \n"
                         + "              (|     | )                              \n"
                         + "             /'\\_   _/`\\                            \n"
                         + "             \\___)=(___/                             \n"
                         + "                                                      \n"
                         + "+-----------------------------------------------------+\n"
                         + "|       RELOAD: 'R'       |         EXIT: 'Q'         |\n"
                         + "+-----------------------------------------------------+\n"
                         + "Command>");
            
        while(!flag && (s = sin.readLine()) != null) {

            if(s.equalsIgnoreCase("Q"))
                System.exit(1);

            if(s.equalsIgnoreCase("R"))
                flag = true;
        }

    }


    public String printInit() throws IOException{

        boolean flag = false;
        String s = null;
        BufferedReader sin = new BufferedReader( new InputStreamReader(System.in));
    

        System.out.print("\033[H\033[2J");  //CLEAR;

        printCabecalho();

        System.out.println("                 .--.                                \n"   
                         + "                |o_o |   WELCOME MATE                \n"
                         + "                |:_/ |                               \n"
                         + "               //   \\ \\                            \n"
                         + "              (|     | )                             \n"
                         + "             /'\\_   _/`\\                           \n"
                         + "             \\___)=(___/                            \n"
                         + "                                                     \n"
                         + "+-----------------------------------------------------+\n"
                         + "|    LOGIN: 'L'   |   REGISTER: 'R'   |   EXIT: 'Q'   |\n"
                         + "+-----------------------------------------------------+\n"
                         + "Command> ");


        while(!flag && (s = sin.readLine()) != null) {
            if(s.equalsIgnoreCase("L") || s.equalsIgnoreCase("R") || s.equalsIgnoreCase("Q"))
                flag = true; 
        }

        return s;
    }

    public String printInitError() throws IOException{

        boolean flag = false;
        String s = null;
        BufferedReader sin = new BufferedReader( new InputStreamReader(System.in));
    

        System.out.print("\033[H\033[2J");  //CLEAR;

        printCabecalho();

        System.out.println("                 .--.                                  \n"
                         + "                |o_o |   SEEMS SOMETHING               \n"
                         + "                |:_/ |      WENT WRONG :(              \n"
                         + "               //   \\ \\                              \n"
                         + "              (|     | )                               \n"
                         + "             /'\\_   _/`\\                             \n"
                         + "             \\___)=(___/                              \n"
                         + "                                                       \n"
                         + "+-----------------------------------------------------+\n"
                         + "|    LOGIN: 'L'   |   REGISTER: 'R'   |   EXIT: 'Q'   |\n"
                         + "+-----------------------------------------------------+\n"
                         + "Command> ");


        while(!flag && (s = sin.readLine()) != null) {
            if(s.equalsIgnoreCase("L") || s.equalsIgnoreCase("R") || s.equalsIgnoreCase("Q"))
                flag = true; 
        }

        return s;
    }

    public String printUsedUsername() throws IOException{

        boolean flag = false;
        String s = null;
        BufferedReader sin = new BufferedReader( new InputStreamReader(System.in));
    

        System.out.print("\033[H\033[2J");  //CLEAR;

        printCabecalho();

        System.out.println("                 .--.                                  \n"
                         + "                |o_o |   THIS USERNAME IS              \n"
                         + "                |:_/ |      ALREADY USED               \n"
                         + "               //   \\ \\                              \n"
                         + "              (|     | )                               \n"
                         + "             /'\\_   _/`\\                             \n"
                         + "             \\___)=(___/                              \n"
                         + "                                                       \n"
                         + "+-----------------------------------------------------+\n"
                         + "|    LOGIN: 'L'   |   REGISTER: 'R'   |   EXIT: 'Q'   |\n"
                         + "+-----------------------------------------------------+\n"
                         + "Command> ");


        while(!flag && (s = sin.readLine()) != null) {
            if(s.equalsIgnoreCase("L") || s.equalsIgnoreCase("R") || s.equalsIgnoreCase("Q"))
                flag = true; 
        }

        return s;
    }

    public String printLoginUsername() throws IOException{

        String user;
        BufferedReader sin = new BufferedReader( new InputStreamReader(System.in));

        System.out.println("Username> ");
        while( (user = sin.readLine()).equals(""));

        return (user);
    }

    public String printLoginPassword() throws IOException{

        String pass;
        BufferedReader sin = new BufferedReader( new InputStreamReader(System.in));

        System.out.println("Password> ");
        while( (pass = sin.readLine()).equals(""));

        return (pass);
    }


}
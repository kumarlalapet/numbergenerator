package com.lotterysyndicate;


import java.io.*;
import java.util.*;

/**
 * Created by mlalapet on 1/18/16.
 */
public class TicketGenerator {

    Set<Integer> tickets;
    Set<Integer> powerBall;

    Set<Integer> generatedTicketPicks;
    Set<Integer> generatedPowerPicks;

    public TicketGenerator(){
        tickets = new LinkedHashSet<Integer>();
        powerBall = new LinkedHashSet<Integer>();
        generatedTicketPicks = new LinkedHashSet<Integer>();
        generatedPowerPicks = new LinkedHashSet<Integer>();
    }

    public void addTicket(int[] ticket) {
        for(int i : ticket) {
            tickets.add(i);
        }
    }

    public void addPowerBall(int powerball) {
        this.powerBall.add(powerball);
    }

    public void parseAndAddTicket(String ticket) throws Exception {
        int[] ticketnumbers = new int[5];
        int powerball = -1;

        int tokenCount = 0;

        StringTokenizer tokens = new StringTokenizer(ticket,",");
        while(tokens.hasMoreTokens()) {
            String number = tokens.nextToken();
            try {
                if(tokenCount < 5) {
                    ticketnumbers[tokenCount] = Integer.parseInt(number);
                } else {
                    powerball = Integer.parseInt(number);
                }
                tokenCount = tokenCount + 1;
            } catch(NumberFormatException e) {
                throw new Exception("Ticket not valid "+ticket);
            }
        }

        if(tokenCount != 6)
            throw new Exception("Ticket not valid "+ticket);
        if(powerball == -1)
            throw new Exception("Ticket not valid "+ticket);

        this.addTicket(ticketnumbers);
        this.addPowerBall(powerball);
    }

    public void generatePicks() {
        for(int i : tickets) {
            generatedTicketPicks.add(i);
            generatedTicketPicks.add(i + 1);
            generatedTicketPicks.add(i - 1);
        }
    }

    public void generatePowerPicks() {
        for(int i : powerBall) {
            generatedPowerPicks.add(i);
            generatedPowerPicks.add(i + 1);
            generatedPowerPicks.add(i - 1);
        }
    }

    public static void main(String args[]) throws Exception {
        //test input is as follows
        //21,45,51,55,62,16
        //10,18,37,54,60,20
        //22,37,44,48,59,24
        //40,45,50,63,64,06
        //03,24,48,49,50,07
        //02,24,31,38,50,13

        //21,45,51,55,62,16 10,18,37,54,60,20 22,37,44,48,59,24 40,45,50,63,64,06 03,24,48,49,50,07 02,24,31,38,50,13

        if(args.length == 0)
            System.exit(0);

        TicketGenerator ticketGen = new TicketGenerator();

        for(String arg : args) {
            validateTickets(arg);
            ticketGen.parseAndAddTicket(arg);
        }

        ticketGen.generatePicks();
        ticketGen.generatePowerPicks();

        Permutation pm = new Permutation();

        int arr[] = new int[ticketGen.generatedTicketPicks.size()];
        Integer intArr[] = ticketGen.generatedTicketPicks.toArray(new Integer[ticketGen.generatedTicketPicks.size()]);
        for(int i = 0; i < intArr.length; i++) {
            arr[i] = intArr[i];
        }

        int r = 5;
        int n = arr.length;
        pm.printCombination(arr, n, r);
        pm.weedOutDuplicates();

        Set<String> finalTicketsWithRPowerBall = new LinkedHashSet<String>();
        for(String s : pm.finalTickets) {
            for(int i : ticketGen.generatedPowerPicks) {
                finalTicketsWithRPowerBall.add(s+" "+i);
            }
        }

        Set<String> finalTicketsWithPowerBall = new LinkedHashSet<String>();
        for(String s : pm.finalTickets) {
            for(int i : ticketGen.powerBall) {
                finalTicketsWithPowerBall.add(s+" "+i);
            }
        }

        writeTicketNumber("no-powerball.txt",pm.finalTickets);
        writeTicketNumber("with-generated-powerball.txt",finalTicketsWithRPowerBall);
        writeTicketNumber("with-asis-powerball.txt",finalTicketsWithPowerBall);

        Map<Integer, String> noPB = new HashMap<Integer, String>();
        for(String s : pm.finalTickets) {
            StringTokenizer tokens = new StringTokenizer(s);
            int total = 0;
            while(tokens.hasMoreTokens()) {
                total = total + Integer.parseInt(tokens.nextToken());
            }
            noPB.put(total,s);
        }

        String winningNumber = "3 51 52 61 64 6";
        String winningNumberNoPB = "3 51 52 61 64";

        System.out.println(pm.finalTickets.size());//+" "+noPB.size()+" winning ticket "+noPB.get(winningNumberNoPBtotal));
        System.out.println(finalTicketsWithRPowerBall.size());
        System.out.println(finalTicketsWithPowerBall.size());

        /**
        System.out.println(args.length);
        System.out.println("Original : "+ticketGen.tickets.size());
        System.out.println("Original : "+ticketGen.powerBall.size());

        System.out.println("Original : "+ticketGen.tickets);
        System.out.println("Original : "+ticketGen.powerBall);

        System.out.println(ticketGen.generatedTicketPicks.size());
        System.out.println(ticketGen.generatedPowerPicks.size());

        System.out.println(ticketGen.generatedTicketPicks);
        System.out.println(ticketGen.generatedPowerPicks);
         **/
    }

    private static void validateTickets(String ticket) throws Exception {
        StringTokenizer tokens = new StringTokenizer(ticket,",");
        int tokenCount = 0;
        while(tokens.hasMoreTokens()) {
            tokenCount = tokenCount + 1;
            String number = tokens.nextToken();
            try {
                Integer.parseInt(number);
            } catch(NumberFormatException e) {
                tokenCount = tokenCount - 1;
            }
        }
        if(tokenCount != 6)
            throw new Exception("Number not valid "+ticket);
    }

    private static void writeTicketNumber(String fileName, Set<String> ticketsToBuy) throws IOException {
        File fout = new File(fileName);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (String s : ticketsToBuy) {
            bw.write(s);
            bw.newLine();
        }

        bw.close();
    }
}

package com.utm;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SubscriberLauncher {

    private static InputStreamReader inputStreamReader;
    private static OutputStreamWriter outputStreamWriter;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

    public static void main(String[] args) {
        System.out.println("A receiver has started");
        sendSubscriptionRequest();
    }

    private static void sendSubscriptionRequest() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input the subject you want to subscribe to");
            String subject = scanner.nextLine();
            Socket brokerSocket = new Socket("localhost", 8088);
            inputStreamReader = new InputStreamReader(brokerSocket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(brokerSocket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write("{\"subscriptionRequest\":" + subject + "}");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            while (true) {
                String brokerMessage = bufferedReader.readLine();
                System.out.println(brokerMessage);
            }
        } catch (IOException unknownHostException) {
            System.out.println(unknownHostException.getMessage());
        }
    }
}

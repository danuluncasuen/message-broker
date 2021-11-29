package com.utm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        System.out.println("A publisher has started");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input the topic: ");
            String topic = scanner.nextLine().toLowerCase();
            scanner = new Scanner(System.in);
            System.out.println("Input the message: ");
            String message = scanner.nextLine();

            sendNews(topic, message);
        }
    }

    private static void sendNews(String topic, String message) {
        try {
            Socket brokerSocket = new Socket("localhost", 8088);

            JSONObject json = new JSONObject();
            json.put("topic", topic);
            json.put("message", message);

            try (OutputStreamWriter out = new OutputStreamWriter(
                    brokerSocket.getOutputStream(), StandardCharsets.UTF_8)) {
                out.write(json.toString());
            }
            brokerSocket.close();
        } catch (IOException e) {
            System.out.println("The broker is not available");
        } catch (JSONException je) {
            System.out.println("The json you are trying to send is not valid");
        } catch (Exception e) {
            System.out.println("Could not send the message because of an exception " +  e.getMessage());
        }
    }

}

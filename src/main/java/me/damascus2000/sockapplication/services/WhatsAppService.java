package me.damascus2000.sockapplication.services;

import it.auties.whatsapp.api.PairingCodeHandler;
import it.auties.whatsapp.api.QrHandler;
import it.auties.whatsapp.api.WebOptionsBuilder;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.model.companion.CompanionDevice;
import it.auties.whatsapp.model.mobile.PhoneNumber;
import it.auties.whatsapp.model.mobile.VerificationCodeMethod;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Logger;

public class WhatsAppService {



    public WhatsAppService() {

        /*WebOptionsBuilder whatsapp = Whatsapp.webBuilder().lastConnection();
        System.out.println(whatsapp.toString());
        Whatsapp whats = whatsapp.unregistered(32492889929L, (a) -> {
                System.out.println(a);
            Logger.getLogger(this.getClass().toString()).info(a);
            });
        System.out.println(whats.isConnected());
        Whatsapp connected = whats.addLoggedInListener(api -> System.out.printf("Connected: %s%n", api.store().privacySettings())).connect().join();

        System.out.println(whats.isConnected());
        System.out.println(connected.isConnected());*/

        Whatsapp api = Whatsapp.webBuilder() // Use the Web api
            .lastConnection() // Deserialize the last connection, or create a new one if it doesn't exist
            .unregistered(32492889929L, PairingCodeHandler.toTerminal()) // Print the pairing code to the terminal
            .addLoggedInListener(a -> System.out.printf("Connected: %s%n", a.store().privacySettings())) // Print a message when connected
            .addDisconnectedListener(reason -> System.out.printf("Disconnected: %s%n", reason)) // Print a message when disconnected
            .addNewChatMessageListener(message -> System.out.printf("New message: %s%n", message.toJson())) // Print a message when a new chat message arrives
            .connect() // Connect to Whatsapp asynchronously
            .join();

        Void join = api.reconnect().join();
        System.out.println(join);
/*
        Whatsapp.mobileBuilder() // Use the Mobile api
            .lastConnection() // Deserialize the last connection, or create a new one if it doesn't exist
            .device(CompanionDevice.android(false)) // Use a non-business iOS account
            .unregistered() // If the connection was just created, it needs to be registered
            .verificationCodeMethod(VerificationCodeMethod.SMS) // If the connection was just created, send an SMS OTP
            .verificationCodeSupplier(() -> { // Called when the OTP needs to be sent to Whatsapp
                System.out.println("Enter OTP: ");
                var scanner = new Scanner(System.in);
                return scanner.nextLine();
            })
            .register(32492889929L) // Register the phone number asynchronously, if necessary
            .join() // Await the result
            .whatsapp() // Get the resulting whatsapp instance
            .addLoggedInListener(a -> System.out.printf("Connected: %s%n", a.store().privacySettings())) // Print a message when connected
            .addDisconnectedListener(reason -> System.out.printf("Disconnected: %s%n", reason)) // Print a message when disconnected
            .addNewChatMessageListener(message -> System.out.printf("New message: %s%n", message.toJson())) // Print a message when a new chat message arrives
            .connect() // Connect to Whatsapp asynchronously
            .join(); // Await the result*/


    }


}

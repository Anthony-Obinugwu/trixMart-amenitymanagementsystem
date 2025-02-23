package com.education.amenity.management;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {

    private final WhatsappBotService whatsappBotService;

    // Constructor to inject the WhatsappBotService
    public WhatsappController(WhatsappBotService whatsappBotService) {
        this.whatsappBotService = whatsappBotService;
    }

    @PostMapping("/incoming")
    public String handleIncomingMessage(
            @RequestParam("From") String from,
            @RequestParam("Body") String body
    ) {
        System.out.println("Received message from " + from + ": " + body);

        // Process the incoming message by passing it to the bot service
        whatsappBotService.handleIncomingMessage(from, body);

        // After the bot processes the message, return an appropriate response
        return "Message received";
    }
}

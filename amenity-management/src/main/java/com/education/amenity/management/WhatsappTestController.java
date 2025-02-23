//package com.education.amenity.management;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/whatsapp")
//public class WhatsappTestController {
//    private final WhatsappService whatsAppService;
//
//    @Autowired
//    public WhatsappTestController(WhatsappService whatsAppService) {
//        this.whatsAppService = whatsAppService;
//    }
//
//    @GetMapping("/send-test-message")
//    public String sendTestMessage() {
//        String toPhoneNumber = "+2347017349468"; // The phone number you're using for testing
//        String messageBody = "Hello from the Trix Mart!";
//        whatsAppService.sendMessage(toPhoneNumber, messageBody);
//        return "Test message sent!";
//    }
//}

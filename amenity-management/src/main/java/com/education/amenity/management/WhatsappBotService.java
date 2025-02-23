package com.education.amenity.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappBotService {
    private final WhatsappService whatsAppService;
    private final StudentRepository studentRepository;

    private Map<String, Student> userState = new HashMap<>();  // Store the entire Student object for each user

    @Autowired
    public WhatsappBotService(WhatsappService whatsAppService, StudentRepository studentRepository) {
        this.whatsAppService = whatsAppService;
        this.studentRepository = studentRepository;
    }

    public void handleIncomingMessage(String studentPhone, String message) {
        // Get the current state of the user, default to "START"
        String state = userState.containsKey(studentPhone) ? "NAME" : "START";

        switch (state) {
            case "START":
                // Greet the user and ask for their name
                sendNextQuestion(studentPhone);
                break;

            case "NAME":
                handleNameResponse(studentPhone, message);
                break;

            case "STUDENT_ID":
                handleStudentIdResponse(studentPhone, message);
                break;

            case "BUSINESS_NAME":
                handleBusinessNameResponse(studentPhone, message);
                break;

            case "PRODUCT_TYPE":
                handleProductTypeResponse(studentPhone, message);
                break;

            case "ID_PICTURE":
                handleIdPictureResponse(studentPhone, message);
                break;

            case "SUBSCRIPTION":
                handleSubscriptionResponse(studentPhone, message);
                break;

            default:
                whatsAppService.sendMessage(studentPhone, "Invalid input, please start again.");
                break;
        }
    }

    private void sendNextQuestion(String studentPhone) {
        // Initial greeting and instructions
        String instructions = "Welcome to the WhatsApp Bot! Here's what we'll do:\n" +
                "1. I'll ask for your name.\n" +
                "2. Then, I'll ask for your student ID and other details.\n" +
                "3. Please follow the steps carefully to complete the process.";

        // Send instructions to the user
        whatsAppService.sendMessage(studentPhone, instructions);

        // Ask for the user's name
        whatsAppService.sendMessage(studentPhone, "What is your name?");

        // Initialize a new Student object for this user and store it in memory
        userState.put(studentPhone, new Student());
    }


    private void handleNameResponse(String studentPhone, String message) {
        if (message.trim().isEmpty()) {
            whatsAppService.sendMessage(studentPhone, "Please provide a valid name.");
            return;
        }

        // Retrieve the student object stored in userState for this phone
        Student student = userState.get(studentPhone);
        student.setStudentName(message);  // Set the student's name

        // Save the updated student object in the map
        userState.put(studentPhone, student);

        // Update the state to ask for student ID next
        whatsAppService.sendMessage(studentPhone, "Great! What is your student ID?");
    }

    private void handleStudentIdResponse(String studentPhone, String message) {
        if (!message.matches("^[a-zA-Z0-9]+$")) {
            whatsAppService.sendMessage(studentPhone, "Invalid student ID. Please enter a valid one.");
            return;
        }

        // Retrieve the student object stored in userState for this phone
        Student student = userState.get(studentPhone);
        student.setStudentId(message);  // Set the student's ID

        // Save the updated student object in the map
        userState.put(studentPhone, student);

        // Move to the next step (asking for business name)
        whatsAppService.sendMessage(studentPhone, "What is the name of your business?");
    }

    private void handleBusinessNameResponse(String studentPhone, String message) {
        // Retrieve the student object stored in userState for this phone
        Student student = userState.get(studentPhone);
        student.setBusinessName(message);  // Set the student's business name

        // Save the updated student object in the map
        userState.put(studentPhone, student);

        // Move to the next step (asking for product type)
        whatsAppService.sendMessage(studentPhone, "What type of product do you sell?");
    }

    private void handleProductTypeResponse(String studentPhone, String message) {
        if (message.trim().isEmpty() || !isValidProductType(message)) {
            whatsAppService.sendMessage(studentPhone, "Invalid product type. Please choose from food, clothes, jewelry, etc.");
            return;
        }

        // Retrieve the student object stored in userState for this phone
        Student student = userState.get(studentPhone);
        student.setBusinessType(message);  // Set the student's product type

        // Save the updated student object in the map
        userState.put(studentPhone, student);

        // Move to the next step (asking for ID picture)
        whatsAppService.sendMessage(studentPhone, "Please send a picture of your ID.");
    }

    private boolean isValidProductType(String message) {
        List<String> validTypes = Arrays.asList("food", "clothes", "jewelry", "accessories");
        return validTypes.contains(message.toLowerCase());
    }

    private void handleIdPictureResponse(String studentPhone, String message) {
        whatsAppService.sendMessage(studentPhone, "Thank you for providing your ID. We have received it.");

        // Proceed to subscription step
        whatsAppService.sendMessage(studentPhone, "What type of subscription have you paid for? (e.g., PREMIUM, NORMAL)");
    }

    private void handleSubscriptionResponse(String studentPhone, String message) {
        if (!message.equalsIgnoreCase("PREMIUM") && !message.equalsIgnoreCase("NORMAL")) {
            whatsAppService.sendMessage(studentPhone, "Invalid subscription type. Please enter either PREMIUM or NORMAL.");
            return;
        }

        // Retrieve the student object stored in userState for this phone
        Student student = userState.get(studentPhone);
        student.setSubscriptionType(message);  // Set the student's subscription type

        // Save the updated student object in the map
        userState.put(studentPhone, student);

        // Final message when data is stored
        studentRepository.save(student);  // Save the student to the database
        whatsAppService.sendMessage(studentPhone, "Thank you! Your data has been successfully stored.");
    }
}

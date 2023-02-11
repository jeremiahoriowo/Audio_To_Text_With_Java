/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.goxr3plus.sphinx5mavenexample.application;

/**
 *
 * @author JERRY
 */
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import edu.cmu.sphinx.api.Configuration;
import java.io.FileWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main2 {
    public static void main(String[] args) throws Exception {
        // Configuration
        Configuration configuration = new Configuration();
        // Load model from the jar
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		
        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("C:\\Users\\JERRY\\Downloads\\1.wav"));
        // Create a blocking queue to hold the transcribed text
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        // Start the recognition process in a separate thread
        new Thread(() -> {
            try {
                recognizer.startRecognition(stream);
                SpeechResult result;

                // Get the transcribed text and put it in the queue
                while ((result = recognizer.getResult()) != null) {
                    queue.put(result.getHypothesis());
                }

                // Stop recognition
                recognizer.stopRecognition();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Get the transcribed text from the queue
        String transcription = queue.take();

        // Handle the transcription
        try (FileWriter writer = new FileWriter("C:\\Users\\JERRY\\Downloads\\transcript.txt")) {
            writer.write(transcription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
//        recognizer.startRecognition(stream);
//        SpeechResult result;
            
            
//        while (result != null) {
//               result = recognizer.getResult();
////            System.out.format("Hypothesis: %s\n", result.getHypothesis());
//        }
//        recognizer.stopRecognition();
//        String transcript = recognizer.getResult().getHypothesis();

//        
    }


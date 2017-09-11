package com.example.androidthings.myproject;
import com.google.android.things.pio.Gpio;

/**
 * Created by noahpitts on 9/9/2017.
 */

public class AsciiKeyApp extends SimplePicoPro {

    // Mapping of buttons to GPIO pins
    Gpio buttonSet = GPIO_174;
    Gpio buttonClear = GPIO_175;
//    Gpio[] buttonInput = new Gpio[]{GPIO_10, GPIO_128, GPIO_32, GPIO_33, GPIO_34, GPIO_35, GPIO_37, GPIO_39};
    Gpio[] buttonInput = new Gpio[]{GPIO_39, GPIO_32, GPIO_35, GPIO_34, GPIO_33, GPIO_37, GPIO_128, GPIO_10};


    @Override
    public void setup() {
        // Set pins as input and set edge triggers
        pinMode(buttonSet,Gpio.DIRECTION_IN);
        setEdgeTrigger(buttonSet,Gpio.EDGE_BOTH);

        pinMode(buttonClear,Gpio.DIRECTION_IN);
        setEdgeTrigger(buttonClear,Gpio.EDGE_BOTH);

        for (Gpio button : buttonInput) {
            pinMode(button,Gpio.DIRECTION_IN);
            setEdgeTrigger(button,Gpio.EDGE_BOTH);
        }
    }

    @Override
    public void loop() {
        // Not used in this project
    }

    @Override
    void digitalEdgeEvent(Gpio pin, boolean value) {

        // Runs when the Set Button is released
        if(pin==buttonSet && value==HIGH) {
            println("BUTTON SET <------->");

            // State of each input button
            boolean[] buttonState = new boolean[8];

            // Read the state of all input buttons
            for (int i = 0; i < 8; i++) {
                buttonState[i] = !digitalRead(buttonInput[i]);
            }

            char inputChar = (char)((buttonState[0]?1<<7:0) + (buttonState[1]?1<<6:0)
                          + (buttonState[2]?1<<5:0) + (buttonState[3]?1<<4:0)
                          + (buttonState[4]?1<<3:0) + (buttonState[5]?1<<2:0)
                          + (buttonState[6]?1<<1:0) + (buttonState[7]?1:0));

            printCharacterToScreen(inputChar);

            // print
            println("STATE: " + buttonState[0] + " " + buttonState[1] + " "
                    + buttonState[2] + " " + buttonState[3] + " "
                    + buttonState[4] + " " + buttonState[5] + " "
                    + buttonState[6] + " " + buttonState[7]);
            println("CHARACTER: " + inputChar);
            println("ASCII CODE: " + (int)inputChar);
        }


        // Runs when the Clear Button is released
        else if (pin==buttonClear && value==HIGH) {
            // Clear all text on screen
            clearStringOnScreen();
        }
    }
}

package ru.lukutin.color;

import java.io.Serializable;

public class ColorService implements Serializable {

    private static final long serialVersionUID = 1L;

    String createHelloMessage(String name) {
        return "Hello " + name + "!";
    }

}

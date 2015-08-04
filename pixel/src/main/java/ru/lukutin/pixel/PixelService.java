package ru.lukutin.pixel;

import java.io.Serializable;

/**
 * Created by Sergey on 5/25/2015.
 */
public class PixelService implements Serializable {

    private static final long serialVersionUID = 1L;

    String createHelloMessage(String name) {
        return "Hello " + name + "!";
    }

}

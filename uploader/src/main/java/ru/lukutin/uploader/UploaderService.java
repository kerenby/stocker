package ru.lukutin.uploader;

import java.io.Serializable;

/**
 * Created by Sergey on 7/10/2015.
 */
public class UploaderService  implements Serializable {

    private static final long serialVersionUID = 1L;

    String createHelloMessage(String name) {
        return "Bye " + name + "!";
    }
}

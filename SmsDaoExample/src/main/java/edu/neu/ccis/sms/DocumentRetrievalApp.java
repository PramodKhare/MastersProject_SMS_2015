package edu.neu.ccis.sms;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DocumentRetrievalApp {
    public static void main(String[] args) throws IOException {
        FileUtils.deleteDirectory(new File("F:\\uploads"));
    }
}

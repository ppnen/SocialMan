package com.company;
import java.time.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

import com.company.SQLiteJDBC;

public class Contact {
    long last_call; // Seconds since epoch, UTC
    String name;

    Contact(String name, long startCall) {
        // Create a new contact
        this.name = name;
        this.last_call = startCall;
    }

    public String last_call_fmt() {
        // return as string the last time our contact was call, return string in localtime.
        LocalDateTime d = LocalDateTime.ofEpochSecond(this.last_call, 0, ZoneOffset.UTC);
        return d.format(DateTimeFormatter.ofPattern("yyyyMMdd Hms"));
    }

    public String getName() {
        return this.name;
    }

    public long getLastCall() {
        return this.last_call;
    }

    public static void main(String[] args) {
        /* Object creation */
        SocMan.test();


    }
}





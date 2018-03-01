package com.company;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.sql.*;
import com.company.SQLiteJDBC;

public class Contact {
    int last_call;
    String name;

    Contact(String startName, int startCall) {
        // This constructor has one parameter, name.
        this.name = startName;
        this.last_call = startCall;
    }

    public String last_call_fmt() {
        LocalDateTime d = LocalDateTime.now();
        System.out.println(d);
        return null;
    }

    public String getnName() {
        System.out.println("Puppy's age is :" + name);
        return null;
    }
    /*public static Clock systemDefaultZone(){
        return 0;
    }*/

/*
    public class SosMan {
        public String juttu() {
            Connection c = null;
            Statement stmt = null;

            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:test.db");
                String sql = "CREATE TABLE CONTACT " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " LAST_CALL            INT     NOT NULL, ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Opened database successfully");
            return null;
        }


        public void add_concact() {

        }
    }
*/
    public static void main(String[] args) {
        /* Object creation */
        Contact myPuppy = new Contact("jaakko", 2);
        Contact uusipuppy = new Contact("helvetti", 2);
        /* Call class method to set puppy's age */
        myPuppy.getnName();
        myPuppy.last_call_fmt();
        SQLiteJDBC sq1= new SQLiteJDBC();
        sq1.add();
        sq1.add();
        System.out.println(Clock.systemUTC());


    }
    }



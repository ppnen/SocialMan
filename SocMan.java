package com.company;
import java.io.File;
import java.sql.*;

import com.company.Contact;
import java.time.LocalDateTime;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class SocMan {
    // Social Contact Manager
    Connection dbc;

    SocMan(String dbname)  // Create db or open a connection to existing database
    {
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            dbc = DriverManager.getConnection("jdbc:sqlite:"+dbname);
            dbc.setAutoCommit(true);
            System.out.println("Opened database successfully");

            stmt = dbc.createStatement();
            String sql = "create table if not exists Contact (name text primary key, last_call real)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public void add_contact(Contact contact){
        Statement stmt = null;

        String sql = "insert into Contact(name, last_call values(?, ?)";
        try (Connection conn = this.dbc;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setDouble(2, contact.getLastCall());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Contact added successfully");
    }

    public Contact find_contact(String name){
        String sql = "select name, last_call from Contact where name = ?";

        try (Connection conn = this.dbc;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs    = pstmt.executeQuery(sql);{


                // loop through the result set
                if (rs.next()) {
                    System.out.println(rs.getString("name") + "\t" +
                            rs.getLong("last_call"));
                    return new Contact(rs.getString("name"), rs.getLong("last_call"));
                }
            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public void contact_called(Contact contact, long called_at_time) {

            String sql = "update Contact set last_call=? where name = ?";

            try (Connection conn = this.dbc;

                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setDouble(1, called_at_time);
                pstmt.setString(2, contact.getName());


                // update
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        public ArrayList<Contact> get_contacts(int n){


                //'Get all contacts, order by first to call. If n > 0, return only first n'

                String sql = "select name, last_call from Contact order by last_call, name";
                ArrayList<Contact> contacts = new ArrayList<Contact>();
                int i=0;
                try (Connection conn = this.dbc;
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    ResultSet rs    = pstmt.executeQuery(sql);{
                        while (rs.next()) {
                            contacts.add(new Contact(rs.getString("name"),rs.getLong("last_call")));
                            i++;
                            if (n!=0 && i >=n){
                                break;
                            }
                            rs.next();
                                }
                            }

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
            return contacts;
        }

        public Contact get_first_contact(){
        //return the contact we should call, the one we have not called for longest time
            Contact tocall = this.get_contacts(1).get(0);
            // List of one contact
            return tocall;
    }
        public static void test(){
            Date date = new Date(System.currentTimeMillis());
            String dbname= "test-contacts.db";
            File f = new File("dbname");
                            if(f.exists()) {
                                f.delete();
                            }
                            SocMan cdb= new SocMan(dbname);
                            List<String> names = new ArrayList<String>();
                            names.add("Putkonen,Sini");
                            names.add("Lempola,Pekka");
                            names.add("Brown,Jackson");
                            names.add("Smith,Roger");
                            names.add("Hill,Sam");

                            for (String name : names){
                                Contact ok=new  Contact (name,0);
                                cdb.add_contact(ok);
                            }

                            System.out.println("*** DB created");

                            for (Contact c : cdb.get_contacts(4)){
                                System.out.println(c.getName()+ c.last_call_fmt());
                            }

                            Contact contact = cdb.find_contact("Smith,Roger");
                            if (contact != null){
                                cdb.contact_called(contact,contact.getLastCall());
                            }
                            contact = cdb.find_contact("Putkonen,Sini");
                            cdb.contact_called(contact, date.getTime()/1000-3600); // called one hour ago;
                            cdb.contact_called(cdb.find_contact("Hill,Sam"), date.getTime()/1000-7200); // # called two hours ago
                            cdb.contact_called(cdb.find_contact("Brown,Jackson"), date.getTime()/1000-1800); // # called 30 minutes ago

                            assert (cdb.get_first_contact().name == "Lempola,Pekka"); //  # Only we have not called must be the first

                            System.out.println("*** After some calls, next call order");
                            for (Contact c : cdb.get_contacts(1)){
                                System.out.println(c.getName()+ c.last_call_fmt());
                            }
                        }

}








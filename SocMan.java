package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import Contact;
import java.time.DateTime;
import java.nio.file.*;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Contact added successfully");
    }
    
   public Contact find_contact(String name){
        String sql = "select name, last_call from Contact where name = ?";
        
        try (Connection conn = this.dbc;
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
             ResultSet rs    = stmt.executeQuery(sql)){
             
            
            // loop through the result set
            if (rs.next()) {
                System.out.println(rs.getString("name") + "\t" +
                                   rs.getLong("last_call"));
                return new Contact(rs.getString("name"), rs.getLong("last_call");
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

   public void contact_called(Contact contact, long called_at_time){
   
   String sql = "update Contact set last_call=? where name = ?";
 
        try (Connection conn = this.connect();
            if (not called_at_time){
                called_at_time= Date.getTime()/1000;
                }
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setDouble(1, called_at_time);
            pstmt.setString(2, contact.getName());
            
            
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    public Contact get_first_contact(){
    //return the contact we should call, the one we have not called for longest time
        Contact tocall= SocMan.get_first_contact(n=1); // List of one contact
        return tocall[0];
    }
    
    public ArrayList<Contact> get_contacts(int n=0){
        //'Get all contacts, order by first to call. If n > 0, return only first n'
        
        String sql = "select name, last_call from Contact order by last_call, name";
        
        try (List<Contact> contacts = new ArrayList<Contact>();
            int i=0;
            Connection conn = this.dbc;
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
             ResultSet rs    = stmt.executeQuery(sql)){
             while (rs.next()) {
                contacts.add(new Contact(rs.getString("name"),rs.getLong("last_call");
                i++;
                if (n and i >=n){
                    break;
                    }
                rs.next();
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
        return contacts;    
    
    public void test(){
        dbname= "test-contacts.db"
        File f = new File("dbname");
        if(f.exists()) { 
            f.delete();
        }
        SocMan cdb= new SocMan(dbname)
        List<String> names = new ArrayList<String>()
            names.add("Putkonen,Sini"); 
            names.add("Lempola,Pekka");
            names.add("Brown,Jackson");
            names.add("Smith,Roger");
            names.add("Hill,Sam");
        for (name in names){
            cdb.add_contact(contact.getName());
            }
            
        System.out.println("*** DB created");
        for (c in cdb.get_contacts()){
        System.out.println(c.name, c.last_call_fmt());
        }
        
        contact = cdb.find_contact('Smith,Roger');
        if (contact){
            cdb.contact_called(contact);
            }
    contact = cdb.find_contact('Putkonen,Sini');
    cdb.contact_called(contact, Date.getTime()/1000-3600); // called one hour ago;
    cdb.contact_called(cdb.find_contact('Hill,Sam'), Date.getTime()/1000-7200); // # called two hours ago
    cdb.contact_called(cdb.find_contact('Brown,Jackson'), Date.getTime()/1000-1800); // # called 30 minutes ago

    assert (cdb.get_first_contact().name == 'Lempola,Pekka'); //  # Only we have not called must be the first

    System.out.println("*** After some calls, next call order");
    for (c in cdb.get_contacts()){
        System.out.println(c.name, c.last_call_fmt())
        }
    }
}
    
    
    
    
    
    
    
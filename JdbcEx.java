import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
public class JdbcEx extends Applet implements ActionListener {

    Label lblName;
    TextField txtName;
    Button btnInsert, btnDelete, btnShow, btnUpdate;
    List list;      

    Connection con;
    ResultSet rs ;

    String myname;
    int id = 0;

    public void init() {
        
        setLayout(new BorderLayout());

        Panel p1 = new Panel();

        lblName=new Label("Name:");
        txtName=new TextField(20);
        btnInsert=new Button("Insert");
        btnDelete=new Button("Delete");
        btnShow=new Button("Show");
        btnUpdate = new Button("Update");

        p1.add(lblName);
        p1.add(txtName);
        p1.add(btnInsert);        
        p1.add(btnUpdate);
        p1.add(btnDelete);
        p1.add(btnShow);
        add(p1, BorderLayout.NORTH);

        Panel p2 = new Panel();
        
        list = new java.awt.List(5);
        list.setSize(200, 300);
        list.setVisible(false);
        p2.add(list); 
        
        add(p2, BorderLayout.CENTER);

        
        btnInsert.addActionListener(this);
        btnDelete.addActionListener(this);
        btnShow.addActionListener(this);
        btnUpdate.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) { 

        try {
          Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
          con = DriverManager.getConnection("jdbc:odbc:mydb");
        } catch (Exception ex) {
          ex.printStackTrace();
        }


        String cmd = ae.getActionCommand();
        myname = txtName.getText().toString();

        if(cmd.equals("Insert")){ 
            Insert();            
        }
        else if(cmd.equals("Delete")){
            Delete();
        }
        else if(cmd.equals("Show")){        
            Show();
        }
        else if(cmd.equals("Update")){        
            Update();
        }
       
        
    }

    public void paint(Graphics g) {
        repaint();
    }

    public void Insert(){
        if (myname!=null && !myname.isEmpty()) {
                try{          
                    Statement smt = con.createStatement();  
                    smt.executeUpdate("insert into student(name) values('"+ myname +"')");   
                    showStatus("Inserted");     
                    Show();
                } 
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else showStatus("Cannot leave blank");
    }

    public void Delete(){
        id = getId(list.getSelectedIndex());
        
        try{          
            Statement smt = con.createStatement();  
            
            smt.executeUpdate("delete from student where id="+ id);   
            showStatus("Deleted");  
            Show();   
        } 
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public  void Show(){
        try{          
                Statement smt = con.createStatement();  
                rs = smt.executeQuery("select * from student");  
                                
                int n_records=0;
                list.removeAll();
                list.setVisible(true);
                while(rs.next()){
                    list.add(rs.getString(2));  
                    n_records++;                  
                }
                showStatus(""+n_records+" Records found"); 
            } 
            catch(Exception ex){
                ex.printStackTrace();
            }
    }

    public void Update(){
        id = getId(list.getSelectedIndex());
        
        try{          
            Statement smt = con.createStatement();  
            
            smt.executeUpdate("update student set name='"+txtName.getText()+"' where id="+ id);   
            showStatus("Deleted");  
            Show();   
        } 
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public  int getId(int index){
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        try{          
                Statement smt = con.createStatement();  
                rs = smt.executeQuery("select ID from student");  
                
                int c = 0;               
                while(rs.next()){
                    IDs.add(rs.getInt(1));
                    c++;                 
                } 
            } 
            catch(Exception ex){
                ex.printStackTrace();
            }
            return IDs.get(index);
    }

}

/*

<html>
<applet code="JdbcEx.class" width=800 height=600>
    <param name='cache_option' value='no'>
</applet>

</html>

*/
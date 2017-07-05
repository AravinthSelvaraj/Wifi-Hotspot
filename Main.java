import java.io.*;
import java.awt.event.*;
import javax.swing.*;
public class Main  {
    JFrame f;
    JLabel l1,l2,l3,l4;
    JTextField name,pass;
    JButton b1;
    Boolean status;
    Main()throws Exception{
        status=false;
        f=new JFrame("My Wifi Hotspot");
        f.setLayout(null);
        l1=new JLabel("My Wifi Hotspot");
        l1.setFont(l1.getFont().deriveFont(20f));
        l1.setBounds(115,10,200,60);
        l2=new JLabel("Hotspot Name");
        l2.setFont(l2.getFont().deriveFont(14f));
        l2.setBounds(65,70,100,30);
        l3=new JLabel("Password");
        l3.setFont(l2.getFont().deriveFont(14f));
        l3.setBounds(65,100,100,30);
        l4=new JLabel("Status : OFF");
        l4.setFont(l2.getFont().deriveFont(14f));
        l4.setBounds(150,200,100,30);
        name=new JTextField();
        name.setBounds(200, 75, 120, 20);
        pass=new JTextField();
        pass.setBounds(200, 105, 120, 20);
        b1=new JButton("Start Hotspot");
        b1.setBounds(135,150,110,27);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    if(status==false){
                        runProcess("netsh wlan set hostednetwork mode=allow ssid="+name.getText()+" key="+pass.getText());
                        runProcess("netsh wlan start hosted network");
                        b1.setText("Stop Hotspot");
                        l4.setText("Status : ON");
                        status=true;
                        name.disable();
                        pass.disable();
                    }
                    else{
                        runProcess("netsh wlan stop hosted network");
                        b1.setText("Start Hotspot");
                        l4.setText("Status : OFF");
                        name.enable();
                        pass.enable();
                        status=false;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        f.add(l1);
        f.add(l2);
        f.add(l3);
        f.add(l4);
        f.add(name);
        f.add(pass);
        f.add(b1);
        f.setSize(400, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
    }

    private static void runProcess(String command) throws Exception {
    Process pro = Runtime.getRuntime().exec(command);
    printLines(command + "\n" + "stdout:", pro.getInputStream());
    //printLines(command + "\n" + "stderr:", pro.getErrorStream());
    pro.waitFor();
  }

  private static void printLines(String name, InputStream ins) throws Exception {
    String line = null;
    System.out.print(name);
    BufferedReader in = new BufferedReader(
        new InputStreamReader(ins));
    while ((line = in.readLine()) != null) {
        System.out.println(line);
    }
  }

    public static void main(String[] args) throws Exception{
        new Main();
    }

}

package application;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import java.io.*;
public class Client  implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client(){
        // Panel One
        f.setLayout(null);
        JPanel panel_one = new JPanel();
        panel_one.setBackground(new Color(7,94,84));
        panel_one.setBounds(0,0,450,70);
        panel_one.setLayout(null);
        f.add(panel_one);

        // Back ICON
        ImageIcon item_one = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image item_two = item_one.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon item_three = new ImageIcon(item_two);
        JLabel back = new JLabel(item_three);
        back.setBounds(5,20,25,25);
        panel_one.add(back);

        //MouseClick Event

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {

                System.exit(0);
            }
        });

        // Profile Photo

        ImageIcon item_four = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image item_five = item_four.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon item_six = new ImageIcon(item_five);
        JLabel profile = new JLabel(item_six);
        profile.setBounds(40,10,50,50);
        panel_one.add(profile);

        /// Video icon

        ImageIcon item_seven = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image item_eight = item_seven.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon item_nine = new ImageIcon(item_eight);
        JLabel video = new JLabel(item_nine);
        video.setBounds(310,20,30,30);
        panel_one.add(video);


        // phone icon

        ImageIcon item_ten = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image item_eleven = item_ten.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon item_twelve = new ImageIcon(item_eleven);
        JLabel phone = new JLabel(item_twelve);
        phone.setBounds(360,20,35,30);
        panel_one.add(phone);


        // Menu Bar

        ImageIcon item_13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image item_14= item_13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon item_15 = new ImageIcon(item_14);
        JLabel menu = new JLabel(item_15);
        menu.setBounds(420,20,10,25);
        panel_one.add(menu);

        // Name

        JLabel name = new JLabel("Monkey D. Luffy");
        name.setBounds(110,15,180,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SANS_SERIF", Font.BOLD, 16));
        panel_one.add(name);

        //Status

        JLabel status = new JLabel("Active now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SANS_SERIF", Font.BOLD, 13));
        panel_one.add(status);

        // Bottom panel

        a1 = new JPanel();
        f.setUndecorated(true);
        a1.setBounds(5,75, 440, 570);
        status.setFont(new Font("SANS_SERIF", Font.BOLD, 14));
        f.add(a1);

        // Text panel

        text = new JTextField();
        text.setBounds(5, 650, 310, 40);
        text.setFont(new Font("SANS_SERIF", Font.BOLD, 14));
        f.add(text);

        // Send Button

        JButton send = new JButton("Send");
        send.setBounds(320, 650, 123, 40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.DARK_GRAY);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
        //Send Action
        send.addActionListener(this);
        f.add(send);


        // Main Layout

        f.setSize(450,700);
        f.setVisible(true);
        f.getContentPane().setBackground(Color.white);
        f.setLocation(100,50);
    }

    public void actionPerformed(ActionEvent ae){

        try {
            String out = text.getText();
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html> <p style = \"width : 150px\" >" + out + "</p></html>" );
        output.setFont(new Font("Tohoma",Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel("12:00");
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;

    }

    public static void main(String[] args) {

        new Client();
        try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while (true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

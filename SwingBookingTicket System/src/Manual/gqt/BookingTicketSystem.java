package Manual.gqt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// ---------------- LOGIN PAGE ----------------
class LoginPage extends JFrame implements ActionListener {

    JTextField userField;
    JButton loginBtn;

    public LoginPage() {

        setTitle("Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 20, 60));

        JLabel title = new JLabel("BUS BOOKING SYSTEM");
        title.setBounds(500, 120, 700, 50);
        title.setFont(new Font("Times New Roman", Font.BOLD, 34));
        title.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Enter Username:");
        userLabel.setBounds(600, 250, 200, 30);
        userLabel.setForeground(Color.WHITE);

        userField = new JTextField();
        userField.setBounds(750, 250, 200, 30);

        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(700, 320, 150, 40);

        add(title);
        add(userLabel);
        add(userField);
        add(loginBtn);

        loginBtn.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (!userField.getText().trim().isEmpty()) {

            new BookingPage(userField.getText());
            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Enter Username!");
        }
    }
}

// ---------------- BOOKING PAGE ----------------
class BookingPage extends JFrame implements ActionListener {

    JComboBox<String> sourceBox, destBox, busBox;

    JButton searchBtn, nextBtn;

    JSpinner passengerCount;
    JSpinner dateSpinner;

    ArrayList<String> buses = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<Integer> prices = new ArrayList<>();

    String username;

    public BookingPage(String username) {

        this.username = username;

        setTitle("Booking");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        getContentPane().setBackground(
                new Color(240, 245, 255));

        JLabel title = new JLabel(
                "WELCOME " + username.toUpperCase());

        title.setBounds(450, 30, 700, 50);

        title.setFont(
                new Font("Times New Roman",
                        Font.BOLD, 32));

        title.setForeground(Color.BLUE);

        String cities[] = {
                "Bangalore",
                "Mysore",
                "Chennai",
                "Hyderabad",
                "Mumbai",
                "Delhi",
                "Pune",
                "Kochi"
        };

        JLabel srcLabel = new JLabel("Source");
        srcLabel.setBounds(550, 90, 100, 25);

        sourceBox = new JComboBox<>(cities);
        sourceBox.setBounds(550, 120, 200, 30);

        JLabel dstLabel = new JLabel("Destination");
        dstLabel.setBounds(780, 90, 100, 25);

        destBox = new JComboBox<>(cities);
        destBox.setBounds(780, 120, 200, 30);

        JLabel passLabel = new JLabel("Passengers:");
        passLabel.setBounds(550, 170, 120, 30);

        passengerCount = new JSpinner(
                new SpinnerNumberModel(1, 1, 6, 1));

        passengerCount.setBounds(670, 170, 80, 30);

        JLabel dateLabel = new JLabel("Journey Date:");
        dateLabel.setBounds(550, 220, 120, 30);

        dateSpinner = new JSpinner(
                new SpinnerDateModel());

        dateSpinner.setBounds(670, 220, 200, 30);

        JSpinner.DateEditor editor =
                new JSpinner.DateEditor(
                        dateSpinner,
                        "dd-MM-yyyy");

        dateSpinner.setEditor(editor);

        searchBtn = new JButton("SEARCH BUSES");
        searchBtn.setBounds(1000, 120, 180, 40);

        busBox = new JComboBox<>();
        busBox.setBounds(400, 300, 800, 35);

        nextBtn = new JButton("CONFIRM TICKET");
        nextBtn.setBounds(650, 380, 220, 45);

        add(title);
        add(srcLabel);
        add(sourceBox);
        add(dstLabel);
        add(destBox);
        add(passLabel);
        add(passengerCount);
        add(dateLabel);
        add(dateSpinner);
        add(searchBtn);
        add(busBox);
        add(nextBtn);

        searchBtn.addActionListener(this);
        nextBtn.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        // SEARCH BUSES
        if (e.getSource() == searchBtn) {

            busBox.removeAllItems();

            buses.clear();
            times.clear();
            prices.clear();

            String src =
                    (String) sourceBox.getSelectedItem();

            String dst =
                    (String) destBox.getSelectedItem();

            if (src.equals(dst)) {

                JOptionPane.showMessageDialog(this,
                        "Source and Destination cannot be same!");

                return;
            }

            // Sample buses
            buses.add(src + " Express");
            times.add("09:00 AM");
            prices.add(800);

            buses.add(src + " Sleeper");
            times.add("02:00 PM");
            prices.add(650);

            buses.add("Night AC Bus");
            times.add("10:00 PM");
            prices.add(1200);

            for (int i = 0; i < buses.size(); i++) {

                busBox.addItem(
                        buses.get(i)
                                + " | "
                                + times.get(i)
                                + " | ₹"
                                + prices.get(i));
            }

            JOptionPane.showMessageDialog(this,
                    "Buses Loaded Successfully!");
        }

        // CONFIRM TICKET
        if (e.getSource() == nextBtn) {

            int index = busBox.getSelectedIndex();

            if (index == -1) {

                JOptionPane.showMessageDialog(this,
                        "Select a Bus!");

                return;
            }

            Date selectedDate =
                    (Date) dateSpinner.getValue();

            String date =
                    new SimpleDateFormat("dd-MM-yyyy")
                            .format(selectedDate);

            int count =
                    (int) passengerCount.getValue();

            ArrayList<String> names =
                    new ArrayList<>();

            for (int i = 1; i <= count; i++) {

                String name =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Passenger Name " + i);

                if (name == null || name.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Passenger name cannot be empty!");

                    return;
                }

                names.add(name);
            }

            new PaymentPage(
                    username,
                    buses.get(index),
                    times.get(index),
                    prices.get(index),
                    (String) sourceBox.getSelectedItem(),
                    (String) destBox.getSelectedItem(),
                    date,
                    names
            );

            dispose();
        }
    }
}

// ---------------- PAYMENT PAGE ----------------
class PaymentPage extends JFrame implements ActionListener {

    JTextField accField;
    JButton payBtn;

    String user, bus, time, src, dst, date;

    int price;

    ArrayList<String> names;

    public PaymentPage(
            String user,
            String bus,
            String time,
            int price,
            String src,
            String dst,
            String date,
            ArrayList<String> names) {

        this.user = user;
        this.bus = bus;
        this.time = time;
        this.price = price;
        this.src = src;
        this.dst = dst;
        this.date = date;
        this.names = names;

        setTitle("Payment");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        getContentPane().setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("PAYMENT");
        title.setBounds(650, 120, 400, 40);

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font("Arial",
                        Font.BOLD,
                        30));

        JLabel amount =
                new JLabel("Amount: ₹" + price);

        amount.setBounds(650, 200, 300, 30);

        amount.setForeground(Color.GREEN);

        amount.setFont(
                new Font("Arial",
                        Font.BOLD,
                        22));

        JLabel accLabel =
                new JLabel("Account No (12 digits):");

        accLabel.setBounds(600, 260, 250, 30);

        accLabel.setForeground(Color.WHITE);

        accField = new JTextField();
        accField.setBounds(850, 260, 220, 35);

        payBtn = new JButton("PAY NOW");
        payBtn.setBounds(680, 340, 180, 45);

        add(title);
        add(amount);
        add(accLabel);
        add(accField);
        add(payBtn);

        payBtn.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String acc = accField.getText();

        // REGEX VALIDATION
        if (!acc.matches("\\d{12}")) {

            JOptionPane.showMessageDialog(this,
                    "Account Number must contain exactly 12 digits!");

            return;
        }

        JOptionPane.showMessageDialog(this,
                "Payment Successful!");

        new TicketImagePage(
                user,
                bus,
                time,
                price,
                src,
                dst,
                date,
                names
        );

        dispose();
    }
}

// ---------------- FINAL TICKET PAGE ----------------
class TicketImagePage extends JFrame {

    public TicketImagePage(
            String user,
            String bus,
            String time,
            int price,
            String src,
            String dst,
            String date,
            ArrayList<String> names) {

        setTitle("Ticket");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // CREATE IMAGE
        BufferedImage img =
                new BufferedImage(
                        1100,
                        650,
                        BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();

        // BACKGROUND
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1100, 650);

        // BORDER
        g.setColor(Color.BLACK);
        g.drawRect(20, 20, 1060, 610);

        // TITLE
        g.setFont(
                new Font("Times New Roman",
                        Font.BOLD,
                        30));

        g.drawString("BUS TICKET", 430, 60);

        g.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        20));

        g.drawString("Passenger : " + user, 60, 120);

        g.drawString(
                "Route : "
                        + src
                        + " → "
                        + dst,
                60,
                170);

        g.drawString("Journey Date : " + date,
                60,
                220);

        g.drawString("Bus : " + bus,
                60,
                270);

        g.drawString("Departure Time : " + time,
                60,
                320);

        // PASSENGER DETAILS
        int y = 390;

        g.drawString("Passenger Details:",
                60,
                y);

        for (int i = 0; i < names.size(); i++) {

            y += 35;

            g.drawString(
                    (i + 1)
                            + ". "
                            + names.get(i)
                            + "   | Seat No : "
                            + (i + 1),
                    90,
                    y);
        }

        // TOTAL AMOUNT
        g.drawString(
                "Total Amount : ₹" + price,
                60,
                y + 60);

        // CONFIRMATION
        g.setFont(
                new Font("Arial",
                        Font.BOLD,
                        24));

        g.setColor(Color.BLUE);

        g.drawString(
                "TICKET CONFIRMED",
                390,
                560);

        g.drawString(
                "Happy Journey!",
                430,
                600);

        g.dispose();

        // SCALE IMAGE
        Image scaled =
                img.getScaledInstance(
                        Toolkit.getDefaultToolkit()
                                .getScreenSize().width - 50,

                        Toolkit.getDefaultToolkit()
                                .getScreenSize().height - 50,

                        Image.SCALE_SMOOTH);

        JLabel label =
                new JLabel(new ImageIcon(scaled));

        label.setHorizontalAlignment(
                JLabel.CENTER);

        add(label);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

// ---------------- MAIN CLASS ----------------
public class BookingTicketSystem {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new LoginPage();

        });
    }
}


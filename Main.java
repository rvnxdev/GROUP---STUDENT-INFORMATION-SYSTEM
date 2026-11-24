import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.regex.Pattern;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// ==========================================
// STUDENT INFORMATION SYSTEM
// PART 1 - MAIN WINDOW
// ==========================================

public class Main extends JFrame {

    // DESIGN COLORS
    static final Color NAVY =
        new Color(25, 38, 64);

    static final Color BLUE =
        new Color(77, 112, 205);

    static final Color GOLD =
        new Color(139, 112, 231);

    static final Color BACKGROUND =
        new Color(246, 248, 253);

    static final Color WHITE =
        Color.WHITE;

    static final Color TEXT =
        new Color(42, 51, 72);

    static final Color GRAY =
        new Color(112, 124, 148);

    // MAIN COMPONENTS
    private CardLayout cardLayout;
    private JPanel mainContent;

    private DashboardPanel dashboard;
    private StudentPanel studentPanel;
    private String activePage = "Dashboard";

    // CONSTRUCTOR
    public Main() {

    setTitle("Student Information System");
    setSize(1360, 820);
    setMinimumSize(new Dimension(1100, 700));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Create navigation
    JPanel sidebar = createSidebar();
    add(sidebar, BorderLayout.WEST);

    // Create content
    cardLayout = new CardLayout();
    mainContent = new FadePanel(cardLayout);

    initializeModules();

    // Add content
    add(mainContent, BorderLayout.CENTER);

    // Show dashboard
    cardLayout.show(mainContent, "Dashboard");

    refreshDashboard();

    setVisible(true);
}
    // ==========================================
    // PART 2 - SIDEBAR AND NAVIGATION
    // ==========================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
            new Dimension(245, 0)
        );

        sidebar.setBackground(new Color(19, 31, 59));

        // SYSTEM BRANDING
        JPanel branding = new JPanel();

        branding.setBackground(new Color(19, 31, 59));

        branding.setBorder(
            new EmptyBorder(30, 20, 30, 15)
        );

        branding.setLayout(
            new BoxLayout(
                branding,
                BoxLayout.Y_AXIS
            )
        );

        JLabel logo = new JLabel("SIS");

        logo.setFont(
            new Font("Segoe UI", Font.BOLD, 36)
        );

        logo.setForeground(GOLD);

        JLabel name = new JLabel(
            "STUDENT INFORMATION"
        );

        name.setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );

        name.setForeground(WHITE);

        JLabel subtitle = new JLabel(
            "ACADEMIC MANAGEMENT SYSTEM"
        );

        subtitle.setFont(
            new Font("Segoe UI", Font.PLAIN, 9)
        );

        subtitle.setForeground(
            new Color(190, 200, 215)
        );

        branding.add(logo);

        branding.add(
            Box.createVerticalStrut(10)
        );

        branding.add(name);

        branding.add(
            Box.createVerticalStrut(5)
        );

        branding.add(subtitle);

        sidebar.add(
            branding,
            BorderLayout.NORTH
        );

        // NAVIGATION BUTTONS
        JPanel menu = new JPanel(
            new GridLayout(8, 1, 0, 8)
        );

        menu.setBackground(new Color(19, 31, 59));

        menu.setBorder(
            new EmptyBorder(10, 12, 10, 12)
        );

        String[] items = {
            "Dashboard",
            "Student Profiles",
            "Academic Records",
            "Performance",
            "Instructors",
            "Reports",
            "Settings",
            "Log Out"
        };

        final java.util.Map<String, JButton> navigationButtons = new java.util.LinkedHashMap<>();
        for (String item : items) {

            JButton button = new JButton(navLabel(item));
            button.setBackground(new Color(19, 31, 59));
            button.setForeground(new Color(222, 230, 246));
            button.setFont(new Font("Segoe UI", Font.BOLD, 13));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFocusPainted(false);
            button.setBorder(new EmptyBorder(13, 16, 13, 8));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setOpaque(true);
            navigationButtons.put(item, button);
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!item.equals(activePage)) button.setBackground(new Color(48, 65, 100));
                    button.setForeground(Color.WHITE);
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!item.equals(activePage)) button.setBackground(new Color(19, 31, 59));
                    button.setForeground(item.equals(activePage) ? Color.WHITE : new Color(222, 230, 246));
                }
            });

            button.addActionListener(e -> navigate(item));

            menu.add(button);
        }

        sidebar.add(
            menu,
            BorderLayout.CENTER
        );

        // SIDEBAR FOOTER
        JLabel footer = new JLabel(
            "  STUDENT MANAGEMENT SYSTEM"
        );

        footer.setForeground(
            new Color(175, 190, 210)
        );

        footer.setFont(
            new Font("Segoe UI", Font.PLAIN, 10)
        );

        footer.setBorder(
            new EmptyBorder(15, 10, 20, 10)
        );

        sidebar.add(
            footer,
            BorderLayout.SOUTH
        );

        return sidebar;
    }

    // ==========================================
    // NAVIGATION FUNCTION
    // ==========================================

    private void navigate(String page) {

        if (page.equals("Log Out")) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Log out of the Student Information System?",
                "Confirm Log Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginFrame());
            }
            return;
        }

        activePage = page;
        // Sidebar selection is intentionally persistent; hover is a separate state.
        Component side = getContentPane().getComponent(0);
        if (side instanceof JPanel) {
            for (Component child : ((JPanel) side).getComponents()) {
                if (child instanceof JPanel) {
                    for (Component item : ((JPanel) child).getComponents()) {
                        if (item instanceof JButton) {
                            JButton nav = (JButton) item;
                            boolean selected = nav.getText().contains(navLabel(page).trim().substring(0, Math.min(4, navLabel(page).trim().length())));
                            nav.setBackground(selected ? new Color(65, 91, 151) : new Color(19, 31, 59));
                            nav.setForeground(selected ? Color.WHITE : new Color(222, 230, 246));
                        }
                    }
                }
            }
        }
        cardLayout.show(mainContent, page);
        if (mainContent instanceof FadePanel) ((FadePanel) mainContent).startFade();

        if (page.equals("Dashboard")) {
            refreshDashboard();
        }

        if (page.equals("Student Profiles")) {
            studentPanel.refreshTable();
        }
        if (page.equals("Academic Records")) {
            for (Component component : mainContent.getComponents()) if (component instanceof AcademicRecordsPanel) ((AcademicRecordsPanel)component).refreshCards();
        }
    }

    // ==========================================
    // PART 3 - DASHBOARD PANEL
    // ==========================================

    // Animated academic dashboard: motion is lightweight and Swing-friendly.
    static class DashboardPanel extends JPanel {
        private JLabel totalStudents, averageGPA, atRisk;
        private int motion = 0;
        DashboardPanel() {
            setLayout(new BorderLayout(20,20)); setBackground(BACKGROUND); setBorder(new EmptyBorder(26,28,26,28));
            JPanel heading = new JPanel(new BorderLayout()); heading.setOpaque(false);
            JPanel titleBox = new JPanel(); titleBox.setOpaque(false); titleBox.setLayout(new BoxLayout(titleBox,BoxLayout.Y_AXIS));
            JLabel eyebrow = new JLabel("ACADEMIC CONTROL CENTER  •  STUDENT INFORMATION SYSTEM"); eyebrow.setFont(new Font("Segoe UI",Font.BOLD,11)); eyebrow.setForeground(new Color(104,124,174));
            JLabel title = new JLabel("Dashboard Overview"); title.setFont(new Font("Segoe UI",Font.BOLD,29)); title.setForeground(NAVY);
            JLabel sub = new JLabel("Your campus data, organized in one clear view."); sub.setFont(new Font("Segoe UI",Font.PLAIN,13)); sub.setForeground(GRAY);
            titleBox.add(eyebrow); titleBox.add(Box.createVerticalStrut(7)); titleBox.add(title); titleBox.add(Box.createVerticalStrut(4)); titleBox.add(sub); heading.add(titleBox,BorderLayout.WEST);
            JLabel tag=new JLabel(" ✦ LEARNING  •  GROWTH  •  EXCELLENCE ✦ "); tag.setOpaque(true); tag.setBackground(new Color(230,237,255)); tag.setForeground(new Color(65,88,153)); tag.setFont(new Font("Segoe UI",Font.BOLD,11)); tag.setBorder(new EmptyBorder(11,12,11,12)); heading.add(tag,BorderLayout.EAST); add(heading,BorderLayout.NORTH);
            JPanel center=new JPanel(new BorderLayout(16,16)); center.setOpaque(false);
            JPanel cards=new JPanel(new GridLayout(1,3,14,14)); cards.setOpaque(false); totalStudents=new JLabel("0"); averageGPA=new JLabel("0.00"); atRisk=new JLabel("0");
            cards.add(metric("ENROLLED STUDENTS",totalStudents,"▣  Student records",new Color(73,111,207))); cards.add(metric("ACADEMIC AVERAGE",averageGPA,"◎  GPA overview",new Color(125,100,211))); cards.add(metric("NEEDS FOLLOW-UP",atRisk,"△  Academic support",new Color(216,155,75)));
            JPanel lower=new JPanel(new GridLayout(1,2,16,16)); lower.setOpaque(false); lower.add(welcomeCard()); lower.add(new CampusMotionCard()); center.add(cards,BorderLayout.NORTH); center.add(lower,BorderLayout.CENTER); add(center,BorderLayout.CENTER);
            new Timer(40,e->{motion++;repaint();}).start();
        }
        private JPanel metric(String heading,JLabel value,String detail,Color accent){
            JPanel c=new MotionMetricCard(accent); c.setLayout(new BorderLayout(8,8)); c.setBorder(new EmptyBorder(18,20,18,20));
            JLabel h=new JLabel(heading); h.setFont(new Font("Segoe UI",Font.BOLD,11)); h.setForeground(new Color(100,112,138)); value.setFont(new Font("Segoe UI",Font.BOLD,32)); value.setForeground(NAVY);
            JLabel d=new JLabel(detail); d.setFont(new Font("Segoe UI",Font.PLAIN,12)); d.setForeground(GRAY); c.add(h,BorderLayout.NORTH); c.add(value,BorderLayout.CENTER); c.add(d,BorderLayout.SOUTH); return c;
        }
        private JPanel welcomeCard(){
            RoundedPanel c=new RoundedPanel(24,WHITE); c.setLayout(new BorderLayout(12,14)); c.setBorder(new EmptyBorder(22,22,22,22));
            JPanel intro=new JPanel(); intro.setOpaque(false); intro.setLayout(new BoxLayout(intro,BoxLayout.Y_AXIS));
            JLabel chip=new JLabel("  CAMPUS HUB  "); chip.setOpaque(true); chip.setBackground(new Color(235,240,255)); chip.setForeground(BLUE); chip.setFont(new Font("Segoe UI",Font.BOLD,10)); chip.setBorder(new EmptyBorder(6,9,6,9));
            JLabel welcome=new JLabel("Welcome to your academic hub"); welcome.setFont(new Font("Segoe UI",Font.BOLD,20)); welcome.setForeground(NAVY);
            JLabel text=new JLabel("<html>Manage student profiles, academic records,<br>performance, instructors and reports with ease.</html>"); text.setFont(new Font("Segoe UI",Font.PLAIN,13)); text.setForeground(GRAY);
            intro.add(chip);intro.add(Box.createVerticalStrut(11));intro.add(welcome);intro.add(Box.createVerticalStrut(8));intro.add(text); c.add(intro,BorderLayout.NORTH);
            JPanel tiles=new JPanel(new GridLayout(2,2,9,9));tiles.setOpaque(false);String[] a={"▣  Student profiles","▤  Academic records","◎  Performance insights","▧  Reports & summaries"};for(String x:a){JLabel t=new JLabel(x);t.setOpaque(true);t.setBackground(new Color(246,248,253));t.setForeground(new Color(65,79,112));t.setBorder(new EmptyBorder(12,9,12,9));t.setFont(new Font("Segoe UI",Font.BOLD,11));tiles.add(t);} c.add(tiles,BorderLayout.CENTER);
            JLabel foot=new JLabel("SELECT A MODULE FROM THE SIDEBAR TO GET STARTED");foot.setFont(new Font("Segoe UI",Font.BOLD,10));foot.setForeground(new Color(123,137,163));c.add(foot,BorderLayout.SOUTH);return c;
        }
        void updateStatistics(ArrayList<StudentRecord> students){
            animateNumber(totalStudents,students.size(),false);double sum=0;int risk=0;for(StudentRecord st:students){sum+=st.gpa;if(st.gpa<3.00)risk++;}double avg=students.isEmpty()?0:sum/students.size();animateNumber(averageGPA,avg,true);animateNumber(atRisk,risk,false);
        }
        private void animateNumber(JLabel label,double target,boolean decimals){final int[] f={0};double from;try{from=Double.parseDouble(label.getText());}catch(Exception ex){from=0;}final double begin=from;Timer t=new Timer(22,e->{f[0]++;double ease=1-Math.pow(1-f[0]/18.0,3);double v=begin+(target-begin)*ease;label.setText(decimals?String.format("%.2f",v):String.format("%.0f",v));if(f[0]>=18){label.setText(decimals?String.format("%.2f",target):String.valueOf((int)target));((Timer)e.getSource()).stop();}});t.start();}
    }
    static class MotionMetricCard extends JPanel { Color accent; boolean hover=false; MotionMetricCard(Color c){accent=c;setOpaque(false);addMouseListener(new java.awt.event.MouseAdapter(){public void mouseEntered(java.awt.event.MouseEvent e){hover=true;repaint();}public void mouseExited(java.awt.event.MouseEvent e){hover=false;repaint();}});}protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g.create();g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);int dy=hover?-2:0;g2.setColor(new Color(20,30,55,18));g2.fillRoundRect(2,5+dy,getWidth()-4,getHeight()-7,23,23);g2.setColor(Color.WHITE);g2.fillRoundRect(0,dy,getWidth()-1,getHeight()-6,23,23);g2.setColor(accent);g2.fillRoundRect(0,dy,5,getHeight()-6,8,8);g2.dispose();super.paintComponent(g);}}
    static class CampusMotionCard extends JPanel {int tick=0;CampusMotionCard(){setOpaque(false);new Timer(40,e->{tick++;repaint();}).start();}protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g.create();g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);int w=getWidth(),h=getHeight();g2.setColor(new Color(22,36,70));g2.fillRoundRect(0,0,w,h,26,26);g2.setColor(new Color(46,65,112));g2.fillRoundRect(w/2,0,w/2,h,26,26);for(int i=0;i<24;i++){int x=(i*61+tick/3)%Math.max(1,w),y=(i*31+tick/5)%Math.max(1,h);g2.setColor(new Color(180,200,255,50));g2.fillOval(x,y,3+(i%3),3+(i%3));}int bob=(int)(Math.sin(tick/17.0)*6);g2.setColor(new Color(255,255,255,28));g2.drawOval(w-185,35,130,130);g2.drawOval(w-160,60,80,80);g2.setFont(new Font("Segoe UI",Font.BOLD,19));g2.setColor(Color.WHITE);g2.drawString("Learning in motion",22,40);g2.setFont(new Font("Segoe UI",Font.PLAIN,12));g2.setColor(new Color(205,219,246));g2.drawString("A connected view of your academic community",22,62);g2.setFont(new Font("Segoe UI",Font.PLAIN,42));g2.drawString("✦",w-145,130+bob);g2.setColor(new Color(255,255,255,40));g2.fillRoundRect(22,86,w-44,3,3,3);g2.setColor(new Color(163,184,255));g2.fillRoundRect(22,86,(int)((w-44)*(0.5+0.5*Math.sin(tick/42.0))),3,3,3);g2.setFont(new Font("Segoe UI",Font.BOLD,11));g2.setColor(Color.WHITE);g2.drawString("STUDENTS",22,115);g2.drawString("RECORDS",22,140);g2.drawString("INSIGHTS",22,165);g2.setColor(new Color(176,195,255));g2.drawString("✧  Organized   •   Accessible   •   Academic",22,h-25);g2.dispose();}}

    // ==========================================
    // PART 4 - STUDENT DATA MODEL
    // ==========================================

    static class StudentRecord {

        int id;
        String name;
        int age;
        String course;
        String year;
        double gpa;
        String picturePath;

        StudentRecord(
            int id,
            String name,
            int age,
            String course,
            String year,
            double gpa
        ) {

            this.id = id;
            this.name = name;
            this.age = age;
            this.course = course;
            this.year = year;
            this.gpa = gpa;
            this.picturePath = null;
        }
    }

    // ==========================================
    // STUDENT PROFILE PANEL
    // ==========================================

    static class StudentPanel extends JPanel {

        private DefaultTableModel model;

        private JTable table;

        private JTextField searchField;

        private ArrayList<StudentRecord> students =
            new ArrayList<>();

        private Runnable onChange;

        StudentPanel(Runnable onChange) {

            this.onChange = onChange;

            setLayout(
                new BorderLayout(15, 20)
            );

            setBackground(BACKGROUND);

            setBorder(
                new EmptyBorder(30, 30, 30, 30)
            );

            // PAGE TITLE

            JLabel title = new JLabel(
                "Student Profile Management"
            );

            title.setFont(new Font("Segoe UI", Font.BOLD, 27));

            title.setForeground(NAVY);

            // SEARCH FIELD

            searchField = new JTextField(15);

            JButton search = createButton(
                "SEARCH", BLUE
            );

            JButton reset = createButton(
                "RESET", NAVY
            );

            search.addActionListener(
                e -> searchStudents()
            );

            reset.addActionListener(e -> {
                searchField.setText("");
                refreshTable();
            });

            JPanel searchPanel = new JPanel();

            searchPanel.setOpaque(false);

            JLabel searchLabel = new JLabel("SEARCH STUDENT");
            searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
            searchLabel.setForeground(GRAY);
            searchPanel.add(searchLabel);

            searchPanel.add(searchField);
            searchPanel.add(search);
            searchPanel.add(reset);

            JPanel top = new JPanel(
                new BorderLayout()
            );

            top.setOpaque(false);

            JPanel titleStack = new JPanel();
            titleStack.setOpaque(false);
            titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));
            JLabel eyebrow = new JLabel("CAMPUS DIRECTORY  /  STUDENT SERVICES");
            eyebrow.setFont(new Font("Segoe UI", Font.BOLD, 10));
            eyebrow.setForeground(new Color(105, 125, 174));
            titleStack.add(eyebrow);
            titleStack.add(Box.createVerticalStrut(5));
            titleStack.add(title);
            top.add(titleStack, BorderLayout.WEST);
            top.add(searchPanel, BorderLayout.EAST);

            // STUDENT TABLE

            model = new DefaultTableModel(
                new String[]{
                    "Student ID",
                    "Full Name",
                    "Age",
                    "Course",
                    "Year Level",
                    "GPA"
                },
                0
            ) {

                @Override
                public boolean isCellEditable(
                    int row,
                    int column
                ) {
                    return false;
                }
            };

            table = new JTable(model);

            table.setRowHeight(40);
            table.setDefaultRenderer(Object.class, new AcademicTableCellRenderer());
            table.setDefaultRenderer(Number.class, new AcademicTableCellRenderer());
            table.setShowVerticalLines(false);
            table.setShowHorizontalLines(true);
            table.setGridColor(new Color(235, 239, 247));
            table.setSelectionBackground(new Color(231, 237, 255));
            table.setSelectionForeground(NAVY);
            table.setIntercellSpacing(new Dimension(0, 1));

            table.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
            );
            table.setDefaultRenderer(Object.class, new AcademicTableCellRenderer());
            table.setDefaultRenderer(Number.class, new AcademicTableCellRenderer());

            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

            table.getTableHeader().setBackground(new Color(232, 238, 251));

            table.getTableHeader().setForeground(NAVY);
            table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            table.getTableHeader().setPreferredSize(new Dimension(0, 42));

            table.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = table.getSelectedRow();
                        if (row >= 0) showStudentProfile(row);
                    }
                }
            });

            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setBackground(Color.WHITE);
            scroll.getVerticalScrollBar().setUnitIncrement(16);

            JPanel studentContent = new JPanel(new BorderLayout(14, 14));
            studentContent.setOpaque(false);
            JPanel studentSummary = new JPanel(new GridLayout(1, 3, 12, 12));
            studentSummary.setOpaque(false);
            studentSummary.add(new StudentMiniStat("TOTAL PROFILES", String.valueOf(students.size()), "Directory size", new Color(73,111,207)));
            studentSummary.add(new StudentMiniStat("ACADEMIC TRACKS", "2+", "Program categories", new Color(125,100,211)));
            studentSummary.add(new StudentMiniStat("RECORD HEALTH", "ACTIVE", "Profile management", new Color(63,153,126)));
            JPanel studentTableSurface = new RoundedPanel(22, Color.WHITE);
            studentTableSurface.setLayout(new BorderLayout());
            studentTableSurface.setBorder(new EmptyBorder(12, 12, 12, 12));
            studentTableSurface.add(scroll, BorderLayout.CENTER);
            studentContent.add(studentSummary, BorderLayout.NORTH);
            studentContent.add(studentTableSurface, BorderLayout.CENTER);

            // ACTION BUTTONS

            JButton add = createButton(
                "ADD STUDENT", BLUE
            );

            JButton update = createButton(
                "UPDATE", GOLD
            );

            JButton delete = createButton(
                "DELETE",
                new Color(170, 55, 55)
            );

            JButton refresh = createButton(
                "REFRESH", NAVY
            );

            add.addActionListener(
                e -> addStudent()
            );

            update.addActionListener(
                e -> updateStudent()
            );

            delete.addActionListener(
                e -> deleteStudent()
            );

            refresh.addActionListener(e -> {
                searchField.setText("");
                refreshTable();
            });

            JPanel buttons = new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT, 10, 10
                )
            );

            buttons.setOpaque(false);

            buttons.add(add);
            buttons.add(update);
            buttons.add(delete);
            buttons.add(refresh);

            add(top, BorderLayout.NORTH);
            add(studentContent, BorderLayout.CENTER);
            add(buttons, BorderLayout.SOUTH);

            // SAMPLE STUDENT RECORDS

            students.add(new StudentRecord(
                2026001,
                "Juan Dela Cruz",
                19,
                "BS Information Technology",
                "1st Year",
                3.50
            ));

            students.add(new StudentRecord(
                2026002,
                "Maria Santos",
                20,
                "BS Computer Science",
                "2nd Year",
                3.75
            ));

            students.add(new StudentRecord(
                2026003,
                "Pedro Reyes",
                21,
                "BS Information Technology",
                "3rd Year",
                2.75
            ));

            refreshTable();
        }

        private void showStudentProfile(int row) {
            int id = (int) model.getValueAt(row, 0);
            int index = findStudent(id);
            if (index < 0) return;
            StudentRecord student = students.get(index);
            JPanel details = new RoundedPanel(24, Color.WHITE);
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            details.setBorder(new EmptyBorder(22, 24, 22, 24));
            JLabel avatar = new JLabel("STUDENT", SwingConstants.CENTER);
            avatar.setOpaque(true);
            avatar.setBackground(new Color(231, 237, 255));
            avatar.setForeground(BLUE);
            avatar.setFont(new Font("Segoe UI", Font.BOLD, 18));
            avatar.setBorder(new EmptyBorder(18, 24, 18, 24));
            JLabel name = new JLabel(student.name);
            name.setFont(new Font("Segoe UI", Font.BOLD, 22));
            name.setForeground(NAVY);
            JLabel info = new JLabel("ID: " + student.id + "   |   Age: " + student.age);
            JLabel course = new JLabel(student.course + "  •  " + student.year);
            JLabel gpa = new JLabel(String.format("GPA  %.2f", student.gpa));
            gpa.setFont(new Font("Segoe UI", Font.BOLD, 18));
            gpa.setForeground(BLUE);
            details.add(avatar); details.add(Box.createVerticalStrut(16));
            details.add(name); details.add(Box.createVerticalStrut(8));
            details.add(info); details.add(Box.createVerticalStrut(8));
            details.add(course); details.add(Box.createVerticalStrut(14));
            details.add(gpa);
            JOptionPane.showMessageDialog(this, details, "Student Profile", JOptionPane.PLAIN_MESSAGE);
        }

        // CREATE BUTTON

        private JButton createButton(
            String text,
            Color color
        ) {

            JButton button = new RoundedButton(text, 16);

            button.setBackground(color);

            button.setForeground(WHITE);

            button.setFont(
                new Font("Segoe UI", Font.BOLD, 11)
            );

            button.setFocusPainted(false);

            button.setBorder(
                new EmptyBorder(12, 18, 12, 18)
            );
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setOpaque(false);

            button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
            );

            return button;
        }


        // ==========================================
        // PART 5 - ADD STUDENT
        // ==========================================

        private void addStudent() {

            JTextField id = new JTextField();
            JTextField name = new JTextField();
            JTextField age = new JTextField();
            JTextField course = new JTextField();
            JTextField year = new JTextField();
            JTextField gpa = new JTextField();
            JTextField picture = new JTextField("No picture selected");
            picture.setEditable(false);
            JButton choosePicture = new JButton("Choose photo...");
            final String[] selectedPicture = {null};
            choosePicture.addActionListener(ev -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    selectedPicture[0] = chooser.getSelectedFile().getAbsolutePath();
                    picture.setText(chooser.getSelectedFile().getName());
                }
            });

            Object[] fields = {
                "Student ID:", id,
                "Full Name:", name,
                "Age:", age,
                "Course:", course,
                "Year Level:", year,
                "GPA:", gpa,
                "Student Photo:", picture, choosePicture
            };

            int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Register Student",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            try {

                int studentID = Integer.parseInt(
                    id.getText().trim()
                );

                int studentAge = Integer.parseInt(
                    age.getText().trim()
                );

                double studentGPA = Double.parseDouble(
                    gpa.getText().trim()
                );

                validate(
                    name.getText(),
                    studentAge,
                    course.getText(),
                    year.getText(),
                    studentGPA
                );

                if (findStudent(studentID) != -1) {

                    throw new IllegalArgumentException(
                        "Student ID already exists."
                    );
                }

                StudentRecord newStudent = new StudentRecord(
                    studentID, name.getText().trim(), studentAge,
                    course.getText().trim(), year.getText().trim(), studentGPA
                );
                newStudent.picturePath = selectedPicture[0];
                students.add(newStudent);

                refreshTable();
                onChange.run();

                JOptionPane.showMessageDialog(
                    this,
                    "Student successfully added!"
                );

            } catch (NumberFormatException ex) {

                showError(
                    "Please enter valid numeric values."
                );

            } catch (IllegalArgumentException ex) {

                showError(ex.getMessage());
            }
        }

        // ==========================================
        // UPDATE STUDENT
        // ==========================================

        private void updateStudent() {

            int row = table.getSelectedRow();

            if (row == -1) {

                showError(
                    "Please select a student first."
                );

                return;
            }

            int id = (int) model.getValueAt(row, 0);

            int index = findStudent(id);

            if (index == -1) return;

            StudentRecord old = students.get(index);

            JTextField name = new JTextField(old.name);

            JTextField age = new JTextField(
                String.valueOf(old.age)
            );

            JTextField course = new JTextField(old.course);

            JTextField year = new JTextField(old.year);

            JTextField gpa = new JTextField(
                String.valueOf(old.gpa)
            );
            JTextField picture = new JTextField(old.picturePath == null ? "No picture selected" : new File(old.picturePath).getName());
            picture.setEditable(false);
            final String[] selectedPicture = {old.picturePath};
            JButton choosePicture = new JButton("Change photo...");
            choosePicture.addActionListener(ev -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    selectedPicture[0] = chooser.getSelectedFile().getAbsolutePath();
                    picture.setText(chooser.getSelectedFile().getName());
                }
            });

            Object[] fields = {
                "Full Name:", name,
                "Age:", age,
                "Course:", course,
                "Year Level:", year,
                "GPA:", gpa,
                "Student Photo:", picture, choosePicture
            };

            int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Update Student",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            try {

                int studentAge = Integer.parseInt(
                    age.getText().trim()
                );

                double studentGPA = Double.parseDouble(
                    gpa.getText().trim()
                );

                validate(
                    name.getText(),
                    studentAge,
                    course.getText(),
                    year.getText(),
                    studentGPA
                );

                StudentRecord updatedStudent = new StudentRecord(
                    id, name.getText().trim(), studentAge,
                    course.getText().trim(), year.getText().trim(), studentGPA
                );
                updatedStudent.picturePath = selectedPicture[0];
                students.set(index, updatedStudent);

                refreshTable();
                onChange.run();

                JOptionPane.showMessageDialog(
                    this,
                    "Student record updated!"
                );

            } catch (NumberFormatException ex) {

                showError(
                    "Please enter valid numeric values."
                );

            } catch (IllegalArgumentException ex) {

                showError(ex.getMessage());
            }
        }

        // ==========================================
        // DELETE STUDENT
        // ==========================================

        private void deleteStudent() {

            int row = table.getSelectedRow();

            if (row == -1) {

                showError(
                    "Please select a student first."
                );

                return;
            }

            int id = (int) model.getValueAt(row, 0);

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                int index = findStudent(id);

                if (index != -1) {
                    students.remove(index);
                }

                refreshTable();
                onChange.run();

                JOptionPane.showMessageDialog(
                    this,
                    "Student successfully deleted!"
                );
            }
        }

        private void showStudentProfile(StudentRecord student) {
            JPanel content = new JPanel(new BorderLayout(16, 12));
            content.setBackground(Color.WHITE);
            JLabel photo = new JLabel();
            photo.setPreferredSize(new Dimension(160, 160));
            photo.setHorizontalAlignment(SwingConstants.CENTER);
            photo.setOpaque(true); photo.setBackground(new Color(239, 243, 251));
            if (student.picturePath != null) {
                try {
                    BufferedImage image = ImageIO.read(new File(student.picturePath));
                    if (image != null) photo.setIcon(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
                } catch (Exception ignored) { }
            }
            if (photo.getIcon() == null) photo.setText("No photo");
            JPanel details = new JPanel(new GridLayout(0, 1, 5, 5));
            details.setBackground(Color.WHITE);
            details.add(new JLabel("Student ID: " + student.id));
            details.add(new JLabel("Name: " + student.name));
            details.add(new JLabel("Age: " + student.age));
            details.add(new JLabel("Course: " + student.course));
            details.add(new JLabel("Year: " + student.year));
            details.add(new JLabel(String.format("GPA: %.2f", student.gpa)));
            content.add(photo, BorderLayout.WEST); content.add(details, BorderLayout.CENTER);
            content.setBorder(new EmptyBorder(18, 18, 18, 18));
            JOptionPane.showMessageDialog(this, content, "Student Profile", JOptionPane.PLAIN_MESSAGE);
        }

        // ==========================================
        // PART 6 - SEARCH STUDENT
        // ==========================================

        private void searchStudents() {

            String keyword = searchField.getText()
                .trim().toLowerCase();

            model.setRowCount(0);

            for (StudentRecord student : students) {

                String id = String.valueOf(student.id);

                String name = student.name.toLowerCase();

                if (id.contains(keyword)
                    || name.contains(keyword)) {

                    addRow(student);
                }
            }
        }

        // ==========================================
        // REFRESH STUDENT TABLE
        // ==========================================

        void refreshTable() {

            model.setRowCount(0);

            for (StudentRecord student : students) {

                addRow(student);
            }
        }

        // ==========================================
        // ADD STUDENT TO TABLE
        // ==========================================

        private void addRow(StudentRecord student) {

            model.addRow(new Object[]{

                student.id,
                student.name,
                student.age,
                student.course,
                student.year,
                student.gpa

            });
        }

        // ==========================================
        // FIND STUDENT BY ID
        // ==========================================

        private int findStudent(int id) {

            for (int i = 0; i < students.size(); i++) {

                if (students.get(i).id == id) {

                    return i;
                }
            }

            return -1;
        }

        // ==========================================
        // INPUT VALIDATION
        // ==========================================

        private void validate(
            String name,
            int age,
            String course,
            String year,
            double gpa
        ) {

            if (name.trim().isEmpty()
                || course.trim().isEmpty()
                || year.trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Please complete all required fields."
                );
            }

            if (age < 1 || age > 120) {

                throw new IllegalArgumentException(
                    "Please enter a valid age."
                );
            }

            if (gpa < 0 || gpa > 5) {

                throw new IllegalArgumentException(
                    "GPA must be between 0 and 5."
                );
            }
        }

        // ==========================================
        // ERROR MESSAGE
        // ==========================================

        private void showError(String message) {

            JOptionPane.showMessageDialog(
                this,
                message,
                "Input Error",
                JOptionPane.WARNING_MESSAGE
            );
        }

        // ==========================================
        // GET STUDENT RECORDS
        // ==========================================

        ArrayList<StudentRecord> getStudents() {

            return students;
        }

    }

    // ==========================================
    // PART 7 - GENERAL MODULE PANEL
    // ==========================================

    // ==========================================
    // ACADEMIC RECORDS - STUDENT CARD VIEW
    // ==========================================
    static class AcademicRecordsPanel extends JPanel {
        private final StudentPanel studentSource;
        private JPanel cards;
        private JLabel countLabel;
        AcademicRecordsPanel(StudentPanel source) {
            studentSource = source;
            setLayout(new BorderLayout(16,16)); setBackground(BACKGROUND);
            setBorder(new EmptyBorder(26,28,26,28));
            JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
            JPanel headings = new JPanel(); headings.setOpaque(false); headings.setLayout(new BoxLayout(headings,BoxLayout.Y_AXIS));
            JLabel eyebrow = new JLabel("ACADEMIC OVERVIEW  /  STUDENT GRADES"); eyebrow.setFont(new Font("Segoe UI",Font.BOLD,11)); eyebrow.setForeground(new Color(104,124,174));
            JLabel title = new JLabel("Academic Records"); title.setFont(new Font("Segoe UI",Font.BOLD,27)); title.setForeground(NAVY);
            JLabel sub = new JLabel("Student cards with profile photos and grade summaries."); sub.setFont(new Font("Segoe UI",Font.PLAIN,13)); sub.setForeground(GRAY);
            headings.add(eyebrow); headings.add(Box.createVerticalStrut(6)); headings.add(title); headings.add(Box.createVerticalStrut(5)); headings.add(sub);
            countLabel = new JLabel("  STUDENT RECORDS  "); countLabel.setOpaque(true); countLabel.setBackground(new Color(232,238,255)); countLabel.setForeground(BLUE); countLabel.setFont(new Font("Segoe UI",Font.BOLD,11)); countLabel.setBorder(new EmptyBorder(10,12,10,12));
            top.add(headings,BorderLayout.WEST); top.add(countLabel,BorderLayout.EAST); add(top,BorderLayout.NORTH);
            cards = new JPanel(new GridLayout(0,3,16,16)); cards.setOpaque(false);
            JScrollPane scroll = new JScrollPane(cards); scroll.setBorder(null); scroll.getViewport().setBackground(BACKGROUND); scroll.getVerticalScrollBar().setUnitIncrement(18);
            add(scroll,BorderLayout.CENTER); refreshCards();
        }
        void refreshCards() {
            cards.removeAll();
            ArrayList<StudentRecord> data = studentSource.getStudents();
            countLabel.setText("  " + data.size() + " STUDENT RECORDS  ");
            if(data.isEmpty()) {
                JPanel empty = new RoundedPanel(22,Color.WHITE); empty.setLayout(new BorderLayout());
                JLabel msg = new JLabel("No student records yet. Add students in Student Profiles.",SwingConstants.CENTER); msg.setForeground(GRAY); msg.setFont(new Font("Segoe UI",Font.PLAIN,14)); empty.add(msg);
                cards.setLayout(new BorderLayout()); cards.add(empty,BorderLayout.CENTER);
            } else {
                cards.setLayout(new GridLayout(0,3,16,16));
                for(StudentRecord student:data) cards.add(studentCard(student));
            }
            cards.revalidate(); cards.repaint();
        }
        private JPanel studentCard(StudentRecord student) {
            AcademicHoverCard card = new AcademicHoverCard(24, Color.WHITE); card.setLayout(new BorderLayout(0,0)); card.setBorder(new EmptyBorder(14,14,14,14));
            JPanel photoFrame = new JPanel(new BorderLayout()); photoFrame.setBackground(new Color(239,243,251)); photoFrame.setPreferredSize(new Dimension(180,150));
            JLabel photo = new JLabel("STUDENT PHOTO",SwingConstants.CENTER); photo.setForeground(new Color(130,145,175)); photo.setFont(new Font("Segoe UI",Font.BOLD,11));
            if(student.picturePath!=null) try { BufferedImage img=ImageIO.read(new File(student.picturePath)); if(img!=null) photo.setIcon(new ImageIcon(img.getScaledInstance(180,150,Image.SCALE_SMOOTH))); photo.setText(""); } catch(Exception ignored){}
            photoFrame.add(photo,BorderLayout.CENTER);
            JPanel info = new JPanel(); info.setOpaque(false); info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS)); info.setBorder(new EmptyBorder(13,4,2,4));
            JLabel name = new JLabel(student.name); name.setFont(new Font("Segoe UI",Font.BOLD,16)); name.setForeground(NAVY);
            JLabel meta = new JLabel(student.id + "  •  " + student.course + "  •  Year " + student.year); meta.setFont(new Font("Segoe UI",Font.PLAIN,11)); meta.setForeground(GRAY);
            JPanel gradeBox = new JPanel(new BorderLayout()); gradeBox.setBackground(new Color(241,244,255)); gradeBox.setBorder(new EmptyBorder(12,13,12,13));
            JPanel gradeText = new JPanel(); gradeText.setOpaque(false); gradeText.setLayout(new BoxLayout(gradeText,BoxLayout.Y_AXIS));
            JLabel gradeTitle = new JLabel("OVERALL GPA"); gradeTitle.setFont(new Font("Segoe UI",Font.BOLD,10)); gradeTitle.setForeground(new Color(100,117,159));
            JLabel grade = new JLabel(String.format("%.2f",student.gpa)); grade.setFont(new Font("Segoe UI",Font.BOLD,25)); grade.setForeground(new Color(64,92,180));
            gradeText.add(gradeTitle); gradeText.add(Box.createVerticalStrut(4)); gradeText.add(grade);
            JLabel status = new JLabel(student.gpa>=3.5?"EXCELLENT STANDING":student.gpa>=3.0?"GOOD STANDING":"REVIEW NEEDED"); status.setFont(new Font("Segoe UI",Font.BOLD,10)); status.setForeground(student.gpa>=3.0?new Color(50,140,100):new Color(190,135,55));
            gradeBox.add(gradeText,BorderLayout.WEST); gradeBox.add(status,BorderLayout.EAST);
            JLabel note = new JLabel("Individual subject grades are not yet linked."); note.setFont(new Font("Segoe UI",Font.PLAIN,10)); note.setForeground(new Color(135,145,165));
            info.add(name); info.add(Box.createVerticalStrut(5)); info.add(meta); info.add(Box.createVerticalStrut(12)); info.add(gradeBox); info.add(Box.createVerticalStrut(7)); info.add(note);
            card.add(photoFrame,BorderLayout.NORTH); card.add(info,BorderLayout.CENTER);
            return card;
        }
    }

    static class ModulePanel extends JPanel {
        private float pageReveal = 0f;
        private Timer pageTimer;
        @Override public void paint(Graphics g) {
            super.paint(g);
            if (pageReveal < 1f) {
                Graphics2D overlay = (Graphics2D) g.create();
                overlay.setColor(new Color(246, 248, 253, (int)(180 * (1f-pageReveal))));
                overlay.fillRect(0, 0, getWidth(), getHeight());
                overlay.dispose();
            }
        }

        ModulePanel(
            String title,
            String[] columns,
            Object[][] data
        ) {

            setLayout(new BorderLayout(20, 20));
            setBackground(BACKGROUND);
            setBorder(new EmptyBorder(28, 28, 28, 28));
            setOpaque(true);
            // Soft page-entry animation for every data module.
            pageReveal = 0f;
            pageTimer = new Timer(18, e -> {
                pageReveal = Math.min(1f, pageReveal + 0.075f);
                repaint(); if (pageReveal >= 1f) ((Timer)e.getSource()).stop();
            });
            pageTimer.start();

            // PAGE HEADING

            JLabel heading = new JLabel("ACADEMIC MODULE  /  " + title.toUpperCase());

            heading.setFont(
                new Font("Segoe UI", Font.BOLD, 25)
            );

            heading.setForeground(NAVY);

            JLabel subtitle = new JLabel(
                "Student Information System Management Module"
            );

            subtitle.setForeground(GRAY);

            subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
            );

            JPanel top = new JPanel();

            top.setOpaque(false);

            top.setLayout(
                new BoxLayout(
                    top,
                    BoxLayout.Y_AXIS
                )
            );

            heading.setBorder(new EmptyBorder(0, 0, 2, 0));
            top.add(heading);
            top.add(Box.createVerticalStrut(8));
            top.add(subtitle);
            top.setBorder(new EmptyBorder(0, 0, 4, 0));

            // TABLE MODEL

            DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                @Override
                public boolean isCellEditable(
                    int row,
                    int column
                ) {
                    return false;
                }
            };

            // LOAD SAMPLE DATA

            for (Object[] row : data) {

                model.addRow(row);
            }

            // CREATE TABLE

            JTable table = new JTable(model);

            table.setRowHeight(42);
            table.setShowVerticalLines(false);
            table.setShowHorizontalLines(true);
            table.setGridColor(new Color(235, 239, 247));
            table.setIntercellSpacing(new Dimension(0, 1));

            table.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
            );
            table.setDefaultRenderer(Object.class, new AcademicTableCellRenderer());
            table.setDefaultRenderer(Number.class, new AcademicTableCellRenderer());

            table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
            );

            table.getTableHeader().setBackground(new Color(237, 241, 250));

            table.getTableHeader().setForeground(NAVY);
            table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
            table.getTableHeader().setPreferredSize(new Dimension(0, 42));

            table.setSelectionBackground(new Color(224, 233, 255));
            table.setSelectionForeground(NAVY);

            table.setSelectionForeground(TEXT);

            JScrollPane scroll = new JScrollPane(table);

            scroll.setBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 231, 242), 1
                )
            );

            // PROFESSIONAL MODULE BODY: summary strip + searchable table surface
            JPanel body = new JPanel(new BorderLayout(14, 14));
            body.setOpaque(false);
            JPanel summaryStrip = new JPanel(new GridLayout(1, 3, 12, 12));
            summaryStrip.setOpaque(false);
            summaryStrip.add(moduleSummary("MODULE", title, new Color(78, 111, 202)));
            summaryStrip.add(moduleSummary("RECORDS LOADED", String.valueOf(model.getRowCount()), new Color(121, 99, 203)));
            summaryStrip.add(moduleSummary("STATUS", "Ready to review", new Color(63, 153, 126)));

            JPanel tableSurface = new RoundedPanel(22, Color.WHITE);
            tableSurface.setLayout(new BorderLayout(12, 12));
            tableSurface.setBorder(new EmptyBorder(16, 16, 16, 16));
            JPanel tableTools = new JPanel(new BorderLayout(10, 0));
            tableTools.setOpaque(false);
            JLabel tableTitle = new JLabel("RECORD DIRECTORY");
            tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
            tableTitle.setForeground(new Color(100, 115, 151));
            JTextField quickSearch = new JTextField();
            quickSearch.setPreferredSize(new Dimension(240, 36));
            quickSearch.setToolTipText("Filter visible rows by any column");
            quickSearch.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222,229,242)), new EmptyBorder(7,10,7,10)));
            quickSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
            quickSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                private void apply() { String text=quickSearch.getText().trim(); sorter.setRowFilter(text.isEmpty()?null:RowFilter.regexFilter("(?i)"+Pattern.quote(text))); }
                public void insertUpdate(javax.swing.event.DocumentEvent e){apply();}
                public void removeUpdate(javax.swing.event.DocumentEvent e){apply();}
                public void changedUpdate(javax.swing.event.DocumentEvent e){apply();}
            });
            tableTools.add(tableTitle, BorderLayout.WEST);
            tableTools.add(quickSearch, BorderLayout.EAST);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            tableSurface.add(tableTools, BorderLayout.NORTH);
            tableSurface.add(scroll, BorderLayout.CENTER);
            JPanel moduleHero = new AcademicModuleHero(title, accentFor(title));
            JPanel summaryAndHero = new JPanel(new BorderLayout(0, 13));
            summaryAndHero.setOpaque(false);
            summaryAndHero.add(moduleHero, BorderLayout.NORTH);
            summaryAndHero.add(summaryStrip, BorderLayout.CENTER);
            body.add(summaryAndHero, BorderLayout.NORTH);
            body.add(tableSurface, BorderLayout.CENTER);
            add(top, BorderLayout.NORTH);
            add(body, BorderLayout.CENTER);
        }
        private Color accentFor(String title) {
            String t = title.toLowerCase();
            if (t.contains("performance")) return new Color(115, 101, 208);
            if (t.contains("instructor")) return new Color(58, 143, 129);
            if (t.contains("report")) return new Color(211, 151, 65);
            if (t.contains("setting")) return new Color(100, 121, 158);
            return new Color(78, 111, 202);
        }

        private JPanel moduleSummary(String label, String value, Color accent) {
            JPanel card = new RoundedPanel(19, Color.WHITE);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new EmptyBorder(14, 16, 14, 16));
            JLabel cap = new JLabel(label); cap.setFont(new Font("Segoe UI", Font.BOLD, 10)); cap.setForeground(new Color(105, 119, 149));
            JLabel val = new JLabel(value); val.setFont(new Font("Segoe UI", Font.BOLD, 16)); val.setForeground(accent);
            card.add(cap); card.add(Box.createVerticalStrut(7)); card.add(val);
            return card;
        }
    }

    // ==========================================
    // MODULE DATA
    // ==========================================

    private void initializeModules() {

        // DASHBOARD

        dashboard = new DashboardPanel();

        mainContent.add(
            dashboard,
            "Dashboard"
        );

        // STUDENT PROFILES

        studentPanel = new StudentPanel(
            this::refreshDashboard
        );

        mainContent.add(
            studentPanel,
            "Student Profiles"
        );

        // ACADEMIC RECORDS — visual student cards with photo and grade summary
        mainContent.add(new AcademicRecordsPanel(studentPanel), "Academic Records");

        // PERFORMANCE

        mainContent.add(
            new ModulePanel(
                "Student Performance",

                new String[]{
                    "Student ID",
                    "Name",
                    "GPA",
                    "Academic Status"
                },

                new Object[][]{
                    {
                        "2026001",
                        "Juan Dela Cruz",
                        "3.50",
                        "Good Standing"
                    },
                    {
                        "2026002",
                        "Maria Santos",
                        "3.75",
                        "Excellent"
                    },
                    {
                        "2026003",
                        "Pedro Reyes",
                        "2.75",
                        "Needs Monitoring"
                    }
                }
            ),

            "Performance"
        );

        // INSTRUCTORS

        mainContent.add(
            new ModulePanel(
                "Instructor Information",

                new String[]{
                    "Instructor ID",
                    "Instructor Name",
                    "Department"
                },

                new Object[][]{
                    {
                        "I001",
                        "Sample Instructor",
                        "Information Technology"
                    },
                    {
                        "I002",
                        "Sample Instructor 2",
                        "Computer Science"
                    }
                }
            ),

            "Instructors"
        );

        // REPORTS

        mainContent.add(
            new ModulePanel(
                "Reports",

                new String[]{
                    "Report ID",
                    "Report Name",
                    "Category",
                    "Status"
                },

                new Object[][]{
                    {
                        "R001",
                        "Student Academic Summary",
                        "Academic",
                        "Sample"
                    },
                    {
                        "R002",
                        "Performance Summary",
                        "Performance",
                        "Sample"
                    }
                }
            ),

            "Reports"
        );

        // SETTINGS

        mainContent.add(
            new ModulePanel(
                "Settings",

                new String[]{
                    "Setting",
                    "Description",
                    "Status"
                },

                new Object[][]{
                    {
                        "Application",
                        "Student Information System",
                        "Active"
                    },
                    {
                        "Database",
                        "MySQL Integration",
                        "Not Connected"
                    },
                    {
                        "Version",
                        "1.0",
                        "Prototype"
                    }
                }
            ),

            "Settings"
        );
    }

    // ==========================================
    // UPDATE DASHBOARD
    // ==========================================

    private void refreshDashboard() {

        if (dashboard != null
            && studentPanel != null) {

            dashboard.updateStatistics(
                studentPanel.getStudents()
            );
        }
    }



    // ==========================================
    // ANIMATED CARD + TABLE + MINI STAT COMPONENTS
    // Shared across student directory and module pages
    // ==========================================
    static class AcademicHoverCard extends RoundedPanel {
        private boolean hovered = false;
        private float hoverProgress = 0f;
        private Timer transition;
        AcademicHoverCard(int radius, Color fill) {
            super(radius, fill);
            transition = new Timer(16, e -> {
                hoverProgress += hovered ? 0.12f : -0.12f;
                hoverProgress = Math.max(0f, Math.min(1f, hoverProgress));
                repaint();
                if (hoverProgress == (hovered ? 1f : 0f)) transition.stop();
            });
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { hovered = true; transition.start(); }
                public void mouseExited(java.awt.event.MouseEvent e) { hovered = false; transition.start(); }
            });
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int lift = Math.round(hoverProgress * 3);
            g2.setColor(new Color(25, 38, 64, 13 + (int)(hoverProgress * 13)));
            g2.fillRoundRect(1, 5 - lift, getWidth()-2, getHeight()-6, 24, 24);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class StudentMiniStat extends JPanel {
        private final Color accent;
        private boolean hover = false;
        StudentMiniStat(String heading, String value, String hint, Color accent) {
            this.accent = accent;
            setOpaque(false);
            setLayout(new BorderLayout(6, 5));
            setBorder(new EmptyBorder(12, 15, 12, 15));
            JLabel cap = new JLabel(heading);
            cap.setFont(new Font("Segoe UI", Font.BOLD, 10));
            cap.setForeground(new Color(106, 119, 147));
            JLabel val = new JLabel(value);
            val.setFont(new Font("Segoe UI", Font.BOLD, 18));
            val.setForeground(accent);
            JLabel small = new JLabel(hint);
            small.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            small.setForeground(new Color(132, 144, 164));
            add(cap, BorderLayout.NORTH);
            add(val, BorderLayout.CENTER);
            add(small, BorderLayout.SOUTH);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { hover = true; repaint(); }
                public void mouseExited(java.awt.event.MouseEvent e) { hover = false; repaint(); }
            });
        }
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int lift = hover ? 2 : 0;
            g.setColor(new Color(20, 31, 55, hover ? 22 : 12));
            g.fillRoundRect(1, 5-lift, getWidth()-2, getHeight()-7, 18, 18);
            g.setColor(Color.WHITE);
            g.fillRoundRect(0, -lift, getWidth()-1, getHeight()-6, 18, 18);
            g.setColor(accent);
            g.fillRoundRect(0, -lift, 4, getHeight()-6, 7, 7);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    static class AcademicTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        private boolean alternating = true;
        AcademicTableCellRenderer() {
            setBorder(new EmptyBorder(0, 12, 0, 12));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean selected, boolean focus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, selected, focus, row, column);
            if (selected) {
                component.setBackground(new Color(225, 234, 255));
                component.setForeground(new Color(31, 48, 85));
            } else {
                component.setBackground(row % 2 == 0 ? Color.WHITE : new Color(249, 251, 254));
                component.setForeground(new Color(54, 65, 88));
            }
            setBorder(new EmptyBorder(0, 12, 0, 12));
            return component;
        }
    }

    static class AcademicPageIndicator extends JPanel {
        private float pulse = 0f;
        private boolean increasing = true;
        private final Timer pulseTimer;
        AcademicPageIndicator(String text) {
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));
            JLabel dot = new JLabel("●");
            dot.setForeground(new Color(67, 165, 127));
            JLabel label = new JLabel(text);
            label.setForeground(new Color(93, 119, 156));
            label.setFont(new Font("Segoe UI", Font.BOLD, 10));
            add(dot); add(label);
            pulseTimer = new Timer(60, e -> {
                pulse += increasing ? 0.08f : -0.08f;
                if (pulse >= 1f) increasing = false;
                if (pulse <= 0f) increasing = true;
                dot.setForeground(new Color(60, 130 + (int)(pulse*35), 105));
            });
            pulseTimer.start();
        }
    }

    static class AcademicSoftGlowPanel extends JPanel {
        private int phase = 0;
        private final Timer glowTimer;
        AcademicSoftGlowPanel() {
            setOpaque(false);
            glowTimer = new Timer(48, e -> { phase = (phase + 1) % 240; repaint(); });
            glowTimer.start();
        }
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int radius = 16 + phase % 12;
            g.setColor(new Color(76, 106, 181, 10 + phase % 8));
            g.fillRoundRect(0, 0, w, h, radius, radius);
            g.dispose();
        }
    }

    // ==========================================
    // PREMIUM ACADEMIC MODULE VISUALS
    // Shared animated hero banner and status decorations
    // ==========================================
    static class AcademicModuleHero extends JPanel {
        private final String title;
        private final Color accent;
        private int phase = 0;
        private final Timer motionTimer;
        AcademicModuleHero(String title, Color accent) {
            this.title = title;
            this.accent = accent;
            setOpaque(false);
            setPreferredSize(new Dimension(500, 104));
            motionTimer = new Timer(38, e -> { phase = (phase + 1) % 360; repaint(); });
            motionTimer.start();
        }
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g.setColor(new Color(19, 34, 66));
            g.fillRoundRect(0, 0, w, h, 24, 24);
            GradientPaint gradient = new GradientPaint(0, 0, accent, w, h, new Color(41, 59, 100));
            g.setPaint(gradient);
            g.fillRoundRect(w / 2, 0, w / 2, h, 24, 24);
            // Moving light ribbon
            int ribbonX = (int) ((phase / 360.0) * (w + 120)) - 120;
            g.setColor(new Color(255, 255, 255, 17));
            g.fillRoundRect(ribbonX, -18, 95, h + 36, 30, 30);
            // Decorative academic rings and sparkles
            g.setColor(new Color(255, 255, 255, 30));
            int ring = 30 + (phase % 34);
            g.drawOval(w - 116 - ring / 2, h / 2 - ring / 2, ring, ring);
            g.drawOval(w - 92 - ring / 3, h / 2 - ring / 3, ring / 2, ring / 2);
            for (int i = 0; i < 14; i++) {
                int x = (i * 67 + phase * 2) % Math.max(1, w);
                int y = (i * 17 + phase / 3) % Math.max(1, h);
                g.setColor(new Color(225, 235, 255, 30 + (i % 3) * 12));
                g.fillOval(x, y, 3 + i % 3, 3 + i % 3);
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g.drawString("CAMPUS OPERATIONS  /  " + title.toUpperCase(), 20, 34);
            g.setColor(new Color(222, 231, 251));
            g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g.drawString("Organized records • Clear insights • Academic progress", 20, 57);
            g.setColor(new Color(255, 255, 255, 70));
            g.fillRoundRect(20, 76, Math.max(10, w - 40), 3, 3, 3);
            g.setColor(new Color(235, 242, 255, 190));
            int progressWidth = (int) ((w - 40) * (0.22 + 0.62 * (0.5 + 0.5 * Math.sin(phase / 35.0))));
            g.fillRoundRect(20, 76, progressWidth, 3, 3, 3);
            g.dispose();
        }
    }

    static class AnimatedAcademicBadge extends JLabel {
        private int phase = 0;
        private final Timer pulse;
        AnimatedAcademicBadge(String label, Color accent) {
            super(label);
            setOpaque(true);
            setBackground(new Color(239, 243, 255));
            setForeground(accent);
            setFont(new Font("Segoe UI", Font.BOLD, 10));
            setBorder(new EmptyBorder(8, 11, 8, 11));
            pulse = new Timer(60, e -> { phase++; setBorder(new EmptyBorder(8, 11 + (phase % 8 == 0 ? 1 : 0), 8, 11)); });
            pulse.start();
        }
    }

    static class GradientAcademicSurface extends JPanel {
        private int phase = 0;
        private final Timer timer = new Timer(45, e -> { phase = (phase + 1) % 360; repaint(); });
        GradientAcademicSurface() { setOpaque(false); timer.start(); }
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g.setColor(new Color(24, 39, 72)); g.fillRoundRect(0, 0, w, h, 22, 22);
            g.setPaint(new GradientPaint((float)(w * (0.5 + 0.4 * Math.sin(phase / 50.0))), 0,
                    new Color(78, 107, 183), w, h, new Color(41, 61, 107)));
            g.fillRoundRect(w / 3, 0, w * 2 / 3, h, 22, 22);
            for (int i = 0; i < 20; i++) {
                int x = (i * 43 + phase) % Math.max(1, w);
                int y = (i * 19 + phase / 2) % Math.max(1, h);
                g.setColor(new Color(210, 225, 255, 35));
                g.fillOval(x, y, 3, 3);
            }
            g.dispose();
        }
    }

    static class FadePanel extends JPanel {
        private int fadeAlpha = 0;
        FadePanel(CardLayout layout) { super(layout); setOpaque(true); setBackground(Color.WHITE); }
        void startFade() {
            fadeAlpha = 135; repaint();
            Timer timer = new Timer(18, null);
            timer.addActionListener(e -> { fadeAlpha = Math.max(0, fadeAlpha - 15); repaint(); if (fadeAlpha == 0) timer.stop(); });
            timer.start();
        }
        @Override public void paint(Graphics g) {
            super.paint(g);
            if (fadeAlpha > 0) { Graphics2D g2 = (Graphics2D) g.create(); g2.setComposite(AlphaComposite.SrcOver); g2.setColor(new Color(255,255,255,fadeAlpha)); g2.fillRect(0,0,getWidth(),getHeight()); g2.dispose(); }
        }
    }

    // Modern UI helpers: rounded surfaces, buttons and icon-style navigation labels
    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color surface;
        RoundedPanel(int radius, Color surface) {
            this.radius = radius; this.surface = surface;
            setOpaque(false);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(surface);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(new Color(226, 232, 243));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }


    static class RoundedButton extends JButton {
        private final int radius;
        private boolean hover = false;
        RoundedButton(String text, int radius) {
            super(text); this.radius = radius;
            setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
            setForeground(Color.WHITE); setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { hover = true; repaint(); }
                public void mouseExited(java.awt.event.MouseEvent e) { hover = false; repaint(); }
            });
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color base = getBackground();
            g2.setColor(hover ? base.brighter() : base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose(); super.paintComponent(g);
        }
    }

    private static String navLabel(String item) {
        switch(item) {
            case "Dashboard": return "◈   Dashboard";
            case "Student Profiles": return "♙   Student Profiles";
            case "Academic Records": return "▤   Academic Records";
            case "Performance": return "◉   Performance";
            case "Instructors": return "♧   Instructors";
            case "Reports": return "▧   Reports";
            case "Settings": return "⚙   Settings";
            default: return "↪   Log Out";
        }
    }

    // ==========================================
    // LOGIN SCREEN
    // ==========================================
    static class LoginFrame extends JFrame {
        private final AnimatedLoginBackground background = new AnimatedLoginBackground();
        LoginFrame() {
            setTitle("SIS | Secure Sign In");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setMinimumSize(new Dimension(740, 560));
            setSize(900, 650);
            setLocationRelativeTo(null);
            setResizable(true);

            background.setLayout(new GridBagLayout());
            background.setBorder(new EmptyBorder(28, 28, 28, 28));

            RoundedPanel card = new RoundedPanel(32, new Color(255,255,255,245));
            card.setLayout(new BorderLayout(0, 0));
            card.setBorder(new EmptyBorder(0,0,0,0));
            card.setPreferredSize(new Dimension(440, 510));

            JPanel header = new JPanel();
            header.setOpaque(false);
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            header.setBorder(new EmptyBorder(34, 36, 22, 36));

            JLabel emblem = new JLabel("SIS");
            emblem.setOpaque(true);
            emblem.setBackground(new Color(90, 112, 215));
            emblem.setForeground(Color.WHITE);
            emblem.setFont(new Font("Segoe UI", Font.BOLD, 25));
            emblem.setHorizontalAlignment(SwingConstants.CENTER);
            emblem.setBorder(new EmptyBorder(11, 19, 11, 19));
            emblem.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel heading = new JLabel("Welcome back");
            heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
            heading.setForeground(NAVY);
            heading.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel subtitle = new JLabel("Sign in to your student portal");
            subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            subtitle.setForeground(GRAY);
            subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            header.add(emblem);
            header.add(Box.createVerticalStrut(19));
            header.add(heading);
            header.add(Box.createVerticalStrut(7));
            header.add(subtitle);

            JPanel form = new JPanel();
            form.setOpaque(false);
            form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
            form.setBorder(new EmptyBorder(0, 38, 32, 38));

            JLabel userLabel = fieldLabel("USERNAME");
            JTextField user = new JTextField();
            styleLoginField(user);
            JLabel passLabel = fieldLabel("PASSWORD");
            JPasswordField pass = new JPasswordField();
            styleLoginField(pass);

            JCheckBox showPassword = new JCheckBox("Show password");
            showPassword.setOpaque(false);
            showPassword.setForeground(GRAY);
            showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            showPassword.setFocusPainted(false);
            char defaultEcho = pass.getEchoChar();
            showPassword.addActionListener(e -> pass.setEchoChar(showPassword.isSelected() ? (char)0 : defaultEcho));

            JLabel status = new JLabel(" ");
            status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            status.setForeground(new Color(190, 60, 70));
            status.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton login = new RoundedButton("SIGN IN   →", 20);
            login.setBackground(BLUE);
            login.setFont(new Font("Segoe UI", Font.BOLD, 14));
            login.setPreferredSize(new Dimension(0, 48));
            login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            login.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel demo = new JLabel("DEMO ACCOUNT  ·  admin / admin123");
            demo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            demo.setForeground(new Color(130, 140, 160));
            demo.setAlignmentX(Component.CENTER_ALIGNMENT);

            form.add(userLabel); form.add(Box.createVerticalStrut(7));
            form.add(user); form.add(Box.createVerticalStrut(18));
            form.add(passLabel); form.add(Box.createVerticalStrut(7));
            form.add(pass); form.add(Box.createVerticalStrut(5));
            showPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
            form.add(showPassword); form.add(Box.createVerticalStrut(7));
            form.add(status); form.add(Box.createVerticalStrut(8));
            form.add(login); form.add(Box.createVerticalStrut(17));
            form.add(demo);

            login.addActionListener(e -> {
                String enteredUser = user.getText().trim();
                String enteredPassword = new String(pass.getPassword());
                if (enteredPassword.length() < 6) {
                    status.setText("Password must be at least 6 characters.");
                    pass.requestFocusInWindow();
                    return;
                }
                if (enteredUser.equals("admin") && enteredPassword.equals("admin123")) {
                    status.setForeground(new Color(35, 145, 90));
                    status.setText("Access granted. Opening dashboard...");
                    login.setEnabled(false);
                    Timer delay = new Timer(450, ev -> { dispose(); new Main(); });
                    delay.setRepeats(false); delay.start();
                } else {
                    status.setText("Incorrect username or password. Try the demo account.");
                    pass.setText("");
                    pass.requestFocusInWindow();
                }
            });
            pass.addActionListener(e -> login.doClick());
            user.addActionListener(e -> pass.requestFocusInWindow());

            card.add(header, BorderLayout.NORTH);
            card.add(form, BorderLayout.CENTER);
            background.add(card);
            setContentPane(background);
            setVisible(true);
            background.startAnimation();
        }

        private static JLabel fieldLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Segoe UI", Font.BOLD, 11));
            label.setForeground(new Color(88, 101, 130));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            return label;
        }
        private static void styleLoginField(JTextField field) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            field.setForeground(TEXT);
            field.setBackground(new Color(248, 250, 255));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 240), 1),
                new EmptyBorder(12, 13, 12, 13)));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            field.setPreferredSize(new Dimension(300, 48));
        }
    }

    static class AnimatedLoginBackground extends JPanel {
        private float phase = 0f;
        private Timer animation;
        AnimatedLoginBackground() { setOpaque(true); }
        void startAnimation() {
            animation = new Timer(35, e -> { phase += 0.012f; if (phase > 1f) phase = 0f; repaint(); });
            animation.start();
        }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, new Color(18, 29, 57), getWidth(), getHeight(), new Color(91, 104, 191)));
            g2.fillRect(0,0,getWidth(),getHeight());
            // Soft, slowly drifting glow circles behind the login card
            int shift = (int)(Math.sin(phase * Math.PI * 2) * 36);
            g2.setColor(new Color(142, 155, 255, 38));
            g2.fillOval(-130 + shift, -150, 390, 390);
            g2.setColor(new Color(168, 130, 255, 34));
            g2.fillOval(getWidth()-290-shift, getHeight()-250, 420, 420);
            g2.setColor(new Color(255,255,255,24));
            for (int i=0; i<34; i++) {
                int x = (int)((i * 97L + (phase * 120)) % Math.max(1,getWidth()));
                int y = (i * 61) % Math.max(1,getHeight());
                int r = 2 + (i % 3);
                g2.fillOval(x, y, r, r);
            }
            g2.dispose();
        }
    }

    // ==========================================
    // PART 8 - MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}


// test append

/*
 * EasyparkServerView.java
 */

package easyparkserver;



import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;



/**
 * The application's main frame.
 */
public class EasyparkServerView extends FrameView {

    String SerialForwarderIPAddress;
    Integer SerialForwarderPort;

    String fromServer, fromUser;
    String statusLogDisplayText = "";
    Integer numFreeLots, numMaxLots;

    nodeBatteryChart panel;
	nodeBatteryChart.DataGenerator chartGen;

    public static String[] serverTokens = new String[31];
	String[] tableColumnTitles = {"Node ID", "Lot Status", 
				"Raw sensor reading", "Battery level", "Last update", "PDR"};

	ImageIcon iconBatt20 = new ImageIcon(getClass().getResource
				("/easyparkserver/resources/battery_discharging_020.png"));
	ImageIcon iconBatt40 = new ImageIcon(getClass().getResource
				("/easyparkserver/resources/battery_discharging_040.png"));
	ImageIcon iconBatt60 = new ImageIcon(getClass().getResource
				("/easyparkserver/resources/battery_discharging_060.png"));
	ImageIcon iconBatt80 = new ImageIcon(getClass().getResource
				("/easyparkserver/resources/battery_discharging_080.png"));
	ImageIcon iconBatt100 = new ImageIcon(getClass().getResource
				("/easyparkserver/resources/battery_discharging_100.png"));

    Socket clientSocket = null;
    PrintWriter socketOut = null;
    BufferedReader socketIn = null;

	Thread updateThread;

    JFrame mainFrame = EasyparkServerApp.getApplication().getMainFrame();

    public EasyparkServerView(SingleFrameApplication app) {
        super(app);

        initComponents();

        btn_end.setEnabled(false);
        statusMessageLabel.setText("Disconnected");
        carparkComboBox.setSelectedIndex(1);
        levelComboBox.setSelectedIndex(2);
        progressBar.setVisible(false);
        statusPanel.setVisible(true);
		textarea_output.setVisible(false);

        mainFrame.getContentPane().setPreferredSize(new Dimension(1000,600));
        mainFrame.setSize(1000, 620);
        mainFrame.setResizable(false);
        mainFrame.pack();
        

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }
    

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            aboutBox = new EasyparkServerAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        EasyparkServerApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        displayPanel = new javax.swing.JPanel();
        carparkDetailsPanel = new javax.swing.JPanel();
        carparkDetailsLabel = new javax.swing.JLabel();
        carparkComboBox = new javax.swing.JComboBox();
        levelComboBox = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        carparkAddressTextArea = new javax.swing.JTextArea();
        javax.swing.JLabel label_main_IP_address = new javax.swing.JLabel();
        tf_main_IP_addr = new javax.swing.JTextField();
        javax.swing.JLabel label_main_IP_port = new javax.swing.JLabel();
        tf_main_IP_port = new javax.swing.JTextField();
        btn_start = new javax.swing.JButton();
        btn_end = new javax.swing.JButton();
        availLotsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        freeLotsCurrent = new javax.swing.JLabel();
        freeLotsMax = new javax.swing.JLabel();
        logoPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        displayTabbedPane = new javax.swing.JTabbedPane();
        carparkLayeredPane = new javax.swing.JLayeredPane();
        parkingLot1 = new javax.swing.JLabel();
        parkingLot2 = new javax.swing.JLabel();
        parkingLot3 = new javax.swing.JLabel();
        parkingLot4 = new javax.swing.JLabel();
        parkingLot5 = new javax.swing.JLabel();
        carparkBitMapLabel = new javax.swing.JLabel();
        batteryGraphPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        adminTable = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        statusMessageLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea_output = new javax.swing.JTextArea();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setMaximumSize(new java.awt.Dimension(1102, 600));
        mainPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1102, 590));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(easyparkserver.EasyparkServerApp.class).getContext().getResourceMap(EasyparkServerView.class);
        displayPanel.setBackground(resourceMap.getColor("displayPanel.background")); // NOI18N
        displayPanel.setName("displayPanel"); // NOI18N
        displayPanel.setPreferredSize(new java.awt.Dimension(1062, 600));

        carparkDetailsPanel.setBackground(resourceMap.getColor("carparkDetailsPanel.background")); // NOI18N
        carparkDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("carparkDetailsPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("carparkDetailsPanel.border.titleFont"))); // NOI18N
        carparkDetailsPanel.setName("carparkDetailsPanel"); // NOI18N
        carparkDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        carparkDetailsLabel.setText(resourceMap.getString("carparkDetailsLabel.text")); // NOI18N
        carparkDetailsLabel.setName("carparkDetailsLabel"); // NOI18N
        carparkDetailsPanel.add(carparkDetailsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        carparkComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select carpark...", "SysEng Pte Ltd", "Carpark X", "Carpark Y", "Carpark Z" }));
        carparkComboBox.setName("carparkComboBox"); // NOI18N
        carparkComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carparkComboBoxActionPerformed(evt);
            }
        });
        carparkDetailsPanel.add(carparkComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 193, -1));

        levelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select level...", "All", "Ground", "B1", "B2", "B3" }));
        levelComboBox.setName("levelComboBox"); // NOI18N
        levelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelComboBoxActionPerformed(evt);
            }
        });
        carparkDetailsPanel.add(levelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 193, -1));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        carparkAddressTextArea.setColumns(12);
        carparkAddressTextArea.setEditable(false);
        carparkAddressTextArea.setRows(5);
        carparkAddressTextArea.setText(resourceMap.getString("carparkAddressTextArea.text")); // NOI18N
        carparkAddressTextArea.setName("carparkAddressTextArea"); // NOI18N
        jScrollPane2.setViewportView(carparkAddressTextArea);

        carparkDetailsPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 199, 60));

        label_main_IP_address.setText(resourceMap.getString("label_main_IP_address.text")); // NOI18N
        label_main_IP_address.setName("label_main_IP_address"); // NOI18N
        carparkDetailsPanel.add(label_main_IP_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        tf_main_IP_addr.setText(resourceMap.getString("tf_main_IP_addr.text")); // NOI18N
        tf_main_IP_addr.setName("tf_main_IP_addr"); // NOI18N
        carparkDetailsPanel.add(tf_main_IP_addr, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 84, -1));

        label_main_IP_port.setText(resourceMap.getString("label_main_IP_port.text")); // NOI18N
        label_main_IP_port.setName("label_main_IP_port"); // NOI18N
        carparkDetailsPanel.add(label_main_IP_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        tf_main_IP_port.setText(resourceMap.getString("tf_main_IP_port.text")); // NOI18N
        tf_main_IP_port.setName("tf_main_IP_port"); // NOI18N
        carparkDetailsPanel.add(tf_main_IP_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 59, -1));

        btn_start.setText(resourceMap.getString("btn_start.text")); // NOI18N
        btn_start.setName("btn_start"); // NOI18N
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });
        carparkDetailsPanel.add(btn_start, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 100, -1));

        btn_end.setText(resourceMap.getString("btn_end.text")); // NOI18N
        btn_end.setName("btn_end"); // NOI18N
        btn_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_endActionPerformed(evt);
            }
        });
        carparkDetailsPanel.add(btn_end, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 100, -1));

        availLotsPanel.setBackground(resourceMap.getColor("availLotsPanel.background")); // NOI18N
        availLotsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("availLotsPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("availLotsPanel.border.titleFont"))); // NOI18N
        availLotsPanel.setName("availLotsPanel"); // NOI18N
        availLotsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        availLotsPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 27, 79));

        freeLotsCurrent.setFont(resourceMap.getFont("freeLotsCurrent.font")); // NOI18N
        freeLotsCurrent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        freeLotsCurrent.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        freeLotsCurrent.setName("freeLotsCurrent"); // NOI18N
        availLotsPanel.add(freeLotsCurrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 80, 79));

        freeLotsMax.setFont(resourceMap.getFont("freeLotsMax.font")); // NOI18N
        freeLotsMax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        freeLotsMax.setText(resourceMap.getString("freeLotsMax.text")); // NOI18N
        freeLotsMax.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        freeLotsMax.setName("freeLotsMax"); // NOI18N
        availLotsPanel.add(freeLotsMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 80, 79));

        logoPanel.setBackground(resourceMap.getColor("logoPanel.background")); // NOI18N
        logoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logoPanel.setName("logoPanel"); // NOI18N

        titleLabel.setFont(resourceMap.getFont("titleLabel.font")); // NOI18N
        titleLabel.setText(resourceMap.getString("titleLabel.text")); // NOI18N
        titleLabel.setName("titleLabel"); // NOI18N

        subtitleLabel.setFont(resourceMap.getFont("title2Label.font")); // NOI18N
        subtitleLabel.setText(resourceMap.getString("title2Label.text")); // NOI18N
        subtitleLabel.setName("title2Label"); // NOI18N

        javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
        logoPanel.setLayout(logoPanelLayout);
        logoPanelLayout.setHorizontalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoPanelLayout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addComponent(subtitleLabel)
                .addContainerGap())
        );
        logoPanelLayout.setVerticalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoPanelLayout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subtitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        displayTabbedPane.setBackground(resourceMap.getColor("displayTabbedPane.background")); // NOI18N
        displayTabbedPane.setName("displayTabbedPane"); // NOI18N

        carparkLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        carparkLayeredPane.setName("carparkLayeredPane"); // NOI18N

        parkingLot1.setBackground(resourceMap.getColor("parkingLot1.background")); // NOI18N
        parkingLot1.setFont(resourceMap.getFont("parkingLot1.font")); // NOI18N
        parkingLot1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        parkingLot1.setText(resourceMap.getString("parkingLot1.text")); // NOI18N
        parkingLot1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        parkingLot1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("parkingLot1.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("parkingLot1.border.titleFont"))); // NOI18N
        parkingLot1.setName("parkingLot1"); // NOI18N
        parkingLot1.setOpaque(true);
        parkingLot1.setBounds(60, 130, 110, 160);
        carparkLayeredPane.add(parkingLot1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parkingLot2.setBackground(resourceMap.getColor("parkingLot2.background")); // NOI18N
        parkingLot2.setFont(resourceMap.getFont("parkingLot2.font")); // NOI18N
        parkingLot2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        parkingLot2.setText(resourceMap.getString("parkingLot2.text")); // NOI18N
        parkingLot2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        parkingLot2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("parkingLot2.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("parkingLot1.border.titleFont"))); // NOI18N
        parkingLot2.setName("parkingLot2"); // NOI18N
        parkingLot2.setOpaque(true);
        parkingLot2.setBounds(210, 130, 110, 160);
        carparkLayeredPane.add(parkingLot2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parkingLot3.setBackground(resourceMap.getColor("parkingLot3.background")); // NOI18N
        parkingLot3.setFont(resourceMap.getFont("parkingLot3.font")); // NOI18N
        parkingLot3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        parkingLot3.setText(resourceMap.getString("parkingLot3.text")); // NOI18N
        parkingLot3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        parkingLot3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("parkingLot3.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("parkingLot1.border.titleFont"))); // NOI18N
        parkingLot3.setName("parkingLot3"); // NOI18N
        parkingLot3.setOpaque(true);
        parkingLot3.setBounds(500, 130, 110, 160);
        carparkLayeredPane.add(parkingLot3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parkingLot4.setBackground(resourceMap.getColor("parkingLot4.background")); // NOI18N
        parkingLot4.setFont(resourceMap.getFont("parkingLot4.font")); // NOI18N
        parkingLot4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        parkingLot4.setText(resourceMap.getString("parkingLot4.text")); // NOI18N
        parkingLot4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        parkingLot4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("parkingLot4.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("parkingLot1.border.titleFont"))); // NOI18N
        parkingLot4.setName("parkingLot4"); // NOI18N
        parkingLot4.setOpaque(true);
        parkingLot4.setBounds(350, 130, 110, 160);
        carparkLayeredPane.add(parkingLot4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parkingLot5.setBackground(resourceMap.getColor("parkingLot5.background")); // NOI18N
        parkingLot5.setFont(resourceMap.getFont("parkingLot5.font")); // NOI18N
        parkingLot5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        parkingLot5.setText(resourceMap.getString("parkingLot5.text")); // NOI18N
        parkingLot5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        parkingLot5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("parkingLot5.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("parkingLot1.border.titleFont"))); // NOI18N
        parkingLot5.setName("parkingLot5"); // NOI18N
        parkingLot5.setOpaque(true);
        parkingLot5.setBounds(650, 130, 110, 160);
        carparkLayeredPane.add(parkingLot5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        carparkBitMapLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        carparkBitMapLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/easyparkserver/resources/sysengcropped.png"))); // NOI18N
        carparkBitMapLabel.setText(resourceMap.getString("carparkBitMapLabel.text")); // NOI18N
        carparkBitMapLabel.setName("carparkBitMapLabel"); // NOI18N
        carparkBitMapLabel.setBounds(0, 0, 780, 410);
        carparkLayeredPane.add(carparkBitMapLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        displayTabbedPane.addTab(resourceMap.getString("carparkLayeredPane.TabConstraints.tabTitle"), carparkLayeredPane); // NOI18N

        batteryGraphPanel.setBackground(resourceMap.getColor("batteryGraphPanel.background")); // NOI18N
        batteryGraphPanel.setName("batteryGraphPanel"); // NOI18N

        javax.swing.GroupLayout batteryGraphPanelLayout = new javax.swing.GroupLayout(batteryGraphPanel);
        batteryGraphPanel.setLayout(batteryGraphPanelLayout);
        batteryGraphPanelLayout.setHorizontalGroup(
            batteryGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 778, Short.MAX_VALUE)
        );
        batteryGraphPanelLayout.setVerticalGroup(
            batteryGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        displayTabbedPane.addTab(resourceMap.getString("batteryGraphPanel.TabConstraints.tabTitle"), batteryGraphPanel); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        adminTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Node ID", "Lot Status", "Raw sensor reading", "Battery level", "Last update", "PDR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        adminTable.setName("adminTable"); // NOI18N
        jScrollPane3.setViewportView(adminTable);
        adminTable.getColumnModel().getColumn(0).setResizable(false);
        adminTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        adminTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("adminTable.columnModel.title5")); // NOI18N
        adminTable.getColumnModel().getColumn(1).setResizable(false);
        adminTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        adminTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("adminTable.columnModel.title4")); // NOI18N
        adminTable.getColumnModel().getColumn(2).setResizable(false);
        adminTable.getColumnModel().getColumn(2).setPreferredWidth(350);
        adminTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("adminTable.columnModel.title0")); // NOI18N
        adminTable.getColumnModel().getColumn(3).setResizable(false);
        adminTable.getColumnModel().getColumn(3).setPreferredWidth(250);
        adminTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("adminTable.columnModel.title1")); // NOI18N
        adminTable.getColumnModel().getColumn(4).setResizable(false);
        adminTable.getColumnModel().getColumn(4).setPreferredWidth(225);
        adminTable.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("adminTable.columnModel.title2")); // NOI18N
        adminTable.getColumnModel().getColumn(5).setResizable(false);
        adminTable.getColumnModel().getColumn(5).setPreferredWidth(70);
        adminTable.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("adminTable.columnModel.title3")); // NOI18N

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(availLotsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carparkDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(displayTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(displayPanelLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(displayPanelLayout.createSequentialGroup()
                        .addComponent(logoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(availLotsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(carparkDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                    .addGroup(displayPanelLayout.createSequentialGroup()
                        .addComponent(displayTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        menuBar.setMaximumSize(new java.awt.Dimension(1090, 32769));
        menuBar.setMinimumSize(new java.awt.Dimension(0, 0));
        menuBar.setName("menuBar"); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(1090, 21));

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(easyparkserver.EasyparkServerApp.class).getContext().getActionMap(EasyparkServerView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setBackground(resourceMap.getColor("statusPanel.background")); // NOI18N
        statusPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        statusPanel.setMaximumSize(new java.awt.Dimension(1102, 40));
        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(1102, 30));
        statusPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N
        statusPanel.add(statusAnimationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 39, -1, -1));

        progressBar.setBackground(resourceMap.getColor("progressBar.background")); // NOI18N
        progressBar.setName("progressBar"); // NOI18N
        statusPanel.add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(879, 0, 0, 30));

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N
        statusPanel.add(statusMessageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 350, 30));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        textarea_output.setColumns(15);
        textarea_output.setEditable(false);
        textarea_output.setLineWrap(true);
        textarea_output.setRows(1);
        textarea_output.setToolTipText(resourceMap.getString("textarea_output.toolTipText")); // NOI18N
        textarea_output.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        textarea_output.setName("textarea_output"); // NOI18N
        jScrollPane1.setViewportView(textarea_output);

        statusPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(863, 0, 0, 29));

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_endActionPerformed
        closeConnection();
		chartGen.stop();
        btn_start.setEnabled(true);
        btn_end.setEnabled(false);
    }//GEN-LAST:event_btn_endActionPerformed

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        SerialForwarderIPAddress = tf_main_IP_addr.getText();
        SerialForwarderPort = Integer.parseInt(tf_main_IP_port.getText());
        getSocketConnection();
        showChart();
		chartGen.start();
    }//GEN-LAST:event_btn_startActionPerformed

    private void carparkComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carparkComboBoxActionPerformed
        updateCarparkDetails(carparkComboBox.getSelectedItem().toString(), levelComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_carparkComboBoxActionPerformed

    private void levelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelComboBoxActionPerformed
        updateCarparkDetails(carparkComboBox.getSelectedItem().toString(), levelComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_levelComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable adminTable;
    private javax.swing.JPanel availLotsPanel;
    private javax.swing.JPanel batteryGraphPanel;
    private javax.swing.JButton btn_end;
    private javax.swing.JButton btn_start;
    private javax.swing.JTextArea carparkAddressTextArea;
    private javax.swing.JLabel carparkBitMapLabel;
    private javax.swing.JComboBox carparkComboBox;
    private javax.swing.JLabel carparkDetailsLabel;
    private javax.swing.JPanel carparkDetailsPanel;
    private javax.swing.JLayeredPane carparkLayeredPane;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JTabbedPane displayTabbedPane;
    private javax.swing.JLabel freeLotsCurrent;
    private javax.swing.JLabel freeLotsMax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox levelComboBox;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel parkingLot1;
    private javax.swing.JLabel parkingLot2;
    private javax.swing.JLabel parkingLot3;
    private javax.swing.JLabel parkingLot4;
    private javax.swing.JLabel parkingLot5;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JTextArea textarea_output;
    private javax.swing.JTextField tf_main_IP_addr;
    private javax.swing.JTextField tf_main_IP_port;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;


    void getSocketConnection(){
        try {
            clientSocket = new Socket(SerialForwarderIPAddress, SerialForwarderPort);
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            btn_start.setEnabled(false);
            btn_end.setEnabled(true);
            statusMessageLabel.setText("Connected to " + SerialForwarderIPAddress);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + SerialForwarderIPAddress);
            statusMessageLabel.setText("Don't know about host: " + SerialForwarderIPAddress);
            //System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + SerialForwarderIPAddress);
            statusMessageLabel.setText("Couldn't get I/O for the connection to: " + SerialForwarderIPAddress);
            //System.exit(1);
        } 

        Runnable mainLoop = new Runnable() {
            public void run(){
                try {
                    while ((fromServer = socketIn.readLine()) != null) {
                        addStatusMessage(fromServer);
                        updateLotStatus(fromServer);
						updateTable();
                        System.out.println("Received: " + fromServer);
                    } // end while
                }
                catch (IOException ex) {
                }
            }
        };
        updateThread = new Thread(mainLoop);
        updateThread.start();
    }//end getConnection()


    /**
     * Close the socket connection and exit the application.
     */
    void closeConnection() {
        try {
			updateThread.stop();
            socketOut.close();
            socketIn.close();
            clientSocket.close();
            statusMessageLabel.setText("Disconnected");
        } catch (IOException ex) {
            System.err.println("Problem closing sockets!");
            statusMessageLabel.setText("Problem closing sockets!");
        }
    }//end closeConnection()


    /**
     * Print the raw output received from the server into the text area.
     */
    public void addStatusMessage(String newMessageLine){
        statusLogDisplayText += newMessageLine + "\n";
        //textarea_output.setText(statusLogDisplayText);
        //textarea_output.setCaretPosition(textarea_output.getDocument().getLength());

    }


    /**
     * Update carpark details such as address, number of levels, number of lots
     * per level and total, bitmap image, etc.
     */
    public void updateCarparkDetails(String carparkName, String carparkLevel){
        if (carparkName.matches("SysEng Pte Ltd")){
            carparkAddressTextArea.setText("2 Kaki Bukit Place\n"
					+ "#05-00 Tritech Building\nSingapore 416180.");
            tf_main_IP_addr.setText("127.0.0.1");
            tf_main_IP_port.setText("4444");
            if (carparkLevel.matches("Ground")){
                freeLotsMax.setText("5");
                freeLotsCurrent.setText(freeLotsMax.getText());
            }
        }
    }


    public void updateLotStatus(String lotToken){
        serverTokens = lotToken.split(",");
        numFreeLots = 0;
        numMaxLots = Integer.parseInt(serverTokens[serverTokens.length-6]);
        switch (Integer.parseInt(serverTokens[2])){
            case 0: parkingLot1.setBackground(Color.getHSBColor(146, 231, 192)); numFreeLots++; break;
            case 1: parkingLot1.setBackground(Color.getHSBColor(0,163,203)); break;
            case 2: parkingLot1.setBackground(Color.gray.brighter()); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[8])){
            case 0: parkingLot2.setBackground(Color.getHSBColor(146,231,192)); numFreeLots++; break;
            case 1: parkingLot2.setBackground(Color.getHSBColor(0,163,203)); break;
            case 2: parkingLot2.setBackground(Color.gray.brighter()); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[14])){
            case 0: parkingLot3.setBackground(Color.getHSBColor(146,231,192)); numFreeLots++; break;
            case 1: parkingLot3.setBackground(Color.getHSBColor(0,163,203));  break;
            case 2: parkingLot3.setBackground(Color.gray.brighter()); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[20])){
            case 0: parkingLot4.setBackground(Color.getHSBColor(146,231,192)); numFreeLots++; break;
            case 1: parkingLot4.setBackground(Color.getHSBColor(0,163,203)); break;
            case 2: parkingLot4.setBackground(Color.gray.brighter()); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[26])){
            case 0: parkingLot5.setBackground(Color.getHSBColor(146,231,192)); numFreeLots++; break;
            case 1: parkingLot5.setBackground(Color.getHSBColor(0,163,203)); break;
            case 2: parkingLot5.setBackground(Color.gray.brighter()); break;
            default: break;
        }

        switch (categorizeBatt(4)){
            case 1: parkingLot1.setIcon(iconBatt20); break;
            case 2: parkingLot1.setIcon(iconBatt40); break;
            case 3: parkingLot1.setIcon(iconBatt60); break;
            case 4: parkingLot1.setIcon(iconBatt80); break;
            case 5: parkingLot1.setIcon(iconBatt100); break;
            default: break;
        }
        switch (categorizeBatt(10)){
            case 1: parkingLot2.setIcon(iconBatt20); break;
            case 2: parkingLot2.setIcon(iconBatt40); break;
            case 3: parkingLot2.setIcon(iconBatt60); break;
            case 4: parkingLot2.setIcon(iconBatt80); break;
            case 5: parkingLot2.setIcon(iconBatt100); break;
            default: break;
        }
        switch (categorizeBatt(16)){
            case 1: parkingLot3.setIcon(iconBatt20); break;
            case 2: parkingLot3.setIcon(iconBatt40); break;
            case 3: parkingLot3.setIcon(iconBatt60); break;
            case 4: parkingLot3.setIcon(iconBatt80); break;
            case 5: parkingLot3.setIcon(iconBatt100); break;
            default: break;
        }
        switch (categorizeBatt(22)){
            case 1: parkingLot4.setIcon(iconBatt20); break;
            case 2: parkingLot4.setIcon(iconBatt40); break;
            case 3: parkingLot4.setIcon(iconBatt60); break;
            case 4: parkingLot4.setIcon(iconBatt80); break;
            case 5: parkingLot4.setIcon(iconBatt100); break;
            default: break;
        }
        switch (categorizeBatt(28)){
            case 1: parkingLot5.setIcon(iconBatt20); break;
            case 2: parkingLot5.setIcon(iconBatt40); break;
            case 3: parkingLot5.setIcon(iconBatt60); break;
            case 4: parkingLot5.setIcon(iconBatt80); break;
            case 5: parkingLot5.setIcon(iconBatt100); break;
            default: break;
        }

        freeLotsCurrent.setText(Integer.toString(numFreeLots));
        freeLotsMax.setText(Integer.toString(numMaxLots));
    }


	public int categorizeBatt(int i){
		int batteryLevel = Integer.parseInt(serverTokens[i]);
		if ((batteryLevel <= 100) && (batteryLevel > 80)){
			return 5;
		}
		else if((batteryLevel <= 80) && (batteryLevel > 60)) {
			return 4;
		}
		else if((batteryLevel <= 60) && (batteryLevel > 40)) {
			return 3;
		}
		else if((batteryLevel <= 40) && (batteryLevel > 20)) {
			return 2;
		}
		else{
			return 1;
 		}
	}


	public void updateTable(){
		int k = 1;
		for (int j = 0; j<=4; j++){
			for (int i = 0; i<= 5; i++){
					adminTable.getModel().setValueAt(serverTokens[k],j,i);
					k++;
			}
		}
	}


    public void showChart(){
		panel =  new nodeBatteryChart(30000);
        panel.setBounds(0, 0, 769, 410);
        panel.setVisible(true);
        batteryGraphPanel.add(panel);
		chartGen = panel.new DataGenerator(1000);
    }
}
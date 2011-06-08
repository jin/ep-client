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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;





/**
 * The application's main frame.
 */
public class EasyparkServerView extends FrameView {

    String SerialForwarderIPAddress;
    Integer SerialForwarderPort;
    String fromServer;
    String fromUser;
    String statusLogDisplayText = "";
    Integer numFreeLots, numMaxLots;
    public static String[] serverTokens;

    Socket clientSocket = null;
    PrintWriter socketOut = null;
    BufferedReader socketIn = null;

    JFrame mainFrame = EasyparkServerApp.getApplication().getMainFrame();

    public EasyparkServerView(SingleFrameApplication app) {
        super(app);

        initComponents();

        btn_end.setEnabled(false);
        statusMessageLabel.setText("Disconnected");
        carparkComboBox.setSelectedIndex(1);
        levelComboBox.setSelectedIndex(1);
        progressBar.setVisible(false);
        statusPanel.setVisible(true);

	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                freeLotsLabel = new javax.swing.JLabel();
                freeLotsCurrent = new javax.swing.JLabel();
                freeLotsMax = new javax.swing.JLabel();
                logoPanel = new javax.swing.JPanel();
                titleLabel = new javax.swing.JLabel();
                subtitleLabel = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                textarea_output = new javax.swing.JTextArea();
                displayTabbedPane = new javax.swing.JTabbedPane();
                carparkLayeredPane = new javax.swing.JLayeredPane();
                parkingLot1 = new javax.swing.JLabel();
                parkingLot2 = new javax.swing.JLabel();
                parkingLot3 = new javax.swing.JLabel();
                parkingLot4 = new javax.swing.JLabel();
                parkingLot5 = new javax.swing.JLabel();
                carparkBitMapLabel = new javax.swing.JLabel();
                batteryGraphPanel = new javax.swing.JPanel();
                menuBar = new javax.swing.JMenuBar();
                javax.swing.JMenu fileMenu = new javax.swing.JMenu();
                javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
                javax.swing.JMenu helpMenu = new javax.swing.JMenu();
                javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
                statusPanel = new javax.swing.JPanel();
                statusAnimationLabel = new javax.swing.JLabel();
                progressBar = new javax.swing.JProgressBar();
                statusMessageLabel = new javax.swing.JLabel();

                mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                mainPanel.setMaximumSize(new java.awt.Dimension(1102, 600));
                mainPanel.setMinimumSize(new java.awt.Dimension(0, 0));
                mainPanel.setName("mainPanel"); // NOI18N
                mainPanel.setPreferredSize(new java.awt.Dimension(1102, 580));

                displayPanel.setName("displayPanel"); // NOI18N
                displayPanel.setPreferredSize(new java.awt.Dimension(1062, 600));
                displayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                carparkDetailsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                carparkDetailsPanel.setName("carparkDetailsPanel"); // NOI18N
                carparkDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(easyparkserver.EasyparkServerApp.class).getContext().getResourceMap(EasyparkServerView.class);
                carparkDetailsLabel.setText(resourceMap.getString("carparkDetailsLabel.text")); // NOI18N
                carparkDetailsLabel.setName("carparkDetailsLabel"); // NOI18N
                carparkDetailsPanel.add(carparkDetailsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 82, -1, -1));

                carparkComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select carpark...", "Fusionopolis", "Carpark X", "Carpark Y", "Carpark Z" }));
                carparkComboBox.setName("carparkComboBox"); // NOI18N
                carparkComboBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                carparkComboBoxActionPerformed(evt);
                        }
                });
                carparkDetailsPanel.add(carparkComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 193, -1));

                levelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select level...", "All", "Ground", "B1", "B2", "B3" }));
                levelComboBox.setName("levelComboBox"); // NOI18N
                levelComboBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                levelComboBoxActionPerformed(evt);
                        }
                });
                carparkDetailsPanel.add(levelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 48, 193, -1));

                jScrollPane2.setName("jScrollPane2"); // NOI18N

                carparkAddressTextArea.setColumns(12);
                carparkAddressTextArea.setEditable(false);
                carparkAddressTextArea.setRows(5);
                carparkAddressTextArea.setText(resourceMap.getString("carparkAddressTextArea.text")); // NOI18N
                carparkAddressTextArea.setName("carparkAddressTextArea"); // NOI18N
                jScrollPane2.setViewportView(carparkAddressTextArea);

                carparkDetailsPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 106, 199, -1));

                label_main_IP_address.setText(resourceMap.getString("label_main_IP_address.text")); // NOI18N
                label_main_IP_address.setName("label_main_IP_address"); // NOI18N
                carparkDetailsPanel.add(label_main_IP_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

                tf_main_IP_addr.setText(resourceMap.getString("tf_main_IP_addr.text")); // NOI18N
                tf_main_IP_addr.setName("tf_main_IP_addr"); // NOI18N
                carparkDetailsPanel.add(tf_main_IP_addr, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 84, -1));

                label_main_IP_port.setText(resourceMap.getString("label_main_IP_port.text")); // NOI18N
                label_main_IP_port.setName("label_main_IP_port"); // NOI18N
                carparkDetailsPanel.add(label_main_IP_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

                tf_main_IP_port.setText(resourceMap.getString("tf_main_IP_port.text")); // NOI18N
                tf_main_IP_port.setName("tf_main_IP_port"); // NOI18N
                carparkDetailsPanel.add(tf_main_IP_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 59, -1));

                btn_start.setText(resourceMap.getString("btn_start.text")); // NOI18N
                btn_start.setName("btn_start"); // NOI18N
                btn_start.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btn_startActionPerformed(evt);
                        }
                });
                carparkDetailsPanel.add(btn_start, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 100, -1));

                btn_end.setText(resourceMap.getString("btn_end.text")); // NOI18N
                btn_end.setName("btn_end"); // NOI18N
                btn_end.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btn_endActionPerformed(evt);
                        }
                });
                carparkDetailsPanel.add(btn_end, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 100, -1));

                displayPanel.add(carparkDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 233, 266, 314));

                availLotsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                availLotsPanel.setName("availLotsPanel"); // NOI18N

                jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
                jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
                jLabel1.setName("jLabel1"); // NOI18N

                freeLotsLabel.setFont(resourceMap.getFont("freeLotsLabel.font")); // NOI18N
                freeLotsLabel.setText(resourceMap.getString("freeLotsLabel.text")); // NOI18N
                freeLotsLabel.setName("freeLotsLabel"); // NOI18N

                freeLotsCurrent.setFont(resourceMap.getFont("freeLotsCurrent.font")); // NOI18N
                freeLotsCurrent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                freeLotsCurrent.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                freeLotsCurrent.setName("freeLotsCurrent"); // NOI18N

                freeLotsMax.setFont(resourceMap.getFont("freeLotsMax.font")); // NOI18N
                freeLotsMax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                freeLotsMax.setText(resourceMap.getString("freeLotsMax.text")); // NOI18N
                freeLotsMax.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                freeLotsMax.setName("freeLotsMax"); // NOI18N

                javax.swing.GroupLayout availLotsPanelLayout = new javax.swing.GroupLayout(availLotsPanel);
                availLotsPanel.setLayout(availLotsPanelLayout);
                availLotsPanelLayout.setHorizontalGroup(
                        availLotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(availLotsPanelLayout.createSequentialGroup()
                                .addGroup(availLotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(availLotsPanelLayout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(freeLotsLabel))
                                        .addGroup(availLotsPanelLayout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(freeLotsCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(freeLotsMax, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(41, Short.MAX_VALUE))
                );
                availLotsPanelLayout.setVerticalGroup(
                        availLotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(availLotsPanelLayout.createSequentialGroup()
                                .addComponent(freeLotsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availLotsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(freeLotsMax, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                        .addComponent(freeLotsCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                displayPanel.add(availLotsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 97, -1, -1));

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
                                .addContainerGap(61, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoPanelLayout.createSequentialGroup()
                                .addContainerGap(176, Short.MAX_VALUE)
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

                displayPanel.add(logoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, -1, -1));

                jScrollPane1.setName("jScrollPane1"); // NOI18N

                textarea_output.setColumns(15);
                textarea_output.setEditable(false);
                textarea_output.setRows(3);
                textarea_output.setToolTipText(resourceMap.getString("textarea_output.toolTipText")); // NOI18N
                textarea_output.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                textarea_output.setName("textarea_output"); // NOI18N
                jScrollPane1.setViewportView(textarea_output);

                displayPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, 790, 80));

                displayTabbedPane.setName("displayTabbedPane"); // NOI18N

                carparkLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                carparkLayeredPane.setName("carparkLayeredPane"); // NOI18N

                parkingLot1.setBackground(resourceMap.getColor("parkingLot1.background")); // NOI18N
                parkingLot1.setFont(resourceMap.getFont("parkingLot1.font")); // NOI18N
                parkingLot1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                parkingLot1.setIcon(resourceMap.getIcon("parkingLot1.icon")); // NOI18N
                parkingLot1.setText(resourceMap.getString("parkingLot1.text")); // NOI18N
                parkingLot1.setBorder(null);
                parkingLot1.setName("parkingLot1"); // NOI18N
                parkingLot1.setOpaque(true);
                parkingLot1.setBounds(230, 100, 50, 80);
                carparkLayeredPane.add(parkingLot1, javax.swing.JLayeredPane.DEFAULT_LAYER);

                parkingLot2.setBackground(resourceMap.getColor("parkingLot2.background")); // NOI18N
                parkingLot2.setFont(resourceMap.getFont("parkingLot2.font")); // NOI18N
                parkingLot2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                parkingLot2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
                parkingLot2.setText(resourceMap.getString("parkingLot2.text")); // NOI18N
                parkingLot2.setBorder(null);
                parkingLot2.setName("parkingLot2"); // NOI18N
                parkingLot2.setOpaque(true);
                parkingLot2.setBounds(300, 100, 50, 80);
                carparkLayeredPane.add(parkingLot2, javax.swing.JLayeredPane.DEFAULT_LAYER);

                parkingLot3.setBackground(resourceMap.getColor("parkingLot3.background")); // NOI18N
                parkingLot3.setFont(resourceMap.getFont("parkingLot3.font")); // NOI18N
                parkingLot3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                parkingLot3.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
                parkingLot3.setText(resourceMap.getString("parkingLot3.text")); // NOI18N
                parkingLot3.setBorder(null);
                parkingLot3.setName("parkingLot3"); // NOI18N
                parkingLot3.setOpaque(true);
                parkingLot3.setBounds(440, 100, 50, 80);
                carparkLayeredPane.add(parkingLot3, javax.swing.JLayeredPane.DEFAULT_LAYER);

                parkingLot4.setBackground(resourceMap.getColor("parkingLot4.background")); // NOI18N
                parkingLot4.setFont(resourceMap.getFont("parkingLot4.font")); // NOI18N
                parkingLot4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                parkingLot4.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
                parkingLot4.setText(resourceMap.getString("parkingLot4.text")); // NOI18N
                parkingLot4.setBorder(null);
                parkingLot4.setName("parkingLot4"); // NOI18N
                parkingLot4.setOpaque(true);
                parkingLot4.setBounds(370, 100, 50, 80);
                carparkLayeredPane.add(parkingLot4, javax.swing.JLayeredPane.DEFAULT_LAYER);

                parkingLot5.setBackground(resourceMap.getColor("parkingLot5.background")); // NOI18N
                parkingLot5.setFont(resourceMap.getFont("parkingLot5.font")); // NOI18N
                parkingLot5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                parkingLot5.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
                parkingLot5.setText(resourceMap.getString("parkingLot5.text")); // NOI18N
                parkingLot5.setBorder(null);
                parkingLot5.setName("parkingLot5"); // NOI18N
                parkingLot5.setOpaque(true);
                parkingLot5.setBounds(510, 100, 50, 80);
                carparkLayeredPane.add(parkingLot5, javax.swing.JLayeredPane.DEFAULT_LAYER);

                carparkBitMapLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                carparkBitMapLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/easyparkserver/resources/fusionopoliscropped.jpg"))); // NOI18N
                carparkBitMapLabel.setText(resourceMap.getString("carparkBitMapLabel.text")); // NOI18N
                carparkBitMapLabel.setName("carparkBitMapLabel"); // NOI18N
                carparkBitMapLabel.setBounds(0, 0, 780, 440);
                carparkLayeredPane.add(carparkBitMapLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

                displayTabbedPane.addTab(resourceMap.getString("carparkLayeredPane.TabConstraints.tabTitle"), carparkLayeredPane); // NOI18N

                batteryGraphPanel.setName("batteryGraphPanel"); // NOI18N

                javax.swing.GroupLayout batteryGraphPanelLayout = new javax.swing.GroupLayout(batteryGraphPanel);
                batteryGraphPanel.setLayout(batteryGraphPanelLayout);
                batteryGraphPanelLayout.setHorizontalGroup(
                        batteryGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 778, Short.MAX_VALUE)
                );
                batteryGraphPanelLayout.setVerticalGroup(
                        batteryGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 407, Short.MAX_VALUE)
                );

                displayTabbedPane.addTab(resourceMap.getString("batteryGraphPanel.TabConstraints.tabTitle"), batteryGraphPanel); // NOI18N

                displayPanel.add(displayTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

                javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1098, Short.MAX_VALUE)
                );
                mainPanelLayout.setVerticalGroup(
                        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
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

                statusPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
                statusPanel.setMaximumSize(new java.awt.Dimension(1102, 40));
                statusPanel.setName("statusPanel"); // NOI18N
                statusPanel.setPreferredSize(new java.awt.Dimension(1102, 30));
                statusPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N
                statusPanel.add(statusAnimationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 39, -1, -1));

                progressBar.setName("progressBar"); // NOI18N
                statusPanel.add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 209, 30));

                statusMessageLabel.setName("statusMessageLabel"); // NOI18N
                statusPanel.add(statusMessageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 450, 30));

                setComponent(mainPanel);
                setMenuBar(menuBar);
                setStatusBar(statusPanel);
                addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                        public void propertyChange(java.beans.PropertyChangeEvent evt) {
                                formPropertyChange(evt);
                        }
                });
        }// </editor-fold>//GEN-END:initComponents

    private void btn_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_endActionPerformed
        closeConnection();
        btn_start.setEnabled(true);
        btn_end.setEnabled(false);
    }//GEN-LAST:event_btn_endActionPerformed

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        SerialForwarderIPAddress = tf_main_IP_addr.getText();
        SerialForwarderPort = Integer.parseInt(tf_main_IP_port.getText());
        getSocketConnection();
        showGraph();
    }//GEN-LAST:event_btn_startActionPerformed

    private void carparkComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carparkComboBoxActionPerformed
        updateCarparkDetails(carparkComboBox.getSelectedItem().toString(), levelComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_carparkComboBoxActionPerformed

    private void levelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelComboBoxActionPerformed
        updateCarparkDetails(carparkComboBox.getSelectedItem().toString(), levelComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_levelComboBoxActionPerformed

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
	    // TODO add your handling code here:
    }//GEN-LAST:event_formPropertyChange

        // Variables declaration - do not modify//GEN-BEGIN:variables
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
        private javax.swing.JLabel freeLotsLabel;
        private javax.swing.JLabel freeLotsMax;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
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
                        System.out.println("Received: " + fromServer);
                        if (fromServer.equals("Bye!")) {
                            EasyparkServerApp.getApplication().systemExit();
                        }
                        
                    } // end while
                }
                catch (IOException ex) {
                }
            }
        };
        Thread mythread = new Thread(mainLoop);
        mythread.start();
    }//end getConnection()



    /**
     * Close the socket connection and exit the application.
     */
    void closeConnection() {
        try {
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
        textarea_output.setText(statusLogDisplayText);
        textarea_output.setCaretPosition(textarea_output.getDocument().getLength());
    }


    /**
     * Update carpark details such as address, number of levels, number of lots
     * per level and total, bitmap image, etc.
     */
    public void updateCarparkDetails(String carparkName, String carparkLevel){
        if (carparkName.matches("Fusionopolis")){
            carparkAddressTextArea.setText("1 Fusionopolis Way\n(S)138632");
            tf_main_IP_addr.setText("127.0.0.1");
            tf_main_IP_port.setText("4444");
            if (carparkLevel.matches("All")){
                freeLotsMax.setText("5");
                freeLotsCurrent.setText(freeLotsMax.getText());
            }
        }
    }

    public void updateLotStatus(String lotToken){
        serverTokens = lotToken.split(",");
        int lotNum = 0;
        numFreeLots = 0;
        numMaxLots = Integer.parseInt(serverTokens[serverTokens.length-3]);
        String dateTime = serverTokens[0];
        switch (Integer.parseInt(serverTokens[2])){
            case 0: parkingLot1.setBackground(Color.green); numFreeLots++; break;
            case 1: parkingLot1.setBackground(Color.red); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[5])){
            case 0: parkingLot2.setBackground(Color.green); numFreeLots++; break;
            case 1: parkingLot2.setBackground(Color.red); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[8])){
            case 0: parkingLot3.setBackground(Color.green); numFreeLots++; break;
            case 1: parkingLot3.setBackground(Color.red);  break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[11])){
            case 0: parkingLot4.setBackground(Color.green); numFreeLots++; break;
            case 1: parkingLot4.setBackground(Color.red); break;
            default: break;
        }
        switch (Integer.parseInt(serverTokens[14])){
            case 0: parkingLot5.setBackground(Color.green); numFreeLots++; break;
            case 1: parkingLot5.setBackground(Color.red); break;
            default: break;
        }
        freeLotsCurrent.setText(Integer.toString(numFreeLots));
        freeLotsMax.setText(Integer.toString(numMaxLots));
    }

    public void showGraph() {
        //JFrame frame = new JFrame("Battery chart");
        nodeBatteryChart panel = new nodeBatteryChart(30000);
        panel.setBounds(0, 0, 769, 410);
        panel.setVisible(true);
        batteryGraphPanel.add(panel);        
        panel.new DataGenerator(1000).start();
    }

}

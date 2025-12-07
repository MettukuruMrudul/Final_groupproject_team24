/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.Property;
import property.backend.users.UserAccount;

/**
 *
 * @author sruth
 */
public class AdminWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    
    private ArrayList<Property> forSaleProperties = new ArrayList<>();
    private ArrayList<Property> forRentProperties = new ArrayList<>();
    
    // Define colors
    private Color highlightColor = new Color(181, 218, 255); // Light blue
    private Color textColor = new Color(0, 153, 153); // Teal
    private Color titleColor = new Color(0, 102, 102); // Darker teal
    private Color chartColor1 = new Color(70, 130, 180); // Steel blue
    private Color chartColor2 = new Color(0, 191, 255); // Deep sky blue
    private Color tableBgColor = new Color(240, 248, 255); // Alice blue
    private Color cardBgColor = new Color(245, 245, 255); // Light lavender

    /**
     * Creates new form AdminWorkAreaJPanel
     */
    public AdminWorkAreaJPanel(RealEstateNetwork ecosystem, UserAccount account) {
       this.ecosystem = ecosystem;
        this.account = account;
        initComponents();
        
        // Apply styling and populate data
        setupDashboard();
    }
    
    private void setupDashboard() {
        // Set panel background
        setBackground(highlightColor);
        
        // Collect property data
        collectPropertyData();
        
        // Setup dashboard layout
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create main panel with grid layout
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        mainPanel.setBackground(highlightColor);
        
        // Add header, chart panels, and table panels
        mainPanel.add(createHeaderPanel());
        mainPanel.add(createChartsPanel());
        mainPanel.add(createTablesPanel());
        
        // Add summary panel to the left side
        add(createSummaryPanel(), BorderLayout.WEST);
        
        // Add main panel to the center
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void collectPropertyData() {
        // Clear previous data
        forSaleProperties.clear();
        forRentProperties.clear();
        
        // Search for properties in PropertyFirmOrganizations
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                        ArrayList<Property> properties = propertyFirm.getProperties();
                        
                        if (properties != null && !properties.isEmpty()) {
                            for (Property property : properties) {
                                if (property.isForRent()) {
                                    forRentProperties.add(property);
                                } else {
                                    forSaleProperties.add(property);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // If no properties found, add sample data for demonstration
        if (forSaleProperties.isEmpty() && forRentProperties.isEmpty()) {
            addSampleProperties();
        }
    }
    
    private void addSampleProperties() {
        // Sample properties for sale
        String[] saleAddresses = {
            "123 Main Street, Boston MA", 
            "456 Elm Avenue, Cambridge MA",
            "789 Oak Lane, Brookline MA",
            "101 Pine Road, Somerville MA",
            "234 Maple Court, Newton MA"
        };
        
        String[] saleIds = {"101", "202", "303", "404", "505"};
        double[] salePrices = {450000.00, 550000.00, 380000.00, 620000.00, 495000.00};
        
        for (int i = 0; i < saleAddresses.length; i++) {
            Property property = new Property();
            property.setPropertyId(saleIds[i]);
            property.setAddress(saleAddresses[i]);
            property.setForRent(false);
            property.setAvailable(true);
            property.setPrice(salePrices[i]);
            forSaleProperties.add(property);
        }
        
        // Sample properties for rent
        String[] rentAddresses = {
            "555 Beach St, Boston MA", 
            "777 College Ave, Cambridge MA",
            "888 River Rd, Brookline MA",
            "999 Lake View, Somerville MA",
            "111 Mountain Dr, Newton MA"
        };
        
        String[] rentIds = {"606", "707", "808", "909", "010"};
        double[] rentPrices = {2500.00, 1800.00, 2200.00, 3000.00, 1600.00};
        
        for (int i = 0; i < rentAddresses.length; i++) {
            Property property = new Property();
            property.setPropertyId(rentIds[i]);
            property.setAddress(rentAddresses[i]);
            property.setForRent(true);
            property.setAvailable(true);
            property.setPrice(rentPrices[i]);
            forRentProperties.add(property);
        }
    }
    
    private JPanel createHeaderPanel() {
        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(highlightColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create welcome label
        JLabel welcomeLabel = new JLabel("Admin Dashboard");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(titleColor);
        
        // Create subtitle label
        JLabel subtitleLabel = new JLabel("Property Sales and Rental Reports");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitleLabel.setForeground(textColor);
        
        // Add components to header panel
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(welcomeLabel);
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }
    
    private JPanel createChartsPanel() {
        // Create charts panel
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setBackground(highlightColor);
        
        // Create pie chart panel
        JPanel pieChartCard = createPanelCard("Property Distribution");
        pieChartCard.add(createPieChartPanel(), BorderLayout.CENTER);
        
        // Create bar chart panel
        JPanel barChartCard = createPanelCard("Average Prices");
        barChartCard.add(createBarChartPanel(), BorderLayout.CENTER);
        
        // Add charts to panel
        chartsPanel.add(pieChartCard);
        chartsPanel.add(barChartCard);
        
        return chartsPanel;
    }
    
    private JPanel createTablesPanel() {
        // Create tables panel
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        tablesPanel.setBackground(highlightColor);
        
        // Create sale properties table
        JPanel saleTableCard = createPanelCard("Properties For Sale");
        JScrollPane saleScrollPane = createPropertyTable(forSaleProperties, false);
        saleTableCard.add(saleScrollPane, BorderLayout.CENTER);
        
        // Create rent properties table
        JPanel rentTableCard = createPanelCard("Properties For Rent");
        JScrollPane rentScrollPane = createPropertyTable(forRentProperties, true);
        rentTableCard.add(rentScrollPane, BorderLayout.CENTER);
        
        // Add tables to panel
        tablesPanel.add(saleTableCard);
        tablesPanel.add(rentTableCard);
        
        return tablesPanel;
    }
    
    private JPanel createSummaryPanel() {
        // Create summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        summaryPanel.setBackground(highlightColor);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        summaryPanel.setPreferredSize(new Dimension(200, 0));
        
        // Create summary cards
        JPanel totalPropertiesCard = createSummaryCard("Total Properties", 
                forSaleProperties.size() + forRentProperties.size());
        
        JPanel salePropertiesCard = createSummaryCard("For Sale", 
                forSaleProperties.size());
        
        JPanel rentPropertiesCard = createSummaryCard("For Rent", 
                forRentProperties.size());
        
        // Add cards to panel
        summaryPanel.add(totalPropertiesCard);
        summaryPanel.add(salePropertiesCard);
        summaryPanel.add(rentPropertiesCard);
        
        return summaryPanel;
    }
    
    private JPanel createPanelCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(cardBgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(titleColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        card.add(titleLabel, BorderLayout.NORTH);
        
        return card;
    }
    
    private JPanel createSummaryCard(String title, int value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(titleColor);
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(javax.swing.Box.createVerticalStrut(10));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createPieChartPanel() {
        // Create a custom panel for pie chart
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Enable anti-aliasing
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Get panel dimensions
                int width = getWidth();
                int height = getHeight();
                int diameter = Math.min(width, height) - 40;
                int x = (width - diameter) / 2;
                int y = (height - diameter) / 2;
                
                // Calculate percentages
                int saleCount = forSaleProperties.size();
                int rentCount = forRentProperties.size();
                int totalCount = saleCount + rentCount;
                
                // Draw pie chart only if there are properties
                if (totalCount > 0) {
                    double salePercentage = (double) saleCount / totalCount;
                    double rentPercentage = (double) rentCount / totalCount;
                    
                    // Draw sale slice
                    g2d.setColor(chartColor1);
                    g2d.fillArc(x, y, diameter, diameter, 0, (int) (salePercentage * 360));
                    
                    // Draw rent slice
                    g2d.setColor(chartColor2);
                    g2d.fillArc(x, y, diameter, diameter, (int) (salePercentage * 360), (int) (rentPercentage * 360));
                    
                    // Draw legend
                    int legendX = 20;
                    int legendY = height - 60;
                    int boxSize = 15;
                    
                    // Sale legend
                    g2d.setColor(chartColor1);
                    g2d.fillRect(legendX, legendY, boxSize, boxSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("For Sale: " + saleCount + " (" + (int) (salePercentage * 100) + "%)", 
                            legendX + boxSize + 5, legendY + boxSize);
                    
                    // Rent legend
                    g2d.setColor(chartColor2);
                    g2d.fillRect(legendX, legendY + 20, boxSize, boxSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("For Rent: " + rentCount + " (" + (int) (rentPercentage * 100) + "%)", 
                            legendX + boxSize + 5, legendY + boxSize + 20);
                } else {
                    // Display "No data" message
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("No property data available", width / 2 - 70, height / 2);
                }
                
                g2d.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 200);
            }
        };
    }
    
    private JPanel createBarChartPanel() {
        // Calculate average prices
        double avgSalePrice = calculateAveragePrice(forSaleProperties);
        double avgRentPrice = calculateAveragePrice(forRentProperties);
        
        // Find max value for scaling
        final double maxValue = Math.max(avgSalePrice, avgRentPrice);
        
        // Format values for display
        final String saleValueText = String.format("$%,.2f", avgSalePrice);
        final String rentValueText = String.format("$%,.2f", avgRentPrice);
        
        // Create a custom panel for bar chart
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Enable anti-aliasing
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Get panel dimensions
                int width = getWidth();
                int height = getHeight();
                
                // Define chart area
                int chartX = 60;
                int chartY = 20;
                int chartWidth = width - chartX - 20;
                int chartHeight = height - chartY - 60;
                
                // Draw axes
                g2d.setColor(Color.BLACK);
                g2d.drawLine(chartX, chartY, chartX, chartY + chartHeight);
                g2d.drawLine(chartX, chartY + chartHeight, chartX + chartWidth, chartY + chartHeight);
                
                // Draw y-axis labels
                g2d.drawString("0", chartX - 25, chartY + chartHeight + 5);
                g2d.drawString(String.format("$%,.0f", maxValue), chartX - 60, chartY + 15);
                
                // Calculate bar width and spacing
                int barWidth = 60;
                int gap = 30;
                int startX = chartX + (chartWidth - (2 * barWidth + gap)) / 2;
                
                if (maxValue > 0) {
                    // Draw sale price bar
                    int barHeight = (int) ((avgSalePrice / maxValue) * chartHeight);
                    g2d.setColor(chartColor1);
                    g2d.fillRect(startX, chartY + chartHeight - barHeight, barWidth, barHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("For Sale", startX + 5, chartY + chartHeight + 20);
                    
                    // Draw value on top of bar
                    g2d.drawString(saleValueText, startX - 5, chartY + chartHeight - barHeight - 5);
                    
                    // Draw rent price bar
                    barHeight = (int) ((avgRentPrice / maxValue) * chartHeight);
                    g2d.setColor(chartColor2);
                    g2d.fillRect(startX + barWidth + gap, chartY + chartHeight - barHeight, barWidth, barHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("For Rent", startX + barWidth + gap + 5, chartY + chartHeight + 20);
                    
                    // Draw value on top of bar
                    g2d.drawString(rentValueText, startX + barWidth + gap - 5, chartY + chartHeight - barHeight - 5);
                } else {
                    // Display "No data" message
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("No price data available", width / 2 - 70, height / 2);
                }
                
                g2d.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 200);
            }
        };
    }
    
    private double calculateAveragePrice(ArrayList<Property> properties) {
        if (properties.isEmpty()) {
            return 0;
        }
        
        double total = 0;
        for (Property property : properties) {
            total += property.getPrice();
        }
        
        return total / properties.size();
    }
    
    private JScrollPane createPropertyTable(ArrayList<Property> properties, boolean isRent) {
        // Create table model
        String[] columnNames;
        if (isRent) {
            columnNames = new String[]{"ID", "Address", "Monthly Rent ($)"};
        } else {
            columnNames = new String[]{"ID", "Address", "Sale Price ($)"};
        }
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Add property data
        for (Property property : properties) {
            Object[] row;
            if (isRent) {
                row = new Object[]{
                    property.getPropertyId(),
                    property.getAddress(),
                    String.format("%.2f", property.getPrice())
                };
            } else {
                row = new Object[]{
                    property.getPropertyId(),
                    property.getAddress(),
                    String.format("%,.2f", property.getPrice())
                };
            }
            model.addRow(row);
        }
        
        // Create table
        JTable table = new JTable(model);
        table.setBackground(tableBgColor);
        table.setGridColor(new Color(200, 200, 220));
        table.setRowHeight(25);
        
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(titleColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        return scrollPane;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

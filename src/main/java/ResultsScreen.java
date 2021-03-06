import UCdatabase.MicroControllerEntity;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultsScreen {
    private JPanel resultsPanel;
    private JPanel contentPanel;
    private JList selectedUcList;
    private JButton showAllButton;
    private JButton resetButton;
    private JLabel packageField;
    private JLabel productField;
    private JLabel producentField;
    private JLabel descriptionField;
    private JLabel priceField;
    private JLabel coreField;
    private JLabel cpuSpeedField;
    private JLabel flashField;
    private JLabel ramField;
    private JLabel pinCountField;
    private JLabel comparatorsField;
    private JLabel adcOutputField;
    private JLabel adcResField;
    private JLabel dacInputField;
    private JLabel dacResolutionField;
    private JLabel countersField;
    private JLabel uartField;
    private JLabel spiField;
    private JLabel i2cField;
    private JLabel canField;
    private JLabel usbField;
    private JLabel tempField;
    private JLabel voltageField;
    private JLabel powerConsumptionField;
    private JLabel fpuField;
    private JLabel externalRamField;
    private JLabel paralelField;
    private JLabel serialField;
    private JScrollPane detailsScrollPanel;
    private JButton backButton;

    private ScreenNavigationListener listener;

    public ResultsScreen(List<MicroControllerEntity> ucList) {

        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();


        selectedUcList.setModel(new UCListModel(ucList.subList(0, ucList.size() < 25 ? ucList.size() : 25)));
        selectedUcList.addListSelectionListener(listSelectionEvent -> {
            showDetails(ucList.get(selectedUcList.getSelectedIndex()));
            detailsScrollPanel.getViewport().setViewPosition(new Point(0, 0));
        });
        if (selectedUcList.getModel().getSize() > 0) {
            selectedUcList.setSelectedIndex(0);
        }

        showAllButton.addActionListener(actionEvent -> {
            ListUCScreen listScreen = new ListUCScreen(ucList);
            listScreen.setVisible(true);
        });

        backButton.addActionListener(actionEvent -> {
            if (listener != null) {
                listener.onNavigationButtonClick(ScreenNavigationListener.PREVIEW_BUTTON);
            }
        });

        resetButton.addActionListener(actionEvent -> {
            if (listener != null) {
                listener.onNavigationButtonClick(ScreenNavigationListener.NEXT_BUTTON);
            }
        });

    }

    private void showDetails(MicroControllerEntity microController) {
        packageField.setText(microController.getPackages());
        productField.setText(microController.getProduct_name());
        producentField.setText(microController.getManufacturer());
        descriptionField.setText(microController.getGeneral_description());
        priceField.setText(String.valueOf(microController.getPrice()));
        coreField.setText(microController.getCore());
        cpuSpeedField.setText(microController.getCpu_speed() + " MHz");
        flashField.setText(microController.getFlash_kb() + " kB");
        ramField.setText(microController.getSram_bytes() >= 1024 ? microController.getSram_bytes() / 1024 + " kB" : microController.getSram_bytes() + " B");
        pinCountField.setText(String.valueOf(microController.getPin_count()));
        comparatorsField.setText(String.valueOf(microController.getComparators()));
        adcOutputField.setText(String.valueOf(microController.getADC_input()));
        adcResField.setText(microController.getADC_resolution() + " bit");
        dacInputField.setText(String.valueOf(microController.getDAC_output()));
        dacResolutionField.setText(microController.getDAC_resolution() + " bit");
        countersField.setText(String.valueOf(microController.getCounters()));
        uartField.setText(String.valueOf(microController.getUART()));
        spiField.setText(String.valueOf(microController.getSPI()));
        i2cField.setText(String.valueOf(microController.getI2C()));
        canField.setText(String.valueOf(microController.getCAN()));
        usbField.setText(microController.getUSB() > 0 ? "jest" : "brak");
        tempField.setText("od " + microController.getTemp_min() + "°C do " + microController.getTemp_max() + "°C");
        voltageField.setText(String.format("od %.1fV do %.1fV", microController.getVoltage_min() / 1000f, microController.getVoltage_max() / 1000f));
        powerConsumptionField.setText(microController.getPower_consumption() + "uA");
        fpuField.setText(microController.getFPU() > 0 ? "jest" : "brak");
        externalRamField.setText(microController.getExternal_ram_support() > 0 ? "jest" : "brak");
        paralelField.setText(microController.getParallel_interfaces());
        serialField.setText(microController.getSerial_interfaces());
    }

    public JPanel getResultPanel() {
        return resultsPanel;
    }

    public void setNavigationScreenListener(ScreenNavigationListener listener) {
        this.listener = listener;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        resultsPanel.add(panel1, BorderLayout.NORTH);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Wyniki");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        resultsPanel.add(contentPanel, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel2, BorderLayout.WEST);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Wybrane mikrokontrolery"));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        selectedUcList = new JList();
        scrollPane1.setViewportView(selectedUcList);
        showAllButton = new JButton();
        showAllButton.setText("Pokaż wszystkie");
        panel2.add(showAllButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel3, BorderLayout.CENTER);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Szczegóły"));
        detailsScrollPanel = new JScrollPane();
        panel3.add(detailsScrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(29, 2, new Insets(0, 0, 0, 0), -1, -1));
        detailsScrollPanel.setViewportView(panel4);
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(11);
        label2.setHorizontalTextPosition(11);
        label2.setText("Produkt:");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Producent:");
        panel4.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Opis:");
        panel4.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Obudowa:");
        panel4.add(label5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Cena:");
        panel4.add(label6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Rdzeń:");
        panel4.add(label7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Zegar (max):");
        panel4.add(label8, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Pamięć FLASH:");
        panel4.add(label9, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Pamięć RAM:");
        panel4.add(label10, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Liczba pinów:");
        panel4.add(label11, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Komparatory:");
        panel4.add(label12, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Wejścia ADC:");
        panel4.add(label13, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Rozdzielczość ADC:");
        panel4.add(label14, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Wyjścia DAC:");
        panel4.add(label15, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Rozdzielczość DAC:");
        panel4.add(label16, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Liczniki:");
        panel4.add(label17, new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("UART:");
        panel4.add(label18, new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("SPI:");
        panel4.add(label19, new GridConstraints(17, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("I2C:");
        panel4.add(label20, new GridConstraints(18, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("CAN:");
        panel4.add(label21, new GridConstraints(19, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("USB:");
        panel4.add(label22, new GridConstraints(20, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Temperatura pracy:");
        panel4.add(label23, new GridConstraints(21, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Napięcia pracy:");
        panel4.add(label24, new GridConstraints(22, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("Pobór prądu:");
        panel4.add(label25, new GridConstraints(23, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("FPU:");
        panel4.add(label26, new GridConstraints(24, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("Zewnętrzny RAM:");
        panel4.add(label27, new GridConstraints(25, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label28 = new JLabel();
        label28.setText("Interfejsy równoległe:");
        panel4.add(label28, new GridConstraints(26, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        final JLabel label29 = new JLabel();
        label29.setText("Interfejsy szeregowe:");
        panel4.add(label29, new GridConstraints(27, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        productField = new JLabel();
        productField.setText("-");
        panel4.add(productField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        producentField = new JLabel();
        producentField.setText("-");
        panel4.add(producentField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descriptionField = new JLabel();
        descriptionField.setText("-");
        panel4.add(descriptionField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        packageField = new JLabel();
        packageField.setText("-");
        panel4.add(packageField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceField = new JLabel();
        priceField.setText("-");
        panel4.add(priceField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coreField = new JLabel();
        coreField.setText("-");
        panel4.add(coreField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cpuSpeedField = new JLabel();
        cpuSpeedField.setText("-");
        panel4.add(cpuSpeedField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        flashField = new JLabel();
        flashField.setText("-");
        panel4.add(flashField, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ramField = new JLabel();
        ramField.setText("-");
        panel4.add(ramField, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pinCountField = new JLabel();
        pinCountField.setText("-");
        panel4.add(pinCountField, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comparatorsField = new JLabel();
        comparatorsField.setText("-");
        panel4.add(comparatorsField, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adcOutputField = new JLabel();
        adcOutputField.setText("-");
        panel4.add(adcOutputField, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adcResField = new JLabel();
        adcResField.setText("-");
        panel4.add(adcResField, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dacInputField = new JLabel();
        dacInputField.setText("-");
        panel4.add(dacInputField, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dacResolutionField = new JLabel();
        dacResolutionField.setText("-");
        panel4.add(dacResolutionField, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        countersField = new JLabel();
        countersField.setText("-");
        panel4.add(countersField, new GridConstraints(15, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uartField = new JLabel();
        uartField.setText("-");
        panel4.add(uartField, new GridConstraints(16, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spiField = new JLabel();
        spiField.setText("-");
        panel4.add(spiField, new GridConstraints(17, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        i2cField = new JLabel();
        i2cField.setText("-");
        panel4.add(i2cField, new GridConstraints(18, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canField = new JLabel();
        canField.setText("-");
        panel4.add(canField, new GridConstraints(19, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usbField = new JLabel();
        usbField.setText("-");
        panel4.add(usbField, new GridConstraints(20, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tempField = new JLabel();
        tempField.setText("-");
        panel4.add(tempField, new GridConstraints(21, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voltageField = new JLabel();
        voltageField.setText("-");
        panel4.add(voltageField, new GridConstraints(22, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        powerConsumptionField = new JLabel();
        powerConsumptionField.setText("-");
        panel4.add(powerConsumptionField, new GridConstraints(23, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fpuField = new JLabel();
        fpuField.setText("-");
        panel4.add(fpuField, new GridConstraints(24, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        externalRamField = new JLabel();
        externalRamField.setText("-");
        panel4.add(externalRamField, new GridConstraints(25, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paralelField = new JLabel();
        paralelField.setText("-");
        panel4.add(paralelField, new GridConstraints(26, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serialField = new JLabel();
        serialField.setText("-");
        panel4.add(serialField, new GridConstraints(27, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel4.add(spacer1, new GridConstraints(28, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(5, 5, 5, 5), -1, -1));
        resultsPanel.add(panel5, BorderLayout.SOUTH);
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        resetButton = new JButton();
        resetButton.setEnabled(true);
        resetButton.setText("Koniec");
        panel5.add(resetButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Wstecz");
        panel5.add(backButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return resultsPanel;
    }

    private void createUIComponents() {

    }

    public interface ScreenNavigationListener {
        public final int NEXT_BUTTON = 0;
        public final int PREVIEW_BUTTON = 1;

        void onNavigationButtonClick(int buttonID);
    }

    private class UCListModel implements ListModel {
        List<MicroControllerEntity> ucList;

        UCListModel(List<MicroControllerEntity> ucList) {
            this.ucList = ucList;
        }

        @Override
        public int getSize() {
            return ucList.size();
        }

        @Override
        public Object getElementAt(int i) {
            return ucList.get(i).getProduct_name();
        }

        @Override
        public void addListDataListener(ListDataListener listDataListener) {

        }

        @Override
        public void removeListDataListener(ListDataListener listDataListener) {

        }
    }
}

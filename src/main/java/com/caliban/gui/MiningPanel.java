package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import com.caliban.activity.HulkIceMiningActivity;
import com.caliban.activity.MackinawIceMiningActivity;
import com.caliban.activity.OreMiningActivity;
import com.caliban.enums.MinerType;
import com.caliban.gui.TronComponents.TronButton;
import com.caliban.gui.TronComponents.TronComboBox;
import com.caliban.gui.TronComponents.TronLabel;
import com.caliban.gui.TronComponents.TronPanel;
import com.caliban.gui.TronComponents.TronRadioButton;
import com.caliban.gui.TronComponents.TronSpinner;

public class MiningPanel extends TronPanel {

    private TronButton miningButton;
    private TronRadioButton iceRadioButton, oreRadioButton;
    private TronComboBox<MinerType> minerTypeComboBox;
    private TronSpinner charCountSpinner;
    private TronLabel charCountLabel;
    private TronLabel stateLabel;
    private boolean isMining = false;

    private MackinawIceMiningActivity mackinawIceMining = new MackinawIceMiningActivity();
    private HulkIceMiningActivity hulkIceMining = new HulkIceMiningActivity();
    private OreMiningActivity simpleOreMining = new OreMiningActivity();

    public MiningPanel() {
        setLayout(new BorderLayout());

        // Create state label with Tron styling
        stateLabel = new TronLabel("[ STATE: " + mackinawIceMining.getState() + " ]");
        stateLabel.setHorizontalAlignment(JLabel.CENTER);
        stateLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        
        // Create a panel for the controls
        TronPanel buttonPanel = getButtonPanel();
        
        // Set up a timer to update the state label every second
        Timer stateUpdateTimer = new Timer(1000, e -> {
            stateLabel.setText("[ STATE: " + mackinawIceMining.getState() + " ]");
        });
        stateUpdateTimer.start();

        miningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isMining) {
                    // Start mining with Tron-style feedback
                    isMining = true;
                    miningButton.setText("[ TERMINATE ]");
                    miningButton.setBackground(TronComponents.TRON_GREEN);
                    miningButton.setBorder(new TronComponents.TronBorder(TronComponents.TRON_GREEN, true));
                    Thread imageFinderThread = new Thread(() -> {
                        startMiningActivity();
                    });
                    imageFinderThread.start();
                } else {
                    // Stop mining with Tron-style feedback
                    isMining = false;
                    miningButton.setText("[ INITIALIZE ]");
                    miningButton.setBackground(TronComponents.TRON_MEDIUM);
                    miningButton.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN));
                    mackinawIceMining.stop();
                    hulkIceMining.stop();
                    simpleOreMining.stop();
                }
            }
        });

        add(stateLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private TronPanel getButtonPanel() {
        TronPanel panel = new TronPanel(new FlowLayout());
        
        minerTypeComboBox = new TronComboBox<>(MinerType.values());
        minerTypeComboBox.setSelectedItem(MinerType.MACKINAW);
        
        iceRadioButton = new TronRadioButton("[ ICE ]");
        iceRadioButton.setSelected(true);
        oreRadioButton = new TronRadioButton("[ ORE ]");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(iceRadioButton);
        radioGroup.add(oreRadioButton);

        miningButton = new TronButton("[ INITIALIZE ]");
        
        // Make the mining button larger and more prominent
        Dimension defaultSize = miningButton.getPreferredSize();
        miningButton.setPreferredSize(new Dimension(defaultSize.width * 2, defaultSize.height * 2));
        miningButton.setFont(new Font("Consolas", Font.BOLD, 18));

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(15, 1, 20, 1);
        charCountSpinner = new TronSpinner(spinnerModel);
        charCountLabel = new TronLabel("[ UNITS ]");

        panel.add(minerTypeComboBox);
        panel.add(iceRadioButton);
        panel.add(oreRadioButton);
        panel.add(miningButton);
        panel.add(charCountLabel);
        panel.add(charCountSpinner);
        return panel;
    }

    private void startMiningActivity() {
        MinerType selectedMinerType = (MinerType) minerTypeComboBox.getSelectedItem();
        if (iceRadioButton.isSelected()) {
            if (selectedMinerType == MinerType.HULK) {
                hulkIceMining.start((int) charCountSpinner.getValue());
            } else if (selectedMinerType == MinerType.MACKINAW) {
                mackinawIceMining.start((int) charCountSpinner.getValue());
            }
        } else if (oreRadioButton.isSelected()) {
            simpleOreMining.start((int) charCountSpinner.getValue(), selectedMinerType);
        }
    }
}

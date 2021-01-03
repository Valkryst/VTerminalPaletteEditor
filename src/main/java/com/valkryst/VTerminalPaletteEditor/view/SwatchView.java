package com.valkryst.VTerminalPaletteEditor.view;

import com.valkryst.VTerminalPaletteEditor.controller.SwatchController;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class SwatchView extends View {
	private final JColorChooser colorChooser = new JColorChooser();
	private final JButton applyButton = new JButton("Apply Changes");

	public SwatchView(final @NonNull SwatchController controller) {
		setupColorChooser();
		setupSaveButton(controller);

		this.setLayout(new GridBagLayout());

		var constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		this.add(colorChooser, constraints);

		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(4, 0, 4, 0);
		this.add(applyButton, constraints);
	}

	private void setupColorChooser() {
		final var chooserPanels = colorChooser.getChooserPanels();

		colorChooser.removeChooserPanel(chooserPanels[0]);
		colorChooser.removeChooserPanel(chooserPanels[1]);
		colorChooser.removeChooserPanel(chooserPanels[2]);
		colorChooser.removeChooserPanel(chooserPanels[4]);

		chooserPanels[3].setColorTransparencySelectionEnabled(false);
	}

	private void setupSaveButton(final SwatchController controller) {
		applyButton.addActionListener(actionEvent -> {
			controller.changeColor(colorChooser.getColor());
		});
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
		if (event.getPropertyName().equals(SwatchController.COLOR_PROPERTY)) {
			colorChooser.setColor((Color) event.getNewValue());
		}
	}
}

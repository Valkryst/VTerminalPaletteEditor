package com.valkryst.VTerminalPaletteEditor.view;

import com.valkryst.VTerminalPaletteEditor.controller.RibbonController;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class RibbonView extends View {
	public RibbonView(final @NonNull RibbonController controller) {
		final var menuBar = new JMenuBar();
		menuBar.add(createFileMenu(controller));

		this.setLayout(new BorderLayout());
		this.add(menuBar, BorderLayout.WEST);
	}

	private JMenu createFileMenu(final RibbonController controller) {
		final var menu = new JMenu("File");

		var menuItem = new JMenuItem("New");
		menuItem.setActionCommand(menuItem.getText());
		menuItem.addActionListener(e -> controller.newPalette());
		menu.add(menuItem);

		menuItem = new JMenuItem("Load");
		menuItem.setActionCommand(menuItem.getText());
		menuItem.addActionListener(e -> controller.loadPalette());
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.setActionCommand(menuItem.getText());
		menuItem.addActionListener(e -> controller.savePalette());
		menu.add(menuItem);

		menuItem = new JMenuItem("Save As");
		menuItem.setActionCommand(menuItem.getText());
		menuItem.addActionListener(e -> controller.savePaletteAs());
		menu.add(menuItem);

		return menu;
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {

	}
}

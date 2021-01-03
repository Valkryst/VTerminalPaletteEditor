package com.valkryst.VTerminalPaletteEditor.controller;

import com.valkryst.VTerminalPaletteEditor.Display;
import com.valkryst.VTerminalPaletteEditor.model.PaletteModel;
import lombok.NonNull;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class RibbonController extends Controller {
	private final PaletteModel paletteModel;
	private File paletteFile = null;

	public RibbonController(final @NonNull PaletteModel paletteModel) {
		this.paletteModel = paletteModel;
		displayFileNameInFrameTitle();
	}

	public void newPalette() {
		final var result = JOptionPane.showConfirmDialog(
				Display.getInstance().getFrame(),
				"Save the current palette first?"
		);

		if (result == 0) { // Yes
			savePalette();
		}

		paletteFile = null;
		paletteModel.loadDefaultSwatches();
		displayFileNameInFrameTitle();
	}

	@SneakyThrows
	public void loadPalette() {
		var result = JOptionPane.showConfirmDialog(
				Display.getInstance().getFrame(),
				"Save the current palette first?"
		);

		switch (result) {
			case 0: { // Yes
				savePalette();
				paletteFile = null;
				break;
			}
			case 2: { // Cancel
				return;
			}
		}

		final var fileChooser = createFileChooser();
		result = fileChooser.showOpenDialog(Display.getInstance().getFrame());

		if (result == JFileChooser.APPROVE_OPTION) {
			paletteFile = fileChooser.getSelectedFile();
			paletteModel.loadSwatches(paletteFile);
		}

		displayFileNameInFrameTitle();
	}

	@SneakyThrows
	public void savePalette() {
		if (paletteFile == null) {
			savePaletteAs();
		}

		paletteModel.saveSwatches(paletteFile);
	}

	public void savePaletteAs() {
		final var fileChooser = createFileChooser();
		fileChooser.setSelectedFile(new File("MyPalette.properties"));

		final var frame = Display.getInstance().getFrame();

		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String fileName = fileChooser.getSelectedFile().toString();
			fileName += (fileName.endsWith(".properties") ? "" : ".properties");

			final var selectedFile = new File(fileName);

			if (selectedFile.exists()) {
				final var result = JOptionPane.showConfirmDialog(
						Display.getInstance().getFrame(),
						"Overwrite " + selectedFile.getName() + "?"
				);

				if (result != 0) {
					return;
				}
			}

			paletteFile = selectedFile;
			savePalette();
			displayFileNameInFrameTitle();
		}
	}

	private void displayFileNameInFrameTitle() {
		Display.getInstance()
			   .setTitleSuffix(" - " + (paletteFile == null ? "*" : paletteFile.getName()));
	}

	private JFileChooser createFileChooser() {
		final var fileChooser = new JFileChooser();
		fileChooser.setDragEnabled(false);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Palette Files", "properties"));
		fileChooser.setMultiSelectionEnabled(false);
		return fileChooser;
	}
}

package com.valkryst.VTerminalPaletteEditor.model;

import com.formdev.flatlaf.FlatLightLaf;
import com.valkryst.VTerminalPaletteEditor.controller.PaletteController;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaletteModel extends Model {
	private final List<SwatchModel> swatches = new ArrayList<>();

	public PaletteModel() {
		loadDefaultSwatches();
	}

	@SneakyThrows
	public void loadDefaultSwatches() {
		final var oldSwatches = new ArrayList<>(swatches);
		swatches.clear();

		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		for (final var entry : UIManager.getLookAndFeelDefaults().entrySet()) {
			final var value = entry.getValue();

			if (value instanceof Color) {
				final var key = entry.getKey().toString();
				swatches.add(new SwatchModel(key, (Color) value));
			}
		}
		FlatLightLaf.install();

		sortSwatches();
		super.firePropertyChange(PaletteController.SWATCHES_PROPERTY, oldSwatches, swatches);
	}

	public void loadSwatches(final File file) throws IOException {
		final var oldSwatches = new ArrayList<>(swatches);
		swatches.clear();

		try (
			final var fileReader = new FileReader(file);
			final var bufferedReader = new BufferedReader(fileReader);
		) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				final var splitLine = line.split("=");

				if (splitLine.length != 2) {
					System.err.println("The line '" + line + "' could not be split into an identifier and a color.");
					continue;
				}

				try {
					final var key = splitLine[0];
					final var value = Color.decode(splitLine[1]);
					swatches.add(new SwatchModel(key, value));
				} catch (final NumberFormatException ignored) {
					System.err.println("The color '" + splitLine[1] + "' on the line '" + line +"' could not be decoded.");
				}
			}
		}

		sortSwatches();
		super.firePropertyChange(PaletteController.SWATCHES_PROPERTY, oldSwatches, swatches);
	}

	public void saveSwatches(final File file) throws IOException {
		try (
			final var fileWriter = new FileWriter(file);
			final var printWriter = new PrintWriter(fileWriter);
		) {
			for (final var swatch : swatches) {
				printWriter.print(swatch.getIdentifier());
				printWriter.print("=#");
				printWriter.print(swatch.getHexadecimalColor());
				printWriter.write('\n');
			}

			printWriter.flush();
		}
	}

	private void sortSwatches() {
		swatches.sort((o1, o2) -> o2.getIdentifier().compareTo(o1.getIdentifier()));
	}

	@Override
	public void fireInitialProperties() {
		super.firePropertyChange(PaletteController.SWATCHES_PROPERTY, null, swatches);
	}
}

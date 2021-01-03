package com.valkryst.VTerminalPaletteEditor.model;

import com.valkryst.VTerminalPaletteEditor.controller.SwatchController;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;

public class SwatchModel extends Model{
	@Getter private final String identifier;
	@Getter private Color color;

	public SwatchModel(final @NonNull SwatchModel model) {
		this(model.identifier, model.color);
	}

	public SwatchModel(final @NonNull String identifier, final @NonNull Color color) {
		if (identifier.isEmpty() || identifier.isBlank()) {
			throw new IllegalArgumentException("Identifiers cannot be empty or blank.");
		}

		this.identifier = identifier;
		this.color = copyColor(color);
	}

	@Override
	public void fireInitialProperties() {
		super.firePropertyChange(SwatchController.IDENTIFIER_PROPERTY, null, identifier);
		super.firePropertyChange(SwatchController.COLOR_PROPERTY, null, color);
	}

	/**
	 * Creates a deep copy of the given color.
	 *
	 * @param color A color to copy.
	 * @return A deep copy of the given color.
	 */
	private Color copyColor(final @NonNull Color color) {
		final int red = color.getRed();
		final int green = color.getGreen();
		final int blue = color.getBlue();
		final int alpha = color.getAlpha();
		return new Color(red, green, blue, alpha);
	}

	/**
	 * Retrieves the color as a 6 character hexadecimal string. The leading '#'
	 * and two alpha characters are omitted.
	 *
	 * @return The color as a 6 character hexadecimal string.
	 */
	public String getHexadecimalColor() {
		return Integer.toHexString(getColor().getRGB())
				.substring(2)
				.toUpperCase();
	}

	public void setColor(final @NonNull Color newColor) {
		final Color oldColor = color;
		color = newColor;
		super.firePropertyChange(SwatchController.COLOR_PROPERTY, oldColor, newColor);
	}
}

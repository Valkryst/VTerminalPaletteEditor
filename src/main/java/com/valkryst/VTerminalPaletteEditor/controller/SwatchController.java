package com.valkryst.VTerminalPaletteEditor.controller;

import java.awt.*;

public class SwatchController extends Controller {
	public static final String IDENTIFIER_PROPERTY = "Identifier";
	public static final String COLOR_PROPERTY = "Color";

	public void changeColor(final Color color) {
		super.setModelProperty(COLOR_PROPERTY, color);
	}
}

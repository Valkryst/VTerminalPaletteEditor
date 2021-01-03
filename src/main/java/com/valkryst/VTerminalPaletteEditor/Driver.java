package com.valkryst.VTerminalPaletteEditor;

import com.formdev.flatlaf.FlatLightLaf;
import com.valkryst.VTerminalPaletteEditor.controller.PaletteController;

public class Driver {
    public static void main(final String[] args) {
		FlatLightLaf.install();

    	final var controller = new PaletteController();
    	controller.displayColorsView();

    	final var frame = Display.getInstance().getFrame();
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    }
}

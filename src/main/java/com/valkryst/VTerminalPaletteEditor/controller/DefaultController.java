package com.valkryst.VTerminalPaletteEditor.controller;

import com.valkryst.VTerminalPaletteEditor.Display;
import com.valkryst.VTerminalPaletteEditor.model.PaletteModel;
import com.valkryst.VTerminalPaletteEditor.view.PaletteView;
import com.valkryst.VTerminalPaletteEditor.view.RibbonView;

import java.awt.*;

public class DefaultController extends Controller {
    public void displayColorsView() {
		Display.getInstance().setLayout(new GridBagLayout());

        final var controller = new PaletteController();
        final var model = new PaletteModel();
        controller.addModel(model);

        var constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
        controller.addView(new RibbonView(new RibbonController(model)), constraints);

        constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
        controller.addView(new PaletteView(), constraints);

        model.fireInitialProperties();
    }
}

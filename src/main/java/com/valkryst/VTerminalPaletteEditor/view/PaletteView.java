package com.valkryst.VTerminalPaletteEditor.view;

import com.valkryst.VTerminalPaletteEditor.controller.PaletteController;
import com.valkryst.VTerminalPaletteEditor.controller.SwatchController;
import com.valkryst.VTerminalPaletteEditor.model.SwatchModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.List;

public class PaletteView extends View {
	private static final String[] TABLE_COLUMN_NAMES = {"Identifier", "Color"};

	private final JTable table = new JTable();
	private final SwatchController swatchController = new SwatchController();

	public PaletteView() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(createTablePanel()));
	}

	private Component createTablePanel() {
		final var model = new DefaultTableModel(TABLE_COLUMN_NAMES, 0);
		table.setModel(model);

		final var columnModel = table.getColumnModel();
		final var propertyColumn = columnModel.getColumn(0);
		final var colorColumn = columnModel.getColumn(1);

		propertyColumn.setResizable(false);
		propertyColumn.setMinWidth(310);
		propertyColumn.setMaxWidth(310);

		colorColumn.setCellRenderer(new ColorCellRenderer());

		table.getTableHeader().setReorderingAllowed(false);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(e -> {
			if (e.getValueIsAdjusting()) {
				return;
			}

			if (table.getRowCount() == 0 || table.getColumnCount() == 0) {
				return;
			}

			if (table.getSelectedRow() < 0) {
				return;
			}

			swatchController.removeAllViews();
			swatchController.removeAllModels();

			final var constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.weightx = 1;
			swatchController.addView(new SwatchView(swatchController), constraints);

			final var swatchModel = (SwatchModel) model.getValueAt(table.getSelectedRow(), 1);
			swatchController.addModel(swatchModel);
			swatchModel.addPropertyChangeListener(event -> {
				if (event.getPropertyName().equals(SwatchController.COLOR_PROPERTY)) {
					table.repaint();
				}
			});

			table.requestFocus();
		});

		return table;
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
		if (event.getPropertyName().equals(PaletteController.SWATCHES_PROPERTY)) {
			final var model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);

			final var swatches = (List<SwatchModel>) event.getNewValue();
			for (final var swatch : swatches) {
				model.insertRow(0, new Object[]{
					swatch.getIdentifier(),
					swatch
				});
			}

			table.setRowSelectionInterval(0, 0);
			return;
		}

		if (event.getPropertyName().equals(SwatchController.COLOR_PROPERTY)) {
			this.repaint();
			return;
		}
	}

	private static class ColorCellRenderer extends JPanel implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
			final var swatchModel = (SwatchModel) value;
			final var color = swatchModel.getColor();
			this.setBackground(color);
			return this;
		}
	}
}

package com.valkryst.VTerminalPaletteEditor;

import com.valkryst.VTerminalPaletteEditor.view.View;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display {
    /** Singleton instance. */
    private final static Display INSTANCE = new Display();

    @Getter private final JFrame frame = new JFrame();

    /** Constructs a new Display. */
    private Display() {
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(591, 430));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
            frame.dispose();
            System.exit(0);
            }
        });
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null); // Must be called after pack()
		frame.setBackground(Color.GREEN);
		frame.getContentPane().setBackground(Color.YELLOW);
    }

    public void addView(final @NonNull View view) {
    	frame.add(view);
    	frame.revalidate();
    	view.requestFocusInWindow();
	}

    public void addView(final @NonNull View view, final @NonNull Object constraints) {
		frame.add(view, constraints);
		frame.revalidate();
		view.requestFocusInWindow();
	}

    /**
     * Removes a view from the frame.
     *
     * @param view The view to remove.
     */
    public void removeView(final View view) {
        if (view != null) {
            frame.remove(view);
            frame.revalidate();
        }
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return The singleton instance.
     */
    public static Display getInstance() {
        return INSTANCE;
    }

    public void setLayout(final LayoutManager layout) {
    	frame.setLayout(layout);
	}

	public void setTitleSuffix(final String suffix) {
		frame.setTitle("VTerminal Palette Editor" + suffix);
	}
}

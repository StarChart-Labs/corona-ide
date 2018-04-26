package com.coronaide.test.ui.util;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.stage.Screen;

public class ScaledBounds extends BoundingBox {

    public static ScaledBounds wrap(Bounds bounds) {
        double scale = 1.0;
        if (!GraphicsEnvironment.isHeadless()) {
            scale = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / Screen.getPrimary().getBounds().getWidth();
        }
        return new ScaledBounds(bounds, scale);
    }

    private ScaledBounds(Bounds wrapped, double scale) {
        super(
                wrapped.getMinX() * scale,
                wrapped.getMinY() * scale,
                wrapped.getMinZ() * scale,
                wrapped.getWidth() * scale,
                wrapped.getHeight() * scale,
                wrapped.getDepth());
    }
}
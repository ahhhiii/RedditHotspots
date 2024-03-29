package net.bruhitsalex.reddithotspots.display;

import java.awt.*;

public class ColorPalette {

    public static boolean dark = true;

    private static final Color MAIN_BACKGROUND_DARK = Color.decode("#101010");
    private static final Color MAIN_BACKGROUND_LIGHT = Color.decode("#EFEFEF");
    private static final Color CONTENT_PANEL_BACKGROUND_DARK = Color.decode("#1D1D1D");
    private static final Color CONTENT_PANEL_BACKGROUND_LIGHT = Color.decode("#DCDCDC");
    private static final Color SWITCH_THEME_TEXT_DARK = Color.decode("#E1E1E1");
    private static final Color SWITCH_THEME_TEXT_LIGHT = Color.decode("#1D1D1D");
    private static final Color AXIS_BACKGROUND_DARK = Color.decode("#2E2E2E");
    private static final Color AXIS_BACKGROUND_LIGHT = Color.decode("#CECECE");
    private static final Color ELEMENT_COLOR = Color.decode("#9B3C3C");

    public static Color getMainBackground() {
        return dark ? MAIN_BACKGROUND_DARK :  MAIN_BACKGROUND_LIGHT;
    }

    public static Color getContentPanelBackground() { return dark ? CONTENT_PANEL_BACKGROUND_DARK : CONTENT_PANEL_BACKGROUND_LIGHT; }

    public static Color getSwitchThemeText() {
        return dark ? SWITCH_THEME_TEXT_DARK : SWITCH_THEME_TEXT_LIGHT;
    }

    public static Color getTitleText() {
        return dark ? SWITCH_THEME_TEXT_DARK : SWITCH_THEME_TEXT_LIGHT;
    }

    public static Color getSubredditInputBackground() { return dark ? CONTENT_PANEL_BACKGROUND_DARK : CONTENT_PANEL_BACKGROUND_LIGHT; }

    public static Color getSearchBackground() { return dark ? CONTENT_PANEL_BACKGROUND_DARK : CONTENT_PANEL_BACKGROUND_LIGHT; }

    public static Color getAxisBackground() {
        return dark ? AXIS_BACKGROUND_DARK : AXIS_BACKGROUND_LIGHT;
    }

    public static Color getElementColor() {
        return ELEMENT_COLOR;
    }

}

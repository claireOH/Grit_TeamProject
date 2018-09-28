package ViewBase;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Set_Size {
    public static int      screenWidth, screenHeight;

    public static int      topPn_width, topPn_height;
    public static int      btmPn_width, btmPn_height;

    public static int       separ_width, separ_height;

    public static int      leftPn_width, rightPn_width, btnPn_width;
    public static int      pnInBtmPn_height;

    /* frame */
    public static void setFrameSize() {
        /* Monitor device to use size measurements */
        Toolkit toolkit             = Toolkit.getDefaultToolkit();
        Dimension   screenSize      = toolkit.getScreenSize();

        /* Monitor screen size to be used */
        screenWidth                 = (int)screenSize.getWidth();
        screenHeight                = (int)screenSize.getHeight();

    }

    /* top panel */
    public static void setTopPanelSize() {
        topPn_width                 = screenWidth;
        topPn_height                = (screenHeight / 10) -10;
    }

    /* bottom panel */
    public static void setBtmPanelSize() {
        btmPn_width                 = screenWidth;
        btmPn_height                = (screenHeight - topPn_height) - 2;
    }

    /* separator */
    public static void setSeparatorSize() {
        separ_width = screenWidth - 24;
        separ_height = 11;
    }

    /* panel in the bottom panel */
    public static void setLeft_RightPanelSize () {
        pnInBtmPn_height            = btmPn_height - 20;

        btnPn_width                 = ((btmPn_width - 48) / 10) * 1 - 50;
        leftPn_width                = ((btmPn_width - 48) / 10) * 3 + 50;
        rightPn_width               = ((btmPn_width - 48) / 10) * 6;

    }
}

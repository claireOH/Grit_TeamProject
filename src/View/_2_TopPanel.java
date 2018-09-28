package View;

import ViewBase.Set_Font;
import ViewBase.Set_ImageIcon;
import ViewBase.Set_Size;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class _2_TopPanel extends JPanel {
    
    JLabel      currentTime_lb, classRoom_lb;
    JButton     main_btn;
    
    String      timeText;

    public _2_TopPanel () {
        
        Set_Size.setTopPanelSize();

        this.setBackground(Color.WHITE);
        this.setLocation(0, 0);
        this.setLayout(null);

        /* -------------------------------------------------------- */

        /* definition component in the top panel */
        currentTime_lb          = new JLabel();
        classRoom_lb            = new JLabel();
        main_btn                = new JButton();

        /* -------------------------------------------------------- */

        /* current time label */
        currentTime_lb.setBackground(Color.WHITE);
        currentTime_lb.setVerticalAlignment(SwingConstants.BOTTOM);
        currentTime_lb.setFont(Set_Font.setBoldFont(50));
        currentTime_lb.setBounds(12, 10, (Set_Size.topPn_width / 3), (Set_Size.topPn_height - 20));

        /* current time check thread */
        Runnable    runnable    = new RunnableTime();
        Thread      thread      = new Thread(runnable);

        thread.start();

        /* -------------------------------------------------------- */

        /* current classroom label */
        classRoom_lb.setHorizontalAlignment(SwingConstants.RIGHT);
        classRoom_lb.setVerticalAlignment(SwingConstants.BOTTOM);
        classRoom_lb.setText("講義室： 200室");
        classRoom_lb.setFont(Set_Font.setBoldFont(40));
        classRoom_lb.setBackground(Color.WHITE);
        classRoom_lb.setBounds(1450, 10, 340, (Set_Size.topPn_height - 20));

        /* -------------------------------------------------------- */

        /* main home button */
        main_btn.setBounds(1813, 10, 100, (Set_Size.topPn_height - 20));
        main_btn.setBackground(Color.DARK_GRAY);
        main_btn.setBorderPainted(false);
        main_btn.setFocusPainted(false);
        main_btn.setIcon(Set_ImageIcon.scan);

        main_btn.setActionCommand("register");

        /* -------------------------------------------------------- */

        /* add components */
        this.add(currentTime_lb);
        this.add(classRoom_lb);
        this.add(main_btn);

        /* view settings */
        this.setVisible(true);

        /* -------------------------------------------------------- */

    }

    private class RunnableTime implements Runnable {

        /* set time output */
        SimpleDateFormat    simpleDateFormat_cal    = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat    simpleDateFormat_time   = new SimpleDateFormat("HH:mm:ss");

        @Override
        public void run() {
            /* real time renewal */
            while (true) {
                Calendar    cal     = Calendar.getInstance();

                /* set current time label */
                timeText     = simpleDateFormat_cal.format(cal.getTime()) + " " +
                                simpleDateFormat_time.format(cal.getTime());

                currentTime_lb.setText(timeText);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

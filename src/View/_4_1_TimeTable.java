package View;

import Singleton.SingleClass_web;
import ViewBase.Set_Font;
import ViewBase.Set_Size;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class _4_1_TimeTable extends JPanel {

    SingleClass_web                     single;

    JPanel                              title_pn, content_pn;
    JLabel                              title_lb;

    Timer                               update_timer, change_nextDay_timer, change_today_timer;
    
    Calendar                            midnight, nextMidnight;
    Calendar                            clock9, clock21, current;
    Calendar                            nextHour, nextDay;
    
    SimpleDateFormat                    dateFormat;

    Vector<JTextField>                  time_vec, lecture_vec;
    
    LinkedHashMap<Integer, String>      timeTable_hm;

    String[]                            timeString = {"09:00 ~ 09:50", "10:00 ~ 10:50", "11:00 ~ 11:50", "12:00 ~ 12:50",
                                                        "13:00 ~ 13:50", "14:00 ~ 14:50", "15:00 ~ 15:50", "16:00 ~ 16:50",
                                                        "17:00 ~ 17:50", "18:00 ~ 18:50", "19:00 ~ 19:50", "20:00 ~ 20:50"};

    public _4_1_TimeTable () {
        
        /* set time table panel */
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        /* -------------------------------------------------------- */

        /* definition component in the time table panel */
        title_pn                = new JPanel();
        content_pn              = new JPanel();

        title_lb                = new JLabel();

        update_timer            = new Timer();
        change_nextDay_timer    = new Timer();
        change_today_timer      = new Timer();

        time_vec                = new Vector<JTextField>();
        lecture_vec             = new Vector<JTextField>();

        timeTable_hm            = new LinkedHashMap<Integer, String>();

        current                 = Calendar.getInstance();
        midnight                = Calendar.getInstance();
        nextMidnight            = Calendar.getInstance();
        clock9                  = Calendar.getInstance();
        clock21                 = Calendar.getInstance();
        nextHour                = Calendar.getInstance();
        nextDay                 = Calendar.getInstance();

        dateFormat              = new SimpleDateFormat("HH:mm:ss");

        /* -------------------------------------------------------- */

        /* title panel */
        title_pn.setBackground(Color.WHITE);
        title_pn.setBounds(0, 0, 600, 65);
        title_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* title label */
        title_lb.setText("時間割り");
        title_lb.setFont(Set_Font.setBoldFont(30));
        title_lb.setBackground(Color.WHITE);
        title_lb.setBounds(10, 10, 1105, 55);

        /* -------------------------------------------------------- */

        /* add title label in the title panel */
        title_pn.add(title_lb);

        /*  -------------------------------------------------------- */

        /* content panel */
        content_pn.setBackground(Color.WHITE);
        content_pn.setBounds(0, 65, 600, Set_Size.pnInBtmPn_height - 65);
        content_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* check real time */
        /* update timetable immediately after midnight */
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.set(Calendar.MILLISECOND, 0);

        nextMidnight.set(Calendar.DATE, current.get(Calendar.DATE) + 1);
        nextMidnight.set(Calendar.MINUTE, 0);
        nextMidnight.set(Calendar.SECOND, 0);
        nextMidnight.set(Calendar.MILLISECOND, 0);

        /* update */
        if (current.after(midnight) && current.before(nextMidnight)) {
            update();
        }

        update_timer.scheduleAtFixedRate(new Update_timeTable(), nextMidnight.getTime(), 1000 * 60 * 60 * 24);

        /* -------------------------------------------------------- */

        /* change color timer */
        /* set time */
        clock9.set(Calendar.HOUR_OF_DAY, 9);
        clock9.set(Calendar.MINUTE, 0);
        clock9.set(Calendar.SECOND, 0);
        clock9.set(Calendar.MILLISECOND, 0);

        clock21.set(Calendar.HOUR_OF_DAY, 21);
        clock21.set(Calendar.MINUTE, 0);
        clock21.set(Calendar.SECOND, 0);
        clock21.set(Calendar.MILLISECOND, 0);

        nextHour.set(Calendar.HOUR_OF_DAY, current.get(Calendar.HOUR_OF_DAY) + 1);
        nextHour.set(Calendar.MINUTE, current.get(Calendar.MINUTE) + 1);
        nextHour.set(Calendar.SECOND, 0);
        nextHour.set(Calendar.MILLISECOND, 0);

        nextDay.set(Calendar.DATE, current.get(Calendar.DATE) + 1);
        nextDay.set(Calendar.HOUR_OF_DAY, 9);
        nextDay.set(Calendar.MINUTE, 0);
        nextDay.set(Calendar.SECOND, 0);
        nextDay.set(Calendar.MILLISECOND, 0);

        /* change color */
        if (current.after(clock9) && current.before(clock21)) {
            /* run immediately*/
            change();
        } 
        /* run at 9 o'clock the next day */
        change_today_timer.schedule(new ChangeColor_timeTable(), current.get(Calendar.HOUR_OF_DAY) + 1, 1000 * 60 * 60);
        
        /* -------------------------------------------------------- */

        /* add component to the user confirm panel */
        this.add(title_pn);
        this.add(content_pn);

        /* component visible or invisible*/
        this.setVisible(true);

    }

    /* add textField in the content panel */
    private void addTextField (Boolean existence) {
        String lecture_name = "";

        /* -------------------------------------------------------- */

        for (int tfCount = 0, col = 5, strCount = 0, period = 1 ; tfCount < 24 ; tfCount++) {
            JTextField  textField       = new JTextField();

            /* set textField */
            textField.setBackground(Color.WHITE);
            textField.setBorder(null);
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setEditable(false);

            /* -------------------------------------------------------- */
            /* set time textField */
            if (tfCount % 2 == 0) {
                textField.setBounds(5, col, 170, 73);
                textField.setFont(Set_Font.setBoldFont(24));
                textField.setText(timeString[strCount++]);

                time_vec.add(textField);

            } else { /* set lecture field */
                textField.setBounds(175, col, 420, 73);
                textField.setFont(Set_Font.setBoldFont(24));
                col += 73;

                /* -------------------------------------------------------- */

                /* check lecture */
                if (timeTable_hm != null) {
                    lecture_name = timeTable_hm.get(period++);
                }
                
                if (existence && lecture_name != null) {
                    textField.setText(lecture_name);
                } else {
                    textField.setText("");
                }

                lecture_vec.add(textField);
            }
            
            /* -------------------------------------------------------- */
        }

    }
    
    private void addTextFieldInPanel () {
        /* add to the panel */
        for (int count = 0 ; count < timeString.length ; count++) {
            content_pn.add(time_vec.get(count));
            content_pn.add(lecture_vec.get(count));
        }
    }

    private void update () {
        
        single      = SingleClass_web.getInstance();
        
        /* -------------------------------------------------------- */

        /* get time table from the database at midnight */
        timeTable_hm                = single.model_web.get_timeTable();
        
        /* -------------------------------------------------------- */

        /* check today's day */
        if (timeTable_hm == null) {
            addTextField(false);
            addTextFieldInPanel();
        } else {
            addTextField(true);
            addTextFieldInPanel();
        }
        
    }
    
    private void change () {
        
        /* Check the current time and current Hour */
        int currentHour = current.get(Calendar.HOUR_OF_DAY);

        /* -------------------------------------------------------- */
        
        /*Check the current lecture on the timetable */
        for (int vecCount = 0, hour = 9; vecCount < time_vec.size(); vecCount++, hour++) {
            
            if (currentHour != hour) {
                time_vec.get(vecCount).setBackground(Color.WHITE);
                lecture_vec.get(vecCount).setBackground(Color.WHITE);
            } else {
                (time_vec.get(vecCount)).setBackground(new Color(250, 236, 178));
                (lecture_vec.get(vecCount)).setBackground(new Color(250, 236, 178));
            }
            
        }
    }

    private class Update_timeTable extends TimerTask {
        @Override
        public void run() {
            update();
        }
    }

    private class ChangeColor_timeTable extends TimerTask {
        @Override
        public void run() {
            change();
            addTextFieldInPanel();
        }
    }
}

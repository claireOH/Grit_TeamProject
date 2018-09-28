package View;

import ViewBase.Set_Font;
import ViewBase.Set_Size;
import java.awt.CardLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class _3_BottomPanel extends JPanel {
    
    JPanel                          left_pn, right_pn, btn_pn;
    JButton                         signIn_btn, signOut_btn;

    LineBorder              lineBorder;

    CardLayout              left_cards, right_cards;

    _4_1_TimeTable          timeTable;
    _4_2_Check              check;
    _5_1_Status             status;
    _5_2_Confirm            confirm;
    _5_3_Register           register;
    _5_4_Result             result;

    Process                 process;
    
    String                  std_id;
    
    Timer                   status_timer;
    
    String                  currentPanelOfRightCards;

    public _3_BottomPanel () {
        
        /* set the bottom panel */
        Set_Size.setBtmPanelSize();
        Set_Size.setLeft_RightPanelSize();

        this.setLocation(0, 0);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        /* -------------------------------------------------------- */

        /* definition component in the bottom panel */
        left_cards          = new CardLayout(0, 0);
        right_cards         = new CardLayout(0, 0);
        
        currentPanelOfRightCards = "status";

        lineBorder          = new LineBorder(Color.BLACK);

        left_pn             = new JPanel();
        right_pn            = new JPanel();
        btn_pn              = new JPanel();

        signIn_btn          = new JButton();
        signOut_btn         = new JButton();
        
        status_timer       = new Timer();

        /* -------------------------------------------------------- */

        /* left panel */
        left_pn.setBorder(lineBorder);
        left_pn.setBackground(Color.WHITE);
        left_pn.setBounds(12, Set_Size.screenHeight - Set_Size.btmPn_height + 10, 600, Set_Size.pnInBtmPn_height);
        left_pn.setLayout(left_cards);

        /* -------------------------------------------------------- */

        /* right panel */
        right_pn.setBorder(lineBorder);
        right_pn.setBackground(Color.WHITE);
        right_pn.setBounds(624, Set_Size.screenHeight - Set_Size.btmPn_height + 10, 1125, Set_Size.pnInBtmPn_height);
        right_pn.setLayout(right_cards);

        /* -------------------------------------------------------- */

        /* set the card layout */

        timeTable           = new _4_1_TimeTable();
        left_pn.add(timeTable, "timeTable");

        check               = new _4_2_Check();
        left_pn.add(check, "check");

        status              = new _5_1_Status();
        right_pn.add(status, "status");
        
        confirm             = new _5_2_Confirm();
        right_pn.add(confirm, "confirm");

        register            = new _5_3_Register();
        right_pn.add(register, "register");
        
        result              = new _5_4_Result();
        right_pn.add(result, "info");

        /* -------------------------------------------------------- */
        
        /* status renewal */
        status_timer.schedule(new Update_status(), 0, 1000 * 30);
        
        /* -------------------------------------------------------- */

        /* button panel */
        btn_pn.setBackground(Color.WHITE);
        btn_pn.setBounds(Set_Size.btmPn_width - 162, Set_Size.screenHeight - Set_Size.btmPn_height + 10, 150, Set_Size.pnInBtmPn_height);
        btn_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* set the sign in button */
        signIn_btn.setFont(Set_Font.setBoldFont(30));
        signIn_btn.setBackground(new Color(39, 174, 96));
        signIn_btn.setForeground(Color.WHITE);
        signIn_btn.setBounds(0, 0, 150, 75);
        signIn_btn.setText("登校");
        signIn_btn.setBorderPainted(false);
        signIn_btn.setFocusPainted(false);
        signIn_btn.setActionCommand("sign_in");

        /* set the sign out button */
        signOut_btn.setFont(Set_Font.setBoldFont(30));
        signOut_btn.setBackground(new Color(41, 132, 189));
        signOut_btn.setForeground(Color.WHITE);
        signOut_btn.setBounds(0, 85, 150, 75);
        signOut_btn.setText("下校");
        signOut_btn.setBorderPainted(false);
        signOut_btn.setFocusPainted(false);
        signOut_btn.setActionCommand("sign_out");

        /* add component in the button panel */
        btn_pn.add(signIn_btn);
        btn_pn.add(signOut_btn);

        /* -------------------------------------------------------- */

        /* add component int the bottom panel */
        this.add(left_pn);
        this.add(right_pn);
        this.add(btn_pn);

        /* component visible or invisible */
        this.setVisible(true);

        /* -------------------------------------------------------- */
    }

    private class Update_status extends TimerTask {
        @Override
        public void run() {
            
            if (currentPanelOfRightCards == "status") {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat    format = new SimpleDateFormat("HH:mm:ss");
                String time = format.format(cal.getTime());
                System.out.println(time + " status_update");
                
                _5_1_Status status_1 = new _5_1_Status();
                right_pn.add(status_1, "status");
                status.revalidate();
                status.repaint();
                right_cards.show(right_pn, "status");
            }
            
        }
    }
}

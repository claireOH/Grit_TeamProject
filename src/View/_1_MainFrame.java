package View;

import Singleton.SingleClass_web;
import Singleton.SingleClass_raspi;
import ViewBase.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

public class _1_MainFrame extends JFrame {

    _2_TopPanel             top_pn;
    _3_BottomPanel          btm_pn;
    JSeparator              separator;

    ArrayList<String>       infoResult;
    
    String                  id;
    String                  password;
    
    Process                 process;
    
    URL                     photo_link;
    
    /* Check whether the fingerprint registration number stored in the raspberry pi internal DB is present */
    int                 check_dialog_result = -1;
    
    /* Fingerprint registration number stored inside the raspberry pi */
    int                 scan_position;
    
    public _1_MainFrame () {
        
        /* take the monitor screen size */
        Set_Size.setFrameSize();

        /* program position */
        this.setLocation(0, 0);

        /* delete the title bar */
        this.setUndecorated(true);

        /* program size */
        this.setSize(Set_Size.screenWidth, Set_Size.screenHeight);

        /* resize option */
        this.setResizable(false);

        /* exit option */
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* -------------------------------------------------------- */

        /* definition in the main frame */
        top_pn          = new _2_TopPanel();
        btm_pn          = new _3_BottomPanel();
        separator       = new JSeparator();
        
        /* -------------------------------------------------------- */
        
        /* set the panel size */
        Set_Size.setTopPanelSize();
        Set_Size.setBtmPanelSize();
        Set_Size.setSeparatorSize();

        /* -------------------------------------------------------- */

        /* top panel */
        top_pn.setBounds(0, 0, Set_Size.topPn_width, Set_Size.topPn_height);

        /* add event to the fingerprint register and home button */
        (top_pn.main_btn).addActionListener(new MainBtn_ActionEvent());

        /* -------------------------------------------------------- */

        /* bottom panel */
        btm_pn.setBounds(0, Set_Size.topPn_height + 2, Set_Size.btmPn_width, Set_Size.btmPn_height);

        /* add event to the user confirm button */
        (btm_pn.confirm.confirm_btn).addActionListener(new Login_ActionEvent());
        
        (btm_pn.signIn_btn).addActionListener(new attend_ActionEvent());
        (btm_pn.signOut_btn).addActionListener(new attend_ActionEvent());
        
        
        (btm_pn.register.register_btn).addActionListener(new Register_ActionEvent());
        
        (btm_pn.result.result_ok_btn).addActionListener(new Register_ActionEvent());
        
        (btm_pn.check.check_btn).addActionListener(new attend_ActionEvent());

        /* -------------------------------------------------------- */

        /* line */
        separator.setForeground(Color.BLACK);
        separator.setBounds(12, Set_Size.topPn_height + 1, Set_Size.separ_width, Set_Size.separ_height);

        /* -------------------------------------------------------- */

        /* add component in the main frame */
        this.add(top_pn);
        this.add(separator);
        this.add(btm_pn);

        /* component visible or invisible */
        this.setVisible(true);

        /* -------------------------------------------------------- */
        
    }
    
    private class MainBtn_ActionEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            /* set main button in the top panel */
            if (e.getActionCommand().equals("register")) {
                /* fingerprint regist button */
                (btm_pn.right_cards).show(btm_pn.right_pn, "confirm");

                /* change the button's properties */
                (top_pn.main_btn).setText("");
                (top_pn.main_btn).setIcon(Set_ImageIcon.home);
                (top_pn.main_btn).setActionCommand("home");
                
                /* -------------------------------------------------------- */
                
                btm_pn.currentPanelOfRightCards = "confirm";
                
            } else if (e.getActionCommand().equals("home")) {
                /* home button */
                (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                (btm_pn.right_cards).show(btm_pn.right_pn, "status");

                /* change the button's properties */
                (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                (top_pn.main_btn).setActionCommand("register");

                /* textField and passwordField initialization */
                (btm_pn.confirm.id_tf).setText("");
                (btm_pn.confirm.password_pf).setText("aaaa");
                
                btm_pn.currentPanelOfRightCards = "status";
                
            }

            /* -------------------------------------------------------- */
        }
    }
    
    private class Login_ActionEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /* set confirm button in the user confirm panel */
            if (e.getActionCommand().equals("confirm")) {

                btm_pn.confirm.currentInputFieldCheck = 0;
                
                id       = (btm_pn.confirm.id_tf.getText());
                password = "";
                
                /* -------------------------------------------------------- */

                /* if there is no id or password entered, warning window is displayed */
                if (id.length() != 0 && (btm_pn.confirm.password_pf.getPassword()).length != 0) {

                    /* -------------------------------------------------------- */

                    /* extract an array of passwords */
                    for (int pwd_length = 0 ; pwd_length < btm_pn.confirm.password_pf.getPassword().length ; pwd_length++) {
                        password += (btm_pn.confirm.password_pf.getPassword())[pwd_length];
                    }

                    /* -------------------------------------------------------- */

                    /* check input id and password */
                    infoResult = SingleClass_web.getInstance().model_web.post_confirmResult(id, password);

                    /* -------------------------------------------------------- */

                    /* login result */
                    if (infoResult != null) {

                        new ConfirmDialog(true);
                        
                        /* -------------------------------------------------------- */

                        /* Access to the DB inside the raspberry pi */
                        SingleClass_raspi sing = SingleClass_raspi.getInstance();

                        /* -------------------------------------------------------- */
                        
                        /* If a fingerprint is already registered */
                        if (sing.model_raspi.get_fingerprintNum(id) != -1) {

                            int result = sing.model_raspi.get_fingerprintNum(id);

                            new InfoDialog();
                            
                        /* -------------------------------------------------------- */

                        } else {
                            /* When the login is successful, check whether there is a fingerprint registered for the student number */
                            /* Access to the DB inside the raspberry pi */

                            (btm_pn.register.name_lb).setText("名前 : " + infoResult.get(1));
                            (btm_pn.register.id_lb).setText("学籍番号" + infoResult.get(0));
                            (btm_pn.right_cards).show(btm_pn.right_pn, "register");
                            
                            btm_pn.currentPanelOfRightCards = "register";

                            /* -------------------------------------------------------- */
                            
                            /* After switching the screen, fingerprint recognition is performed after 2 seconds */
                            Runnable    scan_runnable    = new Scan_runnable();
                            Thread      scan_thread      = new Thread(scan_runnable);

                            scan_thread.start();

                        }
                        
                        /* -------------------------------------------------------- */

                    } else {
                        new ConfirmDialog(false);
                    }
                    
                    /* -------------------------------------------------------- */

                }
            }
        
        /* -------------------------------------------------------- */
        }
        
    }
    
    private class Register_ActionEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /* Fingerprint registration */
            if (e.getActionCommand().equals("registerCancel")) {
                (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                (btm_pn.status).removeAll();

                _5_1_Status attendanceStatus_1    = new _5_1_Status();
                (btm_pn.right_pn).add(attendanceStatus_1, "status");
                (btm_pn.status).revalidate();
                (btm_pn.status).repaint();
                (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                
                btm_pn.currentPanelOfRightCards = "status";

                /* change the button's properties */
                (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                (top_pn.main_btn).setActionCommand("register");
                
            } else if (e.getActionCommand().equals("registerComplete")) {
                (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                (btm_pn.status).removeAll();

                _5_1_Status attendanceStatus_1    = new _5_1_Status();
                (btm_pn.right_pn).add(attendanceStatus_1, "status");
                (btm_pn.status).revalidate();
                (btm_pn.status).repaint();
                (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                
                btm_pn.currentPanelOfRightCards = "status";

                /* change the button's properties */
                (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                (top_pn.main_btn).setActionCommand("register");
            }
        }
    }
    
    private class attend_ActionEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String                  std_id          = "";
            ArrayList<String>       result_list     = new ArrayList<>();

            /* -------------------------------------------------------- */
            
            if (e.getActionCommand().equals("sign_in")) {
                
                new CheckDialog();
                
                /* -------------------------------------------------------- */
                
                if (check_dialog_result == 1) {
                    /* Raspberry pie DB connection */
                    SingleClass_raspi single_rasp = SingleClass_raspi.getInstance();

                    /* -------------------------------------------------------- */

                    /* If there is a fingerprint registration number registered in Raspberry Pi, */
                    /* and there is an ID number registered in the Raspberry Pie DB with the registration number */
                    if (scan_position != -1 && single_rasp.model_raspi.get_stdId(scan_position) != null) {

                        /* Confirm the student number registered with the current fingerprint registration number */
                        std_id = single_rasp.model_raspi.get_stdId(scan_position);

                        /* -------------------------------------------------------- */

                        /* Server connection and school time registration */
                        SingleClass_web     single      = SingleClass_web.getInstance();

                        result_list = single.model_web.post_attendance("sign_in", std_id);
                        
                        System.out.println(result_list.size());

                        /* -------------------------------------------------------- */

                        /* When a return value exists and the value size is 3 */
                        /* If the return value is 1, send the failure phrase */
                        if (result_list != null && result_list.size() > 1) {

                            /* -------------------------------------------------------- */

                            /* Output the student number, name, school time */
                            (btm_pn.check.id_lb).setText("学籍番号 : " + result_list.get(0));
                            (btm_pn.check.name_lb).setText("名前 : " + result_list.get(1));
                            (btm_pn.check.time_lb).setText(top_pn.timeText);

                            /* -------------------------------------------------------- */

                            /* Attendance check completed screen switching */
                            (btm_pn.left_cards).show(btm_pn.left_pn, "check");

                            /* -------------------------------------------------------- */

                            /* Timer returning to main from attendance check screen */
                            Timer   sign_in_timer       = new Timer(4000, new ActionListener () {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                                    (btm_pn.status).removeAll();

                                    _5_1_Status attendanceStatus_1    = new _5_1_Status();
                                    (btm_pn.right_pn).add(attendanceStatus_1, "status");
                                    (btm_pn.status).revalidate();
                                    (btm_pn.status).repaint();
                                    (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                                    
                                    btm_pn.currentPanelOfRightCards = "status";

                                    /* change the button's properties */
                                    (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                                    (top_pn.main_btn).setActionCommand("register");
                                }
                            });

                            sign_in_timer.setRepeats(false);
                            sign_in_timer.start();

                            /* -------------------------------------------------------- */

                        /* failed */
                        } else if (result_list.size() == 1) {                
                            new sign_errorDialog("今日はもう出席しました");
                        } 
                    }
                }
                
                /* -------------------------------------------------------- */
            } else if (e.getActionCommand().equals("sign_out")) {
                
                new CheckDialog();
                
                /* -------------------------------------------------------- */

                if (check_dialog_result == 1) {
                    /* Raspberry pie DB connection */
                    SingleClass_raspi single_rasp = SingleClass_raspi.getInstance();

                    /* -------------------------------------------------------- */

                    if (scan_position != -1 && single_rasp.model_raspi.get_stdId(scan_position) != null) {
                        std_id = single_rasp.model_raspi.get_stdId(scan_position);
                    }

                    /* -------------------------------------------------------- */

                    /* When a return value exists and the value size is 3 */
                    /* If the return value is 1, send the failure phrase */
                    SingleClass_web     single      = SingleClass_web.getInstance();
                                    result_list = single.model_web.post_attendance("sign_out", std_id);

                    /* -------------------------------------------------------- */

                    /* return to home */
                    if (result_list != null && result_list.size() > 1) {
                            new sign_outDialog();
                    /* -------------------------------------------------------- */
                    } else if (result_list.size() == 1) {
                        String result_string = result_list.toString();
                        String guide_string = "";
                        // exception
                        // 1 If you leave school without going to school
                        System.out.println(result_string);
                        
                        if (result_string.equals("[최근 등교 내역이 없습니다.]")) {
                            guide_string = "今日は登校していません";
                        }
                        // 2 If you go to school and go to school
                        else if (result_string.equals("[오늘은 이미 하교하셨습니다.]")) {
                            guide_string = "今日はもう下校しました";
                        }
                        
                        if (guide_string.length() != 0) {
                            new sign_errorDialog(guide_string);
                        } else {
                            
                        }
                    }
                } 
                
            /* -------------------------------------------------------- */    
            } else if (e.getActionCommand().equals("attendComplete")) {
                (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                (btm_pn.status).removeAll();

                _5_1_Status attendanceStatus_1    = new _5_1_Status();
                (btm_pn.right_pn).add(attendanceStatus_1, "status");
                (btm_pn.status).revalidate();
                (btm_pn.status).repaint();
                (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                
                btm_pn.currentPanelOfRightCards = "status";

                /* change the button's properties */
                (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                (top_pn.main_btn).setActionCommand("register");
            }
        }
    }
    
    
    private class sign_errorDialog extends Set_Dialog implements ActionListener {
        sign_errorDialog (String guide_text) {
            /* position */
            setLocationRelativeTo(this);
            
            /* -------------------------------------------------------- */
            
            guide_lb.setText(guide_text);
            check_btn.setText("OK");
            check_btn.setBackground(new Color(52, 152, 219));
            check_btn.addActionListener(this);
            
            /* -------------------------------------------------------- */
            
            this.setVisible(true);
            
            /* -------------------------------------------------------- */
            
            /* End timer */
            Timer       sign_error_timer  = new Timer(2000, new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                }
                
            });
            
            sign_error_timer.setRepeats(false);
            sign_error_timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.dispose();
            
            (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
        }
    }
    
    private class sign_outDialog extends Set_Dialog implements ActionListener {
        sign_outDialog () {
            
            /* position */
            setLocationRelativeTo(this);
            
            /* -------------------------------------------------------- */
            
            guide_lb.setText("出席のチェックが完了されました");
            check_btn.setText("OK");
            check_btn.setBackground(new Color(52, 152, 219));
            check_btn.addActionListener(this);
            
            this.setVisible(true);
            
            /* -------------------------------------------------------- */
            
            Timer       sign_out_timer  = new Timer(2000, new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                }
                
            });
            
            sign_out_timer.setRepeats(false);
            sign_out_timer.start();
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            this.dispose();
            
            (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
            
        }
    }
    
    private class ConfirmDialog extends Set_Dialog implements ActionListener {

        ConfirmDialog (Boolean checkValue) {
            setLocationRelativeTo(this);
            
            /* -------------------------------------------------------- */

            /* confirm success */
            if (checkValue) {
                /* label */
                guide_lb.setText("認証完了");
                guide_lb.setHorizontalTextPosition(SwingConstants.CENTER);

                /* button */
                check_btn.setBackground(new Color(52, 152, 219));
                check_btn.setText("OK");
                check_btn.setActionCommand("success");
                
            /* -------------------------------------------------------- */
            } else {
                /* label */
                guide_lb.setText("ID or password incorrect.");
                guide_lb.setHorizontalTextPosition(SwingConstants.CENTER);

                /* button */
                check_btn.setBackground(new Color(231, 76, 60));
                check_btn.setText("OK");
                check_btn.setActionCommand("false");
            }
            
            /* -------------------------------------------------------- */
            
            Timer       confirm_timer = new Timer(1000, new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();

                    /* initialization */
                    btm_pn.confirm.id_tf.setText("");
                    btm_pn.confirm.password_pf.setText("aaaa");
                }
                
            });
            
            confirm_timer.setRepeats(false);
            confirm_timer.start();
            
            /* -------------------------------------------------------- */
            
            /* add event to the check button */
            check_btn.addActionListener(this);
            
            /* -------------------------------------------------------- */

            /* component visible or invisible */
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            /* dialog exit */
            this.dispose();

            /* initialization */
            btm_pn.confirm.id_tf.setText("");
            btm_pn.confirm.password_pf.setText("aaaa");
           
        }
        
    }
    
    private class InfoDialog extends JDialog implements ActionListener {

        JPanel      cover_pn, photo_pn, info_pn;
        JLabel      photo_lb, name_lb, id_lb, guide_lb;
        JButton     check_btn;

        public InfoDialog () {

            /* -------------------------------------------------------- */

            /* Exit settings */
            this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            /* Remove title bar */
            this.setUndecorated(true);

            /* At the end of the dialog, no other panel can be selected */
            this.setModal(true);

            /* size */
            this.setBounds(Set_Size.btmPn_width / 2 - 325, Set_Size.pnInBtmPn_height / 2 - 105, 650, 210);

            /* border */
            this.getRootPane().setBorder(new LineBorder(Color.BLACK));

            /* -------------------------------------------------------- */

            /* definition */
            cover_pn            = new JPanel();
            photo_pn            = new JPanel();
            info_pn             = new JPanel();
            photo_lb            = new JLabel();
            name_lb             = new JLabel();
            id_lb               = new JLabel();
            guide_lb            = new JLabel();
            check_btn           = new JButton();

            /* -------------------------------------------------------- */

            /* cover panel */
            cover_pn.setBackground(Color.WHITE);
            cover_pn.setBounds(0, 0, 650, 210);
            cover_pn.setLayout(null);

            /* -------------------------------------------------------- */

            /* picture panel */
            photo_pn.setBackground(Color.DARK_GRAY);
            photo_pn.setBounds(0, 0, 150, 210);
            photo_pn.setLayout(null);

            /* -------------------------------------------------------- */

            /* information panel */
            info_pn.setBackground(Color.WHITE);
            info_pn.setBounds(150, 0, 500, 210);
            info_pn.setLayout(null);

            /* -------------------------------------------------------- */

            /* picture panel */
            String photo_link = "13.124.213.132" + infoResult.get(2);
            photo_lb.setIcon(new ImageIcon(photo_link));
            photo_lb.setBounds(0, 0, 150, 210);

            photo_pn.add(photo_lb);

            /* -------------------------------------------------------- */

            /* name label */
            name_lb.setFont(Set_Font.setPlainFont(25));
            name_lb.setForeground(Color.BLACK);
            name_lb.setText("名前 : " + infoResult.get(1));
            name_lb.setBounds(15, 15, 400, 30);
            name_lb.setHorizontalAlignment(SwingConstants.LEFT);

            /* std_id label */
            id_lb.setFont(Set_Font.setPlainFont(25));
            id_lb.setForeground(Color.BLACK);
            id_lb.setText("学籍番号 : " + infoResult.get(0));
            id_lb.setBounds(15, 50, 400, 30);
            id_lb.setHorizontalAlignment(SwingConstants.LEFT);

            /* guide label */
            guide_lb.setFont(Set_Font.setBoldFont(30));
            guide_lb.setForeground(Color.BLACK);
            guide_lb.setText("もう登録が完了されています");
            guide_lb.setBounds(15, 90, 450, 50);
            guide_lb.setHorizontalAlignment(SwingConstants.LEFT);

            /* -------------------------------------------------------- */

            /* check button */
            check_btn.setBounds(410, 150, 70, 50);
            check_btn.setBorderPainted(false);      /* 테두리 */
            check_btn.setFocusPainted(false);       /* 클릭 이벤트 제거 */
            check_btn.setFont(Set_Font.setPlainFont(20));
            check_btn.setForeground(Color.BLACK);
            check_btn.addActionListener(this);
            check_btn.setBackground(Color.WHITE);
            check_btn.setText("OK");

            /* -------------------------------------------------------- */
            
            /* add component */
            info_pn.add(name_lb);
            info_pn.add(id_lb);
            info_pn.add(guide_lb);
            info_pn.add(check_btn);

            /* -------------------------------------------------------- */

            /* add component */
            cover_pn.add(photo_pn);
            cover_pn.add(info_pn);

            /* -------------------------------------------------------- */

            Timer   info_timer  = new Timer(5000, new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    
                    (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                    (btm_pn.status).removeAll();

                    _5_1_Status attendanceStatus_1    = new _5_1_Status();
                    (btm_pn.right_pn).add(attendanceStatus_1, "status");
                    (btm_pn.status).revalidate();
                    (btm_pn.status).repaint();
                    (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                    
                    btm_pn.currentPanelOfRightCards = "status";

                    /* change the button's properties */
                    (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                    (top_pn.main_btn).setActionCommand("register");
                }
                
            });
            
            info_timer.setRepeats(false);
            info_timer.start();
            
            /* -------------------------------------------------------- */
            
            /* add component */
            this.add(cover_pn);
            this.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(check_btn)) {
                this.dispose();
                
                (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                (btm_pn.status).removeAll();

                _5_1_Status attendanceStatus_1    = new _5_1_Status();
                (btm_pn.right_pn).add(attendanceStatus_1, "status");
                (btm_pn.status).revalidate();
                (btm_pn.status).repaint();
                (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                
                btm_pn.currentPanelOfRightCards = "status";

                /* change the button's properties */
                (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                (top_pn.main_btn).setActionCommand("register");
            }
        }

    }
    
    private class Already_fingerprintDialog extends Set_Dialog implements ActionListener {
        Already_fingerprintDialog () {
            setLocationRelativeTo(this);
            
            /* -------------------------------------------------------- */
            
            guide_lb.setText("もう登録している指紋です");
            guide_lb.setHorizontalTextPosition(SwingConstants.CENTER);
            
            check_btn.setBackground(new Color(231, 76, 60));
            check_btn.setText("OK");
            check_btn.setActionCommand("already");
            check_btn.addActionListener(this);
            
            /* -------------------------------------------------------- */
            
            Timer   already_timer  = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            
                            (btm_pn.right_cards).show(btm_pn.right_pn, "confirm");
                            
                            btm_pn.currentPanelOfRightCards = "confirm";

                        }
                    });

            already_timer.setRepeats(false);
            already_timer.start();
            
            /* -------------------------------------------------------- */
            
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.dispose();
            
            (btm_pn.right_cards).show(btm_pn.right_pn, "confirm");
            
            btm_pn.currentPanelOfRightCards = "confirm";

        }
    }
    
    private class CheckDialog extends Set_Dialog implements ActionListener {
        CheckDialog () {
            
            /* -------------------------------------------------------- */
            
            Runnable    check_runnable      = new Check_runnable();
            Thread      check_thread        = new Thread(check_runnable);

            check_thread.start();
            
            /* -------------------------------------------------------- */
            
            setLocationRelativeTo(this);
            
            /* -------------------------------------------------------- */
            
            guide_lb.setText("指紋を認識してください");
            guide_lb.setHorizontalTextPosition(SwingConstants.CENTER);
            
            check_btn.setBackground(new Color(231, 76, 60));
            check_btn.setText("Cancel");
            check_btn.setActionCommand("cancel");
            check_btn.addActionListener(this);
            
            /* -------------------------------------------------------- */
            
            Timer check_timer   = new Timer(5000, new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    if (scan_position == -1) {
                        process.destroy();
                    }
                }
            });
            
            check_timer.setRepeats(false);
            check_timer.start();
            
            /* -------------------------------------------------------- */
            
            this.setVisible(true);
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            this.dispose();
            process.destroy();
        }
        
    }
    
    
    private class Scan_runnable implements Runnable {
        
        int     position_num;
        
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                
                /* -------------------------------------------------------- */
                
                /* Check which fingerprint is recognized */
                position_num = btm_pn.register.register_sensor();
                
                /* -------------------------------------------------------- */
                
                /* If the return value is not -1, */
                /* the student's fingerprint number is returned. */
                if (position_num != -1) {
                    
                    /* DB connection inside raspberry pi */
                    SingleClass_raspi sing = SingleClass_raspi.getInstance();
                    
                    /* -------------------------------------------------------- */
                    
                    /* Storing fingerprint information in the internal database of raspberry pi */
                    sing.model_raspi.set_fingerprint(id, position_num);
                    
                    /* -------------------------------------------------------- */
                    
                    /* Student information output */
                    (btm_pn.result.name_lb).setText("名前 : " + infoResult.get(1));
                    (btm_pn.result.id_lb).setText("学籍番号 : " + infoResult.get(0));
                    
                    /* -------------------------------------------------------- */

                    String  link = "http://13.124.213.132" + infoResult.get(2);

                    try {
                        photo_link = new URL(link);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    if (photo_link != null) {
                        btm_pn.result.photo_lb.setIcon(new ImageIcon(photo_link));
                    }

                    /* -------------------------------------------------------- */

                    /* Transitions */
                    (btm_pn.right_cards).show(btm_pn.right_pn, "info");
                    
                    btm_pn.currentPanelOfRightCards = "info";
                    
                    /* -------------------------------------------------------- */

                    Timer   home_timer  = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            (btm_pn.left_cards).show(btm_pn.left_pn, "timeTable");
                            (btm_pn.status).removeAll();

                            _5_1_Status attendanceStatus_1    = new _5_1_Status();
                            (btm_pn.right_pn).add(attendanceStatus_1, "status");
                            (btm_pn.status).revalidate();
                            (btm_pn.status).repaint();
                            (btm_pn.right_cards).show(btm_pn.right_pn, "status");
                            
                            btm_pn.currentPanelOfRightCards = "status";

                            /* change the button's properties */
                            (top_pn.main_btn).setIcon(Set_ImageIcon.scan);
                            (top_pn.main_btn).setActionCommand("register");
                          
                        }
                    });

                    home_timer.setRepeats(false);
                    home_timer.start();
                    
                    /* -------------------------------------------------------- */
                } else {
                    /* If the fingerprint is already registered */
                    new Already_fingerprintDialog();
                }
                
                /* -------------------------------------------------------- */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Check_runnable implements Runnable {
        
        ArrayList<String>   lineList    = new ArrayList<String>();
        int                 temp        = 0;
        
        /* -------------------------------------------------------- */
        
        @Override
        public void run() {
            
            scan_position   = -1;
            
            try {
                
                Thread.sleep(1000);
                
                process = Runtime.getRuntime().exec("python /usr/share/doc/python-fingerprint/examples/example_search.py");
            
                BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                
                while ((line = buffer.readLine()) != null) {
                    lineList.add(line);
                }
                
                if (lineList.size() != 0 && lineList.size() != 2) {
                    String subValue = lineList.get(2).substring(28);
                    scan_position = Integer.parseInt(subValue);
                    check_dialog_result = 1;
                } else {
                    
                }
                
                buffer.close();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}

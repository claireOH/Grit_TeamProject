
package View;

import ViewBase.Set_Font;
import ViewBase.Set_ImageIcon;
import ViewBase.Set_Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class _5_2_Confirm extends JPanel implements ActionListener {

    JPanel          title_pn, content_pn;
    JPanel          confirm_pn, btn_pn, flash_pn;
    JPanel          numBtn_pn, specialBtn_pn;
    JLabel          title_lb, userImg_lb, keyImg_lb, flash_lb;
    JButton         confirm_btn;

    JTextField      id_tf;
    JPasswordField  password_pf;

    int             currentInputFieldCheck;

    public _5_2_Confirm() {
        
        /* set userConfirm panel */
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        /* -------------------------------------------------------- */

        /* definition component in the user confirm panel  */
        title_pn            = new JPanel();
        content_pn          = new JPanel();
        confirm_pn          = new JPanel();
        btn_pn              = new JPanel();
        flash_pn            = new JPanel();
        numBtn_pn           = new JPanel();
        specialBtn_pn       = new JPanel();

        title_lb            = new JLabel();
        userImg_lb          = new JLabel();
        keyImg_lb           = new JLabel();
        flash_lb            = new JLabel();

        confirm_btn         = new JButton();

        id_tf               = new JTextField();
        password_pf         = new JPasswordField();

        /* -------------------------------------------------------- */

        /* title panel */
        title_pn.setBackground(Color.WHITE);
        title_pn.setBounds(0, 0, 1125, 65);
        title_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* title label */
        title_lb.setText("ユーザーの認証");
        title_lb.setFont(Set_Font.setBoldFont(35));
        title_lb.setBackground(Color.WHITE);
        title_lb.setBounds(10, 10, 1105, 55);

        /* -------------------------------------------------------- */

        /* add title label in the title panel */
        title_pn.add(title_lb);

        /* -------------------------------------------------------- */

        /* content panel */
        content_pn.setBackground(Color.WHITE);
        content_pn.setBounds(0, 65, 1125, Set_Size.pnInBtmPn_height - 65);
        content_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* user's information confirm panel */
        confirm_pn.setBackground(Color.WHITE);
        confirm_pn.setBounds(70, 40, 500, 150);
        confirm_pn.setLayout(null);

        /* add confirm panel in the content panel */
        content_pn.add(confirm_pn);

        /* -------------------------------------------------------- */

        /* set the field to enter user's id and password */
        /* image label */
        userImg_lb.setIcon(Set_ImageIcon.user);
        userImg_lb.setBackground(Color.WHITE);
        userImg_lb.setBounds(10, 10, 40, 60);

        keyImg_lb.setIcon(Set_ImageIcon.key);
        keyImg_lb.setBackground(Color.WHITE);
        keyImg_lb.setBounds(10, 80, 40, 60);

        /* id textField */
        id_tf.setBounds(55, 10, 320, 60);
        id_tf.setFont(Set_Font.setBoldFont(35));
        id_tf.setColumns(5);

        /* password passwordField */
        password_pf.setBounds(55, 80, 320, 60);
        password_pf.setFont(Set_Font.setBoldFont(35));

        password_pf.setText("aaaa");

        /* -------------------------------------------------------- */

        /* add event to the textField and passwordField */

        /* input number event */
        id_tf.addActionListener(this);
        password_pf.addActionListener(this);

        /* click field event*/
        id_tf.addMouseListener(new ClickField_mouseAdapter());
        password_pf.addMouseListener(new ClickField_mouseAdapter());

        /* -------------------------------------------------------- */

        /* confirm button */
        confirm_btn.setBackground(Color.DARK_GRAY);
        confirm_btn.setForeground(Color.WHITE);
        confirm_btn.setText("認証");
        confirm_btn.setFont(Set_Font.setBoldFont(30));
        confirm_btn.setBorderPainted(false);
        confirm_btn.setFocusPainted(false);
        confirm_btn.setBounds(380, 10, 120, 130);
        confirm_btn.setActionCommand("confirm");

        /* -------------------------------------------------------- */

        /* add label and button, field in the confirm panel */
        confirm_pn.add(userImg_lb);
        confirm_pn.add(keyImg_lb);
        confirm_pn.add(id_tf);
        confirm_pn.add(password_pf);
        confirm_pn.add(confirm_btn);

        /* -------------------------------------------------------- */

        /* number button panel */
        btn_pn.setBackground(Color.YELLOW);
        btn_pn.setBounds(70, 200, 500, 270);
        btn_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* number button panel */
        numBtn_pn.setBackground(Color.WHITE);
        numBtn_pn.setBounds(70, 200, 500, 170);
        numBtn_pn.setLayout(new GridLayout(2, 0, 10, 10));

        /* special button panel */
        specialBtn_pn.setBackground(Color.WHITE);
        specialBtn_pn.setBounds(70, 380, 500, 90);
        specialBtn_pn.setLayout(new GridLayout(1, 0, 10, 10));

        /* -------------------------------------------------------- */

        /* add button in the number button panel */
        addNumberButton();
        addSpecialButton();

        /* -------------------------------------------------------- */

        /* add component to the button panel */
        btn_pn.add(numBtn_pn);
        btn_pn.add(specialBtn_pn);

        /* -------------------------------------------------------- */

        /* flash panel */
        flash_pn.setBackground(Color.WHITE);
        flash_pn.setBounds(590, 40, 400, 430);

        /* -------------------------------------------------------- */

        /* flash label */
        flash_lb.setIcon(Set_ImageIcon.flash);
        flash_lb.setBounds(590, 40, 400, 430);

        /* add label to the flash panel */
        flash_pn.add(flash_lb);

        /* -------------------------------------------------------- */

        /* add component to the content panel */
        content_pn.add(confirm_pn);
        content_pn.add(numBtn_pn);
        content_pn.add(specialBtn_pn);
        content_pn.add(flash_lb);

        /* -------------------------------------------------------- */

        /* add component in the user confirm panel */
        this.add(title_pn);
        this.add(content_pn);

        /* component visible or invisible*/
        this.setVisible(true);

        /* -------------------------------------------------------- */

    }

    private void addNumberButton() {
        /* create 1 ~ 0 button */
        for (int num = 0; num < 10; num++) {
            JButton btn = new JButton();

            /* -------------------------------------------------------- */

            /* set the number button */
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setFont(Set_Font.setBoldFont(35));

            /* set properties */
            btn.setText(Integer.toString(num));
            btn.setActionCommand(Integer.toString(num));

            /* add event */
            btn.addActionListener(this);

            /* -------------------------------------------------------- */

            /* add button in the number panel */
            numBtn_pn.add(btn);

            /* -------------------------------------------------------- */
        }
    }

    private void addSpecialButton() {
        /* definition reset and backspace button */
        JButton reset_btn = new JButton();
        JButton backspace_btn = new JButton();

        /* -------------------------------------------------------- */

        /* reset button */
        reset_btn.setBackground(Color.DARK_GRAY);
        reset_btn.setForeground(Color.WHITE);
        reset_btn.setBorderPainted(false);
        reset_btn.setFocusPainted(false);
        reset_btn.setText("RESET");
        reset_btn.setFont(Set_Font.setBoldFont(35));
        reset_btn.setActionCommand("reset");

        /* -------------------------------------------------------- */

        /* backspace button */
        backspace_btn.setBackground(Color.DARK_GRAY);
        backspace_btn.setForeground(Color.WHITE);
        backspace_btn.setBorderPainted(false);
        backspace_btn.setFocusPainted(false);
        backspace_btn.setText("Backspace");
        backspace_btn.setFont(Set_Font.setBoldFont(35));
        backspace_btn.setActionCommand("backspace");

        /* -------------------------------------------------------- */

        /* add event */
        reset_btn.addActionListener(this);
        backspace_btn.addActionListener(this);

        /* -------------------------------------------------------- */

        /* add to the special button panel */
        specialBtn_pn.add(reset_btn);
        specialBtn_pn.add(backspace_btn);

        /* -------------------------------------------------------- */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* check and enter selected field */
        switch (currentInputFieldCheck) {
            case 1: /* select id field */
                /* -------------------------------------------------------- */

                if (e.getActionCommand().equals("reset")) {
                    id_tf.setText("");
                } else if (e.getActionCommand().equals("backspace")) {
                    /* only the last letter is deleted */
                    String id = "";

                    for (int idCount = 0 ; idCount < id_tf.getText().length() - 1 ; idCount++) {
                        id += (id_tf.getText()).charAt(idCount);
                    }

                    id_tf.setText(id);
                } else {
                    id_tf.setText(id_tf.getText() + e.getActionCommand());
                }

                break;
                
                /* -------------------------------------------------------- */
            case 2: /* select password field */
                /* -------------------------------------------------------- */

                if (e.getActionCommand().equals("reset")) {
                    password_pf.setText("");
                } else if (e.getActionCommand().equals("backspace")) {
                    String password = "";

                    for (int passwordCount = 0; passwordCount < password_pf.getPassword().length - 1; passwordCount++) {
                        password += password_pf.getPassword()[passwordCount];
                    }

                    password_pf.setText(password);
                } else {
                    /* No password entered */
                    if (password_pf.getPassword().length == 0) {
                        password_pf.setText(e.getActionCommand());
                    } else {
                        /* PasswordField can't be extracted entirely */
                        /* Extract one letter to array */
                        String password = "";

                        for (int passwordCount = 0; passwordCount < password_pf.getPassword().length; passwordCount++) {
                            password += password_pf.getPassword()[passwordCount];
                        }

                        password_pf.setText(password + e.getActionCommand());
                    }
                }
                
                break;

                /* -------------------------------------------------------- */
        }
    }

    /* event to determine which fields are currently selected */
    /* if the id textField is selected, the inputFieldCheck value is true */
    private class ClickField_mouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            
            if (e.getSource().equals(id_tf)) {
                currentInputFieldCheck = 1;
            } else if (e.getSource().equals(password_pf)) {
                currentInputFieldCheck = 2;
            }
        }
    }
}
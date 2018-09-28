package View;

import ViewBase.Set_Font;
import ViewBase.Set_Size;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class _4_2_Check extends JPanel {

    JPanel                  content_pn;
    JPanel                  info_pn, photo_pn;
    JLabel                  photo_lb, name_lb, id_lb;
    JLabel                  time_lb, guide_lb;
    JButton                 check_btn;

    LineBorder              lineBorder;

    public _4_2_Check () {
        
        /* set user Register panel  */
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        /* -------------------------------------------------------- */

        /* definition component in the Register fingerprint panel  */
        content_pn          = new JPanel();
        info_pn             = new JPanel();
        photo_pn            = new JPanel();

        photo_lb            = new JLabel();
        name_lb             = new JLabel();
        id_lb               = new JLabel();
        time_lb             = new JLabel();
        guide_lb            = new JLabel();

        check_btn           = new JButton();

        lineBorder          = new LineBorder(Color.BLACK);

        /* -------------------------------------------------------- */

        /* content panel */
        content_pn.setBackground(Color.WHITE);
        content_pn.setBounds(0, 0, Set_Size.rightPn_width, Set_Size.pnInBtmPn_height);
        content_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* photo panel */
        photo_pn.setBounds(30, 150, 170, 200);
        photo_pn.setLayout(null);
        photo_pn.setBackground(Color.DARK_GRAY);
        
        /* photo label */
        photo_lb.setBounds(0, 0, 150, 200);

        /* add photo label to the photo panel */
        photo_pn.add(photo_lb);

        /* -------------------------------------------------------- */

        /* info panel */
        info_pn.setBounds(215, 150, 365, 200);
        info_pn.setLayout(null);
        info_pn.setBackground(new Color(234, 234, 234));

        /* name label */
        name_lb.setFont(Set_Font.setPlainFont(27));
        name_lb.setForeground(Color.BLACK);
        name_lb.setBounds(30, 30, 270, 70);
        name_lb.setHorizontalAlignment(SwingConstants.LEFT);

        /* id label */
        id_lb.setFont(Set_Font.setPlainFont(30));
        id_lb.setForeground(Color.BLACK);
        id_lb.setBounds(30, 110, 270, 70);
        id_lb.setHorizontalAlignment(SwingConstants.LEFT);

        /* add label to the information panel */
        info_pn.add(name_lb);
        info_pn.add(id_lb);

        /* -------------------------------------------------------- */

        /* guide label */
        time_lb.setFont(Set_Font.setBoldFont(30));
        time_lb.setForeground(Color.BLACK);
        time_lb.setBounds(50, 365, 500, 70);
        time_lb.setHorizontalAlignment(SwingConstants.CENTER);
        
        guide_lb.setFont(Set_Font.setBoldFont(50));
        guide_lb.setForeground(Color.BLACK);
        guide_lb.setText("<html>出席のチェックが<br>&nbsp&nbsp完了されました</html>");
        guide_lb.setBounds(50, 450, 500, 140);
        guide_lb.setHorizontalAlignment(SwingConstants.CENTER);

        /* -------------------------------------------------------- */

        /* check button */
        check_btn.setBounds(260, 600, 80, 30);
        check_btn.setBackground(Color.DARK_GRAY);
        check_btn.setForeground(Color.WHITE);
        check_btn.setBorderPainted(false);      
        check_btn.setFocusPainted(false);       
        check_btn.setFont(Set_Font.setBoldFont(16));
        check_btn.setText("OK");
        check_btn.setActionCommand("attendComplete");

        /* -------------------------------------------------------- */

        /* add component to the content panel */
        content_pn.add(photo_pn);
        content_pn.add(info_pn);
        content_pn.add(time_lb);
        content_pn.add(guide_lb);
        content_pn.add(check_btn);

        /* -------------------------------------------------------- */

        /* add component in the user confirm panel */
        this.add(content_pn);

        /* component visible or invisible*/
        this.setVisible(true);

        /* -------------------------------------------------------- */

    }
}
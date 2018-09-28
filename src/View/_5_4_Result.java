package View;

import ViewBase.Set_Font;
import ViewBase.Set_Size;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class _5_4_Result extends JPanel {
    JPanel                  content_pn;
    JPanel                  info_pn, photo_pn;
    JLabel                  photo_lb, name_lb, id_lb, checkScan_lb;
    JLabel                  guide_lb;
    JButton                 result_ok_btn;

    ArrayList<String>       infoResult;

    LineBorder              lineBorder;

    public _5_4_Result () {

        /* set user result panel  */
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
        checkScan_lb        = new JLabel();
        guide_lb            = new JLabel();

        result_ok_btn       = new JButton();

        infoResult          = new ArrayList<String>();

        lineBorder          = new LineBorder(Color.BLACK);

        /* -------------------------------------------------------- */

        /* content panel */
        content_pn.setBackground(Color.WHITE);
        content_pn.setBounds(0, 0, Set_Size.rightPn_width, Set_Size.pnInBtmPn_height);
        content_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* photo panel */
        photo_pn.setBounds(160, 150, 300, 300);
        photo_pn.setLayout(null);
        photo_pn.setBackground(Color.DARK_GRAY);

        /* photo label */
        photo_lb.setBounds(0, 0, 300, 300);

        /* add photo label to the photo panel */
        photo_pn.add(photo_lb);

        /* -------------------------------------------------------- */

        /* info panel */
        info_pn.setBounds(485, 150, 480, 300);
        info_pn.setLayout(null);
        info_pn.setBackground(new Color(234, 234, 234));

        /* name label */
        name_lb.setFont(Set_Font.setPlainFont(35));
        name_lb.setForeground(Color.BLACK);
        name_lb.setBounds(30, 30, 450, 70);
        name_lb.setHorizontalAlignment(SwingConstants.LEFT);

        /* id label */
        id_lb.setFont(Set_Font.setPlainFont(35));
        id_lb.setForeground(Color.BLACK);
        id_lb.setBounds(30, 110, 450, 70);
        id_lb.setHorizontalAlignment(SwingConstants.LEFT);

        /* check register fingerprint */
        checkScan_lb.setFont(Set_Font.setPlainFont(35));
        checkScan_lb.setForeground(Color.BLACK);
        checkScan_lb.setText("指紋登録の状態 : ○");
        checkScan_lb.setBounds(30, 190, 450, 70);
        checkScan_lb.setHorizontalAlignment(SwingConstants.LEFT);

        /* add label to the information panel */
        info_pn.add(name_lb);
        info_pn.add(id_lb);
        info_pn.add(checkScan_lb);

        /* -------------------------------------------------------- */

        /* guide label */
        guide_lb.setFont(Set_Font.setBoldFont(50));
        guide_lb.setForeground(Color.BLACK);
        guide_lb.setText("指紋が登録されました");
        guide_lb.setBounds(160, 500, 810, 80);
        guide_lb.setHorizontalAlignment(SwingConstants.CENTER);

        /* -------------------------------------------------------- */

        /* check button */
        result_ok_btn.setBounds(525, 600, 120, 50);
        result_ok_btn.setBackground(Color.DARK_GRAY);
        result_ok_btn.setForeground(Color.WHITE);
        result_ok_btn.setBorderPainted(false);      /* 테두리 제거*/
        result_ok_btn.setFocusPainted(false);       /* 클릭 효과 제거 */
        result_ok_btn.setFont(Set_Font.setBoldFont(22));
        result_ok_btn.setText("OK");
        result_ok_btn.setActionCommand("registerComplete");

        /* -------------------------------------------------------- */

        /* add component to the content panel */
        content_pn.add(photo_pn);
        content_pn.add(info_pn);
        content_pn.add(guide_lb);
        content_pn.add(result_ok_btn);

        /* -------------------------------------------------------- */

        /* add component in the user confirm panel */
        this.add(content_pn);

        /* component visible or invisible*/
        this.setVisible(true);

        /* -------------------------------------------------------- */

    }
}

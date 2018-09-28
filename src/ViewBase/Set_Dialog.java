package ViewBase;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;

/* Common Dialog -> guide label, button */
public class Set_Dialog extends JDialog {
    
                        JPanel      cover_pn, top_pn;
    public      JLabel      guide_lb;
    public      JButton     check_btn;
    
    public Set_Dialog () {
        /* -------------------------------------------------------- */

        /* Shutdown settings */
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        /* delete title bar */
        this.setUndecorated(true);

        /* Unable to select another panel when not in the dialog */
        this.setModal(true);

        /* size */
        this.setSize(500, 200);

        /* border */
        this.getRootPane().setBorder(new LineBorder(Color.BLACK));

        /* -------------------------------------------------------- */

        /* Define the components that make up a dialog */
        cover_pn            = new JPanel();
        top_pn              = new JPanel();
        guide_lb            = new JLabel();
        check_btn           = new JButton();

        /* -------------------------------------------------------- */

        /* cover panel */
        cover_pn.setBackground(Color.WHITE);
        cover_pn.setBounds(0, 0, 500, 200);
        cover_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* top panel */
        top_pn.setBackground(Color.WHITE);
        top_pn.setBounds(10, 10, 480, 100);
        top_pn.setLayout(new MigLayout());

        /* -------------------------------------------------------- */

        /* guide label */
        /* Set the contents of the text according to where the Dialog is used. */
        guide_lb.setFont(Set_Font.setBoldFont(33));
        guide_lb.setForeground(Color.BLACK);

        /* Center Alignment */
        guide_lb.setVerticalAlignment(SwingConstants.CENTER);

        /* -------------------------------------------------------- */

        /* Add Labels to a Panel */
        top_pn.add(guide_lb, "push, align center");

        /* -------------------------------------------------------- */

        /* check button */
        /* Specify the color and wording of the button later depending on where the Dialog is used. */
        check_btn.setBounds(10, 120, 480, 70);

        /* delete border */
        check_btn.setBorderPainted(false);

        /* Remove events that occur when clicked */
        check_btn.setFocusPainted(false);

        check_btn.setFont(Set_Font.setBoldFont(30));
        check_btn.setForeground(Color.WHITE);

        /* -------------------------------------------------------- */

        /* add in the cover panel */
        cover_pn.add(top_pn);
        cover_pn.add(check_btn);

        /* -------------------------------------------------------- */

        /* add the dialog */
        this.add(cover_pn);
        
        /* -------------------------------------------------------- */
         
    }
}

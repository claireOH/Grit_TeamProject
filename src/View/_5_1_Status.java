package View;

import Singleton.SingleClass_web;
import ViewBase.Set_Font;
import ViewBase.Set_Size;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class _5_1_Status extends JPanel {

    JPanel                              title_pn, content_pn;
    JPanel                              attend_pn, late_pn, absence_pn, attendList_pn, lateList_pn, absenceList_pn;
    JLabel                              title_lb, allStdCount_lb;

    JTextField                          attendCount_tf, lateCount_tf, absenceCount_tf;
    JScrollPane                         attend_pane, late_pane, absence_pane;

    LineBorder                          lineBorder;

    ArrayList<HashMap<String, String>>  list_arr;
    HashMap<String, String>             list_hash;

    Vector<JPanel>                      attend_vec, late_vec, absence_vec;

    public _5_1_Status () {
       
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        /* -------------------------------------------------------- */

        /* definition component in the status panel */
        title_pn            = new JPanel();
        content_pn          = new JPanel();

        attend_pn           = new JPanel();
        late_pn             = new JPanel();
        absence_pn          = new JPanel();

        attendList_pn       = new JPanel();
        lateList_pn         = new JPanel();
        absenceList_pn      = new JPanel();

        title_lb            = new JLabel();
        allStdCount_lb      = new JLabel();

        attendCount_tf      = new JTextField();
        lateCount_tf        = new JTextField();
        absenceCount_tf     = new JTextField();

        attend_pane         = new JScrollPane();
        late_pane           = new JScrollPane();
        absence_pane        = new JScrollPane();

        attend_vec          = new Vector<JPanel>();
        late_vec            = new Vector<JPanel>();
        absence_vec         = new Vector<JPanel>();

        list_arr            = new ArrayList<>();
        list_hash           = new HashMap<>();

        lineBorder          = new LineBorder(Color.BLACK);

        /* -------------------------------------------------------- */

        /* title panel */
        title_pn.setBackground(Color.WHITE);
        title_pn.setBounds(0, 0, 1125, 65);
        title_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* title label */
        title_lb.setText("出席の現況");
        title_lb.setFont(Set_Font.setBoldFont(35));
        title_lb.setBounds(10, 10, 200, 55);

        /* -------------------------------------------------------- */

        /* total student count label */
        allStdCount_lb.setFont(Set_Font.setBoldFont(33));
        allStdCount_lb.setBackground(Color.WHITE);
        allStdCount_lb.setBounds(205, 10, 200, 55);
        allStdCount_lb.setVerticalAlignment(SwingConstants.CENTER);

        /* -------------------------------------------------------- */

        /* add component to the title */
        title_pn.add(title_lb);
        title_pn.add(allStdCount_lb);

        /* -------------------------------------------------------- */

        /* content panel */
        content_pn.setBackground(Color.WHITE);
        content_pn.setBounds(0, 65, 1125, Set_Size.pnInBtmPn_height - 65);
        content_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* inside the content panel */
        /* attend panel */
        attend_pn.setBackground(Color.WHITE);
        attend_pn.setBounds(6, 5, 367, Set_Size.pnInBtmPn_height - 75);
        attend_pn.setLayout(null);

        /* late panel */
        late_pn.setBackground(Color.WHITE);
        late_pn.setBounds(379, 5, 367, Set_Size.pnInBtmPn_height - 75);
        late_pn.setLayout(null);

        /* absence panel */
        absence_pn.setBackground(Color.WHITE);
        absence_pn.setBounds(752, 5, 367, Set_Size.pnInBtmPn_height - 75);
        absence_pn.setLayout(null);

        /* -------------------------------------------------------- */

        /* attend textField */
        attendCount_tf.setBorder(lineBorder);
        attendCount_tf.setEditable(false);
        attendCount_tf.setHorizontalAlignment(SwingConstants.CENTER);
        attendCount_tf.setFont(Set_Font.setBoldFont(30));
        attendCount_tf.setBackground(new Color(46, 204, 113));
        attendCount_tf.setBounds(5, 0, 357, 50);

        /* late textField */
        lateCount_tf.setBorder(lineBorder);
        lateCount_tf.setEditable(false);
        lateCount_tf.setHorizontalAlignment(SwingConstants.CENTER);
        lateCount_tf.setFont(Set_Font.setBoldFont(30));
        lateCount_tf.setBackground(new Color(243, 156, 18));
        lateCount_tf.setBounds(5, 0, 357, 50);

        /* absence textField */
        absenceCount_tf.setBorder(lineBorder);
        absenceCount_tf.setEditable(false);
        absenceCount_tf.setHorizontalAlignment(SwingConstants.CENTER);
        absenceCount_tf.setFont(Set_Font.setBoldFont(30));
        absenceCount_tf.setBackground(new Color(231, 76, 60));
        absenceCount_tf.setBounds(5, 0, 357, 50);

        /* -------------------------------------------------------- */

        /* add textField to each panel */
        attend_pn.add(attendCount_tf);
        late_pn.add(lateCount_tf);
        absence_pn.add(absenceCount_tf);

        /* -------------------------------------------------------- */

        /* attend scroll pane */
        attend_pane.setBounds(5, 55, 357, 825);
        attend_pane.setBorder(null);
        attend_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        attend_pane.setViewportView(attendList_pn);

        /* late scroll pane */
        late_pane.setBounds(5, 55, 357, 825);
        late_pane.setBorder(null);
        late_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        late_pane.setViewportView(lateList_pn);

        /* absence scroll pane */
        absence_pane.setBounds(5, 55, 357, 825);
        absence_pane.setBorder(null);
        absence_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        absence_pane.setViewportView(absenceList_pn);

        /* -------------------------------------------------------- */

        /* each list_arr panel */
        attendList_pn.setBackground(Color.WHITE);
        lateList_pn.setBackground(Color.WHITE);
        absenceList_pn.setBackground(Color.WHITE);

        /* -------------------------------------------------------- */

        /* DB_wdb */
        this.connect();

        /* -------------------------------------------------------- */

        /* create name list_arr */
        createNameList(attend_vec, "attend");
        createNameList(late_vec, "late");
        createNameList(absence_vec, "absence");

        addList(attendList_pn, attend_vec);
        addList(lateList_pn, late_vec);
        addList(absenceList_pn, absence_vec);

        /* -------------------------------------------------------- */

        /* count student */
        allStdCount_lb.setText("全員 " + Integer.toString(attend_vec.size() + late_vec.size() + absence_vec.size()) + "人");
        attendCount_tf.setText("出席 " + Integer.toString(attend_vec.size()));
        lateCount_tf.setText("遅刻" + Integer.toString(late_vec.size()));
        absenceCount_tf.setText("欠席 " + Integer.toString(absence_vec.size()));

        /* -------------------------------------------------------- */

        /* add scroll pane to each panel */
        attend_pn.add(attend_pane);
        late_pn.add(late_pane);
        absence_pn.add(absence_pane);

        /* -------------------------------------------------------- */

        /* add panel to the content panel */
        content_pn.add(attend_pn);
        content_pn.add(late_pn);
        content_pn.add(absence_pn);

        /* -------------------------------------------------------- */

        /* add component */
        this.add(title_pn);
        this.add(content_pn);

    }

    public void paint (Graphics g) {
        super.paint(g);

        /* line */
        g.drawLine(376, 65, 376, 950);
        g.drawLine(749, 65, 749, 950);
    }

    private void connect () {
        SingleClass_web sing        = SingleClass_web.getInstance();

        list_arr = sing.model_web.get_attendanceStatus();
    }

    private void createNameList (Vector<JPanel> argList, String action) {
        
        /* check attendance */
        switch (action) {
            case "attend":
                list_hash   = list_arr.get(1);
                break;
            case "late":
                list_hash   = list_arr.get(2);
                break;
            case "absence":
                list_hash   = list_arr.get(0);
                break;
        }

        /* -------------------------------------------------------- */

        /* create panel and add to the vector */
        Set         set = list_hash.entrySet();
        Iterator    it  = list_hash.entrySet().iterator();

        while (it.hasNext()) {

            JPanel      pn      = new JPanel();
            JLabel      id      = new JLabel();
            JLabel      name    = new JLabel();

            /* -------------------------------------------------------- */

            /* name panel */
            pn.setBackground(Color.WHITE);
            pn.setBorder(lineBorder);
            pn.setLayout(new GridLayout(2, 0, 0, 0));
            pn.setSize(100, 80);

            /* -------------------------------------------------------- */

            /* student id label */
            id.setBackground(Color.WHITE);
            id.setBorder(null);
            id.setHorizontalAlignment(SwingConstants.CENTER);
            id.setVerticalAlignment(SwingConstants.CENTER);
            id.setFont(Set_Font.setBoldFont(20));

            /* -------------------------------------------------------- */

            /* student name label */
            name.setBackground(Color.WHITE);
            name.setBorder(null);
            name.setHorizontalAlignment(SwingConstants.CENTER);
            name.setVerticalAlignment(SwingConstants.CENTER);
            name.setFont(Set_Font.setBoldFont(17));

            /* -------------------------------------------------------- */

            Map.Entry e = (Map.Entry)it.next();

            id.setText((String)e.getKey());
            name.setText((String)e.getValue());

            /* -------------------------------------------------------- */

            /* add name label nad id label to the panel */
            pn.add(id);
            pn.add(name);

            /* add panel to vector */
            argList.add(pn);

            /* -------------------------------------------------------- */
        }

    }

    private void addList (JPanel argListPanel, Vector<JPanel> argList) {
        
        int     height      = argList.size() / 2 * 90;

        /* -------------------------------------------------------- */

        argListPanel.setPreferredSize(new Dimension(357, height));
        argListPanel.setLayout(null);

        /* -------------------------------------------------------- */

        for (int listCount = 0, col = 0 ; listCount < argList.size() ; listCount++) {
            JPanel      tmp     = argList.get(listCount);

//            if (listCount % 3 == 0) {
//                tmp.setBounds(0, col, 105, 85);
//            } else if (listCount % 3 == 1) {
//                tmp.setBounds(110, col, 105, 85);
//            } else {
//                tmp.setBounds(220, col, 105, 85);
//                col += 90;
//            }

            if (listCount % 2 == 0) {
                tmp.setBounds(0, col, 157, 85);
            } else {
                tmp.setBounds(168, col, 157, 85);
                col += 90;
            }

            argListPanel.add(tmp);
        }
    }
}
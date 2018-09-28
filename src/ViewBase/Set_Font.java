package ViewBase;

import java.awt.Font;

public class Set_Font {
    
    public static Font setBoldFont (int fontSize) {
        return new Font("Noto Sans CJK JP Bold", Font.PLAIN, fontSize);
    }
    
    public static Font setPlainFont (int fontSize) {
        return new Font("Noto Sans CJK JP Regular", Font.PLAIN, fontSize);
    }
    
}

import javax.swing.*;
import java.awt.*;

public class HtmlInEditorPane {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HTML in JEditorPane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JEditorPane editorPane = new JEditorPane("text/html", "<html><h1>Title</h1><p>This is a paragraph with <b>bold</b> text.</p></html>");
        editorPane.setEditable(false);
        frame.add(new JScrollPane(editorPane), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}

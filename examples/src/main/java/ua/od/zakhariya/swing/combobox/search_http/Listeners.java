package ua.od.zakhariya.swing.combobox.search_http;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Listeners implements DocumentListener {

    private View view;
    private JComboBox<Data> comboBox;
    private Model<Data> model;
    private JTextField editor;

    private Thread timer = null;
    private int timeOut = 0;


    public void prepare() {
        if(view == null)
            view = View.getInstance();
        if(comboBox == null)
            comboBox = view.getComboBox();
        if(model == null)
            model = view.getComboBoxModel();
        if(editor == null)
            editor = (JTextField) comboBox.getEditor().getEditorComponent();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}

    @Override
    public void insertUpdate(DocumentEvent e) {
        searchAfterTimeout();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        searchAfterTimeout();
    }

    private void searchAfterTimeout() {
        if(timer != null && timer.isAlive())
            timer.interrupt();

        timer = new Thread(new Runnable() {
            public void run() {
                timeOut = 0;
                int limit = 1000;
                int millis = 125;

                while(timeOut < limit){
                    if(timeOut >= limit)
                        break;

                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException ex) {
                        break;
                    }

                    timeOut += millis;
                }

                if(timeOut >= limit){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            search();
                        }
                    });
                }
            }
        });
        timer.start();
    }

    private void search() {
        prepare();

        Object obj = comboBox.getEditor().getItem();

        if(obj instanceof Data) {
            System.out.printf("<%nObject is instance of Data: \"%s\"%n>", obj.toString());
        }else{
            int caretPos = editor.getCaretPosition();

            view.updateModel(obj.toString());

            editor.setCaretPosition(caretPos);
            comboBox.setPopupVisible(false);
            comboBox.setPopupVisible(true);

            System.err.printf("<%nUnknown object type \"%s\"%n>", obj.toString());
        }
    }

}

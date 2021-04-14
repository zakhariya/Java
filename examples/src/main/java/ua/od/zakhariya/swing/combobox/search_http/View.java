package ua.od.zakhariya.swing.combobox.search_http;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.apache.http.client.ClientProtocolException;

public class View extends JDialog {

    private static final long serialVersionUID = 1L;

    private static View view;

    private JComboBox<Data> comboBox;
    private Model<Data> model;
    private DataHttpClient httpClient;
    private JTextComponent comboBoxEditor;
    private Listeners boxListeners;


    private View() {
        httpClient = new DataHttpClient();
        model = new Model();
        boxListeners = new Listeners();

        comboBox = new JComboBox<Data>();
        comboBox.setModel(model);
        comboBox.setBounds(10, 28, 364, 20);
        getContentPane().add(comboBox);
        comboBox.setEditable(true);

        comboBoxEditor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        comboBoxEditor.getDocument().addDocumentListener(boxListeners);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(245, 127, 89, 23);
        getContentPane().add(btnOk);
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Data productName = httpClient.save((Data) comboBox.getEditor().getItem());

                    System.out.println("***********\n"
                            + "Product saved: id=" + productName.getId() + " name=" + productName.getName() + "\n"
                            + "***********");
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setSize(400, 200);
        setAlwaysOnTop(true);
        setLayout(null);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void updateModel() {
        try {
            List<Data> names = httpClient.getAll();

            model.clear();
            model.addElements(names);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateModel(String productName) {
        try {
            List<Data> names = httpClient.getByNameLike(productName);

            Data pName = new Data(0, productName);

            model.clear();

            model.addElement(pName);
            model.addElements(names);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public JComboBox<Data> getComboBox(){
        return this.comboBox;
    }

    public Model<Data> getComboBoxModel() {
        return this.model;
    }

    //works fine in case of single threaded environment
    public static View getInstance() {		//synchronized - add to thread-safe
        if(view == null)
            view = new View();

        return view;
    }

	/*
	//double checked locking implementation
	public static ThreadSafeSingleton getInstanceUsingDoubleLocking(){
	    if(instance == null){
	        synchronized (ThreadSafeSingleton.class) {
	            if(instance == null){
	                instance = new ThreadSafeSingleton();
	            }
	        }
	    }
	    return instance;
	}

	//bill pugh
	//when someone calls the getInstance method, SingletonHelper creates
	//the Singleton class instance
	public class BillPughSingleton {

	    private BillPughSingleton(){}

	    private static class SingletonHelper{
	        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
	    }

	    public static BillPughSingleton getInstance(){
	        return SingletonHelper.INSTANCE;
	    }
	}
	 */
}
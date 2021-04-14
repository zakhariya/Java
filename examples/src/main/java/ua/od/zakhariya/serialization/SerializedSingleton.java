package ua.od.zakhariya.serialization;

import java.io.*;

public class SerializedSingleton implements Serializable {

    private static final long serialVersionUID = -7604766932017737115L;

    private SerializedSingleton() {}

    private static class SingletonHelper{
        private static final SerializedSingleton instance = new SerializedSingleton();
    }

    public static SerializedSingleton getInstance(){
        return SingletonHelper.instance;
    }


    //we need to do it provide the implementation of this method
    //to protect pattern from destroying
    protected Object readResolve() {
        return getInstance();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        SerializedSingleton instanceOne = SerializedSingleton.getInstance();

        ObjectOutput out = new ObjectOutputStream(new FileOutputStream("filename.ser"));
        out.writeObject(instanceOne);
        out.close();

        ObjectInput in = new ObjectInputStream(new FileInputStream("filename.ser"));

        SerializedSingleton instanceTwo = (SerializedSingleton) in.readObject();
        in.close();

        System.out.println("instanceOne hashCode = "+instanceOne.hashCode());
        System.out.println("instanceTwo hashCode = "+instanceTwo.hashCode());

    }

}
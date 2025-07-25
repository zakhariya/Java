package ua.od.zakhariya.system;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WakeOnLan {
    private int port = 9;

    public static void main(String[] args) {
        new WakeOnLan().sendMagicPacket("70:85:C2:3C:E5:8C");
    }

    public void sendMagicPacket(String macStr) {
        try {
            final byte[] macBytes = getMacBytes(macStr);
            final byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            final InetAddress address = InetAddress.getByName("255.255.255.255");
            final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, this.port);

            try (DatagramSocket socket = new DatagramSocket()) {
                socket.send(packet);
            }

            System.out.println("Wake-on-LAN packet sent.");
        } catch (Exception e) {
            System.out.println("Failed to send Wake-on-LAN packet:" + e.getMessage());
        }
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        final byte[] bytes = new byte[6];
        final String[] hex = macStr.split("(\\:|\\-)");

        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }

        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }

        return bytes;
    }
}

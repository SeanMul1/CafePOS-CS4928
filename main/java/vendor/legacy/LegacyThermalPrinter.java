package vendor.legacy;

public class LegacyThermalPrinter {
    public void legacyPrint(byte[] payload) {
        System.out.println("[LegacyThermalPrinter] Printed: " + new String(payload, java.nio.charset.StandardCharsets.UTF_8));
    }
}
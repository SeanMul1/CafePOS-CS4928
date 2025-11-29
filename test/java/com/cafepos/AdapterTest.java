// File: src/test/java/com/cafepos/printing/AdapterTest.java
package com.cafepos;

import com.cafepos.printing.LegacyPrinterAdapter;
import com.cafepos.printing.Printer;
import vendor.legacy.LegacyThermalPrinter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FakeLegacyPrinter extends LegacyThermalPrinter {
    int lastLen = -1;
    String lastContent = "";

    @Override
    public void legacyPrint(byte[] payload) {
        lastLen = payload.length;
        lastContent = new String(payload, java.nio.charset.StandardCharsets.UTF_8);
    }
}

class AdapterTest {

    @Test
    void adapter_converts_text_to_bytes() {
        var fake = new FakeLegacyPrinter();
        Printer p = new LegacyPrinterAdapter(fake);

        p.print("ABC");

        assertTrue(fake.lastLen >= 3);
        assertEquals("ABC", fake.lastContent);
    }

    @Test
    void adapter_handles_complex_receipt() {
        var fake = new FakeLegacyPrinter();
        Printer p = new LegacyPrinterAdapter(fake);

        String receipt = "Order (LAT+L) x2\nSubtotal: 7.80\nTax (10%): 0.78\nTotal: 8.58";
        p.print(receipt);

        assertTrue(fake.lastLen > 0);
        assertTrue(fake.lastContent.contains("Total: 8.58"));
    }
}
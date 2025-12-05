package app;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Update Threshold & Capacity Tests")
class ProductStockUpdateRulesTest {

    private ProductStock stock;

   

    @BeforeEach
    void setup() {
        stock = new ProductStock("P20", "A1", 10, 3, 40);
    }

    @Test
    @DisplayName("updateReorderThreshold updates threshold when valid")
    void updateThresholdValid() {
        stock.updateReorderThreshold(15);
        assertEquals(15, stock.getReorderThreshold());
    }

    @Test
    @DisplayName("updateReorderThreshold rejects negative values")
    void updateThresholdNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> stock.updateReorderThreshold(-1));
    }

    @Test
    @DisplayName("updateReorderThreshold rejects values > maxCapacity")
    void updateThresholdBeyondCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> stock.updateReorderThreshold(100));
    }


    @Test
    @DisplayName("updateMaxCapacity increases capacity when valid")
    void updateCapacityValid() {
        stock.updateMaxCapacity(80);
        assertEquals(80, stock.getMaxCapacity());
    }

    @Test
    @DisplayName("updateMaxCapacity rejects <= 0")
    void updateCapacityZeroOrNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> stock.updateMaxCapacity(0));
        assertThrows(IllegalArgumentException.class,
                () -> stock.updateMaxCapacity(-10));
    }

    @Test
    @DisplayName("updateMaxCapacity rejects values less than current onHand")
    void updateCapacityBelowOnHand() {
        assertThrows(IllegalStateException.class,
                () -> stock.updateMaxCapacity(5));
    }


    @Test
    @DisplayName("isReorderNeeded returns true when available < threshold")
    void reorderNeededTrue() {
        stock = new ProductStock("P1", "L1", 10, 8, 100);
        stock.reserve(5); // available = 5 < threshold 8
        assertTrue(stock.isReorderNeeded());
    }

    @Test
    @DisplayName("isReorderNeeded returns false when available >= threshold")
    void reorderNeededFalse() {
        stock = new ProductStock("P1", "L1", 10, 5, 100);
        assertFalse(stock.isReorderNeeded());
    }
}

package app;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("addStock & removeDamaged Tests")
class ProductStockAddAndRemoveTest {

    private ProductStock stock;

    @BeforeEach
    void setup() {
        stock = new ProductStock("P1", "L1", 10, 2, 50);
    }


    @Test
    @Tag("sanity")
    @DisplayName("addStock increases onHand normally")
    void addStockNormal() {
        stock.addStock(5);
        assertEquals(15, stock.getOnHand());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    @Tag("regression")
    @DisplayName("addStock parameterized valid amounts")
    void addStockParameterized(int amount) {
        stock.addStock(amount);
        assertEquals(10 + amount, stock.getOnHand());
    }

    @Test
    @DisplayName("addStock with amount <= 0 should throw IllegalArgumentException")
    void addStockInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> stock.addStock(0));
        assertThrows(IllegalArgumentException.class, () -> stock.addStock(-5));
    }

    @Test
    @DisplayName("addStock exceeding maxCapacity should throw IllegalStateException")
    void addStockBeyondCapacity() {
        assertThrows(IllegalStateException.class, () -> stock.addStock(1000));
    }


    @Test
    @Tag("sanity")
    @DisplayName("removeDamaged reduces onHand normally")
    void removeDamagedNormal() {
        stock.removeDamaged(3);
        assertEquals(7, stock.getOnHand());
    }

    @Test
    @DisplayName("removeDamaged with amount <= 0 should throw")
    void removeDamagedInvalid() {
        assertThrows(IllegalArgumentException.class, () -> stock.removeDamaged(0));
        assertThrows(IllegalArgumentException.class, () -> stock.removeDamaged(-3));
    }

    @Test
    @DisplayName("removeDamaged cant remove more than onHand")
    void removeDamagedTooMuch() {
        assertThrows(IllegalStateException.class, () -> stock.removeDamaged(999));
    }

    @Test
    @Timeout(1)
    @DisplayName("removeDamaged shouldnt break reserved > onHand rule")
    void removeDamagedAdjustsReservedIfNeeded() {
        stock.reserve(5); 
        stock.removeDamaged(9); 
        assertTrue(stock.getReserved() <= stock.getOnHand());
    }
}

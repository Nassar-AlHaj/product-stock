package app;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Reserve, Release & Ship Tests")
class ProductStockReservationTest {

    private ProductStock stock;

    @BeforeEach
    void setup() {
        stock = new ProductStock("P10", "S1", 20, 5, 100);
    }

    @Nested
    @DisplayName("Reserve Tests")
    class ReserveTests {

        @Test
        @Tag("sanity")
        @DisplayName("reserve increases reserved correctly")
        void reserveNormal() {
            stock.reserve(5);
            assertEquals(5, stock.getReserved());
            assertEquals(15, stock.getAvailable());
        }

        @Test
        @DisplayName("reserve with amount <= 0 should throw")
        void reserveInvalidAmount() {
            assertThrows(IllegalArgumentException.class, () -> stock.reserve(0));
            assertThrows(IllegalArgumentException.class, () -> stock.reserve(-4));
        }

        @Test
        @DisplayName("reserve cant exceed available stock")
        void reserveTooMuch() {
            assertThrows(IllegalStateException.class, () -> stock.reserve(999));
        }
    }

    @Nested
    @DisplayName("Release Reservation Tests")
    class ReleaseTests {

        @Test
        @Tag("regression")
        @DisplayName("releaseReservation reduces reserved correctly")
        void releaseNormal() {
            stock.reserve(6);
            stock.releaseReservation(4);
            assertEquals(2, stock.getReserved());
        }

        @Test
        @DisplayName("releaseReservation with invalid amount should throw")
        void releaseInvalid() {
            assertThrows(IllegalArgumentException.class, () -> stock.releaseReservation(0));
            assertThrows(IllegalArgumentException.class, () -> stock.releaseReservation(-3));
        }

        @Test
        @DisplayName("cant release more than reserved")
        void releaseTooMuch() {
            stock.reserve(3);
            assertThrows(IllegalStateException.class, () -> stock.releaseReservation(10));
        }
    }

    @Nested
    @DisplayName("ShipReserved Tests")
    class ShipTests {

        @Test
        @Tag("sanity")
        @DisplayName("shipReserved removes both reserved and onHand")
        void shipNormal() {
            stock.reserve(8);
            stock.shipReserved(5);

            assertEquals(3, stock.getReserved());
            assertEquals(15, stock.getOnHand());
        }

        @Test
        @DisplayName("shipReserved with amount <= 0 should throw")
        void shipInvalidAmount() {
            assertThrows(IllegalArgumentException.class, () -> stock.shipReserved(0));
            assertThrows(IllegalArgumentException.class, () -> stock.shipReserved(-2));
        }

        @Test
        @DisplayName("cannot ship more than reserved")
        void shipMoreThanReserved() {
            stock.reserve(4);
            assertThrows(IllegalStateException.class, () -> stock.shipReserved(10));
        }

        @Test
        @DisplayName("shipReserved throws when amount exceeds reserved")
        void shipWithInsufficientOnHand() {
            stock.reserve(15);
            assertThrows(IllegalStateException.class, () -> stock.shipReserved(20));
        }
    }

    @Disabled("Feature not implemented yet")
    @Test
    @DisplayName("Future feature: auto-reserve on low stock")
    void futureFeatureAutoReserve() {
        fail("Not implemented yet");
    }
}

package app;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Constructor & Getter Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductStockConstructorTest {
 

    @BeforeAll
    static void beforeAll() {
        System.out.println(">>> STARTING ProductStock Constructor Tests");
    }


    @AfterEach
    void afterEach() {
        System.out.println("Finished one constructor test.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println(">>> FINISHED ALL constructor tests.");
    }


    @Test
    @Order(1)
    @DisplayName("Valid constructor creates correct initial state")
    void validConstructorCreatesCorrectObject() {

        ProductStock ps = new ProductStock("P123", "WH-1-A3", 20, 10, 100);

        assertAll("Constructor fields",
                () -> assertEquals("P123", ps.getProductId()),
                () -> assertEquals("WH-1-A3", ps.getLocation()),
                () -> assertEquals(20, ps.getOnHand()),
                () -> assertEquals(0, ps.getReserved()),
                () -> assertEquals(20, ps.getAvailable()),
                () -> assertEquals(10, ps.getReorderThreshold()),
                () -> assertEquals(100, ps.getMaxCapacity()),
                () -> assertFalse(ps.isReorderNeeded())
        );
    }

    @Nested
    @DisplayName("Constructor validation tests")
    class InvalidConstructorCases {

        @Test
        @DisplayName("Null productId should throw IllegalArgumentException")
        void nullProductIdThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock(null, "L1", 0, 0, 10));
        }

        @Test
        @DisplayName("Blank productId should throw IllegalArgumentException")
        void blankProductIdThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("   ", "L1", 0, 0, 10));
        }

        @Test
        @DisplayName("Null location should throw IllegalArgumentException")
        void nullLocationThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("P1", null, 0, 0, 10));
        }

        @Test
        @DisplayName("Blank location should throw IllegalArgumentException")
        void blankLocationThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("P1", "   ", 0, 0, 10));
        }

        @Test
        @DisplayName("Negative initialOnHand should throw")
        void negativeInitialOnHand() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("P1", "L1", -1, 0, 10));
        }

        @Test
        @DisplayName("Negative reorderThreshold should throw")
        void negativeThreshold() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("P1", "L1", 0, -5, 10));
        }

        @Test
        @DisplayName("maxCapacity <= 0 should throw")
        void zeroOrNegativeCapacity() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("P1", "L1", 0, 0, 0));
        }

        @Test
        @DisplayName("initialOnHand > maxCapacity should throw")
        void onHandExceedsCapacity() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ProductStock("P1", "L1", 100, 0, 10));
        }
    }
}

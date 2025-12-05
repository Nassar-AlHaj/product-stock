package app;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ProductStockConstructorTest.class,
        ProductStockAddAndRemoveTest.class,
        ProductStockReservationTest.class,
        ProductStockUpdateRulesTest.class
})

//@IncludeTags("sanity")   
@IncludeTags("regression")     

public class ProductStockSuite {
}

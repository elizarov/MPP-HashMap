package lock_free_hashtable;

import com.devexperts.dxlab.lincheck.LinChecker;
import com.devexperts.dxlab.lincheck.annotations.Operation;
import com.devexperts.dxlab.lincheck.annotations.Param;
import com.devexperts.dxlab.lincheck.annotations.Reset;
import com.devexperts.dxlab.lincheck.paramgen.IntGen;
import com.devexperts.dxlab.lincheck.stress.StressCTest;
import com.devexperts.dxlab.lincheck.verifier.LongExLinearizabilityVerifier;
import org.junit.Test;

@StressCTest
@StressCTest(actorsPerThread = {"20:30", "20:30"}, verifier = LongExLinearizabilityVerifier.class)
@Param(name = "key", gen = IntGen.class, conf = "1:6")
@Param(name = "value", gen = IntGen.class, conf = "1:100")
public class IntIntHashMapConcurrentTest {

    private IntIntHashMap map;

    @Reset
    public void reload() {
        map = new IntIntHashMap();
    }

    @Operation(params = {"key", "value"})
    public Integer put(Integer key, Integer value) {
        return map.put(key, value);
    }

    @Operation(params = "key")
    public Integer remove(Integer key) {
        return map.remove(key);
    }

    @Operation(params = "key")
    public Integer get(Integer key) {
        return map.get(key);
    }

    @Test
    public void test() throws Exception {
        LinChecker.check(IntIntHashMapConcurrentTest.class);
    }
}

package group5.swp391.onlinelearning.utils;

import java.util.List;
import java.util.function.Consumer;
import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {
    public static <T> void assertListFields(List<T> expectedList, List<T> actualList, Consumer<T> fieldAsserter) {
        assertThat(actualList).hasSameSizeAs(expectedList);
        for (int i = 0; i < expectedList.size(); i++) {
            fieldAsserter.accept(actualList.get(i));
        }
    }
}

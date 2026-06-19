
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    void shouldReturnAFor93Points() {
        Rating rating = new Rating();
        assertEquals("A", rating.determineResultGrade(93));
    }

    @Test
    void shouldReturnAFor100Points() {
        Rating rating = new Rating();
        assertEquals("A", rating.determineResultGrade(100));
    }

    @Test
    void shouldReturnBFor85Points() {
        Rating rating = new Rating();
        assertEquals("B", rating.determineResultGrade(85));
    }

    @Test
    void shouldReturnBFor92Points() {
        Rating rating = new Rating();
        assertEquals("B", rating.determineResultGrade(92));
    }

    @Test
    void shouldReturnEFor61Points() {
        Rating rating = new Rating();
        assertEquals("E", rating.determineResultGrade(61));
    }

    @Test
    void shouldReturnFXFor60Points() {
        Rating rating = new Rating();
        assertEquals("FX", rating.determineResultGrade(60));
    }

    @Test
    void shouldReturnFXFor0Points() {
        Rating rating = new Rating();
        assertEquals("FX", rating.determineResultGrade(0));
    }

    @Test
    void shouldThrowExceptionForNegativePoints() {
        Rating rating = new Rating();
        assertThrows(IllegalArgumentException.class, () -> {
            rating.determineResultGrade(-1);
        });
    }

    @Test
    void shouldThrowExceptionForMoreThan100Points() {
        Rating rating = new Rating();
        assertThrows(IllegalArgumentException.class, () -> {
            rating.determineResultGrade(101);
        });
    }
}
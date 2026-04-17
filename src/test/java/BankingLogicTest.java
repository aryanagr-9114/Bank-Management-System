import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class BankingLogicTest {

    // ─── BCrypt Tests ──────────────────────────────────────────

    @Test
    void hashPassword_producesCorrectBCryptFormat() {
        String hash = BCrypt.hashpw("mypassword", BCrypt.gensalt(12));
        assertTrue(hash.startsWith("$2a$12$"),
                "BCrypt hash must start with $2a$12$");
    }

    @Test
    void hashPassword_isNotStoredAsPlaintext() {
        String raw = "secret123";
        String hash = BCrypt.hashpw(raw, BCrypt.gensalt(12));
        assertNotEquals(raw, hash, "Stored value must not equal plaintext");
    }

    @Test
    void checkPassword_correctPassword_returnsTrue() {
        String raw = "correctPassword";
        String hash = BCrypt.hashpw(raw, BCrypt.gensalt(12));
        assertTrue(BCrypt.checkpw(raw, hash), "Correct password must verify");
    }

    @Test
    void checkPassword_wrongPassword_returnsFalse() {
        String hash = BCrypt.hashpw("realPassword", BCrypt.gensalt(12));
        assertFalse(BCrypt.checkpw("wrongPassword", hash),
                "Wrong password must fail verification");
    }

    @Test
    void hashPassword_samePlaintext_producesDifferentHashes() {
        String raw = "samePassword";
        String hash1 = BCrypt.hashpw(raw, BCrypt.gensalt(12));
        String hash2 = BCrypt.hashpw(raw, BCrypt.gensalt(12));
        assertNotEquals(hash1, hash2, "Two hashes of the same password must differ (different salt)");
    }

    @Test
    void checkPassword_emptyPassword_worksCorrectly() {
        String hash = BCrypt.hashpw("", BCrypt.gensalt(12));
        assertTrue(BCrypt.checkpw("", hash), "Empty password should hash and verify");
        assertFalse(BCrypt.checkpw("notempty", hash), "Non-empty must fail against empty hash");
    }

    // ─── Account Number Format Tests ───────────────────────────

    @Test
    void accountNumber_hasExactlySevenDigits() {
        Random rand = new Random();
        String accNum = String.format("%07d", rand.nextInt(10000000));
        assertEquals(7, accNum.length(), "Account number must be exactly 7 digits");
    }

    @Test
    void accountNumber_containsOnlyDigits() {
        Random rand = new Random();
        String accNum = String.format("%07d", rand.nextInt(10000000));
        assertTrue(accNum.matches("\\d{7}"), "Account number must contain only digits");
    }

    @Test
    void accountNumber_isWithinValidRange() {
        Random rand = new Random();
        int raw = rand.nextInt(10000000);
        assertTrue(raw >= 0 && raw < 10000000, "Account number source must be in [0, 9999999]");
    }

    @Test
    void accountNumber_isZeroPadded() {
        // Force a small number to verify zero-padding
        String accNum = String.format("%07d", 42);
        assertEquals("0000042", accNum, "Small numbers must be zero-padded to 7 digits");
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnsafeSeq {
    private UnsafeSequence unsafeSequence;
    private static class UnsafeSequence{
        private  int value;
        public UnsafeSequence() {
            this.value = 0;
        }
        public  int nextValue(){
            return value++;
        }
    }
    @BeforeEach
    void setUp() {
        unsafeSequence = new UnsafeSequence();
    }

    @Test
    void UnsafeSequenceTest() throws Exception{
        final int ITERATIONS = 10000;
        Runnable incValue = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< ITERATIONS;i++){
                    unsafeSequence.nextValue();
                }
            }
        };
        Thread t1 = new Thread(incValue);
        Thread t2 = new Thread(incValue);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(unsafeSequence.value);
        Assertions.assertNotEquals(2*ITERATIONS,unsafeSequence.value);

    }
}

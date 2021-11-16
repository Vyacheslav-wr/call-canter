package by.salei.center;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenter {

    private List<Operator> operators;
    private Semaphore semaphore;
    private  final ReentrantLock lock = new ReentrantLock();

    public CallCenter(List<Operator> operators) {
        this.operators = operators;
        this.semaphore = new Semaphore(operators.size());
        operators.forEach(operator -> {
            operator.setSemaphore(semaphore);
            operator.setCallCenter(this);
        });
    }

    private Operator connect(Client client) {
        lock.lock();
        Operator operator = operators
                .stream()
                .filter(operator1 -> !operator1.getBusyness())
                .findFirst()
                .get();
        operator.setClient(client);
        operator.isBusy();
        lock.unlock();
        return operator;
    }

    public Operator makeACall(Client client) throws InterruptedException{
        if(!semaphore.tryAcquire(1000,TimeUnit.MILLISECONDS)){
            return null;
        }
        return connect(client);
    }
}

package by.salei.center;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenter {

    List<Operator> operators;
    List<Client> awaitQueue = new ArrayList<>();
    Semaphore semaphore;
    ReentrantLock lock = new ReentrantLock();

    public CallCenter(List<Operator> operators) {
        this.operators = operators;
        this.semaphore = new Semaphore(operators.size());
        operators.forEach(operator -> operator.setSemaphore(semaphore));
    }

    private Operator getOperator(Client client) {
        Operator operator = operators
                .stream()
                .filter(operator1 -> !operator1.getBusyness())
                .findFirst()
                .get();

        operator.isBusy();
        operator.setClient(client);
        awaitQueue.remove(client);
        return operator;
    }

    public Operator makeACall(Client client) throws InterruptedException{
        if(!semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)){
            return  null;
        }
        lock.lock();
        Operator operator = getOperator(client);
        lock.unlock();
        return operator;
    }
}

package by.salei.center;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Operator extends Thread{

    private Exchanger<Client> exchanger;
    private Client client;
    private Semaphore semaphore;
    private Integer number;
    private boolean busy;
    private Logger logger = LoggerFactory.getLogger(Client.class);
    private CallCenter callCenter;

    public Operator(){

    }

    public Operator(Integer number, Exchanger<Client> exchanger){
        this.exchanger = exchanger;
        this.number = number;
    }

    @Override
    public void run() {
        try{
            while(true){
                sleep(100);
                if(this.client == null){
                    break;
                }
                logger.info("Operator {} take a call of client {}", number, client.getNumber());
                if(Math.random()*100 > 40){
                    tryToExchange();
                }
                solveClientProblem();
                logger.info("Operator {} solved a problem(Type {}) of client {}", number, client.getProblemType() ,client.getNumber());
                busy = false;
                this.client=null;
                semaphore.release();
            }
        }
        catch (InterruptedException ex){
            interrupt();
            logger.error("Operator {} resigned", number);
        }
    }

    private void solveClientProblem() throws InterruptedException {
        switch (client.getProblemType()) {
            case 1 -> {
                sleep(1000);
            }
            case 2 -> {
                sleep(2000);
            }
            case 3 -> {
                sleep(3000);
            }
            case 4 -> {
                sleep(4000);
            }
            default -> sleep(100);
        }
    }

    private void tryToExchange() throws InterruptedException {
        try{
        logger.info("Operator {} is stupid and wants to swap customers", number);
        client = exchanger.exchange(client, 100, TimeUnit.MILLISECONDS);
            logger.info("Operator {} swapped customer", number);
        }
        catch (TimeoutException ex){
            logger.info("Operator {} failed to swap customers", number);
        }
    }

    public void setSemaphore(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public boolean getBusyness() {
        return busy;
    }

    public void isBusy(){
        busy = true;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCallCenter(CallCenter callCenter) {
        this.callCenter = callCenter;
    }
}

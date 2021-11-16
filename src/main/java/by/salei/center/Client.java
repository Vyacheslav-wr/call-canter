package by.salei.center;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends Thread{

    private Integer patience;
    private Integer number;
    private CallCenter callCenter;
    private Integer problemType;
    private Logger logger = LoggerFactory.getLogger(Client.class);

    public Client() {
    }

    public Client(Integer number, CallCenter callCenter) {
        this.number = number;
        this.callCenter = callCenter;
        this.patience = (int)(1+Math.random()*5);
        this.problemType = (int)(1+Math.random()*3);
    }

    public Integer getProblemType() {
        return problemType;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (patience != 0) {
                    logger.info("Client {} make a call", number);
                    Operator operator = callCenter.makeACall(this);
                    if (operator == null) {
                        patience--;
                    } else {
                        break;
                    }
                }
                else{
                    logger.info("Client {} get angry and stop trying", number);
                    break;
                }
            }
        }
        catch (InterruptedException ex){
            interrupt();
            logger.error("Client {} magically disappeared", number);
        }
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getPatience() {
        return patience;
    }
}

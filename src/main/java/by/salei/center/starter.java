package by.salei.center;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class starter {
    public static void main(String[] args) {
        Exchanger<Client> exchanger = new Exchanger<>();
        List<Operator> operators = getOperators(exchanger);
        CallCenter callCenter = new CallCenter(operators);
        List<Client> clients = createClients(callCenter);
        clients.forEach(Thread::start);
    }

    private static List<Operator> getOperators(Exchanger<Client> exchanger) {
        List<Operator> operators = new ArrayList<>();
        Operator operator = new Operator(1, exchanger);
        Operator operator1 = new Operator(2, exchanger);
        operators.add(operator);
        operators.add(operator1);
        return operators;
    }

    private static List<Client> createClients(CallCenter callCenter) {
        List<Client> clients = new ArrayList<>();
        Client client1 = new Client(1, callCenter);
        Client client2 = new Client(2, callCenter);
        Client client3 = new Client(3, callCenter);
        Client client4 = new Client(4, callCenter);
        Client client5 = new Client(5, callCenter);
        Client client6 = new Client(6, callCenter);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clients.add(client6);
        return  clients;
    }
}

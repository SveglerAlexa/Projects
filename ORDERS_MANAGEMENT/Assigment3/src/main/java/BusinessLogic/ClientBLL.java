package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;

import java.util.List;


/**
 * Business logic layer for managing clients.
 * Provides methods to insert, update, delete, and retrieve client data.
 */
public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        this.clientDAO = new ClientDAO();
    }
    /**
     * Inserts a new client after validating its data.
     * @param client the Client object to insert
     */

    public void insert(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty.");
        }

        if (client.getAge() <= 0) {
            throw new IllegalArgumentException("Client age must be positive.");
        }

        clientDAO.insert(client);
    }

    public void update(Client client) {
        clientDAO.update(client);
    }

    public void delete(Client client) {
        clientDAO.delete(client);
    }

    public Client findById(int id) {
        return clientDAO.findById(id);
    }

    public List<Client> findAll() {
        return clientDAO.findAll();
    }
}



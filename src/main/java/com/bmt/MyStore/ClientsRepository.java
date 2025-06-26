package com.bmt.MyStore;


import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.sql.ast.Clause.VALUES;


@Repository
public class ClientsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Client> getClients() {
        var clients = new ArrayList<Client>();


        String sql = "SELECT * FROM clients ORDER BY id DESC";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        while (rows.next()) {
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            clients.add(client);
        }

        return clients;
    }

    public Client getClient(int id) {
        String sql = "SELECT * FROM clients WHERE id=?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);

        if (rows.next()) {
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            return client;
        }
        return null;
    }

    public Client getClient(String email) {
        String sql = "SELECT * FROM clients WHERE email=?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);

        if (rows.next()) {
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            return client;
        }
        return null;
    }

    public Client createClient(Client client) {


        String sql = "INSERT INTO clients (firstname, lastname, email, phone, " +
                " address, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        int count = jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress()
                , client.getCreatedAt());

        if (count > 0) {
            int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            return getClient(id);
        }

        return null;
    }

    public Client updateClient(Client client) {
        String sql = "UPDATE clients SET firstname=?, lastname=?, email=?, " +
                "phone=?, address=?, created_at=?, WHERE id=?";

        int update = jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress()
                , client.getCreatedAt(), client.getId());

        return getClient(client.getId());
    }

    public void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
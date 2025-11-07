package org.example;

import java.sql.Timestamp;

public class Portfolio {
    private int id;
    private int userId;
    private String name;
    private Timestamp createdAt;

    public Portfolio(int id, int userId, String name, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public Timestamp getCreatedAt() { return createdAt; }
}


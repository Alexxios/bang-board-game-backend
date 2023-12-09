package database;

interface IDatabase {
    void connectToDatabase(String databaseName);

    void addToTable(String tableName);
}

// Raw SQL insert statement
String insertStatement =
        "INSERT INTO countries(country, create_date, created_by, last_updated_by)" +
        " VALUES('US', '2020-02-22 00:00:00', 'admin', 'admin')";

// Variable SQL insert statement
String countryName = "Canada";
String createDate = "2020-02-22 00:00:00";
String created_by = "admin";
String last_updated_by = "admin";

// Execute SQL statement
statement.execute(insertStatement);

// Confirm SQL execution
if(statement.getUpdateCount() > 0 ) {
    System.out.println(statement.getUpdateCount());
} else {
    System.out.println("No change");
}









DBQuery.setStatement(conn);
Statement statement = DBQuery.getStatement();

String selectStatement = "SELECT * FROM countries";
statement.execute(selectStatement);
ResultSet resultSet = statement.getResultSet();

while(resultSet.next()) {
        int countryID = resultSet.getInt("Country_ID");
        String countryName = resultSet.getString("Country");
        LocalDate dateCreated = resultSet.getDate("Create_Date").toLocalDate();
        LocalTime timeCreated = resultSet.getTime("Create_Date").toLocalTime();
        String createdBy = resultSet.getString("Created_By");
        LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();

        System.out.println(countryID + " | " + countryName + " | " + dateCreated + " | " + timeCreated + " | " + createdBy + " | " + lastUpdate);
}





while (resultSet.next()){
    int countryID=resultSet.getInt("Country_ID");
    String countryName=resultSet.getString("Country");
    LocalDate dateCreated=resultSet.getDate("Create_Date").toLocalDate();
    LocalTime timeCreated=resultSet.getTime("Create_Date").toLocalTime();
    String createdBy=resultSet.getString("Created_By");
    LocalDateTime lastUpdate=resultSet.getTimestamp("Last_Update").toLocalDateTime();

    System.out.println(countryID+" | "+countryName+" | "+dateCreated+" | "+timeCreated+" | "+createdBy+" | "+lastUpdate);
}




DBQuery.setPreparedStatement(conn);
Statement statement = DBQuery.getPreparedStatement();

Scanner input = new Scanner(System.in);
System.out.println("Enter a country: ");
String country = input.nextLine().replace("'","\\'");
String createDate = "2020-01-01 00:00:00";
String createdBy = "admin";
String lastUpdateBy = "admin";

String selectStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By)" + "VALUES(" +
"'" + country + "'," +
"'" + createDate + "'," +
"'" + createdBy + "'," +
"'" + lastUpdateBy + "')";


try {
statement.execute(selectStatement);
statement.getResultSet();
int count = statement.getUpdateCount();
System.out.println(count > 0 ? count : "No change.");
} catch (Exception e) {
e.printStackTrace();
System.out.println(e.getMessage());
}




String insertStatement = "INSERT INTO countries(Country, Create_Date, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?)";


DBQuery.setPreparedStatement(conn, insertStatement);
PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);

String countryName;
String createDate = "2020-11-03 04:20:00";
String lastUpdate = "2020-11-05";
String lastUpdatedBy = "admin";

Scanner input = new Scanner(System.in);
System.out.println("Enter country: ");
countryName = input.next();

preparedStatement.setString(1,countryName);
preparedStatement.setString(2,createDate);
preparedStatement.setString(3,lastUpdate);
preparedStatement.setString(4,lastUpdatedBy);

preparedStatement.execute();

if (preparedStatement.getUpdateCount() > 0) {
System.out.println(preparedStatement.getUpdateCount() + " row(s) affected.");
}

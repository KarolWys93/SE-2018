import UCdatabase.MicroControllerEntity;
import UCdatabase.UCDatabaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UCModelMatcher {

    private Connection dbConnection;


    public UCModelMatcher(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<MicroControllerEntity> matchUCModel(MicroControllerModel ucModel) {
        ResultSet resultSet = null;
        String sqlStatement = "SELECT * FROM uc_database WHERE ";
        StringBuilder stringBuilder = new StringBuilder(sqlStatement);

        stringBuilder.append(prepareManufacturer(ucModel));

        try {
            String statementSQL = stringBuilder.toString();
            System.out.println(statementSQL);
            PreparedStatement statement = dbConnection.prepareStatement(statementSQL);
            statement.execute();
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet != null ? UCDatabaseDAO.rowsToObject(resultSet) : Collections.emptyList();
    }

    private String prepareManufacturer(MicroControllerModel ucModel) {
        StringBuilder sb = new StringBuilder();
        boolean firstCondition = true;

        if (ucModel.parametersFlags.get(MicroControllerModel.MANUFACTURER_STM)){
            sb.append("manufacturer is \'STM\'");
            firstCondition = false;
        }

        if (ucModel.parametersFlags.get(MicroControllerModel.MANUFACTURER_ATMEL)){
            if (!firstCondition) sb.append(" OR ");
            sb.append("manufacturer is \'ATMEL\'");
            firstCondition = false;
        }

        if (firstCondition){
            sb.append("0");
        }
        sb.append(" ");
        return sb.toString();
    }
}

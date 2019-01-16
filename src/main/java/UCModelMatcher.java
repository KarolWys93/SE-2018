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
        List<MicroControllerEntity> ucList = filterUCBase(ucModel);

        return ucList;
    }

    private List<MicroControllerEntity> filterUCBase(MicroControllerModel ucModel){
        ResultSet resultSet = null;
        String sqlStatement = "SELECT * FROM uc_database WHERE ";
        StringBuilder stringBuilder = new StringBuilder(sqlStatement);

        stringBuilder.append(prepareManufacturerFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(preparePackageFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareVoltageFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareInterfacesFilter(ucModel));

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

    private String prepareManufacturerFilter(MicroControllerModel ucModel) {
        StringBuilder sb = new StringBuilder("( ");
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
        sb.append(") ");
        return sb.toString();
    }

    private String preparePackageFilter(MicroControllerModel ucModel){
        int package_lvl = ucModel.parametersValues.get(MicroControllerModel.UC_PACKAGE);
        StringBuilder sb = new StringBuilder("( package_tht = 1");

        if (package_lvl >= MicroControllerModel.PACKAGE_SIMPLE_SMD){
            sb.append(" OR package_easy = 1");
        }

        if (package_lvl >= MicroControllerModel.PACKAGE_ADVANCED_SMD){
            sb.append(" OR package_hard = 1");
        }

        if (package_lvl >= MicroControllerModel.PACKAGE_BGA){
            sb.append(" OR package_bga = 1");
        }

        sb.append(") ");
        return sb.toString();
    }

    private String prepareVoltageFilter(MicroControllerModel ucModel){
        int optimalVoltage = ucModel.parametersValues.get(MicroControllerModel.OPTIMAL_VOLTAGE);
        int minimalVoltage = ucModel.parametersValues.get(MicroControllerModel.MINIMAL_VOLTAGE);
        if (minimalVoltage > optimalVoltage){
            minimalVoltage = optimalVoltage;
        }
        return String.format("( voltage_min <= %d AND voltage_max >= %d ) ", minimalVoltage, optimalVoltage);
    }

    private String prepareInterfacesFilter(MicroControllerModel ucModel){
        StringBuilder sb = new StringBuilder("( ");
        sb.append(String.format("UART >= %d AND SPI >= %d AND I2C >= %d AND CAN >= %d",
                ucModel.parametersValues.get(MicroControllerModel.UART_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.SPI_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.I2C_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.CAN_INTERFACES)
        ));

        if (ucModel.parametersFlags.get(MicroControllerModel.USB_INTERFACES)){
            sb.append(" AND USB >= 1");
        }

        sb.append(") ");
        return sb.toString();
    }

}

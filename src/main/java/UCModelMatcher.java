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

        ucList.sort((uc1, uc2) -> compareUC(uc1, uc2, ucModel));

        return ucList;
    }

    private int compareUC(MicroControllerEntity uc1, MicroControllerEntity uc2, MicroControllerModel ucModel) {
        int uc1Points = 0;
        int uc2Points = 0;

        //price
        if (uc1.getPrice() < uc2.getPrice()) {
            uc1Points += ucModel.parametersFlags.get(MicroControllerModel.SMALL_SERIES) ? 1 : 2;
        }
        if (uc1.getPrice() > uc2.getPrice()) {
            uc2Points += ucModel.parametersFlags.get(MicroControllerModel.SMALL_SERIES) ? 1 : 2;
        }

        //power consumption
        if (ucModel.parametersFlags.get(MicroControllerModel.POWER_SAVING)) {
            uc1Points += (uc1.getPower_consumption() < uc2.getPower_consumption()) ? 1 : 0;
            uc2Points += (uc1.getPower_consumption() > uc2.getPower_consumption()) ? 1 : 0;
        }

        //OS support
        if (ucModel.parametersFlags.get(MicroControllerModel.OS_SUPPORT)) {
            uc1Points += uc1.getManufacturer().equals("STM") ? 1 : 0;
            uc2Points += uc2.getManufacturer().equals("STM") ? 1 : 0;
        }

        //graphics features support
        if (ucModel.parametersFlags.get(MicroControllerModel.GRAPHICS_FEATURES)) {
            uc1Points += uc1.getGraphics_support() > 0 ? 1 : 0;
            uc2Points += uc2.getGraphics_support() > 0 ? 1 : 0;
        }

        //performance
        if (ucModel.parametersFlags.get(MicroControllerModel.HIGH_PERFORMANCE)) {
            uc1Points += (uc1.getCpu_speed() > uc2.getCpu_speed()) ? 1 : 0;
            uc2Points += (uc1.getCpu_speed() < uc2.getCpu_speed()) ? 1 : 0;
        }
        if (ucModel.parametersFlags.get(MicroControllerModel.FPU)) {
            uc1Points += uc1.getFPU() > 0 ? 1 : 0;
            uc2Points += uc2.getFPU() > 0 ? 1 : 0;
        }

        //IO
        if (ucModel.parametersFlags.get(MicroControllerModel.IO_EXPANDERS)) {
            if (uc1.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER)) {
                uc1Points += 2;
            } else if (uc1.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) * 0.5f) {
                uc1Points += 1;
            }
            if (uc2.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER)) {
                uc2Points += 2;
            } else if (uc2.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) * 0.5f) {
                uc2Points += 1;
            }
        } else {
            uc1Points += (uc1.getPin_count() < uc2.getPin_count()) ? 1 : 0;
            uc2Points += (uc1.getPin_count() > uc2.getPin_count()) ? 1 : 0;
        }

        //RAM
        if (ucModel.parametersFlags.get(MicroControllerModel.EXTERNAL_RAM)) {
            uc1Points += uc1.getExternal_ram_support() > 0 ? 1 : 0;
            uc2Points += uc2.getExternal_ram_support() > 0 ? 1 : 0;
        } else {
            uc1Points += (uc1.getSram_bytes() > uc2.getSram_bytes()) ? 1 : 0;
            uc2Points += (uc1.getSram_bytes() < uc2.getSram_bytes()) ? 1 : 0;
        }

        //DAC - using "handmade" DAC
        int numberOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_NUMBER);
        int resolutionOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_RESOLUTION);

        if (uc1.getDAC_output() >= numberOfDAC) {
            uc1Points += 2;
        } else if ((numberOfDAC - uc1.getDAC_output() == 1) && resolutionOfDAC <= 8) {
            if (uc1.getPin_count() > ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) + 10) {
                uc1Points += 1;
            }
        }
        if (uc2.getDAC_output() >= numberOfDAC) {
            uc2Points += 2;
        } else if ((numberOfDAC - uc2.getDAC_output() == 1) && resolutionOfDAC <= 8) {
            if (uc2.getPin_count() > ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) + 10) {
                uc2Points += 1;
            }
        }


        System.out.println(String.format("%s vs %s -> %d:%d",
                uc1.getProduct_name(),
                uc2.getProduct_name(),
                uc1Points,
                uc2Points
        ));

        return uc2Points - uc1Points;   //The first should be the one with more points.
    }

    private List<MicroControllerEntity> filterUCBase(MicroControllerModel ucModel) {
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
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareIOFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareADCFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareDACFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareFlashFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareSramFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareCountersFilter(ucModel));

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

        if (ucModel.parametersFlags.get(MicroControllerModel.MANUFACTURER_STM)) {
            sb.append("manufacturer is \'STM\'");
            firstCondition = false;
        }

        if (ucModel.parametersFlags.get(MicroControllerModel.MANUFACTURER_ATMEL)) {
            if (!firstCondition) sb.append(" OR ");
            sb.append("manufacturer is \'ATMEL\'");
            firstCondition = false;
        }

        if (firstCondition) {
            sb.append("0");
        }
        sb.append(") ");
        return sb.toString();
    }

    private String preparePackageFilter(MicroControllerModel ucModel) {
        int package_lvl = ucModel.parametersValues.get(MicroControllerModel.UC_PACKAGE);
        StringBuilder sb = new StringBuilder("( package_tht = 1");

        if (package_lvl >= MicroControllerModel.PACKAGE_SIMPLE_SMD) {
            sb.append(" OR package_easy = 1");
        }

        if (package_lvl >= MicroControllerModel.PACKAGE_ADVANCED_SMD) {
            sb.append(" OR package_hard = 1");
        }

        if (package_lvl >= MicroControllerModel.PACKAGE_BGA) {
            sb.append(" OR package_bga = 1");
        }

        sb.append(") ");
        return sb.toString();
    }

    private String prepareVoltageFilter(MicroControllerModel ucModel) {
        int optimalVoltage = ucModel.parametersValues.get(MicroControllerModel.OPTIMAL_VOLTAGE);
        int minimalVoltage = ucModel.parametersValues.get(MicroControllerModel.MINIMAL_VOLTAGE);
        if (minimalVoltage > optimalVoltage) {
            minimalVoltage = optimalVoltage;
        }
        return String.format("( voltage_min <= %d AND voltage_max >= %d ) ", minimalVoltage, optimalVoltage);
    }

    private String prepareInterfacesFilter(MicroControllerModel ucModel) {
        StringBuilder sb = new StringBuilder("( ");
        sb.append(String.format("UART >= %d AND SPI >= %d AND I2C >= %d AND CAN >= %d",
                ucModel.parametersValues.get(MicroControllerModel.UART_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.SPI_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.I2C_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.CAN_INTERFACES)
        ));

        if (ucModel.parametersFlags.get(MicroControllerModel.USB_INTERFACES)) {
            sb.append(" AND USB >= 1");
        }

        sb.append(") ");
        return sb.toString();
    }

    private String prepareIOFilter(MicroControllerModel ucModel) {
        String preparedSQL = null;
        int minimalIO = ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER);
        if (!ucModel.parametersFlags.get(MicroControllerModel.IO_EXPANDERS)) {
            preparedSQL = String.format("( pin_count >= %d ) ", minimalIO);
        } else {
            preparedSQL = "( pin_count >= 0 )";
        }
        return preparedSQL;
    }

    private String prepareADCFilter(MicroControllerModel ucModel) {
        int numberOfADC = ucModel.parametersValues.get(MicroControllerModel.ADC_NUMBER);
        int resolutionOfADC = ucModel.parametersValues.get(MicroControllerModel.ADC_RESOLUTION);
        return String.format(" ( ADC_input >= %d AND ADC_resolution >= %d ) ", numberOfADC, resolutionOfADC);
    }

    private String prepareDACFilter(MicroControllerModel ucModel) {
        int numberOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_NUMBER);
        int resolutionOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_RESOLUTION);
        String preparedSQL;

        if (numberOfDAC >= 1 && (resolutionOfDAC > 0)) {
            preparedSQL = String.format(" ( DAC_output >= %d AND DAC_resolution >= %d ) ", numberOfDAC, resolutionOfDAC);
        } else {
            preparedSQL = " ( DAC_output >= 0 ) ";
        }

        return preparedSQL;
    }

    private String prepareFlashFilter(MicroControllerModel ucModel) {
        return String.format(" ( flash_kb >= %d ) ", ucModel.parametersValues.get(MicroControllerModel.FLASH_SIZE));
    }

    private String prepareSramFilter(MicroControllerModel ucModel) {
        String preparedSQL;
        int ramUsage = ucModel.parametersValues.get(MicroControllerModel.RAM_SIZE);
        if (!ucModel.parametersFlags.get(MicroControllerModel.EXTERNAL_RAM)) {
            preparedSQL = String.format("( sram_bytes >= %d ) ", ramUsage);
        } else {
            preparedSQL = "( sram_bytes >= 0 )";
        }
        return preparedSQL;
    }

    private String prepareCountersFilter(MicroControllerModel ucModel) {
        return String.format(" ( counters >= %d ) ", ucModel.parametersValues.get(MicroControllerModel.COUNTERS));
    }

}

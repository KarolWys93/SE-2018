import java.util.HashMap;

public class MicroControllerModel {

    public final static int PACKAGE_THT = 1;
    public final static int PACKAGE_SIMPLE_SMD = 2;
    public final static int PACKAGE_ADVANCED_SMD = 4;
    public final static int PACKAGE_BGA = 8;

    public final static int MANUFACTURER_ATMEL = 1;
    public final static int MANUFACTURER_STM = 2;

    //values
    public final static String MANUFACTURER = "manufacturer";
    public final static String UC_PACKAGE = "package";
    public final static String FLASH_SIZE = "flash_size";
    public final static String RAM_SIZE = "ram_size";
    public final static String ADC_NUMBER = "number_of_adc";
    public final static String ADC_RESOLUTION = "adc_resolution";
    public final static String ADC_FREQ = "adc_freq";
    public final static String DAC_NUMBER = "number_of_dac";
    public final static String DAC_RESOLUTION = "dac_resolution";
    public final static String IO_PORTS_NUMBER = "IO_ports";
    public final static String OPTIMAL_VOLTAGE = "optimal_voltage";
    public final static String MINIMAL_VOLTAGE = "minimal_voltage";
    public final static String SPI_INTERFACES = "spi_interfaces";
    public final static String I2C_INTERFACES = "i2c_interfaces";
    public final static String UART_INTERFACES = "uart_interfaces";
    public final static String USB_INTERFACES = "usb_interfaces";
    public final static String CAN_INTERFACES = "can_interfaces";
    public final static String COUNTERS = "counters";

    //flags
    public final static String SMALL_SERIES = "small_series";
    public final static String EXTERNAL_FLASH = "external_flash";
    public final static String EXTERNAL_RAM = "external_ram";
    public final static String HIGH_PERFORMANCE = "high_performance";
    public final static String FPU = "fpu";
    public final static String IO_EXPANDERS = "IO_expanders";
    public final static String POWER_SAVING = "power_saving";
    public final static String GRAPHICS_FEATURES = "graphics_features";
    public final static String OS_SUPPORT = "os_support";
    public final static String HARD_ENV = "hard_env";


    public HashMap<String, Boolean> parametersFlags;
    public HashMap<String, Integer> parametersValues;

    public MicroControllerModel(){
        parametersFlags = new HashMap<>();
        parametersValues = new HashMap<>();

        //main
        parametersValues.put(MANUFACTURER,  0);
        parametersValues.put(UC_PACKAGE, PACKAGE_THT);
        parametersFlags.put(SMALL_SERIES, true);

        parametersValues.put(COUNTERS, 0);

        //performance
        parametersValues.put(RAM_SIZE, 0);
        parametersValues.put(FLASH_SIZE, 0);

        parametersFlags.put(EXTERNAL_FLASH, false);
        parametersFlags.put(EXTERNAL_RAM, false);
        parametersFlags.put(HIGH_PERFORMANCE, false);
        parametersFlags.put(FPU, false);

        //ADC
        parametersValues.put(ADC_NUMBER, 0);
        parametersValues.put(ADC_RESOLUTION, 0);
        parametersValues.put(ADC_FREQ, 0);

        //DAC
        parametersValues.put(DAC_NUMBER, 0);
        parametersValues.put(DAC_RESOLUTION, 0);


        //IO
        parametersValues.put(IO_PORTS_NUMBER, 0);
        parametersFlags.put(IO_EXPANDERS, false);

        //power
        parametersFlags.put(POWER_SAVING, false);
        parametersValues.put(MINIMAL_VOLTAGE, 0);
        parametersValues.put(OPTIMAL_VOLTAGE, 0);

        //interfaces
        parametersValues.put(SPI_INTERFACES, 0);
        parametersValues.put(I2C_INTERFACES, 0);
        parametersValues.put(UART_INTERFACES, 0);
        parametersValues.put(USB_INTERFACES, 0);
        parametersValues.put(CAN_INTERFACES, 0);

        //other
        parametersFlags.put(GRAPHICS_FEATURES, false);
        parametersFlags.put(OS_SUPPORT, false);

    }

}

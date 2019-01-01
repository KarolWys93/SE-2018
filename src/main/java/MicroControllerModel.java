import java.util.HashMap;

public class MicroControllerModel {

    public enum Importance {LOW, MEDIUM, HIGH}

    public enum Package { THT, EASY_SMD, HARD_SMD, BGA;}
    public final static int MANUFACTURER_ATMEL = 0x01;
    public final static int MANUFACTURER_STM = 0x02;

    //main
    public final static String MANUFACTURER_Importance = "manufacturer"; 
    public int manufacturer = 0;
    public Package ucPackage = Package.THT;

    //performance
    public final static String FLASH_SIZE_Importance = "flash_size";
    public final static String RAM_SIZE_Importance = "ram_size";
    
    public int ramMemory = 0;
    public int flashMemory = 0;

    public boolean externalFlash = false;
    public boolean externalRAM = false;


    public boolean highEfficiency =  false;
    public boolean fpu = false;

    //ADC
    public final static String ADC_Importance = "adc";
    public int amountOfAdc = 0;
    public int adcResolution = 0;
    public int adcFreq = 0;

    //DAC
    public final static String DAC_Importance = "dac";
    public int amountOfDac = 0;
    public int dacResolution = 0;
    public int dacFreq = 0;

    //IO
    public final static String IO_Number_Importance = "io";
    public int ioNumber = 0;

    //power
    public final static String POWER_SAVING_Importance = "power_saving";
    public boolean batteryPower = false;
    public float optimalPowerSupply = 0;
    public float minimalPowerSupply = 0;

    //interfaces
    public int spiNumber = 0;
    public int i2cNumber = 0;
    public int uartNumber = 0;
    public int usbNumber = 0;
    public int canNumber = 0;


    //other
    public boolean graphicsFeatures = false;
    public boolean osSupport = false;
    public boolean hardEnvironment = false;

    public HashMap<String, Importance> parametersImportance;
    
    
    
}

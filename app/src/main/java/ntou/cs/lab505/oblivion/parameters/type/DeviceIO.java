package ntou.cs.lab505.oblivion.parameters.type;

/**
 * Created by alan on 2015/5/7.
 */
public class DeviceIO {

    private int deviceIn;
    private int deviceOut;

    public DeviceIO() {
        this.deviceIn = 0;
        this.deviceOut = 0;
    }

    public DeviceIO(int deviceIn, int deviceOut) {
        this.deviceIn = deviceIn;
        this.deviceOut = deviceOut;
    }

    public void saveData(int deviceIn, int deviceOut) {
        this.deviceIn = deviceIn;
        this.deviceOut = deviceOut;
    }

    public int getDeviceIn() {
        return this.deviceIn;
    }

    public int getDeviceOut() {
        return this.deviceOut;
    }
}

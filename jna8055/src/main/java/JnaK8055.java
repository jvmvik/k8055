import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Simple JNA wrapper for k8055 drive
 * which enables to interact with the card.
 */
public class JnaK8055
{

  /*
   * Wrapper for libk8055 driver.
   *
   * Limitation:
   *  - Read all methods are not supported they are replace by read unique io
   */
  public interface K extends Library
  {
    public int OpenDevice(long board_address);
    public int CloseDevice();
    public long ReadAnalogChannel(long Channelno);
    public int OutputAnalogChannel(long channel, long data);

    public int OutputAllAnalog(long data1, long data2);

    public int ClearAllAnalog();

    public int ClearAnalogChannel(long channel);

    public int SetAnalogChannel(long channel);

    public int SetAllAnalog();

    public int WriteAllDigital(long data);

    public int ClearDigitalChannel(long channel);

    public int ClearAllDigital();

    public int SetDigitalChannel(long channel);

    public int SetAllDigital();

    public int ReadDigitalChannel(long channel);

    public int ResetCounter(long counternr);

    public long ReadCounter(long counterno);

    public int SetCounterDebounceTime(long counterno, long debouncetime);

    public int SetAllValues(int digitaldata, int addata1, int addata2);

    public long SetCurrentDevice(long deviceno);

    public long SearchDevices();
  }

    /***
     * Create JNA connector
     * @return interface to k8055 driver
     */
  public static K create()
  {
      K k = (K) Native.loadLibrary("k8055", K.class);
      return k;
  }
}

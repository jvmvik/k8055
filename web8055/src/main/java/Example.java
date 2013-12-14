import com.sun.jna.Native;

/**
 * @author jvmvik
 */
public class Example
{

    public static void main(String[] args) throws InterruptedException
    {
        JnaK8055.K k = JnaK8055.create();

        if (k.OpenDevice((long) 0) > -1)
        {
            long l = 0;

            worm(k);
              /*while (l < 16)
              {
                l = ReadAllDigital(k);
                Thread.sleep(50L);
              }*/
            System.out.println("> ok: " + l);
            k.CloseDevice();

        }
        else
        {
            System.out.println("Device is not found");
        }
    }

    private static void worm(JnaK8055.K k) throws InterruptedException
    {
        for(int i = 1; i < 10; i++)
        {
            k.ClearAllDigital();
            Thread.sleep(200L);

            System.out.print(">>");
            for(int j = 1; j < 9; j++)
            {
                System.out.print(" " + j);
                if(j -1 > 0)
                    k.ClearDigitalChannel(j-1);
                k.SetDigitalChannel(j);
                Thread.sleep(100L);
            }

            //k.ClearAllDigital();
            System.out.println();
        }
        k.ClearAllDigital();
    }

    public static long ReadAllDigital(JnaK8055.K k)
    {
        long r = 0;
        for (int i = 1; i < 6; i++)
        {
            if (1 == k.ReadDigitalChannel(i))
            {
                r += Math.pow(2, i);
                k.SetDigitalChannel(i);
            } else
            {
                k.ClearDigitalChannel(i);
            }
        }
        return r;
    }
}

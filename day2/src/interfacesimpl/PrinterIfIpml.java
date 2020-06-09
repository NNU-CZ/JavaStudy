package interfacesimpl;

/**
 * @author cz
 */
public class PrinterIfIpml implements interfaces.PrinterIf {
    @Override
    public void print() {
        System.out.println("重写了打印机接口的打印方法");
    }
}

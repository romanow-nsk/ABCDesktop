package romanow.abc.desktop;

import romanow.abc.core.UniException;

public interface I_Calculator {
    public  String getTitle();
    public boolean isMinFormulaValid();
    public String getMinValue();
    public boolean isMaxFormulaValid();
    public  String getMaxValue();
    public String getStartValue();
}

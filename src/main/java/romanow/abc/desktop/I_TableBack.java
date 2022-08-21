package romanow.abc.desktop;

public interface I_TableBack {
    public void rowSelected(int row);
    public void colSelected(int col);
    public void cellSelected(int row, int col);
    public void onClose();
}

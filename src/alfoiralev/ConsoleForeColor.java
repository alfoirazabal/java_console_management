package alfoiralev;

/**
 *
 * @author Alfonso
 */
public enum ConsoleForeColor {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m");
    
    public final String Code;
    
    private ConsoleForeColor(String colCode) {
        this.Code = colCode;
    }
}
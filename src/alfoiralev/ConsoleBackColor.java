/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alfoiralev;

/**
 *
 * @author Alfonso
 */
public enum ConsoleBackColor {
    BLACK("\u001B[40m"),
    RED("\u001B[41m"),
    GREEN("\u001B[42m"),
    YELLOW("\u001B[43m"),
    BLUE("\u001B[44m"),
    PURPLE("\u001B[45m"),
    CYAN("\u001B[46"),
    WHITE("\u001B[47m"),
    RESET("\u001B[0m");
    
    public final String Code;
    
    private ConsoleBackColor(String colCode) {
        this.Code = colCode;
    }
}

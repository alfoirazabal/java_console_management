package alfoiralev;
        
public enum ElementColorType {
    BACKOPTION('b'),
    DESCRIPTION('d'),
    INPUTLISTEN('i'),
    TITLE('t'),
    INVALIDINPUT('n');

    public final char type;

    private ElementColorType(char type) {
        this.type = type;
    }
}
package pl.todrzywolek.design.principles;

class Document {

}

interface Machine {
    void print(Document d);

    void fax(Document d);

    void scan(Document d);
}


// Machine interface is OK when you have a client with machine that have all of this functions
class MultifunctionPrinter implements Machine {

    @Override
    public void print(Document d) {

    }

    @Override
    public void fax(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

class OldFashionedPrinter implements Machine {

    @Override
    public void print(Document d) {
        // ok can be implemented
    }

    @Override
    public void fax(Document d) {
/*
        issue here - old fashioned printer does not have fax
        option 1 - leave the method empty, problem:
        user may still use this method any do not know why fax is not working,
        option 2 - throw exception, problem you have to add exception specification to method and interface
        (but you might not own interface)
*/
    }

    @Override
    public void scan(Document d) {
//        issue here - old fashioned printer does not have scan
    }
}

// Solution: Interface segregation - split to categories

interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

interface Fax {
    void fax(Document d);
}

class JustAPrinter implements Printer {

    @Override
    public void print(Document d) {
        // implementation
    }
}

class Photocopier implements Printer, Scanner {

    @Override
    public void print(Document d) {
        //
    }

    @Override
    public void scan(Document d) {
        //
    }
}

// another solution: new interface that extends other interfaces

interface MultiFunctionDevice extends Printer, Scanner {
}

class MultiFunctionMachine implements MultiFunctionDevice {

    // decorator pattern - reuse functionality that you already have
    private Printer printer;
    private Scanner scanner;

    public MultiFunctionMachine(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document d) {
        printer.print(d);
    }

    @Override
    public void scan(Document d) {
        scanner.scan(d);
    }
}
package idealindustrial.impl.autogen.material.submaterial.chem.parser;

import java.util.Scanner;

public class Tests {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(FormulaParser.INSTANCE
                    .parse(new StringSource(sc.next()), true)
                    .asArgumentString()
            );
        }
    }
}

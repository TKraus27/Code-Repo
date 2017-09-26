package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*List<Instruction> blah = new LinkedList<Instruction>();
        Instruction addiu = new Instruction();
        blah.add(addiu);
        System.out.println(blah.toString());
        List newblah = new LinkedList(Phase1.mal_to_tal(blah));
        System.out.println(newblah.toString());
        */

        List<Instruction> input = new LinkedList<Instruction>();
            // label1: addu $t0, $zero, $zero
            input.add(InstructionFactory.CreateAddu(8, 0, 0, "label1"));
            // addu $s0, $s7, $t4
            input.add(InstructionFactory.CreateAddu(16, 23, 12));
            // blt  $s0,$t0,label1
            input.add(InstructionFactory.CreateBlt(16, 8, "label1"));
            // addiu $s1,$s2,0xF00000
            input.add(InstructionFactory.CreateAddiu(17, 18, 0xF00000));

        List<Instruction> tals = Phase1.mal_to_tal(input);
        System.out.println(tals.size());
        for(int i = 0; i < tals.size(); i++){
            System.out.println(tals.get(i).toString());

        }
    }
}

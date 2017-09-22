package edu.uiowa.cs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Phase2 {

    /* Returns a list of copies of the Instructions with the
     * immediate field of the instruction filled in
     * with the address calculated from the branch_label.
     *
     * The instruction should not be changed if it is not a branch instruction.
     *
     * unresolved: list of instructions without resolved addresses
     * first_pc: address where the first instruction will eventually be placed in memory
     */
    public static List<Instruction> resolve_addresses(List<Instruction> unresolved, int first_pc) {
        //gets the list to iterate through does phase1
        List<Instruction> talList = Phase1.mal_to_tal(unresolved);
        //makes a new list to return
        List<Instruction> returnList = new LinkedList<Instruction>();

        for(int i = 0; i < talList.size(); i++) {
          boolean bne = talList.get(i).instruction_id.equals(Instruction.ID.bne);
          boolean beq = talList.get(i).instruction_id.equals(Instruction.ID.beq);
          if(bne || beq) {
            Instruction.ID id = bne ? Instruction.ID.bne : Instruction.ID.beq;
            Instruction branchi = talList.get(i);
            int instruction_count = 0;
            for(int j = 0; j < talList.size(); j++) {
              if(talList.get(j).label.equals(branchi.branch_label)) {
                if(j < i) {
                  instruction_count = j-i-1;
                  returnList.add(new Instruction(id, talList.get(i).rs, talList.get(i).rt, instruction_count));
                }else if (j > 1) {
                  instruction_count = Math.abs(i-j)-1;
                  returnList.add(new Instruction(id, talList.get(i).rs, talList.get(i).rt, instruction_count));
                }else {
                  returnList.add(new Instruction(id, talList.get(i).rs, talList.get(i).rt, instruction_count));
                }
              }
            }
          }else {
            returnList.add(talList.get(i));
          }
        }
        return returnList;
    }

}
